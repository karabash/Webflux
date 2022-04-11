import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

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

}
