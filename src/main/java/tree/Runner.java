package tree;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by PaulZhang on 2016/4/15.
 */
public class Runner implements Runnable {

    //    private static volatile boolean flag = false;
    private static AtomicBoolean flag = new AtomicBoolean(false);

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start flag = " + flag);
//        if (!flag) {
        if (flag.compareAndSet(false, true)) {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag.set(true);
//            System.out.println("flag = " + flag);
            System.out.println(Thread.currentThread().getName() + " =====================> working");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag.set(false);
        } else {
            System.out.println(Thread.currentThread().getName() + "give up.");
        }
    }
}
