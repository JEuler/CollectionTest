import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: JEuler
 * Date: 14.07.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public class EnumTest {
    static final int ONE = 1;
    static final int TWO = 2;

    enum TestEnum {ONE, TWO}

    @Test
    public void test() {
        testEnum();
        testInteger();
        time("enum", new Runnable() {
            public void run() {
                testEnum();

            }
        });
        time("integer", new Runnable() {
            public void run() {
                testInteger();
            }
        });
    }

    private static void testEnum() {
        TestEnum value = TestEnum.ONE;
        for (long i = 0; i < 10000000000L; i++) {
            if (value == TestEnum.TWO) {
                System.err.println("impossible");
            }
        }
    }

    private static void testInteger() {
        int value = ONE;
        for (long i = 0; i < 10000000000L; i++) {
            if (value == TWO) {
                System.err.println("impossible");
            }
        }
    }

    private static void time(String name, Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        System.err.println(name + ": " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
