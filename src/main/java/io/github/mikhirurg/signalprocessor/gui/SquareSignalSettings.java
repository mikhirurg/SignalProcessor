package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.RandomSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.math.SquareSignal;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class SquareSignalSettings extends SignalSettings {
    private final ResourceBundle resourceBundle;
    private final ResourceBundle resourceBundleUS;
    private final JTextField amplitude;
    private final JTextField frequency;
    private final JTextField approx;
    private final JCheckBox reverseFourier;
    private final JCheckBox addRandom;
    private final JTextField minRVal;
    private final JTextField maxRVal;

    public SquareSignalSettings(double defCycleFreq, int defApprox, double defAmplitude, ResourceBundle resourceBundle, ResourceBundle resourceBundleUS) {
        frequency = new JTextField(String.valueOf(defCycleFreq));
        approx = new JTextField(String.valueOf(defApprox));
        amplitude = new JTextField(String.valueOf(defAmplitude));
        this.resourceBundle = resourceBundle;
        this.resourceBundleUS = resourceBundleUS;
        reverseFourier = new JCheckBox(resourceBundle.getString("checkbox.reversefourier"));
        minRVal = new JTextField("0");
        maxRVal = new JTextField("0");
        addRandom = new JCheckBox(resourceBundle.getString("checkbox.noise"));
        buildGui();
    }

    public SquareSignalSettings(ResourceBundle resourceBundle, ResourceBundle resourceBundleUS) {
        this.resourceBundle = resourceBundle;
        this.resourceBundleUS = resourceBundleUS;
        frequency = new JTextField("0");
        approx = new JTextField("0");
        amplitude = new JTextField("0");
        reverseFourier = new JCheckBox(resourceBundle.getString("checkbox.reversefourier"));
        minRVal = new JTextField("0");
        maxRVal = new JTextField("0");
        addRandom = new JCheckBox(resourceBundle.getString("checkbox.noise"));
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
        return new SquareSignal(getAmplitude(), getCycleFrequency(), getApprox(), reverseFourier.isSelected(), addRandom.isSelected(), new RandomSignal(
                Double.parseDouble(minRVal.getText()),
                Double.parseDouble(maxRVal.getText()))
        );
    }

    @Override
    public String getSignalName() {
        return resourceBundle.getString("type.sine");
    }

    @Override
    public String getSignalId() {
        return resourceBundleUS.getString("type.sine");
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(resourceBundle.getString("label.amplitude"));
        JLabel cycleFrequencyLabel = new JLabel(resourceBundle.getString("label.frequency"));
        JLabel approxLabel = new JLabel(resourceBundle.getString("label.approximation"));
        JLabel minRValLabel = new JLabel(resourceBundle.getString("label.minvalue"));
        JLabel maxRValLabel = new JLabel(resourceBundle.getString("label.maxvalue"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
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

        c.gridx = 0;
        c.gridy = 3;
        add(reverseFourier, c);

        JPanel noise = new JPanel();
        noise.setLayout(new GridBagLayout());
        GridBagConstraints nc = new GridBagConstraints();
        nc.gridx = 0;
        nc.gridy = 0;
        nc.gridwidth = 2;
        nc.gridheight = 1;
        nc.anchor = GridBagConstraints.WEST;
        noise.add(addRandom, nc);

        nc.gridwidth = 1;
        nc.gridy = 1;
        noise.add(minRValLabel, nc);

        nc.gridx = 1;
        noise.add(minRVal, nc);

        nc.gridy = 2;
        nc.gridx = 0;
        noise.add(maxRValLabel, nc);

        nc.gridx = 1;
        noise.add(maxRVal, nc);

        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridheight = 3;
        add(noise, c);
    }
}
