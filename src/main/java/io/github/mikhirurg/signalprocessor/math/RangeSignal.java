package io.github.mikhirurg.signalprocessor.math;

public class RangeSignal implements Signal {
    private final double minVal;
    private final double maxVal;
    private final double amplitude;
    private final boolean addRandom;
    private final Signal noiseSignal;


    public RangeSignal(double minVal, double maxVal, double amplitude, boolean addRandom, Signal noiseSignal) {
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.amplitude = amplitude;
        this.addRandom = addRandom;
        this.noiseSignal = noiseSignal;
    }

    @Override
    public double generateValue(double t) {
        double result = ((t * amplitude) % (maxVal - minVal)) + minVal;
        return addRandom ? result + noiseSignal.generateValue(t) : result;
    }
}
