package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.RandomSignal;
import io.github.mikhirurg.signalprocessor.math.SawtoothSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.util.Application;

import javax.swing.*;
import java.awt.*;

public class SawtoothSignalSettings extends SignalSettings implements Noisable {
    private final JTextField freq;
    private final JTextField amplitude;
    private final JTextField approx;
    private final JCheckBox reverseFourier;
    private final JCheckBox addRandom;
    private final JTextField minRVal;
    private final JTextField maxRVal;

    public SawtoothSignalSettings(double defFreq, double defAmplitude, int defApprox, double defMinRVal,
                                  double defMaxRVal, boolean noised) {
        freq = new JTextField(String.valueOf(defFreq));
        amplitude = new JTextField(String.valueOf(defAmplitude));
        approx = new JTextField(String.valueOf(defApprox));
        reverseFourier = new JCheckBox(Application.getString("checkbox.reversefourier"));
        minRVal = new JTextField(String.valueOf(defMinRVal));
        maxRVal = new JTextField(String.valueOf(defMaxRVal));
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        addRandom.setSelected(noised);
        buildGui();
    }

    public SawtoothSignalSettings() {
        freq = new JTextField("0");
        amplitude = new JTextField("0");
        approx = new JTextField("0");
        reverseFourier = new JCheckBox(Application.getString("checkbox.reversefourier"));
        minRVal = new JTextField("0");
        maxRVal = new JTextField("0");
        addRandom = new JCheckBox(Application.getString("checkbox.noise"));
        buildGui();
    }

    private void buildGui() {
        JLabel amplitudeLabel = new JLabel(Application.getString("label.amplitude"));
        JLabel frequencyLabel = new JLabel(Application.getString("label.frequency"));
        JLabel approxLabel = new JLabel(Application.getString("label.approximation"));
        JLabel minRValLabel = new JLabel(Application.getString("label.minvalue"));
        JLabel maxRValLabel = new JLabel(Application.getString("label.maxvalue"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(amplitudeLabel, c);

        c.gridx = 1;
        add(amplitude, c);

        c.gridx = 0;
        c.gridy = 1;
        add(frequencyLabel, c);

        c.gridx = 1;
        add(freq, c);

        c.gridx = 0;
        c.gridy = 2;
        add(approxLabel, c);

        c.gridx = 1;
        add(approx, c);

        /*c.gridx = 0;
        c.gridy = 3;
        add(reverseFourier, c);*/

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

    public double getFrequency() {
        return Double.parseDouble(freq.getText());
    }

    public double getAmplitude() {
        return Double.parseDouble(amplitude.getText());
    }

    public int getApproximation() {
        return Integer.parseInt(approx.getText());
    }

    public double getMinRVal() {
        return Double.parseDouble(minRVal.getText());
    }

    public double getMaxRVal() {
        return Double.parseDouble(maxRVal.getText());
    }

    @Override
    public boolean isNoised() {
        return addRandom.isSelected();
    }

    @Override
    public Signal getSignal() {
        return new SawtoothSignal(getFrequency(), getAmplitude(), getApproximation(), reverseFourier.isSelected(), addRandom.isSelected(), new RandomSignal(
                Double.parseDouble(minRVal.getText()),
                Double.parseDouble(maxRVal.getText()))
        );
    }

    @Override
    public String getSignalName() {
        return Application.getString("type.sawtooth");
    }

    @Override
    public String getSignalId() {
        return Application.getUSString("type.sawtooth");
    }
}
