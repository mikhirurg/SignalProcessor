package io.github.mikhirurg.signalprocessor.util;

public class Cortege<E> {
    private final E first;
    private final E second;
    private final E third;

    public Cortege(E first, E second, E third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public E getFirst() {
        return first;
    }

    public E getSecond() {
        return second;
    }

    public String toString() {
        return first + "," + second + "," + third;
    }

    public E getThird() {
        return third;
    }
}
