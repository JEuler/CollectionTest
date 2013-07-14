/**
 * Created with IntelliJ IDEA.
 * User: JEuler
 * Date: 14.07.13
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 * based on https://code.google.com/p/core-java-performance-examples/source/browse/trunk/src/test/java/com/google/code/java/core/collections/AddIterateRemoveTest.java,
 * credits to this mister
 */

import org.junit.Test;

import java.util.*;

public class AddIterateRemoveTest {
    static final int RUNS_TIME_MS = 10 * 1000;
    static final int SIZE = 1000 * 1000;
    static final int[] INTS = new int[SIZE];

    static {
        for (int i = 0; i < SIZE; i++)
            INTS[i] = i;
    }

    @Test
    public void perfomanceTest() {
        test(new TreeSet<Integer>());
        test(new ArrayList<Integer>());
        test(new LinkedList<Integer>());
        test(new HashSet<Integer>());
        test(new LinkedHashSet<Integer>());
    }

    private void test(Collection<Integer> ints) {
        test(ints, ints.getClass().getSimpleName());
    }

    private void test(Collection<Integer> ints, String collectionName) {
        for (int size = SIZE; size >= 10; size /= 10) {
            long adding = 0;
            long removing = 0;
            long iterating = 0;
            long searching = 0;

            int runs = 0;
            long endTime = System.currentTimeMillis() + RUNS_TIME_MS;
            do {
                runs++;
                long start = System.nanoTime() / 1000;
                testAdd(ints, size);

                adding += System.nanoTime() / 1000 - start;

                start = System.nanoTime() / 1000;
                for (int repeat = 0; repeat < 100; repeat++)
                    testIterate(ints);
                iterating += System.nanoTime() / 1000 - start;

                start = System.nanoTime() / 1000;
                testRemove(ints, size);
                removing += (System.nanoTime() / 1000 - start);

                start = System.nanoTime() / 1000;
                testSearch(ints);
                searching += (System.nanoTime() / 1000 - start);

                ints.clear();
            } while (endTime > System.currentTimeMillis());

            System.out.println("<tr><td>" + collectionName
                    + "</td><td aligned=\"right\">" + String.format("%,d", size)
                    + "</td><td aligned=\"right\">" + format(10 * adding / runs)
                    + "</td><td aligned=\"right\">" + format(iterating / runs)
                    + "</td><td aligned=\"right\">" + format(10 * removing / runs)
                    + "</td><td aligned=\"right\">" + format(10 * searching / runs)
                    + "</td></tr>"
            );
        }
    }

    private String format(long l) {
        return l < 1000 ?
                "" + (l / 10.0) :
                l < 10000 ?
                        "" + l / 10
                        : String.format("%,d", l / 10);
    }

    private static void testAdd(Collection<Integer> ints, int size) {
        // добавление
        for (int i = 0; i < size; i++)
            ints.add(INTS[i]);
    }

    private static long testIterate(Collection<Integer> ints) {
        // итерация
        long sum = 0;
        for (Integer i : ints)
            sum += i;
        return sum;
    }

    private static void testSearch(Collection<Integer> ints) {
        long searchEl = ints.size() - 1;
        ints.contains(searchEl);
    }

    private void testRemove(Collection<Integer> ints, int size) {
        // удаляем как спереди, так и сзади.
        for (int i = 0; i < size; i++) {
            ints.remove(INTS[i]);
        }
    }
}
