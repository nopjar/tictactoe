package dev.nopjar.tictactoe.domain.state;

/**
 * Represents the state of the game.
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
