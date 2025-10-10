package dev.nopjar.tictactoe.api;

/**
 * Interface which allows the implementation to render a {@code Game} each game loop.
 */
public interface Renderer {

  /**
   * Called when the game should be rendered.
   *
   * @param game the game
   */
  void render(Game game);

}
