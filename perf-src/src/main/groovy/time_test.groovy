public class TimeTester implements Runnable {
    long duration;

    public void run() {
         Date a = new Date();
         duration = a.getTime() - new Date().getTime();
    }
}

new TimeTester();