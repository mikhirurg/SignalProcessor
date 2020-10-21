package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.ConstantSignal;
import io.github.mikhirurg.signalprocessor.math.RandomSignal;
import io.github.mikhirurg.signalprocessor.math.Signal;
import io.github.mikhirurg.signalprocessor.util.Application;
import io.github.mikhirurg.signalprocessor.util.Cortege;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class Display extends JPanel {
    private Signal xInput;
    private Signal yInput;

    private final Timer timer;
    private final DisplayImage image;
    private long startTime;
    private boolean isGrid;
    private List<Cortege<Double>> data;
    private boolean isSaving;

    private final int width;
    private final int height;
    private final int gridSize;

    private double oldX = 0;
    private double oldY = 0;

    private boolean everRun = false;


    public Display() {
        this.width = Integer.parseInt(Application.getProperty("display.width"));
        this.height = Integer.parseInt(Application.getProperty("display.height"));
        xInput = new ConstantSignal(0, false, new RandomSignal(0, 0));
        yInput = new ConstantSignal(0, false, new RandomSignal(0, 0));
        setPreferredSize(new Dimension(width, height));

        setBackground(Color.BLACK);
        timer = new Timer(10, e -> {
            repaint();
        });

        image = new DisplayImage(
                width,
                height,
                Integer.parseInt(Application.getProperty("display.capacity")),
                Integer.parseInt(Application.getProperty("display.len"))
        );
        gridSize = Integer.parseInt(Application.getProperty("grid.size"));
        oldX = width / 2.0;
        oldY = height / 2.0;
        isSaving = false;
        data = new LinkedList<>();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (timer.isRunning()) {
            double time = (System.currentTimeMillis() - startTime) / 1000.0;

            double genx = xInput.generateValue(time);
            double geny = yInput.generateValue(time);

            if (isSaving)
                data.add(new Cortege<>(time, genx, geny));

            double x = width / 2.0 + genx;
            double y = height / 2.0 - geny;
            image.drawLine(new Line(oldX, oldY, x, y));
            oldX = x;
            oldY = y;
            g2d.drawImage(image.getImage(), 0, 0, null);
            g2d.setColor(Color.RED);
            g2d.drawString("t: " + time, 0, g2d.getFontMetrics().getHeight());
        } else {
            if (everRun) {
                g2d.drawImage(image.getImage(), 0, 0, null);
            }
        }
        if (isGrid)
            drawGrid(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
        Color old = g2d.getColor();
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(0.5f));
        g2d.setColor(new Color(255, 255, 255, 80));
        for (int y = height / 2; y <= height; y += gridSize) {
            g2d.drawLine(0, y, width, y);
            if (y > height / 2)
                g2d.drawLine(0, height - y, width, height - y);
        }
        for (int x = width / 2; x <= width; x += gridSize) {
            g2d.drawLine(x, 0, x, height);
            if (x > width / 2)
                g2d.drawLine(width - x, 0, width - x, height);
        }
        g2d.setColor(old);
        g2d.setStroke(oldStroke);
    }

    public void start() {
        everRun = true;
        startTime = System.currentTimeMillis();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void clear() {
        image.clear();
        repaint();
    }

    public List<Cortege<Double>> getData() {
        return data;
    }

    public void setSaving(boolean isSaving) {
        this.isSaving = isSaving;
    }

    public void clearData() {
        data = new LinkedList<>();
    }

    public void setxInput(Signal xInput) {
        this.xInput = xInput;
    }

    public void setyInput(Signal yInput) {
        this.yInput = yInput;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
    }
}
