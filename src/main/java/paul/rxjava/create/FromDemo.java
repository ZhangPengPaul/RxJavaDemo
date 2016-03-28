package paul.rxjava.create;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by PaulZhang on 2016/3/28.
 */
public class FromDemo {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7};

        Observable myObservable = Observable.from(array);
        myObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                System.out.println("on next o = " + o);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("throwable = " + throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("on completed");
            }
        });
    }
}
