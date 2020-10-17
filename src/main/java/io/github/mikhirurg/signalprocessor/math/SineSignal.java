package io.github.mikhirurg.signalprocessor.math;

public class SineSignal implements Signal {

    private final double amplitude;
    private final double freq;
    private final double initPhase;

    public SineSignal(double amplitude, double freq, double initPhase) {
        this.amplitude = amplitude;
        this.freq = freq;
        this.initPhase = initPhase;
    }

    public double generateValue(double t) {
        double omega = 2 * Math.PI * freq;
        return amplitude * Math.sin(omega * t + initPhase);
    }
}
