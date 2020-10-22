package io.github.mikhirurg.signalprocessor.util;

public class ComplexNumber {
    private final double re;
    private final double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public ComplexNumber plus(ComplexNumber other) {
        return new ComplexNumber(this.re + other.re, this.im + other.im);
    }

    public ComplexNumber minus(ComplexNumber other) {
        return new ComplexNumber(this.re - other.re, this.im - other.im);
    }

    public ComplexNumber mul(double k) {
        return new ComplexNumber(this.re * k, this.im * k);
    }

    public static ComplexNumber sum(ComplexNumber first, ComplexNumber second) {
        return new ComplexNumber(first.re + second.getRe(), first.im + second.im);
    }

    public String toString() {
        return getRe() + "," + getIm() + "," + getABS();
    }


    public double getABS() {
        return Math.sqrt(re * re + im * im);
    }
}
