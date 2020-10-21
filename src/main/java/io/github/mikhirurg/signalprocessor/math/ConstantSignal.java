package io.github.mikhirurg.signalprocessor.math;

public class ConstantSignal implements Signal {

    private final double val;
    private final boolean addRandom;
    private final RandomSignal randomSignal;

    public ConstantSignal(double val, boolean addRandom, RandomSignal randomSignal) {
        this.val = val;
        this.addRandom = addRandom;
        this.randomSignal = randomSignal;
    }

    public double generateValue(double t) {
        return addRandom ? val + randomSignal.generateValue(t) : val;
    }
}
