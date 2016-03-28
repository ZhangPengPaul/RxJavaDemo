package paul.rxjava.create;


import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class IntervalDemo {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5};
        Observable intervalObservable = Observable.interval(3, TimeUnit.SECONDS).take(3);

        intervalObservable.toBlocking().subscribe(onNext -> {
            System.out.println("onNext = " + onNext);
        });
    }
}
