package dev.nopjar.tictactoe.domain.service;

/**
 * A single game rule.
 *
 * @param type         the class of the value that this rule holds
 * @param defaultValue the default value of this rule
 * @param <T>          the type of value that this rule holds
 */
public record GameRule<T>(Class<? extends T> type, T defaultValue) {

  public static final GameRule<Boolean> RANDOM_FIRST_PLAYER = new GameRule<>(Boolean.class, false);

}
