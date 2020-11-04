package io.github.mikhirurg.signalprocessor.math;

import io.github.mikhirurg.signalprocessor.util.ComplexNumber;
import io.github.mikhirurg.signalprocessor.util.Cortege;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
import java.util.stream.Collectors;

public class FourierProcessor {
    public static List<ComplexNumber> getFourierProcessing(List<Cortege<Double>> data, int signal) {
        List<ComplexNumber> result = new LinkedList<>();
        int N = data.size();
        for (int k = 0; k < N; k++) {
            ComplexNumber Xk = new ComplexNumber(0, 0);
            for (int n = 0; n < N; n++) {
                Xk = Xk.plus(new ComplexNumber(Math.cos(2 * Math.PI * k * n / N), -Math.sin(2 * Math.PI * k * n / N)).mul(signal == 0 ? data.get(n).getSecond() : data.get(n).getThird()));
            }
            result.add(Xk);
        }
        return result;
    }

    public static List<Cortege<Double>> getReverseFourierProcessing(List<Cortege<Double>> data, List<ComplexNumber> fourierData, int signal) {
        int N = data.size();
        List<Cortege<Double>> result = new LinkedList<>(data);
        for (int n = 0; n < N; n++) {
            ComplexNumber Xk = new ComplexNumber(0, 0);
            for (int k = 0; k < N; k++) {
                Xk = Xk.plus(fourierData.get(k).mul(new ComplexNumber(Math.cos(2.0 * Math.PI * k * n / N), Math.sin(2.0 * Math.PI * k * n / N))));
            }
            Xk = Xk.mul(1.0 / N);
            Cortege<Double> signalVal = new Cortege<>(
                    data.get(n).getFirst(),
                    signal == 0 ? Xk.getRe() : data.get(n).getSecond(),
                    signal == 1 ? Xk.getRe() : data.get(n).getThird()
            );
            result.set(n, signalVal);
        }
        return result;
    }

    public static List<ComplexNumber> filterFourier(List<ComplexNumber> data, double min, double max) {
        int N = data.size();
        List<ComplexNumber> result = new LinkedList<>(data);
        for (int k = 0; k < N; k++) {
            if (result.get(k).getABS() < min || result.get(k).getABS() > max) {
                result.set(k, new ComplexNumber(0, 0));
            }
        }

        return result;
    }

    public static List<Cortege<Double>> getSignalData(File csv) throws FileNotFoundException {
        BufferedReader fileReader = new BufferedReader(new FileReader(csv));
        return fileReader.
                lines()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Cortege<>(
                            Double.parseDouble(parts[0]),
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2])
                    );
                }).collect(Collectors.toList());
    }

    public static List<ComplexNumber> getFourierProcessing(File csv, int signal) throws IOException {
        return getFourierProcessing(getSignalData(csv), signal);
    }
}
