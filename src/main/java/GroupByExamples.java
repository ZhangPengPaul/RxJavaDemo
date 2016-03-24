import rx.Observable;
import rx.functions.Func2;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class GroupByExamples {

    public static void main(String[] args) {
        // odd/even into 2 lists
        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> g.toList()).forEach(System.out::println);

        System.out.println("2--------------------------------------------------------------------------------------------------------");

        // odd/even into lists of 10
        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> g.take(10).toList()).forEach(System.out::println);

        System.out.println("3--------------------------------------------------------------------------------------------------------");

        //odd/even into lists of 10
        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> g.filter(i -> i <= 20).toList()).forEach(System.out::println);

        System.out.println("4--------------------------------------------------------------------------------------------------------");

        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> g.take(20).toList()).take(2).forEach(System.out::println);

        System.out.println("5--------------------------------------------------------------------------------------------------------");

        Observable.range(1, 100)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> g.filter(i -> i <= 30).toList())
                .forEach(System.out::println);

        System.out.println("6--------------------------------------------------------------------------------------------------------");

        Observable.from(Arrays.asList("a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c"))
                .groupBy(n -> n)
                .flatMap(g -> g.take(6).reduce((Func2<String, String, String>) (s, s2) -> s + s2))
                .forEach(System.out::println);

        System.out.println("7--------------------------------------------------------------------------------------------------------");

        Observable.timer(0, 1, TimeUnit.MILLISECONDS).take(20)
                .groupBy(n -> n % 2 == 0)
                .flatMap(g -> g.toList()).toBlocking().forEach(System.out::println);

        System.out.println("8--------------------------------------------------------------------------------------------------------");

        Observable.timer(0, 1, TimeUnit.MILLISECONDS).groupBy(n -> n % 2 == 0)
                .flatMap(g -> g.take(5).toList()).take(4).toBlocking().forEach(System.out::println);
    }
}
