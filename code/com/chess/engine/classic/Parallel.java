package com.chess.engine.classic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Parallel {

    public static interface Operation<R, E> {
        public R perform(E input);
    }

    public static <R, E> Collection<R> forEach(final Iterable<E> inputs,
                                               final Operation<R,E> operation)
            throws InterruptedException, ExecutionException {

        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(1);
        final List<Future<R>> futures = Collections.synchronizedList(new ArrayList<Future<R>>());
        for (final E input : inputs) {
            Callable<R> callable = new Callable<R>() {
                public R call() throws Exception {
                    return operation.perform(input);
                }
            };
            futures.add(service.submit(callable));
        }

        service.shutdown();

        final List<R> outputs = new ArrayList<>();
        for (Future<R> future : futures) {
            outputs.add(future.get());
        }
        return outputs;
    }
}
