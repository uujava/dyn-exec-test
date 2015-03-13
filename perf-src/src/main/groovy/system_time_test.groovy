public class SystemTimeTester implements Runnable {
    long duration;

    public void run() {
        long start = System.currentTimeMillis();
        duration = System.currentTimeMillis() - start;
    }
}

new SystemTimeTester();