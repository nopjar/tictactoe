package dev.nopjar.tictactoe.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class GameOptionsTest {

  @Test
  void givenGameOption_whenGettingWithPriorSet_thenReturnPriorValue() {
    // arrange
    GameOption<Object> mockOption = mock(GameOption.class);
    Object defaultValue = new Object();
    Object priorValue = new Object();
    when(mockOption.defaultValue()).thenReturn(defaultValue);
    GameOptions underTest = GameOptions.defaults();

    // act
    underTest.set(mockOption, priorValue);
    Object result = underTest.get(mockOption);

    // assert
    assertSame(priorValue, result);
    assertNotSame(defaultValue, result);
  }

  @Test
  void givenGameOption_whenGettingWithoutPriorSet_thenReturnDefaultValue() {
    // arrange
    GameOption<Object> mockOption = mock(GameOption.class);
    Object defaultValue = new Object();
    when(mockOption.defaultValue()).thenReturn(defaultValue);
    GameOptions underTest = GameOptions.defaults();

    // act
    Object result = underTest.get(mockOption);

    // assert
    assertSame(defaultValue, result);
  }
}