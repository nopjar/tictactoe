package dev.nopjar.tictactoe.domain.service;

import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

/**
 * A single game option.
 *
 * @param type         the class of the value that this option holds
 * @param defaultValue the default value of this option
 * @param validator    a predicate that validates the value of this option or null if no validation is needed
 * @param <T>          the type of value that this option holds
 */
public record GameOption<T>(Class<? extends T> type, T defaultValue, @Nullable Predicate<T> validator) {

  /**
   * Creates a new game option with no validation.
   *
   * @param type         the class of the value that this option holds
   * @param defaultValue the default value of this option
   */
  public GameOption(Class<? extends T> type, T defaultValue) {
    this(type, defaultValue, null);
  }

  public static final GameOption<Boolean> RANDOM_FIRST_PLAYER = new GameOption<>(Boolean.class, false);
  public static final GameOption<Integer> BOARD_SIZE = new GameOption<>(Integer.class, 3, size -> size > 0 && size < 26); // if size > 26, notation needs rework!

}
