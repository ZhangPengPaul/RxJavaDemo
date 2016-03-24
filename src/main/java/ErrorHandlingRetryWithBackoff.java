import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class ErrorHandlingRetryWithBackoff {
    public static void main(String[] args) {
        Observable.create(subscriber -> {
            System.out.println("1) subscribing");
            subscriber.onError(new RuntimeException("1) always failed"));
        }).retry(3).subscribe(System.out::println, throwable -> System.out.println("(1)Error:" + throwable));

        System.out.println(" ================ ");

        Observable.create(subscriber -> {
            System.out.println("2) subscribing ");
            subscriber.onError(new RuntimeException("2) always failed"));
        }).retryWhen(observable -> observable.zipWith(Observable.range(1, 3), (o, integer) -> integer).flatMap(integer1 -> {
            System.out.println("retry delay by " + integer1 + " second(s)");
            return Observable.timer(integer1, TimeUnit.SECONDS);
        }).concatWith(Observable.error(new RuntimeException("Failed after 3 retries")))).toBlocking().forEach(System.out::println);
    }
}
