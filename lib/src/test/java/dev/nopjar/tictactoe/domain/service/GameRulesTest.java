package dev.nopjar.tictactoe.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class GameRulesTest {

  @Test
  void givenGameRule_whenGettingWithPriorSet_thenReturnPriorValue() {
    // arrange
    GameRule<Object> mockRule = mock(GameRule.class);
    Object defaultValue = new Object();
    Object priorValue = new Object();
    when(mockRule.defaultValue()).thenReturn(defaultValue);
    GameRules underTest = GameRules.defaults();

    // act
    underTest.set(mockRule, priorValue);
    Object result = underTest.get(mockRule);

    // assert
    assertSame(priorValue, result);
    assertNotSame(defaultValue, result);
  }

  @Test
  void givenGameRule_whenGettingWithoutPriorSet_thenReturnDefaultValue() {
    // arrange
    GameRule<Object> mockRule = mock(GameRule.class);
    Object defaultValue = new Object();
    when(mockRule.defaultValue()).thenReturn(defaultValue);
    GameRules underTest = GameRules.defaults();

    // act
    Object result = underTest.get(mockRule);

    // assert
    assertSame(defaultValue, result);
  }
}