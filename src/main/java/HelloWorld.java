import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class HelloWorld {

    public static void main(String[] args) {
        // Hello World
        Observable.create(subscriber -> {
            subscriber.onNext("Hello world");
            subscriber.onCompleted();
        }).subscribe(System.out::println);

        // just method
        Observable.just("Hello world!").subscribe(System.out::println);

        // add onError and onComplete
        Observable.just("Hello world!").subscribe(s -> System.out.println(),
                throwable -> throwable.printStackTrace(),
                () -> System.out.println("Done!"));


        // full
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello world");
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("DONE");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("s = " + s);
            }
        });

        // add error
        Observable.create(subscriber -> {
            try {
                subscriber.onNext("hello world");
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribe(System.out::println);

        // add concurrency (manually)
        Observable.create(subscriber -> new Thread(() -> {
            try {
                subscriber.onNext(getData());
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).start()).subscribe(System.out::println);

        // add concurrency (Scheduler)
        Observable.create(subscriber -> {
            try {
                subscriber.onNext(getData());
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io()).subscribe(System.out::println);

        // add operator
        Observable.create(subscriber -> {
            try {
                subscriber.onNext(getData());
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io()).map(data -> data + " ---> at: " + System.currentTimeMillis())
                .subscribe(System.out::println);

        // add error handling
        Observable.create(subscriber -> {
            try {
                subscriber.onNext(getData());
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .map(data -> data + " ---> at: " + System.currentTimeMillis())
                .onErrorResumeNext(e -> Observable.just("Fallback data"))
                .subscribe(System.out::println);

        // infinite
        Observable.create(subscriber -> {
            int i = 0;
            while (!subscriber.isUnsubscribed()) {
                subscriber.onNext(i++);
            }
        }).take(10).subscribe(System.out::println);

        Observable.create(subscriber -> {
            throw new RuntimeException("error");
        }).onErrorResumeNext(Observable.just("fall back"))
                .subscribe(System.out::println);

        Observable.create(subscriber -> {
            throw new RuntimeException("error");
        }).onErrorResumeNext(throwable -> {
            return Observable.just("fall back");
        }).subscribe(System.out::println);

        Observable.create(subscriber -> {
            throw new RuntimeException("error");
        }).retryWhen(handler -> handler.zipWith(Observable.range(1, 3), (throwable, i) -> i)
                .flatMap(i -> {
                    System.out.println("delay retry by = " + i + " second(s).");
                    return Observable.timer(i, TimeUnit.SECONDS);
                }).concatWith(Observable.error(new RuntimeException("Exceeded 3 retries")))).subscribe(System.out::println, t -> t.printStackTrace());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static String getData() {
        return "Got Data!";
    }
}
