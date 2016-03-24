import rx.Observable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class ConditionalRetry {

    public static void main(String[] args) {
        final AtomicInteger c = new AtomicInteger();
        Observable<String> oWithRuntimeException = Observable.create(subscriber -> {
            System.out.println("Exec = " + c.get());
            if (c.incrementAndGet() < 3) {
                subscriber.onError(new RuntimeException("retryable"));
            } else {
                subscriber.onNext("hello");
                subscriber.onCompleted();
            }
        });

        final AtomicInteger c2 = new AtomicInteger();
        Observable<String> oWithIllegalStateException = Observable.create(subscriber -> {
            System.out.println("Exec = " + c2.get());
            if (c2.incrementAndGet() < 3) {
                subscriber.onError(new RuntimeException("retryable"));
            } else {
                subscriber.onError(new IllegalStateException());
            }
        });

        subscribe(oWithRuntimeException);
        subscribe(oWithIllegalStateException);
    }

    public static void subscribe(Observable<String> observable) {
        observable = observable.materialize().flatMap(n -> {
            if (n.isOnError()) {
                if (n.getThrowable() instanceof IllegalStateException) {
                    return Observable.just(n);
                } else {
                    return Observable.error(n.getThrowable());
                }
            } else {
                return Observable.just(n);
            }
        }).retry().dematerialize();

        observable.subscribe(System.out::println, t -> t.printStackTrace());
    }
}
