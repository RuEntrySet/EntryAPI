package ru.entryset.api.util.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * Функционал обычного {@link Function}, но с дополнительной обработкой исключений.
 *
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Function.html">Java Docs</a>
 */
@FunctionalInterface
public interface SafeFunction<T, R> {

    R apply(T t) throws Exception;

    default <V> SafeFunction<V, R> compose(SafeFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> SafeFunction<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> SafeFunction<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    default <V> SafeFunction<T, V> andThen(SafeFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T> SafeFunction<T, T> identity() {
        return t -> t;
    }

}