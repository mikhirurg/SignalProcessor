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
                Xk = Xk.plus(new ComplexNumber(Math.cos(2 * Math.PI * k * n / N), Math.sin(2 * Math.PI * k * n / N)).mul(signal == 0 ? data.get(n).getSecond() : data.get(n).getThird()));
            }
            result.add(Xk);
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
