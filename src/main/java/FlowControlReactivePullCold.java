import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class FlowControlReactivePullCold {

    public static void main(String[] args) {
        getData(1).observeOn(Schedulers.computation()).toBlocking().forEach(System.out::println);

    }

    public static Observable<Integer> getData(int id) {
        final List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            data.add(i + id);
        }
        return forInterable(data);
    }

    public static Observable<Integer> forInterable(Iterable<Integer> iterable) {
        return Observable.create((Subscriber<? super Integer> s) -> {
            final Iterator<Integer> iterator = iterable.iterator();
            final AtomicLong requested = new AtomicLong();
            s.setProducer(l -> {
                if (requested.getAndAdd(l) == 0) {
                    do {
                        if (s.isUnsubscribed()) {
                            return;
                        }

                        if (iterator.hasNext()) {
                            s.onNext(iterator.next());
                        } else {
                            s.onCompleted();
                        }
                    } while (requested.decrementAndGet() > 0);
                }
            });
        });
    }
}
