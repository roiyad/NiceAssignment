import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static final int NUM_OF_SHUFFLES = 2;
    public static void main(String[] args) {

    }

    public static int randomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }



    /**
     * N = number of numbers to print
     *      This approach trades off randomization for efficiency.
     *      It is O(n) running time and O(n) memory space, but for each draw,
     *      there approximately 2% that the draw is not random.
     *
     *      For one random choice, the probability that an index wasn't chosen is (n-1 / n )
     *      For a switch (which is 2 random choices), the probability is ((n-1)/n)^2 + (1 / n ) ^2.
     *      When the last addition is the situation, when the same index was selected twice in a switch,
     *      which means that the index switched with himself.
     *      Thus, for n rounds, the probability one specific index didn't switch places is
     *      ((n-1)/n)^2n + (1 / n ) ^2n
     *      For n >= 3, from plotting the function.
     *      ((n-1)/n)^ 2n + (1 / n) ^ 2n ~ 0.1354
     *      That means that the probability that a single number didn't switch location is 13%.
     *      If we make two shuffles the probability will be for n >= 3
     *      ((n-1) / 2)^ 4n ~ 0.018.
     *      so with 2n rounds of switches the probability that
     *      the index has been replaced at least one time is 1 - 0.0183 = 0.9817 = 98%.
     *      If we want to increase the probability that every index was chosen we can increase the NUM_OF_SHUFFLES,
     *      but this will also increase our running time.
     * @param min minimum number
     * @param max maximum number
     */
    public static void printNums(int min, int max) {
        if (min >= max) {
            return;
        }
        int[] array = IntStream.range(0, max - min + 1).map(i -> i + min).toArray();
        long switches = 0;
        while (switches < (long) array.length * NUM_OF_SHUFFLES) {
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
     * min and max and print randomly this number. If the number was already  printed,
     * it will print the greatest element that is smaller than the number,
     * or the smallest element that is bigger than the number.
     *   @param min minimum number
     *   @param max maximum number
     */
    public static void printNums2(int min, int max) {
        if (min >= max) {
            return;
        }
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.addAll(IntStream.range(0, max - min + 1).map(i -> i + min).boxed().collect(Collectors.toList()));
        while (!treeSet.isEmpty()) {
            int num = randomInRange(min, max);
            int choose = randomInRange(0,1);
            Integer ciel = treeSet.ceiling(num);
            Integer floor = treeSet.floor(num);
            if (choose == 0) {
                if (floor == null) {
                    System.out.println(ciel);
                    treeSet.remove(ciel);
                } else {
                    System.out.println(floor);
                    treeSet.remove(floor);
                }
            } else {
              if (ciel == null) {
                  System.out.println(floor);
                  treeSet.remove(floor);
              } else {
                  System.out.println(ciel);
                  treeSet.remove(ciel);              }
            }
        }
    }

}
