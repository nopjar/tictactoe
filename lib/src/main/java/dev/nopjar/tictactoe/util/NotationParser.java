package dev.nopjar.tictactoe.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class for parsing cell notation.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotationParser {

  /**
   * Parses a cell notation of the form [a-c][1-3] to the cell index in range [0-8].
   *
   * @param notation the string representation of the cell
   * @return the cell index
   * @throws IllegalArgumentException if the notation is invalid
   */
  public static int parseNotation(String notation) {
    if (notation.length() != 2) {
      throw new IllegalArgumentException("notation must be 2 characters long");
    }
    notation = notation.toLowerCase();
    int cell = (notation.charAt(0) - 'a') + (notation.charAt(1) - '1') * 3;
    if (cell < 0 || cell > 8) {
      throw new IllegalArgumentException("Illegal notation! Must be in [a-c][1-3]");
    }
    return cell;
  }

}
