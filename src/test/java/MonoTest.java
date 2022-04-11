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
        nothing will be happened on consle if you don't subscribe the publisher see monoWithConsumer()
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
        /* we can pass more than one consumer to the subscribe method
        to do something when an error happens or when the stream of elements completes
         */
    }

    /**
     * Empty mono, don't publish any value
     * with help of the empty method
     */
    @Test
    void empty() {
        /*we can see that onNext was never called and no value is printed
         * only onComplete is called*/
        var mono = Mono.empty();
        mono.log().subscribe(c -> System.out.println("Consumer 1"));
        /* when a subscription is set up, the onSubscribe mmethod is
        called passing an implementation of the Subscription interface.

        Then subscriber requests elements from the publisher. In this case,
        we are not specifying the number of elemments we want, an unbounded number of
        elements request.

        It only send one element using the onNext method

        after it is called onComplete
         */
    }

    /**
     * Passing consumer to the subscribe method to print
     * message when the sequence is completed.
     */
    @Test
    void emptyCompleteConsumerMono() {
        /*
        Remember, this consumer has to be third argument. (line 69)
        the second one is for errors (line 69)
         */
        Mono.empty()
                .log()
                .subscribe(System.out::println,
                        null,
                        () -> System.out.println("Done"));
        /*
        you might be wondering when will I ever need an Empty Mono?
        An empty Mono is useful toemulate the void return in traditional programming,
        it doesn't return a value but it emits a completion signal to let you know the processing is done.
         */
    }

    /**
     * throwing runtime exception
     */
    @Test
    void errorRuntimeExceptionMono() {
        Mono.error(new RuntimeException())
                .log()
                .subscribe();
        /*
        Runtime is an unchecked exception, there is NO NEED TO CATCH IT!
         */
    }

    /**
     * throwing check exception
     */

    @Test
    void errorExceptionMono() {
        Mono.error(new Exception())
                .log()
                .subscribe(System.out::println,
                        e -> System.out.println("Error: " + e));
    /*
    try catch doesn't apply here
    you can access the exception with the appropriate method
    this could be like catching the exception, doing something with it
    then rethrowing it
    */
    }

    /**
     * simulate catching the exception,
     * swallowing the exception and returning a new mono
     */
    @Test
    void errorDoOnErrorMono() {
        Mono.error(new Exception())
                .doOnError(e -> System.out.println("Error: " + e))
                .log()
                .subscribe();
    }

    /**
     * Simulate catching exception (swalling exception)
     * returning a new Mono
     */
    @Test
    void errorOnErrorResumeMono() {
        Mono.error(new Exception())
                .onErrorResume(e -> {
                    System.out.println("Caught: " + e);
                    return Mono.just("B");
                })
                .log()
                .subscribe();
    }

    /**
     * Returning a value
     */
    @Test
    void errorOnErrorReturnMono() {
        Mono.error(new Exception())
                .onErrorReturn("B")
                .log()
                .subscribe(c -> System.out.println("Subscribed"));
    }
}

