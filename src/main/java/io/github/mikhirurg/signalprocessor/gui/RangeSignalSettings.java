package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.RandomSignal;
import io.github.mikhirurg.signalprocessor.math.RangeSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.util.Application;

import javax.swing.*;
import java.awt.*;

public class RangeSignalSettings extends SignalSettings implements Noisable {
    private final JTextField minVal;
    private final JTextField maxVal;
    private final JTextField amplitude;
    private final JCheckBox addRandom;
    private final JTextField minRVal;
    private final JTextField maxRVal;

    public RangeSignalSettings(double defMinVal, double defMaxVal, double defAmplitude, double defMinRVal,
                               double defMaxRVal, boolean noised) {
        amplitude = new JTextField(String.valueOf(defAmplitude));
        minVal = new JTextField(String.valueOf(defMinVal));
        maxVal = new JTextField(String.valueOf(defMaxVal));
        minRVal = new JTextField(String.valueOf(defMinRVal));
        maxRVal = new JTextField(String.valueOf(defMaxRVal));
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        addRandom.setSelected(noised);
        buildGui();
    }

    public RangeSignalSettings() {
        amplitude = new JTextField("0");
        minVal = new JTextField("0");
        maxVal = new JTextField("0");
        minRVal = new JTextField("0");
        maxRVal = new JTextField("0");
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(Application.getString("label.amplitude"));
        JLabel minValLabel = new JLabel(Application.getString("label.minvalue"));
        JLabel maxValLabel = new JLabel(Application.getString("label.maxvalue"));
        JLabel minRValLabel = new JLabel(Application.getString("label.minvalue"));
        JLabel maxRValLabel = new JLabel(Application.getString("label.maxvalue"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        add(amplitudeLabel, c);

        c.gridx = 1;
        add(amplitude, c);

        c.gridx = 0;
        c.gridy = 1;
        add(minValLabel, c);

        c.gridx = 1;
        add(minVal, c);

        c.gridx = 0;
        c.gridy = 2;
        add(maxValLabel, c);

        c.gridx = 1;
        add(maxVal, c);

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

    public double getMinVal() {
        return Double.parseDouble(minVal.getText());
    }

    public double getMaxVal() {
        return Double.parseDouble(maxVal.getText());
    }

    public double getAmplitude() {
        return Double.parseDouble(amplitude.getText());
    }

    @Override
    public Signal getSignal() {
        return new RangeSignal(getMinVal(), getMaxVal(), getAmplitude(), addRandom.isSelected(), new RandomSignal(
                Double.parseDouble(minRVal.getText()),
                Double.parseDouble(maxRVal.getText()))
        );
    }

    @Override
    public String getSignalName() {
        return Application.getString("type.range");
    }

    @Override
    public String getSignalId() {
        return Application.getUSString("type.range");
    }

    @Override
    public double getMinRVal() {
        return Double.parseDouble(minRVal.getText());
    }

    @Override
    public double getMaxRVal() {
        return Double.parseDouble(maxRVal.getText());
    }

    @Override
    public boolean isNoised() {
        return addRandom.isSelected();
    }
}
