package paul.rxjava.create;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/29.
 */
public class CreateFactories {

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
//        subscribePrint(Observable.interval(500L, TimeUnit.MILLISECONDS), "Interval Observable");
//
//        subscribePrint(Observable.timer(0, TimeUnit.SECONDS), "Timed Interval Observable");
//
//        subscribePrint(
//                Observable.error(new Exception("Test Error!")),
//                "Error Observable"
//        );
//
//        subscribePrint(Observable.empty(), "Empty Observable");
//
//        subscribePrint(Observable.never(), "Never Observable");
//
//        subscribePrint(Observable.range(1, 3), "Range Observable");
//        Thread.sleep(2000L);

        hotSubObservable();

        subject();
    }

    public static Subscription subscribePrint(Observable observable, String name) {
        return observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                System.out.println("ended!");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error from " + name + " : ");
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onNext(Object o) {
                System.out.println(name + " : " + o);
            }
        });
    }

    public static void hotSubObservable() {
        Observable observable = Observable.interval(100, TimeUnit.MILLISECONDS);
        // publish() 方法，后加入的subscriber不会收到之前发出的信息
//        ConnectableObservable connectableObservable = observable.publish();
        // replay()方法方法，后加入的subscriber依然会顺序收到之前发出的信息
        ConnectableObservable connectableObservable = observable.replay();
        Subscription subscription1 = subscribePrint(connectableObservable, "First");
        Subscription subscription2 = subscribePrint(connectableObservable, "Second");
        connectableObservable.connect();

        Subscription subscription3 = null;
        try {
            Thread.sleep(500);
            subscription3 = subscribePrint(connectableObservable, "Third");
            Thread.sleep(500);

        } catch (InterruptedException e) {
        }

        subscription1.unsubscribe();
        subscription2.unsubscribe();
        subscription3.unsubscribe();
    }

    public static void subject() throws UnsupportedEncodingException {
        Observable interval = Observable.interval(100L, TimeUnit.MILLISECONDS);
        Subject publishSubject = PublishSubject.create();

        interval.subscribe(publishSubject);
        Subscription sub1 = subscribePrint(publishSubject, "Subject First");
        Subscription sub2 = subscribePrint(publishSubject, "Subject Second");
        Subscription sub3 = null;

        try {
            Thread.sleep(300L);
            publishSubject.onNext(555L);
            sub3 = subscribePrint(publishSubject, "Subject Third");
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sub1.unsubscribe();
        sub2.unsubscribe();
        sub3.unsubscribe();

        URI uri = URI.create("http://www.tingshuotong.com:8081/uxue/waiyan/zip/1q4s/Module 1.zip");
        URI uri2 = URI.create(URLEncoder.encode("http://www.tingshuotong.com:8081/uxue/waiyan/zip/1q4s/Module 1.zip", "UTF-8"));
//        System.out.println("uri = " + uri.toString());
        System.out.println("uri2 = " + uri2.toString());
    }
}
