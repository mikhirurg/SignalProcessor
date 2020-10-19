package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.ConstantSignal;
import io.github.mikhirurg.signalprocessor.math.RandomSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class ConstantSignalSettings extends SignalSettings {
    private final ResourceBundle resourceBundle;
    private final ResourceBundle resourceBundleUS;
    private final JTextField val;
    private final JCheckBox addRandom;
    private final JTextField minRVal;
    private final JTextField maxRVal;

    public ConstantSignalSettings(double defVal, ResourceBundle resourceBundle, ResourceBundle resourceBundleUS) {
        val = new JTextField(String.valueOf(defVal));
        this.resourceBundle = resourceBundle;
        this.resourceBundleUS = resourceBundleUS;
        minRVal = new JTextField("0");
        maxRVal = new JTextField("0");
        addRandom = new JCheckBox(resourceBundle.getString("checkbox.noise"));
        buildGui();
    }

    public ConstantSignalSettings(ResourceBundle resourceBundle, ResourceBundle resourceBundleUS) {
        this.resourceBundle = resourceBundle;
        this.resourceBundleUS = resourceBundleUS;
        val = new JTextField("0");
        minRVal = new JTextField("0");
        maxRVal = new JTextField("0");
        addRandom = new JCheckBox(resourceBundle.getString("checkbox.noise"));
        buildGui();
    }

    private void buildGui() {
        JLabel valLabel = new JLabel(resourceBundle.getString("label.value"));
        JLabel minRValLabel = new JLabel(resourceBundle.getString("label.minvalue"));
        JLabel maxRValLabel = new JLabel(resourceBundle.getString("label.maxvalue"));
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

    public double getVal() {
        return Double.parseDouble(val.getText());
    }

    @Override
    public Signal getSignal() {
        return new ConstantSignal(getVal(), addRandom.isSelected(), new RandomSignal(
                Double.parseDouble(minRVal.getText()),
                Double.parseDouble(maxRVal.getText())
        ));
    }

    @Override
    public String getSignalName() {
        return resourceBundle.getString("type.constant");
    }

    @Override
    public String getSignalId() {
        return resourceBundleUS.getString("type.constant");
    }
}
