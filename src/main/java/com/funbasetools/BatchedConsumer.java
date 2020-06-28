package com.funbasetools;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import lombok.Builder;

public final class BatchedConsumer<T> implements Consumer<T> {

    private final Object syncObj = new Object();
    private final Consumer<List<T>> downStreamConsumer;
    private final int batchMaxSize;
    private final long flushTimeElapseInMillis;
    private final Timer timer;

    private LinkedList<T> batch = new LinkedList<>();

    @Builder
    private BatchedConsumer(
        final java.util.function.Consumer<List<T>> downStreamConsumer,
        final int batchSize,
        final Duration flushElapseDuration) {

        Objects.requireNonNull(downStreamConsumer);

        this.downStreamConsumer = Consumer.of(downStreamConsumer);
        this.batchMaxSize = batchSize > 0
            ? batchSize
            : 100;

        this.flushTimeElapseInMillis = Optional
            .ofNullable(flushElapseDuration)
            .map(Duration::toMillis)
            .orElse(0L);

        this.timer = this.flushTimeElapseInMillis > 0
            ? new Timer("Batch flush")
            : null;
    }

    @Override
    public void accept(T arg) {
        synchronized (syncObj) {
            batch.add(arg);
            if (batch.size() == 1) {
                startTimer();
            }
        }
        flushIf(() -> batch.size() >= batchMaxSize);
    }

    private Optional<List<T>> replaceIfNeeded(final Supplier<Boolean> condition) {
        if (condition.get()) {
            synchronized (syncObj) {
                if (condition.get()) {
                    Optional.ofNullable(timer).ifPresent(Timer::cancel);
                    final List<T> flushingBatch = batch;
                    batch = new LinkedList<>();
                    return Optional.of(flushingBatch);
                }
            }
        }
        return Optional.empty();
    }

    private void flushIf(final Supplier<Boolean> condition) {
        replaceIfNeeded(condition)
            .ifPresent(downStreamConsumer);
    }

    private void flushIfNotEmpty() {
        flushIf(() -> !batch.isEmpty());
    }

    private void startTimer() {
        Optional
            .ofNullable(timer)
            .ifPresent(timer ->
                timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            flushIfNotEmpty();
                        }
                    },
                    flushTimeElapseInMillis)
            );
    }
}
