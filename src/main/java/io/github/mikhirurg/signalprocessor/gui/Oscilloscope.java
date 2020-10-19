package io.github.mikhirurg.signalprocessor.gui;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Oscilloscope extends JFrame {

    private final Properties appProperties;
    private final ResourceBundle resourceBundle;
    private final ResourceBundle resourceBundleUS;

    private SignalSettings currentSignalASettings;
    private SignalSettings currentSignalBSettings;

    private final int SIGNAL_A = 1;
    private final int SIGNAL_B = 2;


    private void createSignalSettings(JPanel signal, Display display, int signalNum, boolean update) {
        signal.removeAll();
        signal.setLayout(new GridBagLayout());
        signal.setPreferredSize(new Dimension(Integer.parseInt(appProperties.getProperty("settings.width")), Integer.parseInt(appProperties.getProperty("settings.height"))));
        signal.setBorder(BorderFactory.createTitledBorder((signalNum == SIGNAL_A)
                ? resourceBundle.getString("signal.signalA") : resourceBundle.getString("signal.signalB")));
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(resourceBundle.getString("type.constant"));
        comboBox.addItem(resourceBundle.getString("type.sine"));
        comboBox.addItem(resourceBundle.getString("type.random"));
        comboBox.addItem(resourceBundle.getString("type.square"));
        comboBox.addItem(resourceBundle.getString("type.sawtooth"));
        comboBox.addItem(resourceBundle.getString("type.range"));

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
                if (resourceBundle.getString("type.sine").equals(comboBox.getSelectedItem())) {
                    signalSettings = new SineSignalSettings(resourceBundle, resourceBundleUS);
                } else if (resourceBundle.getString("type.random").equals(comboBox.getSelectedItem())) {
                    signalSettings = new RandomSignalSettings(resourceBundle, resourceBundleUS);
                } else if (resourceBundle.getString("type.constant").equals(comboBox.getSelectedItem())) {
                    signalSettings = new ConstantSignalSettings(resourceBundle, resourceBundleUS);
                } else if (resourceBundle.getString("type.square").equals(comboBox.getSelectedItem())) {
                    signalSettings = new SquareSignalSettings(resourceBundle, resourceBundleUS);
                } else if (resourceBundle.getString("type.range").equals(comboBox.getSelectedItem())) {
                    signalSettings = new RangeSignalSettings(resourceBundle, resourceBundleUS);
                } else if (resourceBundle.getString("type.sawtooth").equals(comboBox.getSelectedItem())) {
                    signalSettings = new SawtoothSignalSettings(resourceBundle, resourceBundleUS);
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
                currentSignalASettings = new ConstantSignalSettings(resourceBundle, resourceBundleUS);
            settingsPanel.add(currentSignalASettings);
        } else if (signalNum == SIGNAL_B) {
            if (!update)
                currentSignalBSettings = new ConstantSignalSettings(resourceBundle, resourceBundleUS);
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

        JButton apply = new JButton(resourceBundle.getString("button.apply"));
        apply.addActionListener(e -> {
            try {
                if (signalNum == SIGNAL_A) {
                    display.setxInput(currentSignalASettings.getSignal());
                } else if (signalNum == SIGNAL_B) {
                    display.setyInput(currentSignalBSettings.getSignal());
                }
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, resourceBundle.getString("dialog.message"));
            }
        });
        signal.add(apply, sac);
    }

    public Oscilloscope(Properties properties, ResourceBundle resourceBundle, ResourceBundle resourceBundleUS) {
        this.appProperties = properties;
        this.resourceBundle = resourceBundle;
        this.resourceBundleUS = resourceBundleUS;
        createGui();
    }

    private String getSignalConfiguration(SignalSettings settings, String name) {
        StringBuilder builder = new StringBuilder();
        String signalName = settings.getSignalId();
        builder.append(name).append(".type = ").append(signalName).append("\n");
        if (signalName.equals(resourceBundleUS.getString("type.sine"))) {
            SineSignalSettings sineSignalSettings = (SineSignalSettings) settings;
            builder.append(name).append(".amplitude = ").append(sineSignalSettings.getAmplitude()).append("\n")
                    .append(name).append(".freq = ").append(sineSignalSettings.getFreq()).append("\n")
                    .append(name).append(".initphase = ").append(sineSignalSettings.getInitPhase()).append("\n");
        } else if (signalName.equals(resourceBundleUS.getString("type.constant"))) {
            ConstantSignalSettings constantSignalSettings = (ConstantSignalSettings) settings;
            builder.append(name).append(".val = ").append(constantSignalSettings.getVal()).append("\n");
        } else if (signalName.equals(resourceBundleUS.getString("type.random"))) {
            RandomSignalSettings randomSignalSettings = (RandomSignalSettings) settings;
            builder.append(name).append(".minval = ").append(randomSignalSettings.getMinVal()).append("\n")
                    .append(name).append(".maxval = ").append(randomSignalSettings.getMaxVal()).append("\n");
        } else if (signalName.equals(resourceBundleUS.getString("type.range"))) {
            RangeSignalSettings rangeSignalSettings = (RangeSignalSettings) settings;
            builder.append(name).append(".minval = ").append(rangeSignalSettings.getMinVal()).append("\n")
                    .append(name).append(".maxval = ").append(rangeSignalSettings.getMaxVal()).append("\n")
                    .append(name).append(".amplitude = ").append(rangeSignalSettings.getAmplitude()).append("\n");
        } else if (signalName.equals(resourceBundleUS.getString("type.square"))) {
            SquareSignalSettings squareSignalSettings = (SquareSignalSettings) settings;
            builder.append(name).append(".approximation = ").append(squareSignalSettings.getApprox()).append("\n")
                    .append(name).append(".cyclefrequency = ").append(squareSignalSettings.getCycleFrequency()).append("\n")
                    .append(name).append(".amplitude = ").append(squareSignalSettings.getAmplitude()).append("\n");
        } else if (signalName.equals(resourceBundleUS.getString("type.sawtooth"))) {
            SawtoothSignalSettings sawtoothSignalSettings = (SawtoothSignalSettings) settings;
            builder.append(name).append(".approximation = ").append(sawtoothSignalSettings.getApproximation()).append("\n")
                    .append(name).append(".cyclefrequency = ").append(sawtoothSignalSettings.getFrequency()).append("\n")
                    .append(name).append(".amplitude = ").append(sawtoothSignalSettings.getAmplitude()).append("\n");
        }

        return builder.toString();
    }

    private String getConfiguration() {
        return getSignalConfiguration(currentSignalASettings, resourceBundleUS.getString("signal.signalA")) +
                getSignalConfiguration(currentSignalBSettings, resourceBundleUS.getString("signal.signalB"));
    }

    private SignalSettings parseSignalSettings(Properties properties, String name) {
        SignalSettings signalSettings = null;
        String type = properties.getProperty(name + ".type");
        if (type.equals(resourceBundleUS.getString("type.sine"))) {
            signalSettings = new SineSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".amplitude")),
                    Double.parseDouble(properties.getProperty(name + ".freq")),
                    Double.parseDouble(properties.getProperty(name + ".initphase")),
                    resourceBundle, resourceBundleUS);
        } else if (type.equals(resourceBundleUS.getString("type.constant"))) {
            signalSettings = new ConstantSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".val")),
                    resourceBundle, resourceBundleUS);
        } else if (type.equals(resourceBundleUS.getString("type.random"))) {
            signalSettings = new RandomSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".minval")),
                    Double.parseDouble(properties.getProperty(name + ".maxval")),
                    resourceBundle,
                    resourceBundleUS);
        } else if (type.equals(resourceBundleUS.getString("type.range"))) {
            signalSettings = new RangeSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".minval")),
                    Double.parseDouble(properties.getProperty(name + ".maxval")),
                    Double.parseDouble(properties.getProperty(name + ".amplitude")),
                    resourceBundle, resourceBundleUS);
        } else if (type.equals(resourceBundleUS.getString("type.square"))) {
            signalSettings = new SquareSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".cyclefrequency")),
                    Integer.parseInt(properties.getProperty(name + ".approximation")),
                    Double.parseDouble(properties.getProperty(name + ".amplitude")),
                    resourceBundle, resourceBundleUS);
        } else if (type.equals(resourceBundleUS.getString("type.sawtooth"))) {
            signalSettings = new SawtoothSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".cyclefrequency")),
                    Double.parseDouble(properties.getProperty(name + ".amplitude")),
                    Integer.parseInt(properties.getProperty(name + ".approximation")),
                    resourceBundle, resourceBundleUS);
        }

        return signalSettings;
    }

    private void createGui() {
        setTitle(resourceBundle.getString("title"));

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
        JMenu file = new JMenu(resourceBundle.getString("menu.file"));
        JMenuItem save = new JMenuItem(resourceBundle.getString("menu.file.save"));
        JMenuItem load = new JMenuItem(resourceBundle.getString("menu.file.load"));
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
            fileChooser.setDialogTitle(resourceBundle.getString("menu.file.save"));
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
            fileChooser.setDialogTitle(resourceBundle.getString("menu.file.load"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    Properties properties = new Properties();
                    properties.load(new FileInputStream(fileChooser.getSelectedFile()));
                    currentSignalASettings = parseSignalSettings(properties, resourceBundleUS.getString("signal.signalA"));
                    currentSignalBSettings = parseSignalSettings(properties, resourceBundleUS.getString("signal.signalB"));

                    createSignalSettings(signalA, display, SIGNAL_A, true);
                    createSignalSettings(signalB, display, SIGNAL_B, true);
                    signalA.updateUI();
                    signalB.updateUI();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        JButton start = new JButton(resourceBundle.getString("button.start"));
        start.addActionListener(e -> {
            if (start.getText().equals(resourceBundle.getString("button.start"))) {
                display.start();
                start.setText(resourceBundle.getString("button.stop"));
            } else if (start.getText().equals(resourceBundle.getString("button.stop"))) {
                display.stop();
                start.setText(resourceBundle.getString("button.start"));
            }
        });
        c.gridx = 3;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(start, c);

        JButton clear = new JButton(resourceBundle.getString("button.clear"));
        clear.addActionListener(e -> display.clear());
        c.gridx = 4;
        pane.add(clear, c);

        JCheckBox grid = new JCheckBox(resourceBundle.getString("checkbox.grid"));
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
        try {
            properties.load(new FileInputStream("app.properties"));
        } catch (FileNotFoundException e) {
            properties.load(Oscilloscope.class.getClassLoader().getResourceAsStream("app.properties"));
        }
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("AppBundle");
        ResourceBundle resourceBundleUS = PropertyResourceBundle.getBundle("AppBundle", Locale.forLanguageTag("en-us"));
        SwingUtilities.invokeLater(() -> new Oscilloscope(properties, resourceBundle, resourceBundleUS).showGui());
    }
}
