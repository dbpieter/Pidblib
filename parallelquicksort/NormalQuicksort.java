
package parallelquicksort;

/**
 *
 * @author Pieter De Bruyne
 *
 */
public class NormalQuicksort {

    private static int MAX_NON_RECURSIVE = 7;

    public static void sort(int[] array) {
        qsort(array, 0, array.length - 1);
    }

    private static void qsort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }
        if (start + MAX_NON_RECURSIVE > end) {
            insertionsort(array, start, end);
            return;
        }

        int pivotindex = partition(array, start, end);
        qsort(array, start, pivotindex - 1);
        qsort(array, pivotindex + 1, end);
    }

    private static int partition(int[] array, int start, int end) {
        int center = (start + end) / 2;
        //mediaan van 3
        if (array[center] < array[start]) {
            swap(array, center, start);
        }
        if (array[end] < array[start]) {
            swap(array, start, end);
        }
        if (array[end] < array[center]) {
            swap(array, center, end);
        }

        int pivotIndex = center;

        //spil achteraan
        swap(array, pivotIndex, end - 1);

        int pivot = array[end - 1];
        int left = start;
        int right = end - 1;
        while (array[++left] < pivot);
        while (pivot < array[--right]);

        while (left < right) {
            swap(array, left, right);
            while (array[++left] < pivot);
            while (pivot < array[--right]);
        }

        pivotIndex = left;
        swap(array, pivotIndex, end - 1);
        return pivotIndex;
    }

    private static void insertionsort(int[] array, int from, int to) {
        for (int i = from; i <= to; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j - 1] > array[j]) {
                    swap(array, j, j - 1);
                }
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
