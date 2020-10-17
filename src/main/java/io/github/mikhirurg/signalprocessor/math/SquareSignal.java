package io.github.mikhirurg.signalprocessor.math;

public class SquareSignal implements Signal {

    private final double amplitude;
    private final double freq;
    private final int approx;

    public SquareSignal(double amplitude, double freq, int approx) {
        this.freq = freq;
        this.approx = approx;
        this.amplitude = amplitude;
    }

    public double generateValue(double t) {
        double result = 0;

        for (int k = 1; k < approx; k++) {
            result += (Math.sin(2 * Math.PI * (2 * k - 1) * freq * t) / (2 * k - 1));
        }

        return amplitude * 4 * result / Math.PI;
    }
}
