package dev.nopjar.tictactoe.api;

import dev.nopjar.tictactoe.player.Player;
import java.util.Optional;
import org.jetbrains.annotations.TestOnly;

/**
 * Represents a TicTacToe board.
 */
public class Board {

  private final Player[][] board;

  public Board() {
    this(new Player[3][3]);
  }

  @TestOnly
  Board(Player[][] board) {
    if (board.length != 3 || board[0].length != 3) {
      throw new IllegalArgumentException("Board must be 3x3!");
    }
    this.board = board;
  }

  boolean apply(Move move) {
    if (!validateMove(move)) {
      return false;
    }
    board[move.cell() / 3][move.cell() % 3] = move.player();
    return true;
  }

  /**
   * Checks if a move is valid based on the current board state.
   *
   * <p>A move is considered invalid if the cell is out of bounds or if the cell is already occupied.
   *
   * @param move the move to validate
   * @return true if the move is valid, false otherwise
   */
  public boolean validateMove(Move move) {
    return move.cell() >= 0 && move.cell() < 9 && board[move.cell() / 3][move.cell() % 3] == null;
  }

  /**
   * Returns the player currently placed in the given cell.
   *
   * @param cell the cell
   * @return the player placed in the cell, or empty if the cell is empty
   */
  public Optional<Player> getCell(int cell) {
    return Optional.ofNullable(board[cell / 3][cell % 3]);
  }

  /**
   * Validates the current board state and checks for a winner.
   *
   * <p>If the board is in a winning state, the current player is the winner due to how tictactoe works.
   *
   * @return the current state
   */
  public BoardState validateCurrentPlacements() {
    // check rows and columns
    for (int i = 0; i < 3; i++) {
      // rows
      if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
        return BoardState.WINNER;
      }
      // columns
      if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
        return BoardState.WINNER;
      }
    }

    // diagonals
    if (board[1][1] != null) {
      if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
        return BoardState.WINNER;
      }
      if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
        return BoardState.WINNER;
      }
    }

    // check for empty cells
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        if (board[r][c] == null) {
          return BoardState.IN_PROGRESS;
        }
      }
    }

    return BoardState.DRAW;
  }

  /**
   * Represents the state of a TicTacToe board.
   */
  public enum BoardState {
    /**
     * The board is in progress, and there are valid moves left.
     */
    IN_PROGRESS,
    /**
     * The board is in a draw state, so there are no valid moves left and no winner.
     */
    DRAW,
    /**
     * The board is in a winning state, so there are no valid moves left and a winner exists.
     */
    WINNER;

    /**
     * Checks if this state has valid moves left.
     *
     * @return true if there are valid moves left, false otherwise
     */
    public boolean hasValidMoves() {
      return this == IN_PROGRESS;
    }

    /**
     * Checks if this state has a winner.
     *
     * @return true if there is a winner, false otherwise
     */
    public boolean hasWinner() {
      return this == WINNER;
    }
  }

}
