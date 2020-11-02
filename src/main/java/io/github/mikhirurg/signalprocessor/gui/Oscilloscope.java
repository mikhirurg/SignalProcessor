package io.github.mikhirurg.signalprocessor.gui;

import com.formdev.flatlaf.FlatIntelliJLaf;
import io.github.mikhirurg.signalprocessor.tools.FourierProcessingWindow;
import io.github.mikhirurg.signalprocessor.util.Application;
import io.github.mikhirurg.signalprocessor.util.Cortege;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;

public class Oscilloscope extends JFrame {

    private SignalSettings currentSignalASettings;
    private SignalSettings currentSignalBSettings;

    private final int SIGNAL_A = 1;
    private final int SIGNAL_B = 2;


    private void createSignalSettings(JPanel signal, Display display, int signalNum, boolean update) {
        signal.removeAll();
        signal.setLayout(new GridBagLayout());
        signal.setPreferredSize(new Dimension(Integer.parseInt(Application.getProperty("settings.width")),
                Integer.parseInt(Application.getProperty("settings.height"))));
        signal.setBorder(BorderFactory.createTitledBorder((signalNum == SIGNAL_A)
                ? Application.getString("signal.signalA") : Application.getString("signal.signalB")));
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(Application.getString("type.constant"));
        comboBox.addItem(Application.getString("type.sine"));
        comboBox.addItem(Application.getString("type.random"));
        comboBox.addItem(Application.getString("type.square"));
        comboBox.addItem(Application.getString("type.sawtooth"));
        comboBox.addItem(Application.getString("type.range"));

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
                if (Application.getString("type.sine").equals(comboBox.getSelectedItem())) {
                    signalSettings = new SineSignalSettings();
                } else if (Application.getString("type.random").equals(comboBox.getSelectedItem())) {
                    signalSettings = new RandomSignalSettings();
                } else if (Application.getString("type.constant").equals(comboBox.getSelectedItem())) {
                    signalSettings = new ConstantSignalSettings();
                } else if (Application.getString("type.square").equals(comboBox.getSelectedItem())) {
                    signalSettings = new SquareSignalSettings();
                } else if (Application.getString("type.range").equals(comboBox.getSelectedItem())) {
                    signalSettings = new RangeSignalSettings();
                } else if (Application.getString("type.sawtooth").equals(comboBox.getSelectedItem())) {
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

        sac.gridx = 0;
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

        JButton apply = new JButton(Application.getString("button.apply"));
        apply.addActionListener(e -> {
            try {
                if (signalNum == SIGNAL_A) {
                    display.setxInput(currentSignalASettings.getSignal());
                } else if (signalNum == SIGNAL_B) {
                    display.setyInput(currentSignalBSettings.getSignal());
                }
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, Application.getString("dialog.message"));
            }
        });
        signal.add(apply, sac);
    }

    public Oscilloscope() {
        createGui();
    }

    private String getSignalConfiguration(SignalSettings settings, String name) {
        StringBuilder builder = new StringBuilder();
        String signalName = settings.getSignalId();
        builder.append(name).append(".type = ").append(signalName).append("\n");
        if (signalName.equals(Application.getUSString("type.sine"))) {
            SineSignalSettings sineSignalSettings = (SineSignalSettings) settings;
            builder.append(name).append(".amplitude = ").append(sineSignalSettings.getAmplitude()).append("\n")
                    .append(name).append(".freq = ").append(sineSignalSettings.getFreq()).append("\n")
                    .append(name).append(".initphase = ").append(sineSignalSettings.getInitPhase()).append("\n");
        } else if (signalName.equals(Application.getUSString("type.constant"))) {
            ConstantSignalSettings constantSignalSettings = (ConstantSignalSettings) settings;
            builder.append(name).append(".val = ").append(constantSignalSettings.getVal()).append("\n");
        } else if (signalName.equals(Application.getUSString("type.random"))) {
            RandomSignalSettings randomSignalSettings = (RandomSignalSettings) settings;
            builder.append(name).append(".minval = ").append(randomSignalSettings.getMinVal()).append("\n")
                    .append(name).append(".maxval = ").append(randomSignalSettings.getMaxVal()).append("\n");
        } else if (signalName.equals(Application.getUSString("type.range"))) {
            RangeSignalSettings rangeSignalSettings = (RangeSignalSettings) settings;
            builder.append(name).append(".minval = ").append(rangeSignalSettings.getMinVal()).append("\n")
                    .append(name).append(".maxval = ").append(rangeSignalSettings.getMaxVal()).append("\n")
                    .append(name).append(".amplitude = ").append(rangeSignalSettings.getAmplitude()).append("\n");
        } else if (signalName.equals(Application.getUSString("type.square"))) {
            SquareSignalSettings squareSignalSettings = (SquareSignalSettings) settings;
            builder.append(name).append(".approximation = ").append(squareSignalSettings.getApprox()).append("\n")
                    .append(name).append(".cyclefrequency = ").append(squareSignalSettings.getCycleFrequency()).append("\n")
                    .append(name).append(".amplitude = ").append(squareSignalSettings.getAmplitude()).append("\n");
        } else if (signalName.equals(Application.getUSString("type.sawtooth"))) {
            SawtoothSignalSettings sawtoothSignalSettings = (SawtoothSignalSettings) settings;
            builder.append(name).append(".approximation = ").append(sawtoothSignalSettings.getApproximation()).append("\n")
                    .append(name).append(".cyclefrequency = ").append(sawtoothSignalSettings.getFrequency()).append("\n")
                    .append(name).append(".amplitude = ").append(sawtoothSignalSettings.getAmplitude()).append("\n");
        }

        if (!signalName.equals(Application.getUSString("type.random"))) {
            Noisable noisable = (Noisable) settings;
            builder.append(name).append(".minrval = ").append(noisable.getMinRVal()).append("\n")
                    .append(name).append(".maxrval = ").append(noisable.getMaxRVal()).append("\n")
                    .append(name).append(".noised = ").append(noisable.isNoised()).append("\n");
        }

        return builder.toString();
    }

    private String getConfiguration() {
        return getSignalConfiguration(currentSignalASettings, Application.getUSString("signal.signalA")) +
                getSignalConfiguration(currentSignalBSettings, Application.getUSString("signal.signalB"));
    }

    private SignalSettings parseSignalSettings(Properties properties, String name) {
        SignalSettings signalSettings = null;
        String type = properties.getProperty(name + ".type");
        if (type.equals(Application.getUSString("type.sine"))) {
            signalSettings = new SineSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".amplitude")),
                    Double.parseDouble(properties.getProperty(name + ".freq")),
                    Double.parseDouble(properties.getProperty(name + ".initphase")),
                    Double.parseDouble(properties.getProperty(name + ".minrval")),
                    Double.parseDouble(properties.getProperty(name + ".maxrval")),
                    Boolean.parseBoolean(properties.getProperty(name + ".noised"))
            );
        } else if (type.equals(Application.getUSString("type.constant"))) {
            signalSettings = new ConstantSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".val")),
                    Double.parseDouble(properties.getProperty(name + ".minrval")),
                    Double.parseDouble(properties.getProperty(name + ".maxrval")),
                    Boolean.parseBoolean(properties.getProperty(name + ".noised"))
            );
        } else if (type.equals(Application.getUSString("type.random"))) {
            signalSettings = new RandomSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".minval")),
                    Double.parseDouble(properties.getProperty(name + ".maxval"))
            );
        } else if (type.equals(Application.getUSString("type.range"))) {
            signalSettings = new RangeSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".minval")),
                    Double.parseDouble(properties.getProperty(name + ".maxval")),
                    Double.parseDouble(properties.getProperty(name + ".amplitude")),
                    Double.parseDouble(properties.getProperty(name + ".minrval")),
                    Double.parseDouble(properties.getProperty(name + ".maxrval")),
                    Boolean.parseBoolean(properties.getProperty(name + ".noised"))
            );
        } else if (type.equals(Application.getUSString("type.square"))) {
            signalSettings = new SquareSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".cyclefrequency")),
                    Integer.parseInt(properties.getProperty(name + ".approximation")),
                    Double.parseDouble(properties.getProperty(name + ".amplitude")),
                    Double.parseDouble(properties.getProperty(name + ".minrval")),
                    Double.parseDouble(properties.getProperty(name + ".maxrval")),
                    Boolean.parseBoolean(properties.getProperty(name + ".noised"))
            );
        } else if (type.equals(Application.getUSString("type.sawtooth"))) {
            signalSettings = new SawtoothSignalSettings(
                    Double.parseDouble(properties.getProperty(name + ".cyclefrequency")),
                    Double.parseDouble(properties.getProperty(name + ".amplitude")),
                    Integer.parseInt(properties.getProperty(name + ".approximation")),
                    Double.parseDouble(properties.getProperty(name + ".minrval")),
                    Double.parseDouble(properties.getProperty(name + ".maxrval")),
                    Boolean.parseBoolean(properties.getProperty(name + ".noised"))
            );
        }

        return signalSettings;
    }

    private void createGui() {
        setTitle(Application.getString("title"));

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
        JMenu file = new JMenu(Application.getString("menu.file"));
        JMenuItem save = new JMenuItem(Application.getString("menu.file.save"));
        JMenuItem load = new JMenuItem(Application.getString("menu.file.load"));
        menuBar.add(file);
        file.add(save);
        file.add(load);
        pane.add(menuBar, c);

        JMenu image = new JMenu(Application.getString("menu.image"));
        JMenuItem saveImage = new JMenuItem(Application.getString("menu.image.save"));
        menuBar.add(image);
        image.add(saveImage);

        JMenu tools = new JMenu(Application.getString("menu.tools"));
        JMenuItem fourier = new JMenuItem(Application.getString("menu.tools.fourier"));
        menuBar.add(tools);
        tools.add(fourier);

        Display display = new Display();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridheight = 8;
        pane.add(display, c);

        JPanel signalA = new JPanel();
        createSignalSettings(signalA, display, SIGNAL_A, false);
        c.gridx = 4;
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
            fileChooser.setDialogTitle(Application.getString("menu.file.save"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Properties files", "properties");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                try (FileWriter fileWriter = new FileWriter(selected)) {
                    fileWriter.write(getConfiguration());
                } catch (IOException fileNotFoundException) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.save"));
                }
            }
        });

        load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(Application.getString("menu.file.load"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Properties files", "properties");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    Properties properties = new Properties();
                    properties.load(new FileInputStream(fileChooser.getSelectedFile()));
                    currentSignalASettings = parseSignalSettings(properties, Application.getUSString("signal.signalA"));
                    currentSignalBSettings = parseSignalSettings(properties, Application.getUSString("signal.signalB"));

                    createSignalSettings(signalA, display, SIGNAL_A, true);
                    createSignalSettings(signalB, display, SIGNAL_B, true);
                    signalA.updateUI();
                    signalB.updateUI();
                } catch (IOException | NullPointerException exception) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.load"));
                }
            }
        });

        saveImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(Application.getString("menu.image.save"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG files", "png");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                Dimension dimension = display.getPreferredSize();
                BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
                display.paint(bufferedImage.getGraphics());
                try {
                    ImageIO.write(bufferedImage, "PNG", selected);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.image.save"));
                }
            }
        });

        FourierProcessingWindow fourierProcessingWindow = new FourierProcessingWindow(display);
        fourier.addActionListener(e -> fourierProcessingWindow.setVisible(true));

        JCheckBox grid = new JCheckBox(Application.getString("checkbox.grid"));
        grid.addActionListener(e -> {
            display.setGrid(grid.isSelected());
            display.repaint();
        });
        c.gridx = 4;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets.left = Integer.parseInt(Application.getProperty("settings.xinset"));
        c.anchor = GridBagConstraints.NORTHWEST;
        pane.add(grid, c);

        JLabel array = new JLabel(Application.getString("textarea.array") + " (" + display.getData().size() + ")");
        c.gridx = 6;
        c.gridy = 3;
        pane.add(array, c);

        JCheckBox isSavingData = new JCheckBox(Application.getString("checkbox.data"));
        isSavingData.addActionListener(e -> display.setSaving(isSavingData.isSelected()));
        c.gridx = 4;
        c.gridy = 4;
        c.insets.top = Integer.parseInt(Application.getProperty("settings.yinset"));
        pane.add(isSavingData, c);


        JTextArea textArea = new JTextArea();
        textArea.setText(display.getData().stream().map(e ->
                e.toString().replaceAll(",", " : "))
                        .collect(Collectors.joining("\n")));

        textArea.setRows(8);
        textArea.setColumns(20);
        textArea.setEditable(false);
        display.getTimer().addActionListener(e -> {
            textArea.setText( display.getData().stream().map(e1 ->
                e1.toString().replaceAll(",", " : "))
                        .collect(Collectors.joining("\n")));
            array.setText(Application.getString("textarea.array") + " (" + display.getData().size() + ")");
        });

        c.gridx = 6;
        c.gridy = 4;
        c.gridheight = 5;
        pane.add(new JScrollPane(textArea), c);

        JButton saveData = new JButton(Application.getString("button.savedata"));
        saveData.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(Application.getString("button.savedata"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                try (FileWriter fileWriter = new FileWriter(selected)) {
                    fileWriter.write(display.getData().stream()
                            .map(Cortege::toString)
                            .collect(Collectors.joining("\n"))
                    );
                } catch (IOException fileNotFoundException) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.data.save"));
                }
            }
        });
        c.gridx = 4;
        c.gridy = 6;
        c.gridheight = 1;
        pane.add(saveData, c);

        JButton clearData = new JButton(Application.getString("button.cleardata"));
        clearData.addActionListener(e -> {
            display.clearData();
            textArea.setText("");
            array.setText(Application.getString("textarea.array") + " (" + display.getData().size() + ")");
        });
        c.gridx = 4;
        c.gridy = 7;
        pane.add(clearData, c);

        JButton start = new JButton(Application.getString("button.start"));
        start.addActionListener(e -> {
            if (start.getText().equals(Application.getString("button.start"))) {
                display.start();
                start.setText(Application.getString("button.stop"));
            } else if (start.getText().equals(Application.getString("button.stop"))) {
                display.stop();
                start.setText(Application.getString("button.start"));
            }
        });
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.gridx = 4;
        c.gridy = 8;
        pane.add(start, c);

        JButton clear = new JButton(Application.getString("button.clear"));
        clear.addActionListener(e -> display.clear());
        c.gridx = 5;
        pane.add(clear, c);

        add(pane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
    }

    public void showGui() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Oscilloscope().showGui());
    }
}
