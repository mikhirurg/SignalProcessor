package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.util.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class NoiseSettings extends SignalSettings {

    private final JComboBox<String> comboBox;
    private SignalSettings currentSignal;

    public NoiseSettings() {
        comboBox = new JComboBox<>();
        comboBox.addItem(Application.getString("type.random"));
        comboBox.addItem(Application.getString("type.sine"));
        currentSignal = new RandomSignalSettings();
        buildGui();
    }

    public NoiseSettings(SignalSettings signalSettings) {
        comboBox = new JComboBox<>();
        comboBox.addItem(Application.getString("type.random"));
        comboBox.addItem(Application.getString("type.sine"));
        currentSignal = signalSettings;
        comboBox.setSelectedItem(currentSignal.getSignalName());
        buildGui();
    }

    private void buildGui() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(comboBox, c);

        c.gridy = 1;
        JPanel settingsPanel = new JPanel();
        settingsPanel.add(currentSignal);
        add(settingsPanel, c);

        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (Application.getString("type.sine").equals(comboBox.getSelectedItem())) {
                    currentSignal = new SineSignalSettings(false);
                } else if (Application.getString("type.random").equals(comboBox.getSelectedItem())) {
                    currentSignal = new RandomSignalSettings();
                }
                settingsPanel.removeAll();
                settingsPanel.add(currentSignal);
                settingsPanel.updateUI();
            }
        });
    }

    @Override
    public Signal getSignal() {
        return currentSignal.getSignal();
    }

    @Override
    public String getSignalName() {
        return currentSignal.getSignalName();
    }

    @Override
    public String getSignalId() {
        return currentSignal.getSignalId();
    }

    public SignalSettings getSignalSettings() {
        return currentSignal;
    }
}
