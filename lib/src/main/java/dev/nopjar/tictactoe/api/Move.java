package dev.nopjar.tictactoe.api;

import dev.nopjar.tictactoe.player.Player;

/**
 * Represents a move on the board.
 *
 * @param player the player making the move
 * @param cell   the cell on which the move was made, [0, dimension * dimension - 1]
 */
public record Move(Player player, int cell) {
}
