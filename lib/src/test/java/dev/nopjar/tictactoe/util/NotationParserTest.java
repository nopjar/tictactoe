package dev.nopjar.tictactoe.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NotationParserTest {

  static Stream<Arguments> validNotationArguments() {
    return Stream.of(
        arguments(3, "a1", 0),
        arguments(3, "b1", 1),
        arguments(3, "c1", 2),
        arguments(3, "a2", 3),
        arguments(3, "b2", 4),
        arguments(3, "c2", 5),
        arguments(3, "a3", 6),
        arguments(3, "b3", 7),
        arguments(3, "c3", 8)
    );
  }

  @ParameterizedTest
  @MethodSource("validNotationArguments")
  void givenString_whenValid_thenReturnCorrectCell(int boardDimension, String notation, int expectedCell) {
    assertEquals(expectedCell, NotationParser.parseNotation(boardDimension, notation));
    assertEquals(expectedCell, NotationParser.parseNotation(boardDimension, notation.toUpperCase()));
  }

  static Stream<Arguments> invalidNotationArguments() {
    return Stream.of(
        arguments(3, "a0"),
        arguments(3, "a4"),
        arguments(3, "e4"),
        arguments(3, "too_long"),
        arguments(3, ""),
        arguments(3, " ")
    );
  }

  @ParameterizedTest
  @MethodSource("invalidNotationArguments")
  void givenString_whenInvalid_thenThrowException(int boardDimension, String notation) {
    assertThrows(IllegalArgumentException.class, () -> NotationParser.parseNotation(boardDimension, notation));
  }
}