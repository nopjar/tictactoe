package dev.nopjar.adapter;

import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.api.InputHandler;
import dev.nopjar.tictactoe.api.Move;
import dev.nopjar.tictactoe.player.Player;
import dev.nopjar.tictactoe.util.NotationParser;
import java.util.Scanner;

/**
 * Represents an input handler that reads input from the console.
 */
@SuppressWarnings("java:S106") // sonarqube; System.out is okay here
public class ConsoleInputHandler implements InputHandler {

  private final Scanner scanner = new Scanner(System.in);

  @Override
  public Move handleInput(Game game, Player player) {
    System.out.print("Enter move for player " + player.getName() + ": ");
    String line = scanner.nextLine();
    int cell = NotationParser.parseNotation(line.trim());
    return new Move(player, cell);
  }

  @Override
  public void onInvalidInput(Game game, Player player, Exception e) {
    System.out.println(e.getMessage());
  }
}
