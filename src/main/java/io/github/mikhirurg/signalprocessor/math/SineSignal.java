package io.github.mikhirurg.signalprocessor.math;

public class SineSignal implements Signal {

    private final double amplitude;
    private final double freq;
    private final double initPhase;
    private final boolean addRandom;
    private final RandomSignal randomSignal;

    public SineSignal(double amplitude, double freq, double initPhase, boolean addRandom, RandomSignal randomSignal) {
        this.amplitude = amplitude;
        this.freq = freq;
        this.initPhase = initPhase;
        this.addRandom = addRandom;
        this.randomSignal = randomSignal;
    }

    public double generateValue(double t) {
        double result;
        double omega = 2 * Math.PI * freq;
        result = amplitude * Math.sin(omega * t + initPhase);
        return addRandom ? result + randomSignal.generateValue(t) : result;
    }
}
