package dev.nopjar.tictactoe.player;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.nopjar.tictactoe.api.Board;
import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.InputHandler;
import dev.nopjar.tictactoe.api.Move;
import org.junit.jupiter.api.Test;

class HumanPlayerTest {

  @Test
  void givenPlayer_whenCallingNextMove_thenCallInputHandler() {
    // arrange
    InputHandler mockInputHandler = mock(InputHandler.class);
    Game mockGame = mock(Game.class);
    Board boardMock = mock(Board.class);
    HumanPlayer underTest = new HumanPlayer("test", Player.Type.X, mockInputHandler);
    Move mockMove = mock(Move.class);

    when(mockInputHandler.handleInput(mockGame, underTest)).thenReturn(mockMove);
    when(boardMock.validateMove(mockMove)).thenReturn(true);
    when(mockGame.getBoard()).thenReturn(boardMock);
    
    // act
    Move move = underTest.nextMove(mockGame);

    // assert
    verify(mockInputHandler).handleInput(mockGame, underTest);
    assertSame(mockMove, move);
  }

  @Test
  void givenPlayer_whenGettingInvalidMove_thenCallInvalidInputAndRetry() {
    // arrange
    InputHandler mockInputHandler = mock(InputHandler.class);
    Game mockGame = mock(Game.class);
    Board boardMock = mock(Board.class);
    HumanPlayer underTest = new HumanPlayer("test", Player.Type.X, mockInputHandler);
    Move mockMove = mock(Move.class);

    when(mockInputHandler.handleInput(mockGame, underTest)).thenReturn(mockMove);
    when(boardMock.validateMove(mockMove)).thenReturn(false, true);
    when(mockGame.getBoard()).thenReturn(boardMock);

    // act
    Move move = underTest.nextMove(mockGame);

    // assert
    verify(mockInputHandler, times(2)).handleInput(same(mockGame), same(underTest));
    verify(mockInputHandler, times(1)).onInvalidInput(same(mockGame), same(underTest), any());
    assertSame(mockMove, move);
  }
}