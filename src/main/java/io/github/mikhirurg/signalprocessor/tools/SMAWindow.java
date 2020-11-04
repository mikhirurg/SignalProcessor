package io.github.mikhirurg.signalprocessor.tools;

import io.github.mikhirurg.signalprocessor.gui.Display;
import io.github.mikhirurg.signalprocessor.math.SMAProcessor;
import io.github.mikhirurg.signalprocessor.util.Application;
import io.github.mikhirurg.signalprocessor.util.Cortege;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SMAWindow extends JFrame {
    private final GraphPanel graph;
    private List<Cortege<Double>> data;

    public SMAWindow(Display display) {
        setTitle(Application.getString("menu.tools.sma"));
        graph = new GraphPanel();
        data = new LinkedList<>();
        int width = Integer.parseInt(Application.getProperty("display.width"));
        int height = Integer.parseInt(Application.getProperty("display.height"));
        graph.setPreferredSize(new Dimension(width, height));
        graph.setBackground(Color.BLACK);
        JButton button = new JButton(Application.getString("button.processdata"));
        JButton importCsv = new JButton(Application.getString("button.importcsv"));
        JButton saveProcessed = new JButton(Application.getString("button.savedata"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets.top = Integer.parseInt(Application.getProperty("settings.yinset"));
        c.insets.left = Integer.parseInt(Application.getProperty("settings.xinset"));
        c.anchor = GridBagConstraints.NORTH;

        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton signalA = new JRadioButton(Application.getString("signal.signalA"));
        JRadioButton signalB = new JRadioButton(Application.getString("signal.signalB"));
        buttonGroup.add(signalA);
        buttonGroup.add(signalB);
        signalA.setSelected(true);
        add(signalA, c);

        c.gridy = 0;
        c.gridx = 2;
        add(signalB, c);

        JLabel windowLabel = new JLabel(Application.getString("label.window"));
        c.gridy = 1;
        c.gridx = 1;
        add(windowLabel, c);

        JTextField textField = new JTextField("10");
        c.gridy = 1;
        c.gridx = 2;
        c.gridwidth = 1;
        add(textField, c);

        c.gridy = 2;
        c.gridwidth = 1;
        c.gridx = 1;
        add(button, c);
        button.addActionListener(e -> {
            try {
                int signal = signalA.isSelected() ? 0 : 1;
                int window = Integer.parseInt(textField.getText());
                data = SMAProcessor.getSMAProcessing(display.getData(), window, signal);
                if (display.getData().size() > 0 && data.size() > 0) {
                    graph.setSignalData(data);
                    graph.start();
                } else throw new Exception();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, Application.getString("dialog.emptyarray"));
            }
        });

        importCsv.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(Application.getString("button.importcsv"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                try {
                    int signal = signalA.isSelected() ? 0 : 1;
                    int window = Integer.parseInt(textField.getText());
                    List<Cortege<Double>> signalData = SMAProcessor.getSignalData(selected);
                    data = SMAProcessor.getSMAProcessing(selected, window, signal);
                    if (signalData.size() > 0 && data.size() > 0) {
                        graph.setSignalData(data);
                        graph.start();
                    } else throw new Exception();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.emptyarray"));
                }
            }
        });
        c.gridy = 2;
        c.gridx = 2;
        add(importCsv, c);

        saveProcessed.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(Application.getString("button.savedata"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                try (FileWriter fileWriter = new FileWriter(selected)) {
                    fileWriter.write(data.stream()
                            .map(Cortege::toString)
                            .collect(Collectors.joining("\n"))
                    );
                } catch (IOException fileNotFoundException) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.data.save"));
                }
            }
        });
        c.gridx = 1;
        c.gridy = 3;
        add(saveProcessed, c);

        c.gridy = 0;
        c.gridx = 0;
        c.gridheight = 4;
        c.insets.top = 0;
        c.insets.left = 0;
        add(graph, c);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                graph.stop();
            }
        });

        setResizable(false);
        pack();
    }
}
