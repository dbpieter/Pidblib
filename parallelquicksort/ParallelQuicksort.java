package parallelquicksort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/*
 * author: Pieter De Bruyne 2012
 * email: dbpieter@gmail.com
 * inspired by http://dlang.org/phobos/std_parallelism.html
 * Usage ParallelQuicksort.sort(someArrayOfInts)
 * 
 * distributed under IDC (I don't care) license
 */
public class ParallelQuicksort extends RecursiveAction {

    // when array length is smaller than this number, sorting happens in sequence (recursive)
    private static final int MAX_SINGLE_THREAD = 10000;
    // when array length is smaller than this number, sorting happens non recursively (insertionsort)
    private static final int MAX_NON_RECURSIVE = 7;
    
    private int[] array;
    private int start;
    private int end;

    private ParallelQuicksort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    private ParallelQuicksort(int[] array) {
        this(array, 0, array.length - 1);
    }

    /*
     * Receives first call
     */
    public static void sort(int[] array) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        pool.invoke(new ParallelQuicksort(array));
    }

    @Override
    protected void compute() {
        if (end - start < MAX_NON_RECURSIVE) {
            insertionsort();
            return;
        }
        if (end - start < MAX_SINGLE_THREAD) {
            Arrays.sort(array, start, end+1); //'standard' java dual pivot quicksort
            return;
        }
        
        int pivotIndex = partition();
        
        ForkJoinTask task = null;
        task = new ParallelQuicksort(array, pivotIndex + 1, end).fork(); //sort right side in parallel
        new ParallelQuicksort(array, start, pivotIndex - 1).invoke(); //sort left side in sequence
        task.join(); //wait until the right side has been sorted
    }

    /*
     * As described in "Algoritmen en Datastructuren - Veerle Fack"
     */
    private int partition() {
        int center = (start + end) / 2;

        if (array[center] < array[start]) {
            swap(center, start);
        }
        if (array[end] < array[start]) {
            swap(start, end);
        }
        if (array[end] < array[center]) {
            swap(center, end);
        }

        int pivotIndex = center;

        swap(pivotIndex, end - 1);

        int pivot = array[end - 1];
        int left = start;
        int right = end - 1;
        
        while (array[++left] < pivot);
        while (pivot < array[--right]);

        while (left < right) {
            swap(left, right);
            while (array[++left] < pivot);
            while (pivot < array[--right]);
        }

        pivotIndex = left;
        swap(pivotIndex, end - 1);
        return pivotIndex;
    }

    /*
     * Simple insertionsort
     */
    private void insertionsort() {
        for (int i = start; i <= end; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j - 1] > array[j]) {
                    swap(j, j - 1);
                }
            }
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
