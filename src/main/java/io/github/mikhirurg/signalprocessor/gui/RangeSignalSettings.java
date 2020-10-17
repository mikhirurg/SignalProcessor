package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.RangeSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class RangeSignalSettings extends SignalSettings {
    private final ResourceBundle resourceBundle;
    private final JTextField minVal;
    private final JTextField maxVal;
    private final JTextField amplitude;

    public RangeSignalSettings(double defMinVal, double defMaxVal, double defAmplitude, ResourceBundle resourceBundle) {
        amplitude = new JTextField(String.valueOf(defMinVal));
        minVal = new JTextField(String.valueOf(defMaxVal));
        maxVal = new JTextField(String.valueOf(defAmplitude));
        this.resourceBundle = resourceBundle;
        buildGui();
    }

    public RangeSignalSettings(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        amplitude = new JTextField("0");
        minVal = new JTextField("0");
        maxVal = new JTextField("0");
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(resourceBundle.getString("label.amplitude"));
        JLabel minValLabel = new JLabel(resourceBundle.getString("label.minvalue"));
        JLabel maxValLabel = new JLabel(resourceBundle.getString("label.maxvalue"));
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
        add(minValLabel, c);

        c.gridx = 1;
        add(minVal, c);

        c.gridx = 0;
        c.gridy = 2;
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

    public double getAmplitude() {
        return Double.parseDouble(amplitude.getText());
    }

    @Override
    public Signal getSignal() {
        return new RangeSignal(getMinVal(), getMaxVal(), getAmplitude());
    }

    @Override
    public String getSignalName() {
        return resourceBundle.getString("type.range");
    }
}
