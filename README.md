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
java -jar SignalProcessor*.jar
```

## How to use the project

<img src="/img/interface_1.png" alt="Demo 1" width=900>

On the left side of the application window, there is a "screen" that displays signals. And on the right side, there is located a panel that allows the user to choose different signals for A and B inputs which will be displayed on the "screen". Also, the control panel interface allows the user to record the generated signal values, analyze the recorded data and export the signal capture to the file.

### Suported signals

- **Constant signal:** fixed point on the oscilloscope display
- **Sinusoidal signal:** user can set the amplitude, frequency and initial phase of the signal
- **Noise signal:** user can set the minimal and maximal value for the signal
- **Meander signal (square waveform):** this signal is modeled by using the Fourier series. Users can set the amplitude and frequency of the signal. Additionally, the user can set the number of elements of the Fourier series used to model the signal.
- **Sawtooth signal:** the signal is modeled by using the Fourier series. The user can set the amplitude, frequency, and number of the Fourier series used to model the signal.
- **"Range" signal:** the signal is just moving the oscilloscope ray from right to left in a given range.

### The noise functionality

<img src="/img/interface_2.png" alt="Demo 1" width=900>

Additionally, the simulation allows user to add some noise to the particular signal (A, B). The noise in this case can be any signal from the list above added to one of the signals (A, B).

### Saving the configuration

<img src="/img/interface_3.png" alt="Demo 1" width=400>

The simulation is supporting special configuration files which can be used to export the signals configuration to the file, and restore the configuration from the file if it is necessary.

### Export the results

<img src="/img/interface_4.png" alt="Demo 1" width=400>

The application allows the user to export the recorded signal values to the text file, and export the signal image.

### Signal smoothing algorithms
| Fourier processing                                      | SMA processing                                          | Fourier filtering                                  |
|---------------------------------------------------------|---------------------------------------------------------|--------------------------------------------------|
| <img src="/img/interface_5.png" alt="Demo 1" width=600> | <img src="/img/interface_6.png" alt="Demo 1" width=600> | <img src="/img/interface_6.png" alt="Demo 1" width=600> |

The modeling application is supporting two signal smoothing algorithms: moving average algorithm and smoothing algorithm based on inverse discrete-time Fourier transform.

## Examples

### Sinusoidal signal

$$ 
\begin{flalign}
x(t) = A1 \cdot sin(f1 \cdot t + \phi_1) 
\end{flalign}
$$

$$ 
\begin{flalign}
y(t) = A2 \cdot sin(f2 \cdot t + \phi_2) 
\end{flalign}
$$

| Configuration                                        | Demonstration                                      |
|------------------------------------------------------|----------------------------------------------------|
| $A_1=200, f_1=1, \phi_1=\frac{\pi}{2},$ <br /> $A_2=200, f_2=2, \phi_2=0$ | <img src="/img/demo_1.png" alt="Demo 1" width=400> |
| $A_1=200, f_1=1, \phi_1=\frac{\pi}{2},$ <br /> $A_2=200, f_2=3, \phi_2=0$ | <img src="/img/demo_2.png" alt="Demo 2" width=400> |
| $A_1=200, f_1=3, \phi_1=\frac{\pi}{2},$ <br /> $A_2=200, f_2=2, \phi_2=0$ | <img src="/img/demo_3.png" alt="Demo 3" width=400> |
| $A_1=200, f_1=5, \phi_1=\frac{\pi}{2},$ <br /> $A_2=200, f_2=4, \phi_2=0$ | <img src="/img/demo_4.png" alt="Demo 4" width=400> |

### Sawtooth signal
$$ y(t) = -\frac{2A}{\pi}\cdot \sum_{k=1}^{n}{\frac{(-1)^n\cdot sin(2\pi k f t)}{k}} $$

| Configuration                                        | Demonstration                                      |
|------------------------------------------------------|----------------------------------------------------|
| $A=70, f=1, n=50$ | <img src="/img/demo_5.png" alt="Demo 5" width=400> |
| $A=70, f=2, n=10$ | <img src="/img/demo_6.png" alt="Demo 6" width=400> |

### Square signal

$$ y(t) = \frac{4A}{\pi} \cdot \sum_{k=1}{n} \frac{2\pi(2k - 1)ft}{2k-1} $$

| Configuration                                        | Demonstration                                      |
|------------------------------------------------------|----------------------------------------------------|
| $A=100, f=1, n=10$ | <img src="/img/demo_7.png" alt="Demo 7" width=400> |

### Sinusoidal signal + noise

$$ y(t) = A\cdot sin(ft + \phi) + rand(min, max) $$ 

| Configuration                                        | Demonstration                                      |
|------------------------------------------------------|----------------------------------------------------|
| $A=150, f=1, \phi=0, min=-50, max = 50$ | <img src="/img/demo_8.png" alt="Demo 8" width=400> |

### Sum of two sinusoidal signals

$$ y(t) = A_1\cdot sin(f_1 t + \phi_1) + A_2 \cdot sin(f_2 t + \phi_2) $$

| Configuration                                        | Demonstration                                      |
|------------------------------------------------------|----------------------------------------------------|
| $A_1=150, f_1=1, \phi_1=0,$ <br /> $A_2=50, f_2=5, \phi_2=\frac{\pi}{2}$ | <img src="/img/demo_9.png" alt="Demo 9" width=400> |

### Moving average smoothing algorithm

The idea of the algorithm is simple: the new value of the signal point is calculated as an average of n previous values.

$$ r_i=\frac{1}{n}\cdot \sum_{j=1}^{n} y(i - j); i > n $$

| Configuration                                        | Demonstration                                      |
|------------------------------------------------------|----------------------------------------------------|
| $y(t)=A\cdot sin(ft + \phi)+rand(min, max)$ <br /> $A=150, f=1, \phi=0$ <br /> $min=-50, max=50$| <img src="/img/demo_10.png" alt="Demo 10" width=400> |

### Smoothing algorithm based on inverse discrete-time Fourier transform

The main idea of the algorithm: we are applying discrete-time Fourier transform. After that, we build a signal amplitude spectrum by using the result from the previous step. After that we are filtering those amplitudes. And after that by using the inverse discrete-time Fourier transform we "recover" the signal from the filtered amplitudes.

$$ X_k = \sum_{n=0}{N-1} (x_n \cdot [cos(\frac{2\pi k n}{N} - i\cdot sin(\frac{2\pi k n}{N}))]) (k=0, \dots, N-1)$$

$$ Y_k= X_k \text{ if } |X_k| \in [min, max], \text{ otherwise } 0 $$

$$ x_n= \frac{1}{N} \sum_{k=0}^{N-1}{Y_k \cdot [cos(\frac{2\pi k n}{N}) + i\cdot sin(\frac{2\pi k n}{N})]}, (n=0, \dots, N-1) $$

#### Example of filtering the signal:

| Configuration                                        | Demonstration                                      |
|------------------------------------------------------|----------------------------------------------------|
| $y(t)=A_1\cdot sin(f_1 t + \phi_1) + A_2\cdot sin(f_2 t + \phi_2)$ <br /> $A_1=150, f_1=1, \phi_1=0$ <br /> $A_2=50, f_2=5, \phi_2=\frac{\pi}{2}$ | <img src="/img/demo_9.png" alt="Demo 9" width=400> |

| Signal amplitude spectrum | Filtered signal |
|---------------------------|-----------------|
| <br /> <img src="/img/demo_11.png" alt="Demo 11" width=400>  |  <br /> <img src="/img/demo_12.png" alt="Demo 12" width=400> 

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](/LICENSE.txt)
