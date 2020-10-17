package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.SawtoothSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;

import javax.swing.*;
import java.awt.*;

public class SawtoothSignalSettings extends SignalSettings {

    private final JTextField freq;
    private final JTextField amplitude;
    private final JTextField approx;

    public SawtoothSignalSettings(double defFreq, double defAmplitude, int defApprox) {
        freq = new JTextField(String.valueOf(defFreq));
        amplitude = new JTextField(String.valueOf(defAmplitude));
        approx = new JTextField(String.valueOf(defApprox));
        buildGui();
    }

    public SawtoothSignalSettings() {
        freq = new JTextField("0");
        amplitude = new JTextField("0");
        approx = new JTextField("0");
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel("Amplitude");
        JLabel frequencyLabel = new JLabel("Frequency");
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
        add(frequencyLabel, c);

        c.gridx = 1;
        add(freq, c);

        c.gridx = 0;
        c.gridy = 2;
        add(approxLabel, c);

        c.gridx = 1;
        add(approx, c);
    }

    public double getFrequency() {
        return Double.parseDouble(freq.getText());
    }

    public double getAmplitude() {
        return Double.parseDouble(amplitude.getText());
    }

    public int getApproximation() {
        return Integer.parseInt(approx.getText());
    }

    @Override
    public Signal getSignal() {
        return new SawtoothSignal(getFrequency(), getAmplitude(), getApproximation());
    }

    @Override
    public String getSignalName() {
        return "Sawtooth";
    }
}
