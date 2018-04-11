package com.hjortsholm.contacts.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Future<Type> {

    private CompletableFuture<Type> future;

    public Future(Supplier<Type> supplier) {
        this.future = CompletableFuture.supplyAsync(supplier);
    }

    public void then(Consumer<Type> consumer) {
        this.future.thenAcceptAsync(consumer);
    }
}
