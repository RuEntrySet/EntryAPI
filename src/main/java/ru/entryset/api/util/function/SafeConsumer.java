package ru.entryset.api.util.function;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Функционал обычного {@link Consumer}, но с дополнительной обработкой исключений.
 *
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Consumer.html">Java Docs</a>
 */
@FunctionalInterface
public interface SafeConsumer<T> {

    void accept(T t) throws Exception;

    default SafeConsumer<T> andThen(SafeConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }

    default SafeConsumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }

}
