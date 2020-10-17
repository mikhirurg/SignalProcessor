package io.github.mikhirurg.signalprocessor.math;

public class RangeSignal implements Signal {
    private final double minVal;
    private final double maxVal;
    private final double amplitude;

    public RangeSignal(double minVal, double maxVal, double amplitude) {
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.amplitude = amplitude;
    }

    @Override
    public double generateValue(double t) {
        return ((t * amplitude) % (maxVal - minVal)) + minVal;
    }
}
