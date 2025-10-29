package dev.nopjar.tictactoe.util;

import dev.nopjar.tictactoe.api.Game;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.TestOnly;

/**
 * Utility class for parsing cell notation.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotationParser {

  /**
   * Parses a cell notation of the form {@code [a-z][1-9]} to the cell index in range {@code [0-{board dimension^2 - 1}]}.
   *
   * @param notation the string representation of the cell
   * @return the cell index
   * @throws IllegalArgumentException if the notation is invalid
   */
  public static int parseNotation(Game game, String notation) {
    return parseNotation(game.getBoard().getDimension(), notation);
  }

  @TestOnly
  static int parseNotation(int dimension, String notation) {
    if (notation.length() != 2) {
      throw new IllegalArgumentException("notation must be 2 characters long");
    }
    notation = notation.toLowerCase();
    int cell = (notation.charAt(0) - 'a') + (notation.charAt(1) - '1') * dimension;
    if (cell < 0 || cell >= dimension * dimension) {
      throw new IllegalArgumentException("Illegal notation! Must be in [a-" + ((char) ('a' + dimension)) + "][1-" + dimension + "]");
    }
    return cell;
  }

}
