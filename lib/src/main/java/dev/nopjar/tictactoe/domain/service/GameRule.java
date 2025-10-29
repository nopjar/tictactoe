package dev.nopjar.tictactoe.domain.service;

import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

/**
 * A single game rule.
 *
 * @param type         the class of the value that this rule holds
 * @param defaultValue the default value of this rule
 * @param validator    a predicate that validates the value of this rule or null if no validation is needed
 * @param <T>          the type of value that this rule holds
 */
public record GameRule<T>(Class<? extends T> type, T defaultValue, @Nullable Predicate<T> validator) {

  /**
   * Creates a new game rule with no validation.
   *
   * @param type         the class of the value that this rule holds
   * @param defaultValue the default value of this rule
   */
  public GameRule(Class<? extends T> type, T defaultValue) {
    this(type, defaultValue, null);
  }

  public static final GameRule<Boolean> RANDOM_FIRST_PLAYER = new GameRule<>(Boolean.class, false);
  public static final GameRule<Integer> BOARD_SIZE = new GameRule<>(Integer.class, 3, size -> size > 0 && size < 26); // if size > 26, notation needs rework!

}
