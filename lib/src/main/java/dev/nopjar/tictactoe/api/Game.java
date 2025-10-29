package dev.nopjar.tictactoe.api;

import dev.nopjar.tictactoe.domain.service.GameRule;
import dev.nopjar.tictactoe.domain.service.GameRules;
import dev.nopjar.tictactoe.domain.service.GameStateService;
import dev.nopjar.tictactoe.domain.state.FinishedGameState;
import dev.nopjar.tictactoe.domain.state.InProgressGameState;
import dev.nopjar.tictactoe.domain.state.PreparationGameState;
import dev.nopjar.tictactoe.player.Player;
import dev.nopjar.tictactoe.util.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Represents a game of TicTacToe.
 *
 * <p>This is the main entry point for the library.
 *
 * <p>Here you can register players, renderers, rules, etc. and start the game.
 */
public class Game {

  private final List<Move> history = new LinkedList<>();
  @Getter
  private final Board board;
  @Getter
  private final GameStateService stateService;
  private final GameRules rules;
  @Getter(value = AccessLevel.PACKAGE, onMethod_ = {@TestOnly})
  private final List<Player> players;
  @Getter(value = AccessLevel.PACKAGE, onMethod_ = {@TestOnly})
  private Renderer renderer;

  /**
   * Creates a new game with default settings.
   */
  public Game() {
    this(GameRules.defaults());
  }

  /**
   * Creates a new game with the given rules.
   *
   * @param rules the rules to use for this game
   */
  public Game(GameRules rules) {
    this(rules, new ArrayList<>(2), new GameStateService(), new Board(rules.get(GameRule.BOARD_SIZE)));
  }

  @Builder
  @TestOnly
  Game(GameRules rules, List<Player> players, GameStateService stateService, Board board) {
    this.rules = rules;
    this.players = players;
    this.stateService = stateService;
    this.board = board;
  }

  /**
   * Registers a renderer for this game.
   *
   * @param renderer the renderer to register
   * @throws IllegalStateException if a renderer is already registered
   */
  public void registerRenderer(Renderer renderer) {
    if (this.renderer != null) {
      throw new IllegalStateException("Renderer already registered.");
    }
    this.renderer = renderer;
  }

  /**
   * Registers a player for this game.
   *
   * @param player the player to register
   * @throws IllegalArgumentException if the player type is already registered
   */
  public void registerPlayer(Player player) {
    boolean collidingPlayerTypes = this.players.stream()
        .anyMatch(p -> p.getType() == player.getType());
    if (collidingPlayerTypes) {
      // This also contains a check that only two players can be registered.
      throw new IllegalArgumentException("Player type already registered.");
    }
    this.players.add(player);
  }

  /**
   * Starts the game.
   *
   * <p>This will switch the game state to {@link PreparationGameState} and start the game loop.
   * The game loop will terminate when the game is over, so this method is blocking.
   *
   * @throws IllegalArgumentException if not exactly two players are registered
   */
  @Blocking
  public void start() {
    // Validate that we have two players.
    if (players.size() != 2) {
      throw new IllegalArgumentException("Game requires exactly two players.");
    }

    this.stateService.switchState(new PreparationGameState(this));

    startGameLoop();
  }

  private void startGameLoop() {
    while (!stateService.isState(FinishedGameState.class)) {
      stateService.tick();
      if (renderer != null) {
        renderer.render(this);
      }
    }
  }

  /**
   * Plays a move in this game.
   *
   * @param move the move to play
   * @return true if the move was valid and applied, false otherwise
   * @throws IllegalStateException if the game is not running
   */
  public boolean playMove(Move move) {
    if (!this.stateService.isState(InProgressGameState.class)) {
      throw new IllegalStateException("Cannot play move if game is not running!");
    }

    if (this.board.apply(move)) {
      this.history.add(move);
      return true;
    }
    return false;
  }

  /**
   * Returns the next player to play.
   *
   * <p>If the current player is null, the first player will be returned, which is either a random player or the first player registered.
   * This behavior can be configured via {@link GameRules}.
   *
   * @param currentPlayer the current player, or null if there is no current player
   * @return the next player to play
   * @see GameRule#RANDOM_FIRST_PLAYER
   */
  public Player getNextPlayer(@Nullable Player currentPlayer) {
    if (currentPlayer == null) {
      return rules.get(GameRule.RANDOM_FIRST_PLAYER)
          ? RandomUtils.getRandomElement(this.players)
          : this.players.getFirst();
    }
    int index = this.players.indexOf(currentPlayer);
    if (index == -1) {
      throw new IllegalArgumentException("Player is not part of this game.");
    }
    return this.players.get((index + 1) % this.players.size());
  }

  /**
   * Returns an unmodifiable view of game history.
   *
   * @return the game history
   */
  @UnmodifiableView
  public List<Move> getHistory() {
    return Collections.unmodifiableList(history);
  }
}
