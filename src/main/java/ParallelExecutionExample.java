import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.List;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class ParallelExecutionExample {

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        Observable<Tile> searchTile = getSearchResult("search term")
                .doOnSubscribe(() -> logTime("search started", startTime))
                .doOnCompleted(() -> logTime("search completed", startTime));


        Observable<TileResponse> populatedTiles = searchTile.flatMap(t -> {
            Observable<Reviews> reviews = getSellerReviews(t.getSellerId())
                    .doOnCompleted(() -> logTime("getSellerReview[" + t.getSellerId() + " completed]", startTime));
            Observable<String> imageUrl = getProductImage(t.getPorductId())
                    .doOnCompleted(() -> logTime("getProductImage[" + t.getPorductId() + " completed]", startTime));

            return Observable.zip(reviews, imageUrl, (r, u) -> new TileResponse(t, r, u))
                    .doOnCompleted(() -> logTime("zip [" + t.id + "]", startTime));
        });

        List<TileResponse> allResponse = populatedTiles.toList().doOnCompleted(() -> logTime("All tiles completed ", startTime))
                .toBlocking().single();
    }

    public static void run() {
        Observable<Tile> searchTile = getSearchResult("search term");
        Observable<TileResponse> populatedTiles = searchTile.flatMap(t -> {
            Observable<Reviews> reviews = getSellerReviews(t.getSellerId());
            Observable<String> imageUrl = getProductImage(t.getPorductId());
            return Observable.zip(reviews, imageUrl, (r, u) -> new TileResponse(t, r, u));
        });

        List<TileResponse> allTiles = populatedTiles.toList().toBlocking().single();
    }

    public static class TileResponse {

        public TileResponse(Tile t, Reviews r, String u) {
            // store the values
        }

    }

    private static void logTime(String message, long startTime) {
        System.out.println(message + " => " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static Observable<String> getProductImage(int id) {
        return mockClient("image_" + id);
    }

    private static Observable<Reviews> getSellerReviews(int id) {
        return mockClient(new Reviews());
    }

    private static Observable<Tile> getSearchResult(String string) {
        return mockClient(new Tile(1), new Tile(2), new Tile(3));
    }

    private static <T> Observable<T> mockClient(T... ts) {
        return Observable.create((Subscriber<? super T> s) -> {
            // simulate latency
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            for (T t : ts) {
                s.onNext(t);
            }
            s.onCompleted();

        }).subscribeOn(Schedulers.io());
    }

    public static class Tile {

        private final int id;

        public Tile(int id) {
            this.id = id;
        }

        public int getSellerId() {
            return id;
        }

        public int getPorductId() {
            return id;
        }
    }

    public static class Reviews {

    }
}
