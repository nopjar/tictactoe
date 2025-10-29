package dev.nopjar.tictactoe.domain.state;

/**
 * Represents the state of the game.
 *
 * <p>This interface is used in combination with the {@link dev.nopjar.tictactoe.domain.service.GameStateService}.
 */
public interface GameState {

  /**
   * GEt the name of the state.
   *
   * @return the name
   */
  String getName();

  /**
   * Ticks the state.
   *
   * <p>This method is called every tick of the game loop.
   */
  void tick();

}
