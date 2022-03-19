import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class main {

    public static void main(String[] args) {
        printNums2(1,5000000);
    }

    public static int randomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }


    // N = number of numbers to print
    // O(N) memory and O(N) running time
    public static void printNums(int min, int max) {
        if (max >= max) {
            return;
        }
        int[] array = IntStream.range(0, max - min + 1).map(i -> i + min).toArray();
        long switches = 0; // switches can be bigger then maximum integer
        while (switches < array.length * 2) {
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
    // this guarantees that the probability for a spot to get changed is over 97%.
    // approximatley for a large number but not larger then the capacity of int
    // 1 - ((n-1)/n)^ n = 0.632 but 1 - ((n-1) / 2)^ 6n = 0.98
    // and this will get us that every random order of print is possible


    // second solution
    // O(N) memory and O(Nlog(N)) runtime
    public static void printNums2(int min, int max) {
        if (max >= max) {
            return;
        }
        TreeSet<Integer> avl = new TreeSet<>();
        avl.addAll(IntStream.range(0, max - min + 1).map(i -> i + min).boxed().collect(Collectors.toList()));
        while(!avl.isEmpty()) {
            int num = randomInRange(min, max);
            int choose = randomInRange(0,1);
            if (choose == 0) {
                if (avl.floor(num) == null) {
                    System.out.println(avl.ceiling(num));
                    avl.remove(avl.ceiling(num));
                } else {
                    System.out.println(avl.floor(num));
                    avl.remove(avl.floor(num));
                }
            } else {
              if (avl.ceiling(num) == null) {
                  System.out.println(avl.floor(num));
                  avl.remove(avl.floor(num));
              } else {
                  System.out.println(avl.ceiling(num));
                  avl.remove(avl.ceiling(num));
              }
            }
        }
    }

}
