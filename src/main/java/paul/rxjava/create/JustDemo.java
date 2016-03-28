package paul.rxjava.create;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class JustDemo {
    public static void main(String[] args) {
        Observable justObservable = Observable.just(1, 2, 3, 4, 5);

        justObservable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.print("integer = [" + integer + "]");
            }

        });
    }
}
