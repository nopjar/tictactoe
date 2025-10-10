package dev.nopjar.tictactoe.player;

import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.Move;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Blocking;

/**
 * Represents a player in the game.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Player {
  private final String name;
  private final Type type;

  /**
   * Returns the next move for the player.
   *
   * <p>The returned move must not be valid for the given game.
   * <p>This method may be blocking.
   *
   * @param game the game instance
   * @return the next move
   */
  @Blocking
  public abstract Move nextMove(Game game);

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    Player that = (Player) obj;
    return Objects.equals(this.name, that.name) &&
        Objects.equals(this.type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }

  @Override
  public String toString() {
    return "Player[" +
        "name=" + name + ", " +
        "type=" + type + ']';
  }

  /**
   * The player type.
   */
  public enum Type {
    X, O;
  }

}
