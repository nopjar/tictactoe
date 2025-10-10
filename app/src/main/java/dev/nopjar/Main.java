package dev.nopjar;

import dev.nopjar.adapter.ConsoleInputHandler;
import dev.nopjar.adapter.ConsoleRenderer;
import dev.nopjar.tictactoe.api.Game;
import dev.nopjar.tictactoe.domain.state.FinishedGameState;
import dev.nopjar.tictactoe.player.HumanPlayer;
import dev.nopjar.tictactoe.player.Player;
import dev.nopjar.tictactoe.player.ai.RandomAiPlayer;
import java.util.Scanner;

@SuppressWarnings("java:S106") // sonarqube; System.out is okay here
public class Main {

  private final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    new Main().start();
  }

  public void start() {
    Game game = new Game();

    game.registerPlayer(createPlayer(Player.Type.X));
    game.registerPlayer(createPlayer(Player.Type.O));
    game.registerRenderer(new ConsoleRenderer());

    game.start();

    // The game is finished, printing the winner.
    System.out.println("Game finished!");
    FinishedGameState state = game.getStateService().getState();
    System.out.print("Result: ");
    if (state.getWinner() != null) {
      System.out.println(state.getWinner().getName() + " has won!");
    } else {
      System.out.println("It's a draw!");
    }
  }

  private Player createPlayer(Player.Type type) {
    String input;
    do {
      System.out.print("Do you want " + type.name() + " to be human? (Y/n)");
      input = scanner.nextLine();
    } while (!input.isBlank() && !input.toLowerCase().matches("[ny]"));
    boolean isHuman = input.isBlank() || input.trim().equalsIgnoreCase("y");
    if (isHuman) {
      System.out.print("Enter your name: ");
      String name = scanner.nextLine();
      return new HumanPlayer(name, type, new ConsoleInputHandler());
    } else {
      return new RandomAiPlayer("AI - " + type.name(), type);
    }
  }

}
