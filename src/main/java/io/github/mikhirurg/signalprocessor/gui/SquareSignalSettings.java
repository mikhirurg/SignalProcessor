package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.math.SquareSignal;
import io.github.mikhirurg.signalprocessor.util.Application;

import javax.swing.*;
import java.awt.*;

public class SquareSignalSettings extends SignalSettings implements Noisable {
    private final JTextField amplitude;
    private final JTextField frequency;
    private final JTextField approx;
    private final JCheckBox reverseFourier;
    private final JCheckBox addRandom;
    private final NoiseSettings noiseSettings;

    public SquareSignalSettings(double defCycleFreq, int defApprox, double defAmplitude,
                                boolean noised, NoiseSettings noiseSettings) {
        frequency = new JTextField(String.valueOf(defCycleFreq));
        approx = new JTextField(String.valueOf(defApprox));
        amplitude = new JTextField(String.valueOf(defAmplitude));
        reverseFourier = new JCheckBox(Application.getString("checkbox.reversefourier"));
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        addRandom.setSelected(noised);
        this.noiseSettings = noiseSettings;
        buildGui();
    }

    public SquareSignalSettings() {
        frequency = new JTextField("0");
        approx = new JTextField("0");
        amplitude = new JTextField("0");
        reverseFourier = new JCheckBox(Application.getString("checkbox.reversefourier"));
        noiseSettings = new NoiseSettings();
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(Application.getString("label.amplitude"));
        JLabel cycleFrequencyLabel = new JLabel(Application.getString("label.frequency"));
        JLabel approxLabel = new JLabel(Application.getString("label.approximation"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
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

        /*c.gridx = 0;
        c.gridy = 3;
        add(reverseFourier, c);*/

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
        noise.add(noiseSettings, nc);

        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridheight = 3;
        c.insets.left = Integer.parseInt(Application.getProperty("settings.width")) / 5;
        add(noise, c);
    }

    @Override
    public Signal getSignal() {
        return new SquareSignal(getAmplitude(), getCycleFrequency(), getApprox(), reverseFourier.isSelected(),
                addRandom.isSelected(), noiseSettings.getSignal()
        );
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
    public String getSignalName() {
        return Application.getString("type.square");
    }

    @Override
    public String getSignalId() {
        return Application.getUSString("type.square");
    }

    @Override
    public boolean isNoised() {
        return addRandom.isSelected();
    }

    @Override
    public NoiseSettings getNoiseSignalSettings() {
        return noiseSettings;
    }
}
