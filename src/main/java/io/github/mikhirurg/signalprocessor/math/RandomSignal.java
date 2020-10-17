package io.github.mikhirurg.signalprocessor.math;

import java.util.Random;

public class RandomSignal implements Signal {
    private final double minVal;
    private final double maxVal;
    private final Random random;

    public RandomSignal(double minVal, double maxVal) {
        this.minVal = minVal;
        this.maxVal = maxVal;
        random = new Random();
    }

    public double generateValue(double t) {
        return minVal + (maxVal - minVal) * random.nextDouble();
    }
}
