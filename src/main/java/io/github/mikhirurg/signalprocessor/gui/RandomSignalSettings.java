package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.RandomSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.util.Application;

import javax.swing.*;
import java.awt.*;

public class RandomSignalSettings extends SignalSettings {

    private final JTextField minVal;
    private final JTextField maxVal;

    public RandomSignalSettings(double defMinVal, double defMaxVal) {
        minVal = new JTextField(String.valueOf(defMinVal));
        maxVal = new JTextField(String.valueOf(defMaxVal));
        buildGui();
    }

    public RandomSignalSettings() {
        minVal = new JTextField("0");
        maxVal = new JTextField("0");
        buildGui();
    }

    private void buildGui() {
        JLabel minValLabel = new JLabel(Application.getString("label.minvalue"));
        JLabel maxValLabel = new JLabel(Application.getString("label.maxvalue"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(minValLabel, c);

        c.gridx = 1;
        add(minVal, c);

        c.gridx = 0;
        c.gridy = 1;
        add(maxValLabel, c);

        c.gridx = 1;
        add(maxVal, c);
    }

    public double getMinVal() {
        return Double.parseDouble(minVal.getText());
    }

    public double getMaxVal() {
        return Double.parseDouble(maxVal.getText());
    }

    @Override
    public Signal getSignal() {
        return new RandomSignal(getMinVal(), getMaxVal());
    }

    @Override
    public String getSignalName() {
        return Application.getString("type.random");
    }

    @Override
    public String getSignalId() {
        return Application.getUSString("type.random");
    }
}
