package paul.rxjava.create;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class TimerDemo {

    public static void main(String[] args) {
        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).flatMap((Func1<Integer, Observable<?>>) integer -> Observable.timer(3, TimeUnit.SECONDS));

        observable.toBlocking().subscribe(i -> {
            System.out.println("i = " + i);
        });
    }
}
