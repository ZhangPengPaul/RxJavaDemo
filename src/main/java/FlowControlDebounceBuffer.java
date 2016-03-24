import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class FlowControlDebounceBuffer {

    public static void main(String[] args) {
        Observable<Integer> bustsStream = intermittentBursts().take(20).publish().refCount();
        Observable<Integer> debounced = bustsStream.debounce(10, TimeUnit.SECONDS);
        Observable<List<Integer>> buffered = bustsStream.buffer(debounced);
        buffered.toBlocking().forEach(System.out::println);
    }

    public static Observable<Integer> intermittentBursts() {
        return Observable.create((Subscriber<? super Integer> subscriber) -> {
            while (!subscriber.isUnsubscribed()) {
                for (int i = 0; i < Math.random() * 20; i++) {
                    subscriber.onNext(i);
                }

                try {
                    Thread.sleep((long) (Math.random() * 1000L));
                } catch (InterruptedException e) {

                }
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
