package dev.nopjar.tictactoe.api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.nopjar.tictactoe.domain.service.GameRule;
import dev.nopjar.tictactoe.domain.service.GameRules;
import dev.nopjar.tictactoe.domain.service.GameStateService;
import dev.nopjar.tictactoe.domain.state.FinishedGameState;
import dev.nopjar.tictactoe.domain.state.IdleGameState;
import dev.nopjar.tictactoe.domain.state.InProgressGameState;
import dev.nopjar.tictactoe.domain.state.PreparationGameState;
import dev.nopjar.tictactoe.player.Player;
import dev.nopjar.tictactoe.util.RandomUtils;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoSession;

class GameTest {

  @Test
  void givenGame_whenRegisteringRendererOnce_thenActAsExpected() {
    // arrange
    Renderer rendererMock = mock(Renderer.class);
    Game underTest = new Game();

    // act and assert
    assertDoesNotThrow(() -> underTest.registerRenderer(rendererMock));
    assertSame(rendererMock, underTest.getRenderer());
  }

  @Test
  void givenGame_whenRegisteringRendererTwice_thenThrowException() {
    // arrange
    Renderer rendererMockOne = mock(Renderer.class);
    Renderer rendererMockTwo = mock(Renderer.class);
    Game underTest = new Game();

    // act
    underTest.registerRenderer(rendererMockOne);

    // act and assert
    assertThrows(IllegalStateException.class, () -> underTest.registerRenderer(rendererMockTwo));
    assertSame(rendererMockOne, underTest.getRenderer());
  }

  @Test
  void givenGame_whenRegisteringPlayersOnce_thenActAsExpected() {
    // arrange
    Player playerMockOne = mock(Player.class);
    Player playerMockTwo = mock(Player.class);
    Game underTest = new Game();

    when(playerMockOne.getType()).thenReturn(Player.Type.X);
    when(playerMockTwo.getType()).thenReturn(Player.Type.O);

    // act
    underTest.registerPlayer(playerMockOne);
    underTest.registerPlayer(playerMockTwo);

    // assert
    assertEquals(2, underTest.getPlayers().size());
    assertTrue(underTest.getPlayers().contains(playerMockOne));
    assertTrue(underTest.getPlayers().contains(playerMockTwo));
  }

  @Test
  void givenGame_whenRegisteringPlayerTypeTwice_thenThrowException() {
    // arrange
    Player playerMockOne = mock(Player.class);
    Player playerMockTwo = mock(Player.class);
    Game underTest = new Game();

    when(playerMockOne.getType()).thenReturn(Player.Type.X);
    when(playerMockTwo.getType()).thenReturn(Player.Type.X);

    // act
    underTest.registerPlayer(playerMockOne);

    // act and assert
    assertThrows(IllegalArgumentException.class, () -> underTest.registerPlayer(playerMockTwo));
    assertEquals(1, underTest.getPlayers().size());
    assertTrue(underTest.getPlayers().contains(playerMockOne));
  }

  @Test
  void givenGame_whenStartingGameWithTooFewPlayers_thenThrowException() {
    // arrange
    Player playerMock = mock(Player.class);
    Game underTest = new Game();
    underTest.registerPlayer(playerMock);

    // act and assert
    assertThrows(IllegalArgumentException.class, underTest::start);
  }

  @Test
  void givenGame_whenStartingGame_thenSwitchState() {
    // arrange
    GameStateService mockStateService = mock(GameStateService.class);
    when(mockStateService.isState(FinishedGameState.class)).thenReturn(true); // will cause the game loop to terminate.
    Player playerMockOne = mock(Player.class);
    Player playerMockTwo = mock(Player.class);
    when(playerMockOne.getType()).thenReturn(Player.Type.X);
    when(playerMockTwo.getType()).thenReturn(Player.Type.O);

    Game underTest = Game.builder()
        .players(new ArrayList<>(2))
        .stateService(mockStateService)
        .build();
    underTest.registerPlayer(playerMockOne);
    underTest.registerPlayer(playerMockTwo);

    // act
    underTest.start();

    // assert
    assertFalse(underTest.getStateService().getState() instanceof IdleGameState);
    verify(mockStateService).switchState(isA(PreparationGameState.class));
  }

  @Test
  void givenGame_whenPlayingMoveInIllegalState_thenThrowException() {
    // arrange
    GameStateService mockStateService = mock(GameStateService.class);
    when(mockStateService.isState(InProgressGameState.class)).thenReturn(false);
    Game underTest = Game.builder()
        .stateService(mockStateService)
        .build();

    // act and assert
    assertThrows(IllegalStateException.class, () -> underTest.playMove(mock(Move.class)));
  }

  @Test
  void givenGame_whenPlayingIllegalMove_thenDoNotAccept() {
    // arrange
    GameStateService mockStateService = mock(GameStateService.class);
    when(mockStateService.isState(InProgressGameState.class)).thenReturn(true);
    Board boardMock = mock(Board.class);
    when(boardMock.apply(ArgumentMatchers.any())).thenReturn(false);
    Game underTest = Game.builder()
        .stateService(mockStateService)
        .board(boardMock)
        .build();

    // act
    boolean result = underTest.playMove(mock(Move.class));

    // assert
    assertFalse(result);
    assertTrue(underTest.getHistory().isEmpty());
  }

  @Test
  void givenGame_whenPlayingMove_thenAppendToHistory() {
    // arrange
    GameStateService mockStateService = mock(GameStateService.class);
    when(mockStateService.isState(InProgressGameState.class)).thenReturn(true);
    Board boardMock = mock(Board.class);
    when(boardMock.apply(ArgumentMatchers.any())).thenReturn(true);
    Game underTest = Game.builder()
        .stateService(mockStateService)
        .board(boardMock)
        .build();
    Move moveOne = mock(Move.class);
    Move moveTwo = mock(Move.class);

    // act
    boolean resultOne = underTest.playMove(moveOne);
    boolean resultTwo = underTest.playMove(moveTwo);

    // assert
    assertTrue(resultOne);
    assertTrue(resultTwo);
    assertEquals(2, underTest.getHistory().size());
    assertSame(moveOne, underTest.getHistory().getFirst());
    assertSame(moveTwo, underTest.getHistory().getLast());
  }

  @Test
  void givenGame_whenGettingNextPlayerWithNullAndNotRandomFirstPlayer_thenReturnFirstPlayer() {
    // arrange
    Player playerMockOne = mock(Player.class);
    when(playerMockOne.getType()).thenReturn(Player.Type.X);
    Player playerMockTwo = mock(Player.class);
    when(playerMockTwo.getType()).thenReturn(Player.Type.O);
    GameRules mockRules = mock(GameRules.class);
    when(mockRules.get(GameRule.RANDOM_FIRST_PLAYER)).thenReturn(false);
    Game underTest = Game.builder()
        .rules(mockRules)
        .players(spy(new ArrayList<>(2)))
        .build();
    underTest.registerPlayer(playerMockOne);
    underTest.registerPlayer(playerMockTwo);

    // act
    Player resultOne = underTest.getNextPlayer(null);

    // assert
    assertSame(playerMockOne, resultOne);
    verify(underTest.getPlayers()).getFirst();
  }

  @Test
  void givenGame_whenGettingNextPlayerWithNullAndRandomFirstPlayer_thenReturnRandomPlayer() {
    // arrange
    Player playerMockOne = mock(Player.class);
    when(playerMockOne.getType()).thenReturn(Player.Type.X);
    Player playerMockTwo = mock(Player.class);
    when(playerMockTwo.getType()).thenReturn(Player.Type.O);
    GameRules mockRules = mock(GameRules.class);
    when(mockRules.get(GameRule.RANDOM_FIRST_PLAYER)).thenReturn(true);
    Game underTest = Game.builder()
        .rules(mockRules)
        .players(new ArrayList<>(2))
        .build();
    underTest.registerPlayer(playerMockOne);
    underTest.registerPlayer(playerMockTwo);
    try (var mockStatic = mockStatic(RandomUtils.class)) {
      // act
      underTest.getNextPlayer(null);

      // assert
      mockStatic.verify(() -> RandomUtils.getRandomElement(underTest.getPlayers()));
    }
  }

  @Test
  void givenGame_whenGettingNextPlayerWithCurrentPlayer_thenReturnNextPlayer() {
    // arrange
    Player playerMockOne = mock(Player.class);
    when(playerMockOne.getType()).thenReturn(Player.Type.X);
    Player playerMockTwo = mock(Player.class);
    when(playerMockTwo.getType()).thenReturn(Player.Type.O);
    GameRules mockRules = mock(GameRules.class);
    when(mockRules.get(GameRule.RANDOM_FIRST_PLAYER)).thenReturn(true);
    Game underTest = Game.builder()
        .players(new ArrayList<>(2))
        .build();
    underTest.registerPlayer(playerMockOne);
    underTest.registerPlayer(playerMockTwo);

    Player currentPlayerMock = playerMockOne;
    for (int i = 0; i < 10; i++) {
      // act
      Player newCurrentPlayerMock = underTest.getNextPlayer(currentPlayerMock);

      // assert
      assertNotSame(currentPlayerMock, newCurrentPlayerMock);
      currentPlayerMock = newCurrentPlayerMock;
    }
  }
}