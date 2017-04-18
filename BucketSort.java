import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Purpose: BucketSort for doubles. Run like this -> java BucketSort < file.txt
 * The file must have only one double per line like so:
 *      1.02
 *      -9.002
 *
 * Input: The file of doubles
 *
 * Output: The input that is now sorted
 *
 * @author NICHOLAS SUNGA
 */
public class BucketSort {
    public static void main(String[] args) {
        // positive and negative buckets
        ArrayList<ArrayList<Double>> posBucket = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> negBucket = new ArrayList<ArrayList<Double>>();

        ArrayList<Double> inputData = new ArrayList<Double>();
		BufferedReader stdIn = new BufferedReader(new InputStreamReader (System.in));

		try {
			String s = stdIn.readLine();

			while(s != null) {
				inputData.add(Double.valueOf(s));
				s = stdIn.readLine();
			} // this should get everything from the file
		} catch (IOException e) {
			System.out.println("BAD DATA");
		}

        sort(posBucket, negBucket, inputData);
    }

    /**
     * Instantiate the right amount of pos and neg buckets, sort them, and
     * put them back into the original ArrayList. Also prints out the ArrayList
     * in ascending order
     *
     * @param posBucket buckets for positive and zero nums
     * @param negBucket buckets for negative nums
     * @param inputData the nums to be sorted
     * @return none
     */
    public static void sort(ArrayList<ArrayList<Double>> posBucket, ArrayList<ArrayList<Double>> negBucket, ArrayList<Double> inputData) {
        ArrayList<Double> negList = new ArrayList<Double>();
        ArrayList<Double> posList = new ArrayList<Double>(); // zero in here too

        // Separate the input into a positive list and a negative list
        for (int i = 0; i < inputData.size(); i++) {
            if (inputData.get(i) < 0) {
                negList.add(inputData.get(i));
            } else {
                posList.add(inputData.get(i));
            }
        }

        for (int i = 0; i < negList.size(); i++) {
            negBucket.add(new ArrayList<Double>());
        }

        for (int i = 0; i < posList.size(); i++) {
            posBucket.add(new ArrayList<Double>());
        }

        // put the values in their buckets
        sortPos(posBucket, posList);
        sortNeg(negBucket, negList);

        int inputIndex = 0;

        // put the negative values back into the original list
        for (int i = negBucket.size() - 1; i > -1; i--) {
            if (negBucket.get(i).size() != 0) {
                for (int j = negBucket.get(i).size() - 1; j > -1; j--) {
                    inputData.set(inputIndex++, negBucket.get(i).get(j));
                }
            }
        }

        // put the positive values back into the original list
        for (int i = 0; i < posBucket.size(); i++) {
            if (posBucket.get(i).size() != 0) {
                for (int j = 0; j < posBucket.get(i).size(); j++) {
                    inputData.set(inputIndex++, posBucket.get(i).get(j));
                }
            }
        }

        // Print out the array in ascending order
        System.out.println(Arrays.toString(inputData.toArray()));
    }

    /**
     * Determine which bucket the positive or zero nums should be
     * and calls insertion sort on every non-empty bucket
     *
     * @param posBucket buckets for positive and zero nums
     * @param posList positive or zero nums from the original input
     * @return none
     */
    public static void sortPos(ArrayList<ArrayList<Double>> posBucket, ArrayList<Double> posList) {
        double theMax = calculateMax(posList);
        double integerPart = Math.floor(theMax);
        double divider = Math.ceil((integerPart + 1) / posBucket.size());
        int floor = 0;

        for (int i = 0; i < posList.size(); i++) {
            floor = (int)Math.floor(posList.get(i)/divider);
            posBucket.get(floor).add(posList.get(i));
        }

        for (int i = 0; i < posBucket.size(); i++) {
            if (posBucket.get(i).size() != 0) {
                insertSort(posBucket.get(i));
            }
        }
    }

    /**
     * Determine which bucket the negative nums should be
     * and calls sortPos
     *
     * @param negBucket buckets for negative nums
     * @param negList negative nums from the original input
     * @return none
     */
    public static void sortNeg(ArrayList<ArrayList<Double>> negBucket, ArrayList<Double> negList) {
        // multiply by negative one so I can just call sortPos() instead
        for (int i = 0; i < negList.size(); i++) {
            negList.set(i, negList.get(i) * -1);
        }
        sortPos(negBucket, negList);

        // revert the numbers back to negative
        for (int i = 0; i < negBucket.size(); i++) {
            if (negBucket.get(i).size() != 0) {
                for (int j = 0; j < negBucket.get(i).size(); j++) {
                    negBucket.get(i).set(j, negBucket.get(i).get(j) * -1);
                }
            }
        }
    }

    /**
     * Determine the max value of the ArrayList
     *
     * @param list the input list
     * @return double - the max value
     */
    public static double calculateMax(ArrayList<Double> list) {
        double theMax = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > theMax) {
                theMax = list.get(i).doubleValue();
            }
        }

        return theMax;
    }

    /**
     * Basic insertion sort
     *
     * @param arr should be a non empty bucket
     * @return none
     */
    public static void insertSort(ArrayList<Double> arr) {
        double temp;

        for (int i = 1; i < arr.size(); i++) {
            for (int j = i ; j > 0 ; j--) {
                if (arr.get(j) < arr.get(j-1)) {
                    temp = arr.get(j);
                    arr.set(j, arr.get(j-1));
                    arr.set(j-1, temp);
                }
            }
        }
    }
}
