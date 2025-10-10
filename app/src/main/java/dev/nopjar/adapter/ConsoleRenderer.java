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
    StringBuilder sb = new StringBuilder();
    sb.append("    a   b   c\n");
    sb.append("  +---+---+---+\n");

    for (int row = 0; row < 3; row++) {
      sb.append(row + 1).append(" |");
      for (int col = 0; col < 3; col++) {
        int cell = row * 3 + col;
        String character = game.getBoard().getCell(cell)
            .map(p -> p.getType().name())
            .orElse(" ");
        sb.append(" ").append(character).append(" |");
      }
      sb.append("\n");
      sb.append("  +---+---+---+\n");
    }
    System.out.println(sb);
  }

}
