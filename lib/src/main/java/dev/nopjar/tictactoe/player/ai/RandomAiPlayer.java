package dev.nopjar.tictactoe.player.ai;

import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.Move;
import dev.nopjar.tictactoe.player.AiPlayer;
import dev.nopjar.tictactoe.util.RandomUtils;
import java.util.stream.IntStream;

/**
 * Represents an AI player that randomly chooses a valid move.
 */
public class RandomAiPlayer extends AiPlayer {

  public RandomAiPlayer(String name, Type type) {
    super(name, type);
  }

  @Override
  public Move nextMove(Game game) {
    int dimension = game.getBoard().getDimension();
    int[] possibleCells = IntStream.range(0, dimension * dimension)
        .filter(i -> game.getBoard().getCell(i).isEmpty())
        .toArray();

    if (possibleCells.length == 0) {
      throw new AssertionError("No possible moves. This code should not be reached.");
    }

    int cell = RandomUtils.getRandomElement(possibleCells);
    return new Move(this, cell);
  }
}
