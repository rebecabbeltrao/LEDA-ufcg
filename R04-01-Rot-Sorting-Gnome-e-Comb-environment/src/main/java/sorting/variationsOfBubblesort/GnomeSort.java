package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place!
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T> {
	public void sort(T[] array, int leftIndex, int rightIndex) {

		if (leftIndex < rightIndex || array != null || array.length != 0 || rightIndex != 0) {
			int index = leftIndex + 1;

			while (index <= rightIndex) {
				if (array[index].compareTo(array[index - 1]) >= 0) {
					index++;
				} else {
					Util.swap(array, index, index - 1);
					if (index > 1) {
						index--;
					} else {
						index++;
					}
				}
			}
		}
	}
}
