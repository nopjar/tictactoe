package dev.nopjar.tictactoe.domain.state;

import dev.nopjar.tictactoe.api.Board.BoardState;
import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.Move;
import dev.nopjar.tictactoe.player.Player;
import lombok.AllArgsConstructor;

/**
 * Represents the state when the game is currently in progress.
 */
@AllArgsConstructor
public class InProgressGameState implements GameState {

  private final Game game;
  private Player currentPlayer;

  @Override
  public String getName() {
    return "IN_PROGRESS";
  }

  @Override
  public void tick() {
    // Getting the next move for the current player.
    boolean validMove;
    do {
      Move move = currentPlayer.nextMove(game);
      validMove = game.playMove(move);
    } while (!validMove);

    // Checking if the game is over.
    BoardState boardState = game.getBoard().validateCurrentPlacements();
    if (boardState.hasValidMoves()) {
      // Game is not over, continue.
      this.currentPlayer = game.getNextPlayer(currentPlayer);
    } else {
      // Game is over, switch to a finished state.
      Player winner = boardState.hasWinner() ? currentPlayer : null;
      game.getStateService().switchState(new FinishedGameState(winner));
    }
  }
}
