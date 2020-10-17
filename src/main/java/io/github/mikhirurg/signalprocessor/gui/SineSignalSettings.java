package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.math.SineSignal;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class SineSignalSettings extends SignalSettings {
    private final ResourceBundle resourceBundle;
    private final JTextField amplitude;
    private final JTextField freq;
    private final JTextField initPhase;

    public SineSignalSettings(double defAmplitude, double defFreq, double defInitPhase, ResourceBundle resourceBundle) {
        amplitude = new JTextField(String.valueOf(defAmplitude));
        freq = new JTextField(String.valueOf(defFreq));
        initPhase = new JTextField(String.valueOf(defInitPhase));
        this.resourceBundle = resourceBundle;
        buildGui();
    }
    public SineSignalSettings(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        amplitude = new JTextField("0");
        freq = new JTextField("0");
        initPhase = new JTextField("0");
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(resourceBundle.getString("label.amplitude"));
        JLabel freqLabel = new JLabel(resourceBundle.getString("label.frequency"));
        JLabel initPhaseLabel = new JLabel(resourceBundle.getString("label.initphase"));
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
    }

    public void setAmplitude(String data) {
        amplitude.setText(data);
    }

    public void setFrequency(String data) {
        freq.setText(data);
    }

    public void setInitPhase(String data) {
        initPhase.setText(data);
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
        return new SineSignal(getAmplitude(), getFreq(), getInitPhase());
    }

    @Override
    public String getSignalName() {
        return resourceBundle.getString("type.sine");
    }
}
