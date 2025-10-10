package dev.nopjar.tictactoe.domain.state;

import dev.nopjar.tictactoe.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a finished game state where the game is over.
 *
 * <p>If the game is a draw, then the winner is null.
 */
@Getter
@RequiredArgsConstructor
public class FinishedGameState implements GameState {

  private final Player winner;

  @Override
  public String getName() {
    return "FINISHED";
  }

  @Override
  public void tick() {
    // do nothing.
  }
}
