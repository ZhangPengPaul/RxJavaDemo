import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * Created by PaulZhang on 2016/3/24.
 */
public class GroupByLogic {

    public static void main(String[] args) {
        final TestScheduler testSchedule = Schedulers.test();
        final PublishSubject<PlayEvent> testSubject = PublishSubject.<PlayEvent>create();
        TestSubscriber<StreamState> ts = new TestSubscriber<>();

        testSubject.groupBy(playEvent -> playEvent.getOriginatorId())
                .flatMap(g -> {
                    System.out.println("***** new group: " + g.getKey());
                    return g.timeout(3, TimeUnit.SECONDS, testSchedule)
                            .distinctUntilChanged(PlayEvent::getSession)
                            .onErrorResumeNext(t -> {
                                System.out.println("     ***** complete group: " + g.getKey());
                                return Observable.empty();
                            }).reduce(new StreamState(), (state, playEvent) -> {
                                System.out.println("    state: " + state + "  event: " + playEvent.id + "-" + playEvent.session);
                                state.addEvent(playEvent);
                                return state;
                            }).filter(streamState -> true);
                }).doOnNext(streamState1 -> System.out.println(">>> Output State: " + streamState1)).subscribe(ts);

        testSubject.onNext(createPlayEvent(1, "a"));
        testSubject.onNext(createPlayEvent(2, "a"));
        testSchedule.advanceTimeBy(2, TimeUnit.HOURS);

        testSubject.onNext(createPlayEvent(1, "b"));
        testSchedule.advanceTimeBy(2, TimeUnit.HOURS);

        testSubject.onNext(createPlayEvent(1, "a"));
        testSubject.onNext(createPlayEvent(2, "b"));

        System.out.println("onNext after 4 hours: " + ts.getOnNextEvents());

        testSchedule.advanceTimeBy(3, TimeUnit.HOURS);

        System.out.println("onNext after 7 hours: " + ts.getOnNextEvents());

        testSubject.onCompleted();
        testSubject.onNext(createPlayEvent(2, "b"));

        System.out.println("onNext after complete: " + ts.getOnNextEvents());
        ts.assertTerminalEvent();
        ts.assertNoErrors();
    }

    public static PlayEvent createPlayEvent(int id, String v) {
        return new PlayEvent(id, v);
    }

    public static class PlayEvent {
        private int id;
        private String session;

        public PlayEvent(int id, String session) {
            this.id = id;
            this.session = session;
        }

        public int getOriginatorId() {
            return id;
        }

        public String getSession() {
            return session;
        }
    }

    public static class StreamState {
        private int id = -1;

        public void addEvent(PlayEvent event) {
            if (id == -1) {
                this.id = event.id;
            }
        }

        @Override
        public String toString() {
            return "StreamState{" +
                    "id=" + id +
                    '}';
        }
    }
}
