package paul.rxjava.create;

import rx.Observable;
import rx.util.async.Async;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class StartDemo {
    public static void main(String[] args) {
        System.out.println("asyncAction ================= ");
        asyncAction();
    }

    public static void asyncAction() {
        Observable observable = Async.start(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        observable.toBlocking().subscribe(i -> {
            System.out.println("i = " + i);
        });
    }
}
