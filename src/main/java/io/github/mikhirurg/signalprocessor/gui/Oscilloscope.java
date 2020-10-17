package io.github.mikhirurg.signalprocessor.gui;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Oscilloscope extends JFrame {

    private final Properties appProperties;

    private SignalSettings currentSignalASettings;
    private SignalSettings currentSignalBSettings;

    private final int SIGNAL_A = 1;
    private final int SIGNAL_B = 2;


    private void createSignalSettings(JPanel signal, Display display, int signalNum, boolean update) {
        signal.removeAll();
        signal.setLayout(new GridBagLayout());
        signal.setPreferredSize(new Dimension(Integer.parseInt(appProperties.getProperty("settings.width")), Integer.parseInt(appProperties.getProperty("settings.height"))));
        signal.setBorder(BorderFactory.createTitledBorder("Signal A"));
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Constant");
        comboBox.addItem("Sine");
        comboBox.addItem("Random");
        comboBox.addItem("Square");
        comboBox.addItem("Sawtooth");
        comboBox.addItem("Range");

        if (update) {
            if (signalNum == SIGNAL_A) {
                comboBox.setSelectedItem(currentSignalASettings.getSignalName());
            } else if (signalNum == SIGNAL_B) {
                comboBox.setSelectedItem(currentSignalBSettings.getSignalName());
            }
        }

        JPanel settingsPanel = new JPanel();
        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                SignalSettings signalSettings = currentSignalASettings;
                if ("Sine".equals(comboBox.getSelectedItem())) {
                    signalSettings = new SineSignalSettings();
                } else if ("Random".equals(comboBox.getSelectedItem())) {
                    signalSettings = new RandomSignalSettings();
                } else if ("Constant".equals(comboBox.getSelectedItem())) {
                    signalSettings = new ConstantSignalSettings();
                } else if ("Square".equals(comboBox.getSelectedItem())) {
                    signalSettings = new SquareSignalSettings();
                } else if ("Range".equals(comboBox.getSelectedItem())) {
                    signalSettings = new RangeSignalSettings();
                } else if ("Sawtooth".equals(comboBox.getSelectedItem())) {
                    signalSettings = new SawtoothSignalSettings();
                }
                if (signalNum == SIGNAL_A) {
                    settingsPanel.removeAll();
                    currentSignalASettings = signalSettings;
                    settingsPanel.add(signalSettings);
                    settingsPanel.updateUI();
                } else if (signalNum == SIGNAL_B) {
                    settingsPanel.removeAll();
                    currentSignalBSettings = signalSettings;
                    settingsPanel.add(signalSettings);
                    settingsPanel.updateUI();
                }
            }
        });

        if (signalNum == SIGNAL_A) {
            if (!update)
                currentSignalASettings = new ConstantSignalSettings();
            settingsPanel.add(currentSignalASettings);
        } else if (signalNum == SIGNAL_B) {
            if (!update)
                currentSignalBSettings = new ConstantSignalSettings();
            settingsPanel.add(currentSignalBSettings);
        }

        GridBagConstraints sac = new GridBagConstraints();
        sac.gridx = 0;
        sac.gridy = 0;
        sac.gridwidth = 1;
        sac.gridheight = 1;
        sac.anchor = GridBagConstraints.WEST;
        sac.weightx = 1;
        sac.weighty = 1;
        signal.add(comboBox, sac);

        sac.gridy = 1;
        signal.add(settingsPanel, sac);

        sac.gridx = 2;
        sac.gridy = 2;
        sac.gridwidth = 1;
        sac.gridheight = 1;
        sac.anchor = GridBagConstraints.EAST;

        if (update) {
            if (signalNum == SIGNAL_A) {
                display.setxInput(currentSignalASettings.getSignal());
            } else if (signalNum == SIGNAL_B) {
                display.setyInput(currentSignalBSettings.getSignal());
            }
        }

        JButton apply = new JButton("Apply");
        apply.addActionListener(e -> {
            try {
                if (signalNum == SIGNAL_A) {
                    display.setxInput(currentSignalASettings.getSignal());
                } else if (signalNum == SIGNAL_B) {
                    display.setyInput(currentSignalBSettings.getSignal());
                }
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "Wrong input data!");
            }
        });
        signal.add(apply, sac);
    }

    public Oscilloscope(Properties properties) {
        appProperties = properties;
        createGui();
    }

    private String getSignalConfiguration(SignalSettings settings, String name) {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(".type = ").append(settings.getSignalName()).append("\n");
        switch (settings.getSignalName()) {
            case "Sine":
                SineSignalSettings sineSignalSettings = (SineSignalSettings) settings;
                builder.append(name).append(".amplitude = ").append(sineSignalSettings.getAmplitude()).append("\n")
                        .append(name).append(".freq = ").append(sineSignalSettings.getFreq()).append("\n")
                        .append(name).append(".initphase = ").append(sineSignalSettings.getInitPhase()).append("\n");
                break;
            case "Constant":
                ConstantSignalSettings constantSignalSettings = (ConstantSignalSettings) settings;
                builder.append(name).append(".val = ").append(constantSignalSettings.getVal()).append("\n");
                break;
            case "Random":
                RandomSignalSettings randomSignalSettings = (RandomSignalSettings) settings;
                builder.append(name).append(".minval = ").append(randomSignalSettings.getMinVal()).append("\n")
                        .append(name).append(".maxval = ").append(randomSignalSettings.getMaxVal()).append("\n");
                break;
            case "Range":
                RangeSignalSettings rangeSignalSettings = (RangeSignalSettings) settings;
                builder.append(name).append(".minval = ").append(rangeSignalSettings.getMinVal()).append("\n")
                        .append(name).append(".maxval = ").append(rangeSignalSettings.getMaxVal()).append("\n")
                        .append(name).append(".amplitude = ").append(rangeSignalSettings.getAmplitude()).append("\n");
                break;
            case "Square":
                SquareSignalSettings squareSignalSettings = (SquareSignalSettings) settings;
                builder.append(name).append(".approximation = ").append(squareSignalSettings.getApprox()).append("\n")
                        .append(name).append(".cyclefrequency = ").append(squareSignalSettings.getCycleFrequency()).append("\n")
                        .append(name).append(".amplitude = ").append(squareSignalSettings.getAmplitude()).append("\n");
                break;
            case "Sawtooth":
                SawtoothSignalSettings sawtoothSignalSettings = (SawtoothSignalSettings) settings;
                builder.append(name).append(".approximation = ").append(sawtoothSignalSettings.getApproximation()).append("\n")
                        .append(name).append(".cyclefrequency = ").append(sawtoothSignalSettings.getFrequency()).append("\n")
                        .append(name).append(".amplitude = ").append(sawtoothSignalSettings.getAmplitude()).append("\n");
                break;
        }

        return builder.toString();
    }

    private String getConfiguration() {
        return getSignalConfiguration(currentSignalASettings, "signalA") +
                getSignalConfiguration(currentSignalBSettings, "signalB");
    }

    private SignalSettings parseSignalSettings(Properties properties, String name) {
        SignalSettings signalSettings = null;
        switch (properties.getProperty(name + ".type")) {
            case "Sine":
                signalSettings = new SineSignalSettings(
                        Double.parseDouble(properties.getProperty(name + ".amplitude")),
                        Double.parseDouble(properties.getProperty(name + ".freq")),
                        Double.parseDouble(properties.getProperty(name + ".initphase"))
                );
                break;
            case "Constant":
                signalSettings = new ConstantSignalSettings(
                        Double.parseDouble(properties.getProperty(name + ".val"))
                );
                break;
            case "Random":
                signalSettings = new RandomSignalSettings(
                        Double.parseDouble(properties.getProperty(name + ".minval")),
                        Double.parseDouble(properties.getProperty(name + ".maxval"))
                );
                break;
            case "Range":
                signalSettings = new RangeSignalSettings(
                        Double.parseDouble(properties.getProperty(name + ".minval")),
                        Double.parseDouble(properties.getProperty(name + ".maxval")),
                        Double.parseDouble(properties.getProperty(name + ".amplitude"))
                );
                break;
            case "Square":
                signalSettings = new SquareSignalSettings(
                        Double.parseDouble(properties.getProperty(name + ".cyclefrequency")),
                        Integer.parseInt(properties.getProperty(name + ".approximation")),
                        Double.parseDouble(properties.getProperty(name + ".amplitude"))
                );
                break;
            case "Sawtooth":
                signalSettings = new SawtoothSignalSettings(
                        Double.parseDouble(properties.getProperty(name + ".cyclefrequency")),
                        Double.parseDouble(properties.getProperty(name + ".amplitude")),
                        Integer.parseInt(properties.getProperty(name + ".approximation"))
                );
                break;
        }

        return signalSettings;
    }

    private void createGui() {
        setTitle("Oscilloscope Lab v1.0 by Mikhail Ushakov M3202");

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Container pane = new Container();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save Configuration");
        JMenuItem load = new JMenuItem("Load Configuration");
        menuBar.add(file);
        file.add(save);
        file.add(load);
        pane.add(menuBar, c);

        Display display = new Display(appProperties);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 3;
        pane.add(display, c);

        JPanel signalA = new JPanel();
        createSignalSettings(signalA, display, SIGNAL_A, false);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 5;
        c.gridheight = 1;
        pane.add(signalA, c);

        JPanel signalB = new JPanel();
        createSignalSettings(signalB, display, SIGNAL_B, false);
        c.gridy = 2;
        pane.add(signalB, c);

        display.setxInput(currentSignalASettings.getSignal());
        display.setyInput(currentSignalBSettings.getSignal());

        save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save configuration");
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                try (FileWriter fileWriter = new FileWriter(selected)) {
                    fileWriter.write(getConfiguration());
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });

        load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load configuration");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    Properties properties = new Properties();
                    properties.load(new FileInputStream(fileChooser.getSelectedFile()));
                    currentSignalASettings = parseSignalSettings(properties, "signalA");
                    currentSignalBSettings = parseSignalSettings(properties, "signalB");

                    createSignalSettings(signalA, display, SIGNAL_A, true);
                    createSignalSettings(signalB, display, SIGNAL_B, true);
                    signalA.updateUI();
                    signalB.updateUI();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            if (start.getText().equals("Start")) {
                display.start();
                start.setText("Stop");
            } else if (start.getText().equals("Stop")) {
                display.stop();
                start.setText("Start");
            }
        });
        c.gridx = 3;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(start, c);

        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> display.clear());
        c.gridx = 4;
        pane.add(clear, c);

        JCheckBox grid = new JCheckBox("Draw grid");
        grid.addActionListener(e -> {
            display.setGrid(grid.isSelected());
            display.repaint();
        });
        c.gridx = 0;
        pane.add(grid, c);

        add(pane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
    }

    public void showGui() {
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("app.properties"));

        SwingUtilities.invokeLater(() -> new Oscilloscope(properties).showGui());
    }
}
