import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

    /**
     * This method oublishes the supplied argument.
     */
    @Test
    void firstMono() {
        /*Reminder Mono can publish at most one element
        that's why we can pass more than 1 element as
        (it takes only one argument!):
        Mono.just("A","B");
        */
        Mono.just("A");
    }

    /**
     * Prints recieved value using Log events using Log4j,
     * if there is no logging frameworks it logs to the console
     */
    @Test
    void monoWithConsumer() {
        /*Handy way to peek at every event of the sequence is using the log operator
         ps: nothing will be happened on consle if you don't subscribe the publisher
          Mono.just("A").log()
         */
        Mono.just("A").log().subscribe(c -> System.out.println("Recieved value is ".concat(c)));
    }

    /**
     * Passing more than one consumer to the subscribe method
     */
    @Test
    void MonoWithDoOn() {
        Mono.just("A")
                .log()
                .doOnSubscribe(sub -> System.out.println("Subscribed: " + sub))
                .doOnRequest(request -> System.out.println("Request: " + request))
                .doOnSuccess(complete -> System.out.println("Complete: " + complete))
                .subscribe(subscribe -> System.out.println("Recieved value is: ".concat(subscribe)));
    }
}

