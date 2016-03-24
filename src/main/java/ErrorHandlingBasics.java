import rx.Observable;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class ErrorHandlingBasics {

    public static void main(String[] args) {
        Observable.create(subscriber -> {
            throw new RuntimeException("error");
        }).subscribe(System.out::println, throwable -> System.out.println("(1)Error:" + throwable));

        Observable.create(subscriber -> {
            throw new RuntimeException("error");
        }).subscribe(System.out::println, throwable -> System.out.println("(2)Error:" + throwable));

        Observable.just("hello").map(s -> {
            throw new RuntimeException("error");
        }).subscribe(System.out::println, throwable -> System.out.println("(3)Error:" + throwable));

        Observable.just(true).map(b -> {
            if (b) {
                return Observable.error(new RuntimeException("error"));
            } else {
                return Observable.just("data", "here");
            }
        }).subscribe(System.out::println, throwable -> System.out.println("(4)Error:" + throwable));

        Observable.error(new RuntimeException("error"))
                .onErrorResumeNext(Observable.just("5) data"))
                .subscribe(System.out::println, throwable -> System.out.println("(5)Error:" + throwable));

        Observable.error(new RuntimeException("error"))
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof IllegalStateException) {
                        return Observable.error(throwable);
                    } else {
                        return Observable.just("6) data");
                    }
                })
                .subscribe(System.out::println, throwable1 -> System.out.println("(6)Error:" + throwable1));
    }
}
