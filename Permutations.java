import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class Permutations {
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

    System.out.println("The solutions are: ");
    for (int i = 0; i < solutions.size(); i++) {
      System.out.println(Arrays.toString(solutions.get(i).toArray()));
    }
    System.out.println("Done");
  }

  public static void permute(ArrayList<Integer> arr, ArrayList<ArrayList<Integer>> table) {
    while (true) {
      int largestIndex = -1;

      table.add(new ArrayList<Integer>(arr));

      for (int i = 0; i < arr.size() - 1; i++) {
        if (arr.get(i) < arr.get(i + 1)) {
          largestIndex = i;
        }
      }

      if (largestIndex == -1) {
        break;
      }

      int largestJIndex = -1;

      for (int j = 0; j < arr.size(); j++) {
        if (arr.get(largestIndex) < arr.get(j)) {
          largestJIndex = j;
        }
      }

      swap(arr, largestIndex, largestJIndex);

      // get the tail and flip it
      ArrayList<Integer> tail = new ArrayList<Integer>(arr.subList(largestIndex + 1, arr.size()));
      arr.removeAll(tail);
      Collections.reverse(tail);
      arr.addAll(tail);
    }

    return;
  }

  public static void swap(ArrayList<Integer> arr, int largestIndex, int largestJIndex) {
    Integer temp = arr.get(largestIndex);
    arr.set(largestIndex, arr.get(largestJIndex));
    arr.set(largestJIndex, temp);
  }

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
