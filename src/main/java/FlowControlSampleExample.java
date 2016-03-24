import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class FlowControlSampleExample {
    public static void main(String[] args) {
        hotStream().sample(500, TimeUnit.MILLISECONDS).toBlocking().forEach(System.out::println);

    }

    public static Observable<Integer> hotStream() {
        return Observable.create((Subscriber<? super Integer> s) -> {
            int i = 0;
            while (!s.isUnsubscribed()) {
                s.onNext(i++);
            }

            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {

            }
        }).subscribeOn(Schedulers.newThread());
    }
}
