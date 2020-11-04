package io.github.mikhirurg.signalprocessor.math;

public class SquareSignal implements Signal {

    private final double amplitude;
    private final double freq;
    private final int approx;
    private final boolean isReverse;
    private final boolean addRandom;
    private final Signal noiseSignal;

    public SquareSignal(double amplitude, double freq, int approx, boolean isReverse, boolean addRandom, Signal noiseSignal) {
        this.freq = freq;
        this.approx = approx;
        this.amplitude = amplitude;
        this.isReverse = isReverse;
        this.addRandom = addRandom;
        this.noiseSignal = noiseSignal;
    }

    public double generateValue(double t) {
        double result = 0;

        for (int k = 1; k < approx; k++) {
            result += (Math.sin(2 * Math.PI * (2 * k - 1) * freq * t) / (2 * k - 1));
        }

        result = amplitude * 4 * result / Math.PI;

        return addRandom ? result + noiseSignal.generateValue(t) : result;
    }
}
