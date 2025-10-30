package dev.nopjar.tictactoe.domain.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Represents the options used during a game.
 *
 * <p>Used to configure some aspects of the game.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameOptions {

  private final Map<GameOption<?>, Object> options = new HashMap<>();

  /**
   * Creates a new set of default options.
   *
   * @return the default options
   */
  public static GameOptions defaults() {
    return new GameOptions();
  }

  /**
   * Sets a option value.
   *
   * @param gameOption the option to set the value for
   * @param value    the value to set, not null
   * @param <T>      the type of the value to set
   * @throws IllegalArgumentException if the value is null
   */
  public <T> void set(GameOption<T> gameOption, T value) {
    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null!");
    }
    if (gameOption.validator() != null && !gameOption.validator().test(value)) {
      throw new IllegalArgumentException("Value is not valid!");
    }
    options.put(gameOption, value);
  }

  /**
   * Gets an option value.
   *
   * @param gameOption the option to get the value for
   * @param <T>      the type of the value to get
   * @return the value of the option, or the default value if not set previously
   */
  @SuppressWarnings("unchecked") // safe operation as insert only by #set(GameOption, Object)
  public <T> T get(GameOption<T> gameOption) {
    return (T) Objects.requireNonNullElse(options.get(gameOption), gameOption.defaultValue());
  }

}
