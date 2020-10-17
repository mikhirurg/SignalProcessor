package io.github.mikhirurg.signalprocessor.math;

public class ConstantSignal implements Signal {

    private double val;

    public ConstantSignal(double val) {
        this.val = val;
    }

    public double generateValue(double t) {
        return val;
    }
}
