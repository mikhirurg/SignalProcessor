package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.RandomSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.math.SineSignal;
import io.github.mikhirurg.signalprocessor.util.Application;

import javax.swing.*;
import java.awt.*;

public class SineSignalSettings extends SignalSettings {
    private final JTextField amplitude;
    private final JTextField freq;
    private final JTextField initPhase;
    private final JCheckBox addRandom;
    private final JTextField minRVal;
    private final JTextField maxRVal;

    public SineSignalSettings(double defAmplitude, double defFreq, double defInitPhase) {
        amplitude = new JTextField(String.valueOf(defAmplitude));
        freq = new JTextField(String.valueOf(defFreq));
        initPhase = new JTextField(String.valueOf(defInitPhase));
        minRVal = new JTextField("0");
        maxRVal = new JTextField("0");
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        buildGui();
    }
    public SineSignalSettings() {
        amplitude = new JTextField("0");
        freq = new JTextField("0");
        initPhase = new JTextField("0");
        minRVal = new JTextField("0");
        maxRVal = new JTextField("0");
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(Application.getString("label.amplitude"));
        JLabel freqLabel = new JLabel(Application.getString("label.frequency"));
        JLabel minRValLabel = new JLabel(Application.getString("label.minvalue"));
        JLabel maxRValLabel = new JLabel(Application.getString("label.maxvalue"));
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
        c.insets.left = Integer.parseInt(Application.getProperty("settings.width")) / 5;
        add(noise, c);
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
        return new SineSignal(getAmplitude(), getFreq(), getInitPhase(), addRandom.isSelected(), new RandomSignal(
                Double.parseDouble(minRVal.getText()),
                Double.parseDouble(maxRVal.getText()))
        );
    }

    @Override
    public String getSignalName() {
        return Application.getString("type.sine");
    }

    @Override
    public String getSignalId() {
        return Application.getUSString("type.sine");
    }
}
