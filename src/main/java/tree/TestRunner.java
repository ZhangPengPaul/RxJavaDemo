package tree;

/**
 * Created by PaulZhang on 2016/4/15.
 */
public class TestRunner {

    public static void main(String[] args) throws InterruptedException {
        Runner runner1 = new Runner();
        Runner runner2 = new Runner();
        Runner runner3 = new Runner();
        Runner runner4 = new Runner();

        new Thread(runner1).start();
        new Thread(runner2).start();
        new Thread(runner3).start();
        new Thread(runner4).start();

//        Thread.sleep(100000L);
    }
}
