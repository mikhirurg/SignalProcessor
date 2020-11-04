package io.github.mikhirurg.signalprocessor.math;

public class ConstantSignal implements Signal {

    private final double val;
    private final boolean addRandom;
    private final Signal noiseSignal;

    public ConstantSignal(double val, boolean addRandom, Signal noiseSignal) {
        this.val = val;
        this.addRandom = addRandom;
        this.noiseSignal = noiseSignal;
    }

    public double generateValue(double t) {
        return addRandom ? val + noiseSignal.generateValue(t) : val;
    }
}
