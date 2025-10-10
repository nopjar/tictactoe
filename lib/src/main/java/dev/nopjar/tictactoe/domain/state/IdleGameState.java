package dev.nopjar.tictactoe.domain.state;

/**
 * Represents the state of the game before the start method has been called.
 */
public class IdleGameState implements GameState {

  @Override
  public String getName() {
    return "IDLE";
  }

  @Override
  public void tick() {
    // do nothing.
  }
}
