# SingnalProcessor
[![Build](https://github.com/mikhirurg/SignalProcessor/actions/workflows/maven.yml/badge.svg)](https://github.com/mikhirurg/SignalProcessor/actions/workflows/maven.yml)

## Project description

This project allows generating and visualizing different signals. Also, it is possible to add noise to the signals and then analyze and transform them. 

## Installation guide
The project requires Java version 13+ in order to run the simulation. 
There are a few different ways how a user can obtain the jar file to execute the project:
- The latest stable version of the project is available in the [Releases](https://github.com/mikhirurg/SignalProcessor/releases) section of the repository
- The user can build the executable jar from sources by using the script ```build.sh```

After obtaining the executable jar, user can just run the following command:

```
java -jar ElectricField.jar
```

## How does the simulation work

![Main window](img/img1.PNG)

![Fourier processing](img/img2.PNG)

## How to use the project
On the left side of the application window, there is a "screen" that displays signals. And on the right side, there is located a panel that allows the user to choose different signals for A and B inputs which will be displayed on the "screen". Also, the control panel interface allows the user to record the generated signal values, analyze the recorded data and export the signal capture to the file.

### Suported signals

- Constant signal: fixed point on the oscilloscope display
- Sinusoidal signal: user can set the amplitude, frequency and initial phase of the signal
- Noise signal: user can set the minimal and maximal value for the signal
- Meander signal (square waveform): this signal is modeled by using the Fourier series. Users can set the amplitude and frequency of the signal. Additionally, the user can set the number of elements of the Fourier series used to model the signal.
- Sawtooth signal: the signal is modeled by using the Fourier series. The user can set the amplitude, frequency, and number of the Fourier series used to model the signal.
- "Range" signal: the signal is just moving the oscilloscope ray from right to left in a given range.

### The noise functionality

Additionally, the simulation allows user to add some noise to the particular signal (A, B). The noise in this case can be any signal from the list above added to one of the signals (A, B).

### Saving the configuration

The simulation is supporting special configuration files which can be used to export the signals configuration to the file, and restore the configuration from the file if it is necessary.

### Export the results

The application allows the user to export the recorded signal values to the text file, and export the signal image.

### Signal smoothing algorithms

The modeling application is supporting two signal smoothing algorithms: moving average algorithm and smoothing algorithm based on inverse discrete-time Fourier transform.

## Examples

### Sinusoidal signal

### Sawtooth signal

### Meander signal

### Sinusoidal signal + noise

### Sum of two sinusoidal signals

### Moving average smoothing algorithm

### Smoothing algorithm based on inverse discrete-time Fourier transform

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](/LICENSE.txt)
