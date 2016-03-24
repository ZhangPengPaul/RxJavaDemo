import rx.Observable;
import rx.Subscriber;

/**
 * Created by PaulZhang on 2016/3/23.
 */
public class Hello {

    public static void main(String[] args) {
//        hello();
//        hell2();
//        hello3();
        convertMarkDown();
    }

    public static void hello() {
        String[] str = {"Paul", "Lily", "Essy"};
        Observable.from(str).subscribe(s -> {
            System.out.println("hello " + s + "!");
        });
    }


    public static void hell2() {
        Observable<String> observable = Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            subscriber.onNext("Hello RxJava");
            subscriber.onCompleted();
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("s = " + s);
            }
        };

        observable.subscribe(subscriber);
    }

    public static void hello3() {
        Observable.just("hello RxJava").subscribe(s -> {
            System.out.println("s = " + s);
        });
    }

    public static void convertMarkDown() {
        Observable.just("#Basic Markdown to HTML").map(s -> {
            if (s != null && s.startsWith("#")) {
                return "<h1>" + s.substring(1, s.length()) + "</h1>";
            }
            return null;
        }).subscribe(s -> {
            System.out.println("s = " + s);
        });
    }
}
