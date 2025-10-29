package dev.nopjar.tictactoe.api;

import static dev.nopjar.tictactoe.player.Player.Type.O;
import static dev.nopjar.tictactoe.player.Player.Type.X;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.nopjar.tictactoe.player.Player;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class BoardTest {

  static Stream<Arguments> validMoveArguments() {
    return Stream.of(
        arguments(-1, false),
        arguments(0, true),
        arguments(1, true),
        arguments(2, true),
        arguments(3, true),
        arguments(4, true),
        arguments(5, true),
        arguments(6, true),
        arguments(7, true),
        arguments(8, true),
        arguments(9, false)
    );
  }

  @ParameterizedTest
  @MethodSource("validMoveArguments")
  void givenBoard_whenValidatingMove_thenReturnCorrectResult(int cell, boolean expectedResult) {
    // arrange
    Move mockMove = mock(Move.class);
    when(mockMove.cell()).thenReturn(cell);
    Board underTest = new Board(3);

    // act
    boolean result = underTest.validateMove(mockMove);

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
  void givenOccupiedCell_whenValidatingMove_thenReturnFalse(int cell) {
    // arrange
    Player mockPlayer = mock(Player.class);
    Move mockMove = mock(Move.class);
    when(mockMove.cell()).thenReturn(cell);
    Board underTest = new Board(3);
    underTest.apply(new Move(mockPlayer, cell));

    // act
    boolean result = underTest.validateMove(mockMove);

    // assert
    assertFalse(result);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
  void givenMove_whenApplying_thenStoreCorrectly(int cell) {
    // arrange
    Player mockPlayer = mock(Player.class);
    Move mockMove = mock(Move.class);
    when(mockMove.cell()).thenReturn(cell);
    when(mockMove.player()).thenReturn(mockPlayer);
    Board underTest = new Board(3);

    // act
    boolean result = underTest.apply(mockMove);

    // assert
    assertTrue(result);
    assertTrue(underTest.getCell(cell).isPresent());
    assertSame(mockPlayer, underTest.getCell(cell).get());
    for (int i = 0; i < 9; i++) {
      if (i == cell) {
        continue;
      }
      assertFalse(underTest.getCell(i).isPresent());
    }
  }

  static Stream<Arguments> validateCurrentPlacementsArguments() {
    return Stream.of(
        // Empty board
        arguments(new Player.Type[]{null, null, null, null, null, null, null, null, null}, Board.BoardState.IN_PROGRESS),
        // In progress states
        arguments(new Player.Type[]{X, null, O, null, X, null, null, null, null}, Board.BoardState.IN_PROGRESS),
        arguments(new Player.Type[]{X, O, X, O, null, null, null, null, null}, Board.BoardState.IN_PROGRESS),
        // Draw states
        arguments(new Player.Type[]{X, O, X, X, O, O, O, X, X}, Board.BoardState.DRAW),
        arguments(new Player.Type[]{O, X, O, X, X, O, O, O, X}, Board.BoardState.DRAW),
        arguments(new Player.Type[]{X, O, X, O, O, X, X, X, O}, Board.BoardState.DRAW),
        // Horizontal wins for X
        arguments(new Player.Type[]{X, X, X, null, O, O, null, null, null}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{O, null, null, X, X, X, O, null, null}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{null, O, O, null, null, null, X, X, X}, Board.BoardState.WINNER),
        // Vertical wins for O
        arguments(new Player.Type[]{O, X, X, O, X, null, O, null, null}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{X, O, null, X, O, null, null, O, null}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{X, X, O, null, null, O, null, null, O}, Board.BoardState.WINNER),
        // Diagonal wins
        arguments(new Player.Type[]{X, O, null, O, X, null, null, O, X}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{null, O, X, null, X, O, X, null, O}, Board.BoardState.WINNER),
        // 4x4 board cases
        arguments(new Player.Type[]{X, X, X, X, O, O, null, null, null, null, null, null, null, null, null, null}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{O, null, null, null, O, null, null, null, O, null, null, null, O, null, null, null}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{X, O, X, O, O, X, O, X, X, X, O, X, O, X, X, O}, Board.BoardState.DRAW),
        arguments(new Player.Type[]{X, O, X, null, O, X, O, X, X, X, O, X, O, X, X, O}, Board.BoardState.IN_PROGRESS),
        // 2x2 board cases (draw not possible)
        arguments(new Player.Type[]{X, X, O, O}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{X, X, null, O}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{X, X, null, null}, Board.BoardState.WINNER),
        arguments(new Player.Type[]{X, O, null, null}, Board.BoardState.IN_PROGRESS)
    );
  }

  @ParameterizedTest
  @MethodSource("validateCurrentPlacementsArguments")
  void givenBoard_whenValidatingCurrentPlacements_thenReturnCorrectState(@ConvertWith(BoardStateConverter.class) Player[] boardState,
                                                                         Board.BoardState expectedState) {
    // arrange
    Board underTest = new Board(boardState, (int) Math.sqrt(boardState.length));

    // act
    Board.BoardState result = underTest.validateCurrentPlacements();

    // assert
    assertEquals(expectedState, result);
  }

  static class BoardStateConverter implements ArgumentConverter {
    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
      if (!(source instanceof Player.Type[] boardState)) {
        throw new ArgumentConversionException("Invalid source type.");
      }

      Player mockPlayerX = mock(Player.class);
      Player mockPlayerO = mock(Player.class);
      Player[] players = new Player[boardState.length];
      for (int i = 0; i < boardState.length; i++) {
        if (boardState[i] == null) {
          continue;
        }
        switch (boardState[i]) {
          case X:
            players[i] = mockPlayerX;
            break;
          case O:
            players[i] = mockPlayerO;
            break;
        }
      }
      return players;
    }
  }

}