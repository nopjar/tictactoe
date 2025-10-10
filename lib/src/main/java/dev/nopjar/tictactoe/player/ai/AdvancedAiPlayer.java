package dev.nopjar.tictactoe.player.ai;

import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.Move;
import dev.nopjar.tictactoe.player.AiPlayer;

/**
 * Represents an AI player that is capable of making advanced moves.
 *
 * <p>This player is currently not implemented but could be with a minimax algorithm.
 */
public class AdvancedAiPlayer extends AiPlayer {

  public AdvancedAiPlayer(String name, Type type) {
    super(name, type);
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Move nextMove(Game game) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
