//Brian Sampson
//CS284
//Assignment 3
//I pledge my honor that I have abided by the Stevens Honor System.

package cs284;

import java.util.*;

public class Permutations {
	public static <E> List<List<E>> allInsertions(E elt, List<E> l) {
		int size = l.size();

		List<List<E>> ret = new LinkedList<List<E>>();

		for (int i = 0; i < size + 1; i = i + 1) {
			List<E> small = new LinkedList<E>();

			// Makes hard copy
			for (int x = 0; x < size; x = x + 1) {

				small.add(l.get(x));
			}
			small.add(null);

			for (int y = size; y > i; y = y - 1) {
				small.set(y, small.get(y - 1));
			}

			// Set element to elt and all hard copy list to ret value
			small.set(i, elt);
			ret.add(small);
		}
		return ret;
	}

	public static <E> List<List<E>> allPermutations(List<E> l) {

		// Check if list is empty...
		if (l.isEmpty()) {
			List<List<E>> empty = new LinkedList<List<E>>();
			empty.add(new LinkedList<E>());
			return empty;
		}

		return helper(l, new LinkedList<List<E>>(), l.size());
	}

	// Helper function for allPermutations
	public static <E> List<List<E>> helper(List<E> l, List<List<E>> tempLst, int r) {
		if (r == 0) {
			return tempLst;
		}

		List<E> tempLst2 = new LinkedList<E>();

		for (int i = 0; i < l.size(); i = i + 1) {
			tempLst2.add(l.get(i));
		}

		helper(l, tempLst, r - 1);

		if (!tempLst.contains(tempLst2)) {
			tempLst.add(tempLst2);
		}

		for (int j = 0; j < r; j = j + 1) {
			// When j < r - 1, replace...
			if (j < r - 1) {
				if (r % 2 == 1) {
					E temp = l.get(0);
					l.set(0, l.get(r - 1));
					l.set(r - 1, temp);

					// When r - 1 % 2 == 1, replace...
				} else if ((r - 1) % 2 == 1) {
					E temp = l.get(j);
					l.set(j, l.get(r - 1));
					l.set(r - 1, temp);
				}
			}
			helper(l, tempLst, r - 1);
		}

		return helper(l, tempLst, r - 1);

	}

	public static void main(String[] args) {

		List test = new ArrayList<>();

		test.add("a");
		test.add("b");
		test.add("c");
		test.add("d");
		test.add("e");

		// System.out.println(allInsertions("!", test));

		// [!,0,1,2] , [0,!,1,2] , [0,1,!,2] , [0,1,2 ,!]

		System.out.print(allPermutations(test));

	}

}