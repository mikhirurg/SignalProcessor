package io.github.mikhirurg.signalprocessor.math;

import io.github.mikhirurg.signalprocessor.util.Cortege;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SMAProcessor {
    public static List<Cortege<Double>> getSMAProcessing(List<Cortege<Double>> data, int window, int signal) {
        List<Cortege<Double>> result = new LinkedList<>();
        for (int i = 0; i < data.size(); i++) {
            double val = 0;
            for (int j = 0; j < window; j++) {
                int ind = Math.max(0, i - j);
                val += signal == 0 ? data.get(ind).getSecond() : data.get(ind).getThird();
            }
            val = val / window;
            result.add(new Cortege<>(
                    data.get(i).getFirst(),
                    signal == 0 ? val : data.get(i).getSecond(),
                    signal == 1 ? val : data.get(i).getThird()
            ));
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

    public static List<Cortege<Double>> getSMAProcessing(File csv, int window, int signal) throws IOException {
        return getSMAProcessing(getSignalData(csv), window, signal);
    }
}
