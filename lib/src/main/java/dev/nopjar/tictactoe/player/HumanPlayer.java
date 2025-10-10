package dev.nopjar.tictactoe.player;

import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.InputHandler;
import dev.nopjar.tictactoe.api.Move;
import org.jetbrains.annotations.Blocking;

/**
 * Represents a human player in the game.
 *
 * <p>This player will call the {@link InputHandler} to get the user input.
 */
@SuppressWarnings("java:S2160") // override equals; would be the same as parent class
public class HumanPlayer extends Player {

  private final InputHandler inputHandler;

  public HumanPlayer(String name, Type type, InputHandler inputHandler) {
    super(name, type);
    this.inputHandler = inputHandler;
  }

  @Blocking
  @Override
  public Move nextMove(Game game) {
    // Trying to get a valid move until the user enters a valid one.
    while (true) {
      try {
        Move move = inputHandler.handleInput(game, this);
        if (game.getBoard().validateMove(move)) {
          return move;
        }
        inputHandler.onInvalidInput(game, this, new IllegalArgumentException("Invalid move."));
      } catch (Exception e) {
        inputHandler.onInvalidInput(game, this, e);
      }
    }
  }

}
