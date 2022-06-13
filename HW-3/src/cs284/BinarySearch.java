//Brian Sampson
//CS284
//Assignment 3
//I pledge my honor that I have abided by the Stevens Honor System.

package cs284;

public class BinarySearch {
	public static <E extends Comparable<? super E>> int binarySearch(E elt, E[] a) {

		// Should call a function that accepts upper and lower bounds for binarySearch
		return recBS(elt, a, a.length - 1, 0);
	}

	public static <E extends Comparable<? super E>> int recBS(E elt, E[] a, int upper, int lower) {

		int middle = lower + ((upper - lower) / 2);

		// Check for bad value
		if (upper < lower) {
			return -1;

			// Return middle if its correct value
		} else if (elt.compareTo(a[middle]) == 0) {
			return middle;

			// Return bottom half if elt is below middle
		} else if (elt.compareTo(a[middle]) < 0) {
			return recBS(elt, a, middle - 1, lower);

			// Return upper half if elt is above middle
		} else if (elt.compareTo(a[middle]) > 0) {
			return recBS(elt, a, upper, middle + 1);
		}

		// If elt is not found in list then return -1
		return -1;

	}

	public static void main(String[] args) {
		Integer[] test = new Integer[10000000];

		for (int i = 0; i < 10000000; i = i + 1) {
			test[i] = i;
		}

		System.out.println(binarySearch(8724543, test));

	}

}
