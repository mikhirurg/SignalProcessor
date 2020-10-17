package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.gui.SignalSettings;
import io.github.mikhirurg.signalprocessor.math.ConstantSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;

import javax.swing.*;
import java.awt.*;

public class ConstantSignalSettings extends SignalSettings {

    private final JTextField val;

    public ConstantSignalSettings(double defVal) {
        val = new JTextField(String.valueOf(defVal));
        buildGui();
    }

    public ConstantSignalSettings() {
        val = new JTextField("0");
        buildGui();
    }

    private void buildGui() {
        JLabel valLabel = new JLabel("Value");
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(valLabel, c);

        c.gridx = 1;
        add(val, 1);
    }

    public double getVal() {
        return Double.parseDouble(val.getText());
    }

    @Override
    public Signal getSignal() {
        return new ConstantSignal(getVal());
    }

    @Override
    public String getSignalName() {
        return "Constant";
    }
}
