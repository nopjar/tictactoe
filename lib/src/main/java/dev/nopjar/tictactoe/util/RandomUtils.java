package dev.nopjar.tictactoe.util;

import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class for handling randomness.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomUtils {

  private static final Random RANDOM = new Random();

  /**
   * Gets a random element from the given list.
   *
   * @param list the list to get the element from
   * @param <T>  the type of the elements in the list
   * @return the random element
   */
  public static <T> T getRandomElement(List<T> list) {
    return list.get(RANDOM.nextInt(list.size()));
  }

  /**
   * Gets a random element from the given int array.
   *
   * @param array the array to get the element from
   * @return the random element
   */
  public static int getRandomElement(int[] array) {
    return array[RANDOM.nextInt(array.length)];
  }

}
