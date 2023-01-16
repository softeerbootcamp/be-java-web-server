package controller;

@FunctionalInterface
public interface BiConsumerThrowable<T, R> {
    void accept(T t, R r);
}
