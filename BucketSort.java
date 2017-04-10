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
        // I think its complete!
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

    public static void sort(ArrayList<ArrayList<Double>> posBucket, ArrayList<ArrayList<Double>> negBucket, ArrayList<Double> inputData) {
        ArrayList<Double> negList = new ArrayList<Double>();
        ArrayList<Double> posList = new ArrayList<Double>(); // zero in here too

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

        sortPos(posBucket, posList);
        sortNeg(negBucket, negList);


        ArrayList<Double> finalArray = new ArrayList<Double>();

        // TODO: negatives are sorted but from the back. Insert them into the
        // original list. Positives are sorted. insert them into the original
        // list. I think its complete!

        int negIndex = 0;
        for (int i = negBucket.size() - 1; i > -1; i--) {
            if (negBucket.get(i).size() != 0) {
                for (int j = negBucket.get(i).size() - 1; j > -1; j--) {
                    finalArray.add(negBucket.get(i).get(j));
                    inputData.set(negIndex++, negBucket.get(i).get(j));
                }
            }
        }

        for (int i = 0; i < posBucket.size(); i++) {
            if (posBucket.get(i).size() != 0) {
                for (int j = 0; j < posBucket.get(i).size(); j++) {
                    inputData.set(negIndex++, posBucket.get(i).get(j));
                }
            }
        }

        System.out.println("Size: " + inputData.size());
        System.out.println(Arrays.toString(inputData.toArray()));
    }

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

    public static void sortNeg(ArrayList<ArrayList<Double>> negBucket, ArrayList<Double> negList) {
        for (int i = 0; i < negList.size(); i++) {
            negList.set(i, negList.get(i) * -1);
        }
        sortPos(negBucket, negList);
        // Sorted like a positive list. Need to start from the end, and switch
        // with an elem in the beginning. Wait, no I dont. Times everything by
        // -1, then just insert or print the list from the back. New double arrayList?
        for (int i = 0; i < negBucket.size(); i++) {
            if (negBucket.get(i).size() != 0) {
                for (int j = 0; j < negBucket.get(i).size(); j++) {
                    negBucket.get(i).set(j, negBucket.get(i).get(j) * -1);
                }
            }
        }
        // "Sorted" like a positive list. Times everything by -1

    }

    public static double calculateMax(ArrayList<Double> list) {
        double theMax = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > theMax) {
                theMax = list.get(i).doubleValue();
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
