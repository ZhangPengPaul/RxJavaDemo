package paul.rxjava.create;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class RangeDemo {
    public static void main(String[] args) {
        Observable rangeObservable = Observable.range(1, 5);

        rangeObservable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("integer = " + integer);
            }
        });
    }
}
