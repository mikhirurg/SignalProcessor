package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.SawtoothSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class SawtoothSignalSettings extends SignalSettings {
    private final ResourceBundle resourceBundle;
    private final JTextField freq;
    private final JTextField amplitude;
    private final JTextField approx;

    public SawtoothSignalSettings(double defFreq, double defAmplitude, int defApprox, ResourceBundle resourceBundle) {
        freq = new JTextField(String.valueOf(defFreq));
        amplitude = new JTextField(String.valueOf(defAmplitude));
        approx = new JTextField(String.valueOf(defApprox));
        this.resourceBundle = resourceBundle;
        buildGui();
    }

    public SawtoothSignalSettings(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        freq = new JTextField("0");
        amplitude = new JTextField("0");
        approx = new JTextField("0");
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(resourceBundle.getString("label.amplitude"));
        JLabel frequencyLabel = new JLabel(resourceBundle.getString("label.frequency"));
        JLabel approxLabel = new JLabel(resourceBundle.getString("label.approximation"));
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
        return resourceBundle.getString("type.sawtooth");
    }
}
