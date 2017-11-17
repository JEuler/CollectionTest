/**
 * Created with IntelliJ IDEA.
 * User: JEuler
 * Date: 14.07.13
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 * based on https://code.google.com/p/core-java-performance-examples/source/browse/trunk/src/test/java/com/google/code/java/core/collections/AddIterateRemoveTest.java,
 * credits to this mister
 */

import org.junit.Test

import java.util.*

class AddIterateRemoveTest {

    @Test
    fun perfomanceTest() {
        test(TreeSet<Integer>())
        test(ArrayList<Integer>())
        test(LinkedList<Integer>())
        test(HashSet<Integer>())
        test(LinkedHashSet<Integer>())
    }

    private fun test(ints: Collection<Integer>, collectionName: String = ints.getClass().getSimpleName()) {
        var size = SIZE
        while (size >= 10) {
            var adding: Long = 0
            var removing: Long = 0
            var iterating: Long = 0
            var searching: Long = 0
            val random: Long = 0

            var runs = 0
            val endTime = System.currentTimeMillis() + RUNS_TIME_MS
            do {
                runs++

                var start = System.nanoTime() / 1000
                testAdd(ints, size)
                adding += System.nanoTime() / 1000 - start

                start = System.nanoTime() / 1000
                for (repeat in 0..99)
                    testIterate(ints)
                iterating += System.nanoTime() / 1000 - start

                start = System.nanoTime() / 1000
                testSearch(ints)
                searching += System.nanoTime() / 1000 - start

                start = System.nanoTime() / 1000
                testRemove(ints, size)
                removing += System.nanoTime() / 1000 - start

                ints.clear()
            } while (endTime > System.currentTimeMillis())

            System.out.println("<tr><td>" + collectionName
                    + "</td><td aligned=\"right\">" + String.format("%,d", size)
                    + "</td><td aligned=\"right\">" + format(10 * adding / runs)
                    + "</td><td aligned=\"right\">" + format(iterating / runs)
                    + "</td><td aligned=\"right\">" + format(10 * searching / runs)
                    + "</td><td aligned=\"right\">" + format(10 * removing / runs)
                    + "</td></tr>"
            )
            size /= 10
        }
    }

    private fun format(l: Long): String {
        return if (l < 1000)
            "" + l / 10.0
        else if (l < 10000)
            "" + l / 10
        else
            String.format("%,d", l / 10)
    }

    private fun testRemove(ints: Collection<Integer>, size: Int) {
        for (i in 0..size - 1) {
            ints.remove(INTS[i])
        }
    }

    companion object {
        internal val RUNS_TIME_MS = 10 * 1000
        internal val SIZE = 1000 * 1000
        internal val INTS = IntArray(SIZE)
        internal val rand = Random()

        init {
            for (i in 0..SIZE - 1)
                INTS[i] = i
        }

        private fun testAdd(ints: Collection<Integer>, size: Int) {
            // добавление
            for (i in 0..size - 1)
                ints.add(INTS[i])
        }

        private fun testIterate(ints: Collection<Integer>): Long {
            // итерация
            var sum: Long = 0
            for (i in ints)
                sum += i.toLong()
            return sum
        }

        private fun testSearch(ints: Collection<Integer>) {
            val searchEl = ints.size() - 1
            ints.contains(searchEl)
        }
    }
}
