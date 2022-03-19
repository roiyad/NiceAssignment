import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class main {

    public static void main(String[] args) {

    }

    public static int randomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }



    /**
     * N = number of numbers to print
     * @param min
     * @param max
     * O(N) running time and O(N) memory space
     * Random print by creating an array with all the numbers to print ,
       and shuffle the array randomly for 2N times.
     */
    public static void printNums(int min, int max) {
        if (min >= max) {
            return;
        }
        int[] array = IntStream.range(0, max - min + 1).map(i -> i + min).toArray();
        long switches = 0; // switches can be bigger then maximum integer
        while (switches < array.length * 2L) {
            int first = randomInRange(0, array.length - 1);
            int sec = randomInRange(0, array.length - 1);
            int temp = array[first];
            array[first] = array[sec];
            array[sec] = temp;
            switches++;
        }
        for (int num : array) {
            System.out.println(num);
        }
    }
    // The number of switches are 2N because
    // this guarantees that the probability for a spot to get changed is over 98%.
    // approximately for a large number but not larger than the capacity of int
    // the probability that an index didn't switch with another index in one round is (n - 1 / n) ^ 2
    // because there are two picks.
    // so for n round it's will be
    // ((n-1)/n)^ 2n ~ 0.134 but ((n-1) / 2)^ 4n ~ 0.018
    // so with 2 rounds of switches the probability that
    // every index has been replaced at least one time is 1 - 0.018 = 0.982


    // second solution
    // O(N) memory and O(Nlog(N)) runtime

    /**
     *
     * @param min
     * @param max
     * O(N) memory space O(NlogN) time
     * Random print by adding the numbers to Avl tree and choose a Number between
     * min and max and print randomly this number and if the number already was printed
     * it will print the greatest element that is smaller than the number
     * or the smallest element that is bigger than the number
     */
    public static void printNums2(int min, int max) {
        if (min >= max) {
            return;
        }
        TreeSet<Integer> avl = new TreeSet<>();
        avl.addAll(IntStream.range(0, max - min + 1).map(i -> i + min).boxed().collect(Collectors.toList()));
        while (!avl.isEmpty()) {
            int num = randomInRange(min, max);
            int choose = randomInRange(0,1);
            if (choose == 0) {
                if (avl.floor(num) == null) {
                    int n = avl.ceiling(num);
                    System.out.println(n);
                    avl.remove(n);
                } else {
                    int n = avl.floor(num);
                    System.out.println(n);
                    avl.remove(n);
                }
            } else {
              if (avl.ceiling(num) == null) {
                  int n = avl.floor(num);
                  System.out.println(n);
                  avl.remove(n);
              } else {
                  int n = avl.ceiling(num);
                  System.out.println(n);
                  avl.remove(n);              }
            }
        }
    }

}
