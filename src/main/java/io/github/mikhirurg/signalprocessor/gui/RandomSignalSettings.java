package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.RandomSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class RandomSignalSettings extends SignalSettings {

    private final ResourceBundle resourceBundle;
    private final ResourceBundle resourceBundleUS;
    private final JTextField minVal;
    private final JTextField maxVal;

    public RandomSignalSettings(double defMinVal, double defMaxVal, ResourceBundle resourceBundle, ResourceBundle resourceBundleUS) {
        this.resourceBundle = resourceBundle;
        minVal = new JTextField(String.valueOf(defMinVal));
        maxVal = new JTextField(String.valueOf(defMaxVal));
        this.resourceBundleUS = resourceBundleUS;
        buildGui();
    }

    public RandomSignalSettings(ResourceBundle resourceBundle, ResourceBundle resourceBundleUS) {
        this.resourceBundle = resourceBundle;
        this.resourceBundleUS = resourceBundleUS;
        minVal = new JTextField("0");
        maxVal = new JTextField("0");
        buildGui();
    }

    private void buildGui() {
        JLabel minValLabel = new JLabel(resourceBundle.getString("label.minvalue"));
        JLabel maxValLabel = new JLabel(resourceBundle.getString("label.maxvalue"));
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
        return resourceBundle.getString("type.random");
    }

    @Override
    public String getSignalId() {
        return resourceBundleUS.getString("type.random");
    }
}
