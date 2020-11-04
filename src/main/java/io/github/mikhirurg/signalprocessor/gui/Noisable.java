package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.Signal;

public interface Noisable {
    boolean isNoised();

    NoiseSettings getNoiseSignalSettings();
}
