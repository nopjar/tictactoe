package dev.nopjar.tictactoe.player.ai;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.nopjar.tictactoe.api.Board;
import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.Move;
import dev.nopjar.tictactoe.player.Player;
import dev.nopjar.tictactoe.util.RandomUtils;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RandomAiPlayerTest {

  @Test
  void givenPlayer_whenGettingNextMove_thenUseRandomUtils() {
    // arrange
    Game mockGame = mock(Game.class);
    Board boardMock = mock(Board.class);
    RandomAiPlayer underTest = new RandomAiPlayer("test", Player.Type.X);

    when(boardMock.getCell(anyInt())).thenReturn(Optional.empty());
    when(boardMock.getDimension()).thenReturn(3);
    when(mockGame.getBoard()).thenReturn(boardMock);

    try (var mockStatic = Mockito.mockStatic(RandomUtils.class)) {
      // act
      Move move = underTest.nextMove(mockGame);

      // assert
      assertNotNull(move);
      assertEquals(Player.Type.X, move.player().getType());
      mockStatic.verify(() -> RandomUtils.getRandomElement(any(int[].class)));
    }
  }
}