import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BucketSort {
    /*Make BucketSort.java, a program that takes an arbitrary file of doubles
    from standard input, then outputs them in ascending order, using the
    algorithm discussed in class. Your program should read the data into a
    java.util.ArrayList; use small ArrayLists (rather than linked lists) for
    the buckets; and merge the buckets back into the original ArrayList before
    outputting the results. A typical invocation of your program might look
    like this: java BucketSort < FileFullOfDoubles*/

    public static void main(String[] args) {
        // TODO: Implement negative doubles and small values ie -> 0.1
        ArrayList<ArrayList<Double>> buckets = new ArrayList<ArrayList<Double>>();

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

        sort(buckets, inputData);
    }

    public static void sort(ArrayList<ArrayList<Double>> buckets, ArrayList<Double> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
            buckets.add(new ArrayList<Double>());
        }

        double theMax = calculateMax(inputData);
        double integerPart = Math.floor(theMax);
        double divider = Math.ceil((integerPart + 1) / buckets.size());
        int floor = 0;

        for (int i = 0; i < inputData.size(); i++) {
            floor = (int)Math.floor(inputData.get(i)/divider);
            buckets.get(floor).add(inputData.get(i));
        }

        for (int i = 0; i < buckets.size(); i++) {
            if (buckets.get(i).size() != 0) {
                insertSort(buckets.get(i));
            }
        }

        ArrayList<Double> finalArray = new ArrayList<Double>();
        for (int i = 0; i < buckets.size(); i++) {
            if (buckets.get(i).size() != 0) {
                for (int j = 0; j < buckets.get(i).size(); j++) {
                    inputData.set(i, buckets.get(i).get(j));
                }
            }
        }

        System.out.println("Size: " + inputData.size());
        System.out.println(Arrays.toString(inputData.toArray()));
    }

    public static double calculateMax(ArrayList<Double> inputData) {
        double theMax = 0;

        for (int i = 0; i < inputData.size(); i++) {
            if (inputData.get(i) > theMax) {
                theMax = inputData.get(i).doubleValue();
            }
        }

        return theMax;
    }

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
