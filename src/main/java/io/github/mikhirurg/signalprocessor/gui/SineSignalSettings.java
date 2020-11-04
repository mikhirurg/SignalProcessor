package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.math.SineSignal;
import io.github.mikhirurg.signalprocessor.util.Application;

import javax.swing.*;
import java.awt.*;

public class SineSignalSettings extends SignalSettings implements Noisable {
    private final JTextField amplitude;
    private final JTextField freq;
    private final JTextField initPhase;
    private final JCheckBox addRandom;
    private final NoiseSettings noiseSettings;
    private final boolean containsNoise;

    public SineSignalSettings(double defAmplitude, double defFreq, double defInitPhase, boolean noised, NoiseSettings noiseSettings, boolean containsNoise) {
        amplitude = new JTextField(String.valueOf(defAmplitude));
        freq = new JTextField(String.valueOf(defFreq));
        initPhase = new JTextField(String.valueOf(defInitPhase));
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        addRandom.setSelected(noised);
        this.containsNoise = containsNoise;
        this.noiseSettings = noiseSettings;
        buildGui();
    }

    public SineSignalSettings(boolean containsNoise) {
        amplitude = new JTextField("0");
        freq = new JTextField("0");
        initPhase = new JTextField("0");
        noiseSettings = new NoiseSettings();
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        this.containsNoise = containsNoise;
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(Application.getString("label.amplitude"));
        JLabel freqLabel = new JLabel(Application.getString("label.frequency"));
        JLabel initPhaseLabel = new JLabel(Application.getString("label.initphase"));
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
        add(freqLabel, c);

        c.gridx = 1;
        add(freq, c);

        c.gridx = 0;
        c.gridy = 2;
        add(initPhaseLabel, c);

        c.gridx = 1;
        add(initPhase, c);

        if (containsNoise) {
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
    }

    public double getAmplitude() {
        return Double.parseDouble(amplitude.getText());
    }

    public double getFreq() {
        return Double.parseDouble(freq.getText());
    }

    public double getInitPhase() {
        return Double.parseDouble(initPhase.getText());
    }

    @Override
    public Signal getSignal() {
        return new SineSignal(getAmplitude(), getFreq(), getInitPhase(), addRandom.isSelected(),
                noiseSettings.getSignal());
    }

    @Override
    public String getSignalName() {
        return Application.getString("type.sine");
    }

    @Override
    public String getSignalId() {
        return Application.getUSString("type.sine");
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
