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
     *      The number of switches are 2n rounds because
     *      this guarantees, that for any positive Integer value the probability for a spot to get changed is over 98%.
     *      and for a number that smaller than 10
     *      the probability that an index didn't switch with another one in a round is (n - 1 / n) ^ 2
     *      and the possibility that the same index has been chosen twice on the same round (1 / n) ^ 2
     *      because there are two picks.
     *      so for n rounds it's will be
     *      ((n-1)/n)^ 2n + (1 / n) ^ 2 ~ 0.1354 but ((n-1) / 2)^ 4n ~ 0.018.
     *      for example ((99/100) ^ 200) + (1 / 100)^ 200 = 0.1339, and (5436 / 5437) ^ 10874 + (1 / 5437) ^ 10874 = 0.1353.
     *      (9999 / 10000) ^ 40000 + (1/10000) ^ 40000 = 0.0183 , (1543212 / 1543211 ) ^ 6172848 + (1 / 1543211) ^ 6172848 = 0.01831
     *      so with 2n rounds of switches the probability that
     *      every index has been replaced at least one time is 1 - 0.0183 = 0.9817
     *        O(n) running time and O(n) memory space
     *       Random print by creating an array with all the numbers to print ,
     *        and shuffle the array randomly for 2n times.
     * @param min minimum number
     * @param max maximum number
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



    /**
     *
     * O(N) memory space O(NlogN) time
     * Random print by adding the numbers to  TreeSet and choose a number between
     * min and max and print randomly this number. if the number was already  printed ,
     * it will print the greatest element that is smaller than the number,
     * or the smallest element that is bigger than the number.
     *   @param min minimum number
     *   @param max maximum number
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
            Integer ciel = avl.ceiling(num);
            Integer floor = avl.floor(num);
            if (choose == 0) {
                if (floor == null) {
                    System.out.println(ciel);
                    avl.remove(ciel);
                } else {
                    System.out.println(floor);
                    avl.remove(floor);
                }
            } else {
              if (avl.ceiling(num) == null) {
                  System.out.println(floor);
                  avl.remove(floor);
              } else {
                  System.out.println(ciel);
                  avl.remove(ciel);              }
            }
        }
    }

}
