package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.math.SquareSignal;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class SquareSignalSettings extends SignalSettings {
    private final ResourceBundle resourceBundle;
    private final JTextField amplitude;
    private final JTextField frequency;
    private final JTextField approx;

    public SquareSignalSettings(double defCycleFreq, int defApprox, double defAmplitude, ResourceBundle resourceBundle) {
        frequency = new JTextField(String.valueOf(defCycleFreq));
        approx = new JTextField(String.valueOf(defApprox));
        amplitude = new JTextField(String.valueOf(defAmplitude));
        this.resourceBundle = resourceBundle;
        buildGui();
    }

    public SquareSignalSettings(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
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
        return resourceBundle.getString("type.sine");
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(resourceBundle.getString("label.amplitude"));
        JLabel cycleFrequencyLabel = new JLabel(resourceBundle.getString("label.frequency"));
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
