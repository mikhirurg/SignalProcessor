package io.github.mikhirurg.signalprocessor.math;

public class SawtoothSignal implements Signal {

    private final double freq;
    private final double amplitude;
    private final int approx;
    private final boolean isReverse;
    private final boolean addRandom;
    private final RandomSignal randomSignal;

    public SawtoothSignal(double freq, double amplitude, int approx, boolean isReverse, boolean addRandom, RandomSignal randomSignal) {
        this.freq = freq;
        this.amplitude = amplitude;
        this.approx = approx;
        this.isReverse = isReverse;
        this.addRandom = addRandom;
        this.randomSignal = randomSignal;
    }

    public double generateValue(double t) {
        double result = 0;

        for (int k = 1; k < approx; k++) {
            result += (Math.pow(-1, k) * Math.sin(2 * Math.PI * k * freq * t) / k);
        }

        result = amplitude / 2 - amplitude / Math.PI * result;

        return addRandom ? result + randomSignal.generateValue(t) : result;
    }
}
