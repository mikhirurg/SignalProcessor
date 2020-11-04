package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.ConstantSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.util.Application;

import javax.swing.*;
import java.awt.*;

public class ConstantSignalSettings extends SignalSettings implements Noisable {
    private final JTextField val;
    private final JCheckBox addRandom;
    private final NoiseSettings noiseSettings;

    public ConstantSignalSettings(double defVal, boolean noised, NoiseSettings noiseSettings) {
        val = new JTextField(String.valueOf(defVal));
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        addRandom.setSelected(noised);
        this.noiseSettings = noiseSettings;
        buildGui();
    }

    public ConstantSignalSettings() {
        val = new JTextField("0");
        noiseSettings = new NoiseSettings();
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        buildGui();
    }

    private void buildGui() {
        JLabel valLabel = new JLabel(Application.getString("label.value"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        add(valLabel, c);

        c.gridx = 1;
        add(val, 1);

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

    public double getVal() {
        return Double.parseDouble(val.getText());
    }

    @Override
    public Signal getSignal() {
        return new ConstantSignal(getVal(), addRandom.isSelected(), noiseSettings.getSignal());
    }

    @Override
    public boolean isNoised() {
        return addRandom.isSelected();
    }

    @Override
    public NoiseSettings getNoiseSignalSettings() {
        return noiseSettings;
    }

    @Override
    public String getSignalName() {
        return Application.getString("type.constant");
    }

    @Override
    public String getSignalId() {
        return Application.getUSString("type.constant");
    }
}
