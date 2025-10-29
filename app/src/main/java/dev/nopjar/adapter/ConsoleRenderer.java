package dev.nopjar.adapter;

import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.Renderer;

/**
 * Represents a renderer that prints the game state to the console.
 */
@SuppressWarnings("java:S106") // sonarqube; System.out is okay here
public class ConsoleRenderer implements Renderer {

  @Override
  public void render(Game game) {
    int dimension = game.getBoard().getDimension();
    StringBuilder sb = new StringBuilder();
    // table header
    sb.append(" ");
    for (int i = 0; i < dimension; i++) {
      sb.append("   ").append((char) ('a' + i));
    }
    sb.append("\n");
    sb.append("  ").append("+---".repeat(dimension)).append("+\n");

    // table rows
    for (int row = 0; row < dimension; row++) {
      sb.append(row + 1).append(" |");
      for (int col = 0; col < dimension; col++) {
        int cell = row * dimension + col;
        String character = game.getBoard().getCell(cell)
            .map(p -> p.getType().name())
            .orElse(" ");
        sb.append(" ").append(character).append(" |");
      }
      sb.append("\n");
      sb.append("  ").append("+---".repeat(dimension)).append("+\n");
    }
    System.out.println(sb);
  }

}
