package io.github.mikhirurg.signalprocessor.gui;

import io.github.mikhirurg.signalprocessor.math.Signal;

import javax.swing.*;

public abstract class SignalSettings extends JPanel {
    public abstract Signal getSignal();

    public abstract String getSignalName();

    public abstract String getSignalId();
}
