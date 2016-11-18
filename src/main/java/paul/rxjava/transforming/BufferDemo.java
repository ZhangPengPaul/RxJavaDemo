package paul.rxjava.transforming;

import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class BufferDemo {
    public static void main(String[] args) {
        String[] strings = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        Observable array = Observable.from(strings)
                .buffer(3);

        array.subscribe(s -> {
            System.out.println("s = " + s);
        });

        Observable skipArray = Observable.from(strings)
                .buffer(2, 3);

        skipArray.subscribe(s -> {
            System.out.println("s = " + s);
        });

        Observable closeSelector = Observable.from(strings)
                .buffer(() -> {
                    return Observable.timer(2, TimeUnit.SECONDS);
                });

        closeSelector.subscribe(s -> {
            System.out.println("s = " + s);
        });
    }
}
