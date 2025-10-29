package dev.nopjar.tictactoe.api;

import dev.nopjar.tictactoe.player.Player;
import java.util.Optional;
import org.jetbrains.annotations.TestOnly;

/**
 * Represents a TicTacToe board.
 */
public class Board {

  private final Player[] board;
  private final int dimension;

  public Board(int dimension) {
    this(new Player[dimension * dimension], dimension);
  }

  @TestOnly
  Board(Player[] board, int dimension) {
    if (dimension == 0 || board.length != dimension * dimension) {
      throw new IllegalArgumentException("Invalid board size.");
    }
    this.board = board;
    this.dimension = dimension;
  }

  boolean apply(Move move) {
    if (!validateMove(move)) {
      return false;
    }
    board[move.cell()] = move.player();
    return true;
  }

  /**
   * Returns the board dimension (n for an n x n board).
   *
   * @return the board dimension
   */
  public int getDimension() {
    return dimension;
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
    return move.cell() >= 0 && move.cell() < board.length && board[move.cell()] == null;
  }

  /**
   * Returns the player currently placed in the given cell.
   *
   * @param cell the cell, [0, dimension * dimension - 1]
   * @return the player placed in the cell, or empty if the cell is empty
   * @throws IndexOutOfBoundsException if the cell is out of bounds
   */
  public Optional<Player> getCell(int cell) {
    return Optional.ofNullable(board[cell]);
  }

  /**
   * Validates the current board state and checks for a winner.
   *
   * <p>If the board is in a winning state, the current player is the winner due to how tictactoe works.
   *
   * @return the current state
   */
  public BoardState validateCurrentPlacements() {
    final int totalCells = board.length;
    final int size = (int) Math.sqrt(totalCells);
    boolean hasEmpty = false;

    // rows and empties
    for (int r = 0; r < size; r++) {
      final int rowStart = r * size;
      if (isUniformLine(rowStart, 1, size)) {
        return BoardState.WINNER;
      }
      for (int c = 0; c < size; c++) {
        if (board[rowStart + c] == null) {
          hasEmpty = true;
          break;
        }
      }
    }

    // columns
    for (int c = 0; c < size; c++) {
      if (isUniformLine(c, size, size)) {
        return BoardState.WINNER;
      }
    }

    // main diagonal
    if (isUniformLine(0, size + 1, size)) {
      return BoardState.WINNER;
    }

    // anti-diagonal
    if (isUniformLine(size - 1, size - 1, size)) {
      return BoardState.WINNER;
    }

    return hasEmpty ? BoardState.IN_PROGRESS : BoardState.DRAW;
  }

  /**
   * Checks whether the same player fills a line on the board (row, column, or diagonal).
   *
   * @param start  the starting index in the board array
   * @param step   the step to move to the next cell in the line
   * @param length the number of cells in the line
   * @return true if all cells are occupied by the same non-null player
   */
  private boolean isUniformLine(int start, int step, int length) {
    Player first = board[start];
    if (first == null) {
      return false;
    }
    int idx = start + step;
    for (int i = 1; i < length; i++, idx += step) {
      if (board[idx] != first) {
        return false;
      }
    }
    return true;
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
