import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class ZipInterval {

    public static void main(String[] args) {
        Observable<String> data = Observable.just("one", "two", "three", "four", "five");
        Observable.zip(data, Observable.interval(1, TimeUnit.SECONDS), (s, t) -> s + " " + t)
                .toBlocking().forEach(System.out::println);
    }
}
