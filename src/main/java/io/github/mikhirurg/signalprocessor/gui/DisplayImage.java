package io.github.mikhirurg.signalprocessor.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class DisplayImage {
    private final List<Line> lineList;
    private final BufferedImage img;

    private final int width;
    private final int height;
    private final int capacity;
    private final double len;

    public DisplayImage(int width, int height, int capacity, double len) {
        this.width = width;
        this.height = height;
        this.capacity = capacity;
        this.len = len;
        lineList = new LinkedList<>();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void drawLine(Line line) {
        if (lineList.size() >= capacity) {
            lineList.remove(0);
        }
        lineList.add(line);
    }

    public void clear() {
        lineList.clear();
    }

    public BufferedImage getImage() {
        Graphics2D img2d = (Graphics2D) img.getGraphics();
        img2d.setColor(Color.BLACK);
        img2d.fillRect(0, 0, width, height);

        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);
            double segLen = Math.sqrt((line.x0 - line.x1) * (line.x0 - line.x1) + (line.y0 - line.y1) * (line.y0 - line.y1));
            img2d.setColor(new Color(201, 255, 218, Math.min(255, (int) (len / segLen * ((double) i) / lineList.size() * 255))));
            img2d.drawLine((int) line.x0, (int) line.y0, (int) line.x1, (int) line.y1);
        }
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
