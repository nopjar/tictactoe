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
        arguments("a1", 0),
        arguments("b1", 1),
        arguments("c1", 2),
        arguments("a2", 3),
        arguments("b2", 4),
        arguments("c2", 5),
        arguments("a3", 6),
        arguments("b3", 7),
        arguments("c3", 8)
    );
  }

  @ParameterizedTest
  @MethodSource("validNotationArguments")
  void givenString_whenValid_thenReturnCorrectCell(String notation, int expectedCell) {
    assertEquals(expectedCell, NotationParser.parseNotation(notation));
    assertEquals(expectedCell, NotationParser.parseNotation(notation.toUpperCase()));
  }

  static Stream<Arguments> invalidNotationArguments() {
    return Stream.of(
        arguments("a0"),
        arguments("a4"),
        arguments("e4"),
        arguments("too_long"),
        arguments(""),
        arguments(" ")
    );
  }

  @ParameterizedTest
  @MethodSource("invalidNotationArguments")
  void givenString_whenInvalid_thenThrowException(String notation) {
    assertThrows(IllegalArgumentException.class, () -> NotationParser.parseNotation(notation));
  }
}