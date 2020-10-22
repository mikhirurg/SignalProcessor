package io.github.mikhirurg.signalprocessor.tools;

import io.github.mikhirurg.signalprocessor.gui.Display;
import io.github.mikhirurg.signalprocessor.util.Application;
import io.github.mikhirurg.signalprocessor.util.ComplexNumber;
import io.github.mikhirurg.signalprocessor.util.Cortege;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class FourierPanel extends JPanel {
    private List<Cortege<Double>> signalData;
    private List<ComplexNumber> data;

    public FourierPanel() {
        signalData = new LinkedList<>();
        data = new LinkedList<>();
        new Timer(50, e -> {
            repaint();
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = Integer.parseInt(Application.getProperty("display.width"));
        int height = Integer.parseInt(Application.getProperty("display.height"));
        if (signalData.size() > 0 && data.size() > 0) {
            double T = signalData.get(signalData.size() - 1).getFirst();
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.WHITE);
            double maxY = data.stream().max((e1, e2) -> (int) (e1.getABS() - e2.getABS())).orElseThrow().getABS();
            double maxX = IntStream.range(0, data.size()).mapToDouble(e1 -> e1 / T * width).max().orElse(0);
            for (int i = 0; i < data.size(); i++) {
                double v = ((i / T) * width) / maxX * width;
                g2d.drawLine((int) v, height, (int) v, (int) (height - data.get(i).getABS() / maxY * height));
            }
        }
    }

    public void setSignalData(List<Cortege<Double>> signalData) {
        this.signalData = signalData;
    }

    public void setData(List<ComplexNumber> data) {
        this.data = data;
    }
}
