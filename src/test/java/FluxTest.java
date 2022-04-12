import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FluxTest {
    private static final List<String> LIST_OF_VALUES = Arrays.asList("A", "B", "C");
    private static final Stream INT_STREAM = Stream.of(1, 2, 3, 4);

    /**
     * Creating a Flux with values.
     */
    @Test
    void firstFlux() {
        Flux.just("A", "B", "C")
                .log()
                .subscribe();
    }

    /**
     * Creating flux from list.
     */
    @Test
    void fluxFromIterable() {
        Flux.just(Arrays.asList("A", "B", "C"))
                .log()
                .subscribe();
        /* obs all values will be passed to the subscriber (onNext) as:
        onNext[A,B,C]
         */
    }

    /**
     * Creating flux from list and iterating values
     */
    @Test
    void fluxFromItarable() {
        Flux.fromIterable(FluxTest.LIST_OF_VALUES)
                .log()
                .subscribe();
    }

    /**
     * creating flux from stream
     */
    @Test
    void fluxFromIntStream() {
        Flux.fromStream(FluxTest.INT_STREAM)
                .log()
                .subscribe();
    }

    /**
     * creating type of stream suppler
     */
    @Test
    void fluxFromIntStream1() {
        Supplier<Stream<Integer>> supStream = () -> Stream.of(1, 2, 3, 5);
        Flux.fromStream(supStream.get())
                .log()
                .subscribe();
    }


    /**
     * Creating fkux from a range of integers
     */
    @Test
    void fluxFromRange() {
        /*
         [ INFO] (main) | onNext(10)
         [ INFO] (main) | onNext(11)
         [ INFO] (main) | onNext(12)
         [ INFO] (main) | onNext(13)
          [ INFO] (main) | onNext(14)
         */
        Flux.range(10, 5)
                .log()
                .subscribe();
    }

    /**
     * creating flux emits values starting from 0
     * and incrementing them at specified time
     * intervals
     */
    @Test
    void fluxFromInterval() throws InterruptedException {
        /*
         Nothing will be printed bc
         interval method publishes elements at regular
         intervals while running on other thread.
         It doesn't block the main thread.

         When test class finished, all the threads are killed
         and nothing happens
         */
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .subscribe();
        /*
         we can do is sleep the main threat for 5 seconds
         to publish some values
         [ INFO] (parallel-1) onNext(0)
         [ INFO] (parallel-1) onNext(1)
         [ INFO] (parallel-1) onNext(2)
         [ INFO] (parallel-1) onNext(3)
         [ INFO] (parallel-1) onNext(4)

         This flux never complete,
         the only reason it was stopped bc
         thread was killed, when the program finished
         */


       /* comented code to avoid  for waiting Flux
        Thread.sleep(5000);
        */
    }

    /**
     * interval taking N elements
     */
    @Test
    void fluxFromIntervalTake() {
     /*   In webserver, for example, thread is always running,
                we could have publisher generating an infinite stream of
        values at a regular interval */
        Flux.interval(Duration.ofSeconds(1))
                //take only first N values from the flux
                //flux is complite aftrer publishing first two values
                .take(2)
                .log()
                .subscribe();
        /*
        take operator doesn't work as backpressure mechanism.
        It just takes N elements from the stream and cancels
        the subscription once they have been published.

         To working with backpressure we need to use request
         method on the subscription object. See below test
         */
    }

    /**
     * requesting an initial number of elements
     */
    @Test
    void fluxRequest() {
        Flux.range(1, 5)
                .log()
                //we have request 3 elements but we got 4 because
                // this method os depracted it will be removed
                //in feature version
                .subscribe(null,
                        null,
                        null,
                        s -> s.request(3L));
    }

    @Test
    void fluxRequestNwwerVersion() {
        Flux.range(1, 5)
                .log()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        subscription.request(3L);
                    }
                });
    }
}
