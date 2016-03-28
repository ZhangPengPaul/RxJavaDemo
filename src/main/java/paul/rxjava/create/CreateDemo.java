package paul.rxjava.create;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class CreateDemo {

    public static void main(String[] args) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        for (int i = 0; i < 5; i++) {
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).toBlocking()
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("on completed ");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("throwable = " + throwable);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("integer = " + integer);
                    }
                });
    }
}
