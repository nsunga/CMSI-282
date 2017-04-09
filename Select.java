import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.Integer;
import java.lang.IndexOutOfBoundsException;
import java.lang.Exception;

public class Select {
	public static void main(String[] args) throws IOException {
		String kthElem = args[0];
		Random rng = new Random();

		ArrayList<Integer> inputData = new ArrayList<Integer>();
		BufferedReader stdIn = new BufferedReader(new InputStreamReader (System.in));

		try {
			String s = stdIn.readLine();

			while(s != null) {
				inputData.add(Integer.valueOf(s));
				s = stdIn.readLine();
			} // this should get everything from the file
		} catch (IOException e) {
			System.out.println("BAD DATA");
		}

		try {
			if (Integer.parseInt(kthElem) == 0) {
				throw new ZeroException();
			}
			System.out.println(doSelect(inputData, Integer.parseInt(kthElem)));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("BAD DATA");
		} catch (ZeroException e) {
			System.out.println("BAD DATA");
		}
	}

	public static Integer doSelect(ArrayList<Integer> theArray, int k) {
		int right = theArray.size() - 1;
		int left = 0;
		int pivotIndex = (int)(Math.random() * theArray.size());
		boolean swapRight = false;
		boolean swapLeft = false;

		while (left <= right) {
			// start at right side
			while (true) {
				if (theArray.get(right) > theArray.get(pivotIndex)) {
					right--;
				} else {
					swapRight = true;
					break;
				}
			}

			// swap pivot w/ right
			if (swapRight) {
				Integer temp = theArray.get(right);
				theArray.set(right, theArray.get(pivotIndex));
				theArray.set(pivotIndex, temp);
				pivotIndex = right;
				right--;
				swapRight = false;
			} else {
				;
			}

			// start at left side
			while (true && (left <= right)) {
				if (theArray.get(left) < theArray.get(pivotIndex)) {
					left++;
				} else {
					swapLeft = true;
					break;
				}
			}

			// swap pivot w/ left
			if (swapLeft) {
				Integer temp = theArray.get(left);
				theArray.set(left, theArray.get(pivotIndex));
				theArray.set(pivotIndex, temp);
				pivotIndex = left;
				left++;
				swapLeft = false;
			}
		}

		if ((k-1) > pivotIndex) {
			theArray = new ArrayList<Integer>(theArray.subList((pivotIndex + 1), theArray.size()));
			return doSelect(theArray, (k - (pivotIndex + 1)));
		} else if ((k-1) < pivotIndex) {
			theArray = new ArrayList<Integer>(theArray.subList(0, pivotIndex));
			return doSelect(theArray, k);
		} else {
			return theArray.get(k-1);
		}
	}
}

class ZeroException extends Exception {
	public ZeroException() {

	}
}
