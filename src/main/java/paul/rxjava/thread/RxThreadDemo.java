package paul.rxjava.thread;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.Callable;

/**
 * Created by PaulZhang on 2016/11/17.
 */
public class RxThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        RxThreadDemo rxThreadDemo = new RxThreadDemo();
        // blocking
//        System.out.println("========================= blocking start ==============================");
//        Observable.fromCallable(rxThreadDemo.returnNumberOne())
//                .map(rxThreadDemo.numberToString())
//                .subscribe(rxThreadDemo.printResult());
//        System.out.println("========================= blocking end ==============================");
//
//        System.out.println("========================= subscribeOn start ==============================");
//        Observable.fromCallable(rxThreadDemo.returnNumberOne())
//                .subscribeOn(Schedulers.io())
//                .map(rxThreadDemo.numberToString())
//                .subscribe(rxThreadDemo.printResult());
//        System.out.println("========================= subscribeOn end ==============================");

        System.out.println("========================= observeOn 1 start ==============================");
        Observable.fromCallable(rxThreadDemo.returnNumberOne())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(rxThreadDemo.numberToString())
                .observeOn(Schedulers.computation())
                .subscribe(rxThreadDemo.printResult(),
                        Throwable::printStackTrace);
        System.out.println("========================= observeOn 1 end ==============================");

        Thread.sleep(10000L);

    }

    private Callable<Integer> returnNumberOne() {
        return () -> {
            Thread.sleep(1000L);
            System.out.println("Observable Thread: " + Thread.currentThread().getName());
            throw new Exception();

        };
    }

    private Func1<Integer, String> numberToString() {
        return number -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Operator Thread: " + Thread.currentThread().getName());
            return String.valueOf(number);
        };

    }

    private Action1<String> printResult() {
        return result -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Subscriber Thread: " + Thread.currentThread().getName());
            System.out.println("Result: " + result);
        };

    }
}
