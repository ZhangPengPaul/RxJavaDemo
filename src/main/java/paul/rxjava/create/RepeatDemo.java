package paul.rxjava.create;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class RepeatDemo {

    public static void main(String[] args) {
        System.out.println("repeat ------------------------- ");
//        repeat();

        System.out.println("repeat times------------------------- ");
        repeatTimes(3);

        System.out.println("repeat when------------------------- ");
        repeatWhen();
    }

    public static void repeat() {
        Observable repeat = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).repeat();

        repeat.subscribe(i -> {
            System.out.println("i = " + i);
        });

    }

    public static void repeatTimes(long times) {
        Observable repeat = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).repeat(times);

        repeat.subscribe(i -> {
            System.out.println("i = " + i);
        });
    }

    public static void repeatWhen() {
        Observable repeat = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                return observable.take(3);
            }
        });

        repeat.subscribe(i -> {
            System.out.println("i = " + i);
        });
    }
}
