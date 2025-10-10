package dev.nopjar.tictactoe.domain.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Represents the rules used during a game.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameRules {

  private final Map<GameRule<?>, Object> rules = new HashMap<>();

  /**
   * Creates a new set of default rules.
   *
   * @return the default rules
   */
  public static GameRules defaults() {
    return new GameRules();
  }

  /**
   * Sets a rule value.
   *
   * @param gameRule the rule to set the value for
   * @param value    the value to set
   * @param <T>      the type of the value to set
   */
  public <T> void set(GameRule<T> gameRule, T value) {
    rules.put(gameRule, value);
  }

  /**
   * Gets a rule value.
   *
   * @param gameRule the rule to get the value for
   * @param <T>      the type of the value to get
   * @return the value of the rule, or the default value if not set previously
   */
  @SuppressWarnings("unchecked") // safe operation as insert only by #set(GameRule, Object)
  public <T> T get(GameRule<T> gameRule) {
    return (T) Objects.requireNonNullElse(rules.get(gameRule), gameRule.defaultValue());
  }

}
