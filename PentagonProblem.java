import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Purpose: PentagonProblem solver. Given a sorted list of 0....9,
 *  Compute permutations of the list s.t., five subLists of the permutations have
 *  equal sums. The subLists are as follows:
 *  sublistOne ~ contains elem 0, 1, 2
 *  sublistTwo ~ contains elem 2, 3, 4
 *  sublistOne ~ contains elem 4, 5, 6
 *  sublistOne ~ contains elem 6, 7, 8
 *  sublistOne ~ contains elem 8, 9, 0
 *
 * Input: Nothing
 *
 * Output: The solutions to the PentagonProblem ~ 60 lists
 *
 * @author NICHOLAS SUNGA
 */
public class PentagonProblem {
  public static void main(String[] args) {
    ArrayList<Integer> arr = new ArrayList<Integer>();
    ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> solutions = new ArrayList<ArrayList<Integer>>();
    int index = 0;

    for (int i = 0; i < 10; i++) {
      arr.add(i);
    }

    permute(arr, table);

    for (int i = 0; i < table.size(); i++) {
      solve(solutions, table.get(i));
    }

    System.out.println("The solutions to the PentagonProblem are: ");
    for (int i = 0; i < solutions.size(); i++) {
      System.out.println(Arrays.toString(solutions.get(i).toArray()));
    }
    System.out.println("Number of solutions: " + solutions.size());
  }

  /**
   * Compute and store the permutations of the list 0,1,2,...,9
   * lexicographically
   *
   * @param arr the list of 0,1,2,....,9
   * @param table stores all permuations of the list
   * @return none
   */
  public static void permute(ArrayList<Integer> arr, ArrayList<ArrayList<Integer>> table) {
    while (true) {
      int largestIndex = -1;

      table.add(new ArrayList<Integer>(arr));

      // give 0...9, start permutations with 8 and 9
      for (int i = 0; i < arr.size() - 1; i++) {
        if (arr.get(i) < arr.get(i + 1)) {
          largestIndex = i;
        }
      }

      // permutations are done
      if (largestIndex == -1) {
        break;
      }

      int largestJIndex = -1;

      // get the element greater and closest to largestIndex
      for (int j = 0; j < arr.size(); j++) {
        if (arr.get(largestIndex) < arr.get(j)) {
          largestJIndex = j;
        }
      }

      // swap them
      swap(arr, largestIndex, largestJIndex);

      // get the tail and flip it so the elements are ascending right to left
      ArrayList<Integer> tail = new ArrayList<Integer>(arr.subList(largestIndex + 1, arr.size()));
      arr.removeAll(tail);
      Collections.reverse(tail);
      arr.addAll(tail);
    }

    return;
  }

  /**
   * Basic swap of two elems
   *
   * @param arr possible permutation of 0,1,2,...,9
   * @param largestIndex index of an elem to be swapped
   * @param largestJIndex the other index of an elem to be swapped
   * @return none
   */
  public static void swap(ArrayList<Integer> arr, int largestIndex, int largestJIndex) {
    Integer temp = arr.get(largestIndex);
    arr.set(largestIndex, arr.get(largestJIndex));
    arr.set(largestJIndex, temp);
  }

  /**
   * Tests all permutations of the list 0,1,2,...,9 and checks if
   * the five subLists have equal sums. If they do, then store them in
   * solutions. A lot of brute force..but it doesn't take thaaaat long.
   *
   * @param solutions the solutions of the PentagonProblem
   * @param arr some permutation of 0,1,2,...,9
   * @return none
   */
  public static void solve(ArrayList<ArrayList<Integer>> solutions, ArrayList<Integer> arr) {
    ArrayList<Integer> subListOne = new ArrayList<Integer>(arr.subList(0, 3));
    ArrayList<Integer> subListTwo = new ArrayList<Integer>(arr.subList(2, 5));
    ArrayList<Integer> subListThree = new ArrayList<Integer>(arr.subList(4, 7));
    ArrayList<Integer> subListFour = new ArrayList<Integer>(arr.subList(6, 9));
    ArrayList<Integer> subListFive = new ArrayList<Integer>(arr.subList(8, 10));
    subListFive.add(arr.get(0));

    int sum = 0;
    for (int i = 0; i < subListOne.size(); i++) {
      sum += subListOne.get(i);
    }

    int sumTwo = 0;
    for (int i = 0; i < subListTwo.size(); i++) {
      sumTwo += subListTwo.get(i);
    }

    if (sum == sumTwo) {
      sumTwo = 0;
      for (int i = 0; i < subListThree.size(); i++) {
        sumTwo += subListThree.get(i);
      }

      if (sum == sumTwo) {
        sumTwo = 0;
        for (int i = 0; i < subListFour.size(); i++) {
          sumTwo += subListFour.get(i);
        }

        if (sum == sumTwo) {
          sumTwo = 0;
          for (int i = 0; i < subListFive.size(); i++) {
            sumTwo += subListFive.get(i);
          }

          if (sum == sumTwo) {
            solutions.add(arr);
            return;
          } else {
            return;
          }

        } else {
          return;
        }

      } else {
        return;
      }
    } else {
      return;
    }
  }
}
