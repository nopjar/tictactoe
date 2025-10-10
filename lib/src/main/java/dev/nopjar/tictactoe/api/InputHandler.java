package dev.nopjar.tictactoe.api;

import dev.nopjar.tictactoe.player.HumanPlayer;
import dev.nopjar.tictactoe.player.Player;

/**
 * Interface which allows the implementation to pass user input for a {@code HumanPlayer} to the lib.
 *
 * @see HumanPlayer
 */
public interface InputHandler {

  /**
   * Called when a move is requested from a {@code Player}.
   *
   * <p>The returned move must not be valid for the given game and player.
   * In case the input is invalid, the {@link #onInvalidInput(Game, Player, Exception)} method will be called before calling this method again.
   *
   * @param game   the game instance
   * @param player the player instance
   * @return the move to play
   */
  Move handleInput(Game game, Player player);

  /**
   * Called when a move is invalid for a {@code Player}.
   *
   * @param game   the game instance
   * @param player the player instance
   * @param e      the exception that was thrown while trying to handle or validate the move
   */
  void onInvalidInput(Game game, Player player, Exception e);
}
