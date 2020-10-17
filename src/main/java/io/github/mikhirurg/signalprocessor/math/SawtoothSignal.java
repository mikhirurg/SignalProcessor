package io.github.mikhirurg.signalprocessor.math;

public class SawtoothSignal implements Signal {

    private double freq;
    private double amplitude;
    private final int approx;

    public SawtoothSignal(double freq, double amplitude, int approx) {
        this.freq = freq;
        this.amplitude = amplitude;
        this.approx = approx;
    }

    public double generateValue(double t) {
        double result = 0;

        for (int k = 1; k < approx; k++) {
            result += (Math.pow(-1, k) * Math.sin(2 * Math.PI * k * freq * t) / k);
        }
        return amplitude / 2 - amplitude / Math.PI * result;
    }
}
