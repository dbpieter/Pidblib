
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;
import java.util.Scanner;

/*
 *
 * @author Pieter De Bruyne
 *
 */
public class Algorithms {

    public static void parallelQuickSort(int[] arr) {
        parallelquicksort.ParallelQuicksort.sort(arr);
    }

    /*
     * return null if x is 0, don't use on too big numbers (smaller than 4000 preferably)
     */
    public static String toRoman(int x) {
        if (x == 0) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        int[] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] daStringz = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < 13; i++) {
            while (x >= numbers[i]) {
                x -= numbers[i];
                ret.append(daStringz[i]);
            }
        }
        return ret.toString();
    }

    public static void main(String[] args) {

    }
}
