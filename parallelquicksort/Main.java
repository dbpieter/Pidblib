
package parallelquicksort;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Pieter De Bruyne
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] array = new int[200000000];
        
        fillArray(array);
        System.gc();
        long before = System.currentTimeMillis();
        Arrays.sort(array);
        System.out.println("STL: "+(System.currentTimeMillis()-before));
        
        fillArray(array);
        System.gc();
        long beforeParallel = System.currentTimeMillis();
        ParallelQuicksort.sort(array);
        System.out.println("Parallel: "+(System.currentTimeMillis()-beforeParallel));
        

    }

    private static void fillArray(int[] array) {
        Random rand = new Random(20);
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt();
        }
    }
}
