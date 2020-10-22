package io.github.mikhirurg.signalprocessor.gui;

public interface Noisable {
    double getMinRVal();
    double getMaxRVal();
    boolean isNoised();
}
