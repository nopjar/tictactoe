package dev.nopjar.tictactoe.api;

import dev.nopjar.tictactoe.player.Player;

/**
 * Represents a move on the board.
 *
 * @param player The player making the move
 * @param cell   The cell on which the move was made
 */
public record Move(Player player, int cell) {
}
