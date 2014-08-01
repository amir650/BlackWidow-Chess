package com.chess.engine.classic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Parallel {

    final static ExecutorService service = Executors.newFixedThreadPool(2);

    public static interface Operation<R, E> {
        public R perform(E input);
    }

    public static <R, E> List<R> forEach(final Iterable<E> inputs,
                                         final Operation<R,E> operation) {


        final List<Future<R>> futures = Collections.synchronizedList(new ArrayList<Future<R>>());

        for (final E input : inputs) {
            final Callable<R> callable = new Callable<R>() {
                public R call() throws Exception {
                    return operation.perform(input);
                }
            };
            futures.add(service.submit(callable));
        }

        final List<R> outputs = new ArrayList<>();

        try {
            for (final Future<R> future : futures) {
                outputs.add(future.get());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return outputs;

    }
}
