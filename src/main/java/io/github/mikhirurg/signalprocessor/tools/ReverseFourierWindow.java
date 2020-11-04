package io.github.mikhirurg.signalprocessor.tools;

import io.github.mikhirurg.signalprocessor.gui.Display;
import io.github.mikhirurg.signalprocessor.math.FourierProcessor;
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

public class ReverseFourierWindow extends JFrame {
    private final GraphPanel graph;
    private List<Cortege<Double>> data;

    public ReverseFourierWindow(Display display) {
        setTitle(Application.getString("menu.tools.reversefourier"));
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
        c.anchor = GridBagConstraints.NORTH;
        c.insets.top = Integer.parseInt(Application.getProperty("settings.yinset"));
        c.insets.left = Integer.parseInt(Application.getProperty("settings.xinset"));

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

        JLabel minLabel = new JLabel(Application.getString("label.minvalue"));
        c.gridy = 1;
        c.gridx = 1;
        add(minLabel, c);

        JTextField min = new JTextField("10");
        c.gridy = 1;
        c.gridx = 2;
        add(min, c);

        JLabel maxLabel = new JLabel(Application.getString("label.maxvalue"));
        c.gridy = 2;
        c.gridx = 1;
        add(maxLabel, c);

        JTextField max = new JTextField("10");
        c.gridy = 2;
        c.gridx = 2;
        add(max, c);

        c.gridy = 3;
        c.gridwidth = 1;
        c.gridx = 1;
        add(button, c);
        button.addActionListener(e -> {
            try {
                int signal = signalA.isSelected() ? 0 : 1;
                double minVal = Double.parseDouble(min.getText());
                double maxVal = Double.parseDouble(max.getText());
                data = FourierProcessor.getReverseFourierProcessing(display.getData(),
                        FourierProcessor.filterFourier(FourierProcessor.getFourierProcessing(display.getData(), signal),
                                minVal, maxVal), signal);
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
                    double minVal = Double.parseDouble(min.getText());
                    double maxVal = Double.parseDouble(max.getText());
                    List<Cortege<Double>> signalData = FourierProcessor.getSignalData(selected);
                    data = FourierProcessor.getReverseFourierProcessing(signalData,
                            FourierProcessor.filterFourier(FourierProcessor.getFourierProcessing(signalData, signal),
                                    minVal, maxVal), signal);
                    if (signalData.size() > 0 && data.size() > 0) {
                        graph.setSignalData(data);
                        graph.start();
                    } else throw new Exception();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.emptyarray"));
                }
            }
        });
        c.gridy = 3;
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
        c.gridy = 4;
        add(saveProcessed, c);

        c.gridy = 0;
        c.gridx = 0;
        c.gridheight = 5;
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
