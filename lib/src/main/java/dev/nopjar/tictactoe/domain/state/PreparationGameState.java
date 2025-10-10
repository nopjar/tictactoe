package dev.nopjar.tictactoe.domain.state;

import dev.nopjar.tictactoe.api.Game;
import lombok.RequiredArgsConstructor;

/**
 * Represents the state when the game is in preparation.
 *
 * <p>This state is just used so the first game loop can render the initial board.
 */
@RequiredArgsConstructor
public class PreparationGameState implements GameState {
  private final Game game;

  @Override
  public String getName() {
    return "PREPARATION";
  }

  @Override
  public void tick() {
    game.getStateService().switchState(new InProgressGameState(game, game.getNextPlayer(null)));
  }
}
