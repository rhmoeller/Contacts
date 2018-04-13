package com.hjortsholm.contacts.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Future<Type> {

    private boolean async;
    private CompletableFuture<Type> future;

    public Future(Supplier<Type> supplier) {
        this(supplier, false);
    }

    public Future(Supplier<Type> supplier, boolean async) {
        this.async = async;
        this.future = CompletableFuture.supplyAsync(supplier);
    }

    public void then(Consumer<Type> consumer) {
        if (this.async)
            this.future.thenAcceptAsync(consumer);
        else
            this.future.thenAccept(consumer);
    }
}
