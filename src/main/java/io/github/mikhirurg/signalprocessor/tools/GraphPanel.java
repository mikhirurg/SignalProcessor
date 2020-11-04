package io.github.mikhirurg.signalprocessor.tools;

import io.github.mikhirurg.signalprocessor.gui.DisplayImage;
import io.github.mikhirurg.signalprocessor.gui.Line;
import io.github.mikhirurg.signalprocessor.util.Application;
import io.github.mikhirurg.signalprocessor.util.Cortege;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GraphPanel extends JPanel {
    private List<Cortege<Double>> signalData;
    private final DisplayImage image;
    private final Timer timer;
    private int ind = 0;
    private double oldX = 0;
    private double oldY = 0;
    private final int width;
    private final int height;

    public GraphPanel() {
        signalData = new LinkedList<>();
        timer = new Timer(10, e -> repaint());
        width = Integer.parseInt(Application.getProperty("display.width"));
        height = Integer.parseInt(Application.getProperty("display.height"));
        image = new DisplayImage(
                width,
                height,
                Integer.parseInt(Application.getProperty("display.capacity")),
                Integer.parseInt(Application.getProperty("display.len"))
        );
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (signalData.size() > 0) {
            double genx = signalData.get(ind).getSecond();
            double geny = signalData.get(ind).getThird();

            double x = width / 2.0 + genx;
            double y = height / 2.0 - geny;
            image.drawLine(new Line(oldX, oldY, x, y));
            oldX = x;
            oldY = y;
            g2d.drawImage(image.getImage(), 0, 0, null);

            ind = (ind + 1) % signalData.size();
        }
    }

    public void start() {
        ind = 0;
        image.clear();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void setSignalData(List<Cortege<Double>> signalData) {
        this.signalData = signalData;
    }

}
