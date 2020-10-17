package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.math.SquareSignal;

import javax.swing.*;
import java.awt.*;

public class SquareSignalSettings extends SignalSettings {

    private final JTextField amplitude;
    private final JTextField frequency;
    private final JTextField approx;

    public SquareSignalSettings(double defCycleFreq, int defApprox, double defAmplitude) {
        frequency = new JTextField(String.valueOf(defCycleFreq));
        approx = new JTextField(String.valueOf(defApprox));
        amplitude = new JTextField(String.valueOf(defAmplitude));
        buildGui();
    }

    public SquareSignalSettings() {
        frequency = new JTextField("0");
        approx = new JTextField("0");
        amplitude = new JTextField("0");
        buildGui();
    }

    public int getApprox() {
        return Integer.parseInt(approx.getText());
    }

    public double getCycleFrequency() {
        return Double.parseDouble(frequency.getText());
    }

    public double getAmplitude() {
        return Double.parseDouble(amplitude.getText());
    }

    @Override
    public Signal getSignal() {
        return new SquareSignal(getAmplitude(), getCycleFrequency(), getApprox());
    }

    @Override
    public String getSignalName() {
        return "Square";
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel("Amplitude");
        JLabel cycleFrequencyLabel = new JLabel("Frequency");
        JLabel approxLabel = new JLabel("Approximation");
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(amplitudeLabel, c);

        c.gridx = 1;
        add(amplitude, c);

        c.gridx = 0;
        c.gridy = 1;
        add(cycleFrequencyLabel, c);

        c.gridx = 1;
        add(frequency, c);

        c.gridx = 0;
        c.gridy = 2;
        add(approxLabel, c);

        c.gridx = 1;
        add(approx, c);
    }
}
