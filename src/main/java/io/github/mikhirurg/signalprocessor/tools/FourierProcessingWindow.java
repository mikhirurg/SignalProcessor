package io.github.mikhirurg.signalprocessor.tools;

import io.github.mikhirurg.signalprocessor.gui.Display;
import io.github.mikhirurg.signalprocessor.math.FourierProcessor;
import io.github.mikhirurg.signalprocessor.util.Application;
import io.github.mikhirurg.signalprocessor.util.ComplexNumber;
import io.github.mikhirurg.signalprocessor.util.Cortege;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class FourierProcessingWindow extends JFrame {
    private final FourierPanel graph;
    private final Display display;
    private List<ComplexNumber> data;

    public FourierProcessingWindow(Display display) {
        setTitle(Application.getString("menu.tools.fourier"));
        graph = new FourierPanel();
        data = new LinkedList<>();
        this.display = display;
        int width = Integer.parseInt(Application.getProperty("display.width"));
        int height = Integer.parseInt(Application.getProperty("display.height"));
        graph.setPreferredSize(new Dimension(width, height));
        graph.setBackground(Color.BLACK);
        JButton button = new JButton(Application.getString("button.processdata"));
        JButton importCsv = new JButton(Application.getString("button.importcsv"));
        JButton saveProcessed = new JButton(Application.getString("button.savedata"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;

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

        c.gridy = 1;
        c.gridx = 0;
        add(button, c);
        button.addActionListener(e -> {
            try {
                int signal = signalA.isSelected() ? 0 : 1;
                data = FourierProcessor.getFourierProcessing(display.getData(), signal);
                if (display.getData().size() > 0 && data.size() > 0) {
                    graph.setSignalData(display.getData());
                    graph.setData(data);
                    graph.repaint();
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
                    List<Cortege<Double>> signalData = FourierProcessor.getSignalData(selected);
                    data = FourierProcessor.getFourierProcessing(selected, signal);
                    if (signalData.size() > 0 && data.size() > 0) {
                        graph.setSignalData(signalData);
                        graph.setData(data);
                        graph.repaint();
                    } else throw new Exception();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.emptyarray"));
                }
            }
        });
        c.gridy = 1;
        c.gridx = 1;
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
                            .map(ComplexNumber::toString)
                            .collect(Collectors.joining("\n"))
                    );
                } catch (IOException fileNotFoundException) {
                    JOptionPane.showMessageDialog(this, Application.getString("dialog.data.save"));
                }
            }
        });
        c.gridx = 2;
        add(saveProcessed, c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 3;
        add(graph, c);
        setResizable(false);
        pack();

    }


}
