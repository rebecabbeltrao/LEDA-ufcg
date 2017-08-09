package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex || array != null || array.length != 0 || rightIndex != 0) {
			int gap = rightIndex;
			int i = leftIndex;
			int swaps = 0;

			while (gap != 1 || swaps == 0) {
				gap = (int) (gap / 1.25);
				if (gap < 1) {
					gap = 1;
				}

				i = 0;

				while (i + gap <= rightIndex) {
					if (array[i].compareTo(array[i + gap]) > 0) {
						Util.swap(array, i, i + gap);
						swaps = 1;
					}
					i++;
				}

			}

		}

	}

	
}
