package dev.nopjar.tictactoe.domain.service;

import dev.nopjar.tictactoe.domain.state.GameState;
import dev.nopjar.tictactoe.domain.state.IdleGameState;

/**
 * Service that manages the current game state.
 */
public class GameStateService {

  private GameState currentState = new IdleGameState();

  /**
   * Gets the current game state.
   *
   * <p>This method is safe to use as long as the state is of the expected type.
   * If not a {@link ClassCastException} will be thrown on the caller side.
   *
   * @param <T> the type of the state to get
   * @return the state
   */
  @SuppressWarnings("unchecked")
  public <T extends GameState> T getState() {
    return (T) currentState;
  }

  /**
   * Checks if the current state is of the given type.
   *
   * @param stateClass the state class to check for
   * @return true if the current state is of the given type, false otherwise
   */
  public boolean isState(Class<? extends GameState> stateClass) {
    return stateClass.isInstance(currentState);
  }

  /**
   * Switches the current game state to the given state.
   *
   * @param newState the new state to switch to
   */
  public void switchState(GameState newState) {
    this.currentState = newState;
  }

  /**
   * Ticks the current game state.
   */
  public void tick() {
    this.currentState.tick();
  }

}
