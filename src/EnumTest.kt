import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: JEuler
 * Date: 14.07.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
class EnumTest {

    internal enum class TestEnum {
        ONE, TWO
    }

    @Test
    fun test() {
        testEnum()
        testInteger()
        time("enum", object : Runnable() {
            fun run() {
                testEnum()

            }
        })
        time("integer", object : Runnable() {
            fun run() {
                testInteger()
            }
        })
    }

    companion object {
        internal val ONE = 1
        internal val TWO = 2

        private fun testEnum() {
            val value = TestEnum.ONE
            for (i in 0..10000000000L - 1) {
                if (value == TestEnum.TWO) {
                    System.err.println("impossible")
                }
            }
        }

        private fun testInteger() {
            val value = ONE
            for (i in 0..10000000000L - 1) {
                if (value == TWO) {
                    System.err.println("impossible")
                }
            }
        }

        private fun time(name: String, runnable: Runnable) {
            val startTime = System.currentTimeMillis()
            runnable.run()
            System.err.println(name + ": " + (System.currentTimeMillis() - startTime) + " ms")
        }
    }
}
