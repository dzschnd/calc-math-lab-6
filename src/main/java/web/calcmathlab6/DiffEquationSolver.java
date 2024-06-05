package web.calcmathlab6;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.*;

public class DiffEquationSolver {
    public static final DoubleBinaryOperator diffEquation1 = (x, y) -> x * x + y * y;
    public static final DoubleBinaryOperator diffEquation2 = (x, y) -> x - y;
    public static final DoubleBinaryOperator diffEquation3 = (x, y) -> y * sin(x);
    public static DoubleUnaryOperator exactSolution1(double x0, double y0) {
        double C = y0 - x0 * x0;
        return (x) -> x * x + C;
    }
    public static DoubleUnaryOperator exactSolution2(double x0, double y0) {
        double C = (y0 - x0 + 1) / exp(-x0);
        return (x) -> C * exp(-x) + x - 1;
    }
    public static DoubleUnaryOperator exactSolution3(double x0, double y0) {
        double C = y0 / exp(-cos(x0));
        return (x) -> exp(-cos(x)) * C;
    }
    public static Pair<ArrayList<Double>, ArrayList<Double>> solveEquation1(double y0, double xLeftBorder, double xRightBorder, double step) {
        ArrayList<Double> xArray = new ArrayList<>();
        xArray.add(0, xLeftBorder);
        for (int i = 1; i <= (xRightBorder - xLeftBorder) / step; i++) {
            xArray.add(i, xArray.get(i-1) + step);
        }
        ArrayList<Double> yArray = new ArrayList<>();
        for (int i = 0; i <= (xRightBorder - xLeftBorder) / step; i++) {
            yArray.add(exactSolution1(xLeftBorder, y0).applyAsDouble(xArray.get(i)));
        }
        return new Pair<>(xArray, yArray);
    }
    public static Pair<ArrayList<Double>, ArrayList<Double>> solveEquation2(double y0, double xLeftBorder, double xRightBorder, double step) {
        ArrayList<Double> xArray = new ArrayList<>();
        xArray.add(0, xLeftBorder);
        for (int i = 1; i <= (xRightBorder - xLeftBorder) / step; i++) {
            xArray.add(i, xArray.get(i-1) + step);
        }
        ArrayList<Double> yArray = new ArrayList<>();
        for (int i = 0; i <= (xRightBorder - xLeftBorder) / step; i++) {
            yArray.add(exactSolution2(xLeftBorder, y0).applyAsDouble(xArray.get(i)));
        }
        return new Pair<>(xArray, yArray);
    }
    public static Pair<ArrayList<Double>, ArrayList<Double>> solveEquation3(double y0, double xLeftBorder, double xRightBorder, double step) {
        ArrayList<Double> xArray = new ArrayList<>();
        xArray.add(0, xLeftBorder);
        for (int i = 1; i <= (xRightBorder - xLeftBorder) / step; i++) {
            xArray.add(i, xArray.get(i-1) + step);
        }
        ArrayList<Double> yArray = new ArrayList<>();
        for (int i = 0; i <= (xRightBorder - xLeftBorder) / step; i++) {
            yArray.add(exactSolution3(xLeftBorder, y0).applyAsDouble(xArray.get(i)));
        }
        return new Pair<>(xArray, yArray);
    }
    public static Pair<ArrayList<Double>, ArrayList<Double>> solveModifiedEuler(DoubleBinaryOperator dY, double y0, double xLeftBorder, double xRightBorder, double step, double accuracy) {
        ArrayList<Double> xArray = new ArrayList<>();
        xArray.add(0, xLeftBorder);
        for (int i = 1; i <= (xRightBorder - xLeftBorder) / step; i++) {
            xArray.add(i, xArray.get(i-1) + step);
        }
        ArrayList<Double> yArray = new ArrayList<>();
        yArray.add(0, y0);
        for (int i = 1; i <= (xRightBorder - xLeftBorder) / step; i++) {
            double xPrev = xArray.get(i-1);
            double yPrev = yArray.get(i-1);
            double h = 2*step;
            double yFullStep;
            double yHalfStep;
            do {
                h = h/2;
                yFullStep = yPrev + h * (dY.applyAsDouble(xPrev, yPrev) + dY.applyAsDouble(xPrev, yPrev + h * dY.applyAsDouble(xPrev, yPrev))) / 2;
                yHalfStep = yPrev + (h/2) * (dY.applyAsDouble(xPrev, yPrev) + dY.applyAsDouble(xPrev, yPrev + (h/2) * dY.applyAsDouble(xPrev, yPrev))) / 2;
            } while (abs(yFullStep - yHalfStep) / 3 > accuracy);
            yArray.add(i, yFullStep);
        }
        return new Pair<>(xArray, yArray);
    }
    public static Pair<ArrayList<Double>, ArrayList<Double>> solveRungeKuttaFourthOrder(DoubleBinaryOperator dY, double y0, double xLeftBorder, double xRightBorder, double step, double accuracy) {
        ArrayList<Double> xArray = new ArrayList<>();
        xArray.add(0, xLeftBorder);
        for (int i = 1; i <= (xRightBorder - xLeftBorder) / step; i++) {
            xArray.add(i, xArray.get(i-1) + step);
        }
        ArrayList<Double> yArray = new ArrayList<>();
        yArray.add(0, y0);
        for (int i = 1; i <= (xRightBorder - xLeftBorder) / step; i++) {
            double xPrev = xArray.get(i-1);
            double yPrev = yArray.get(i-1);
            double k1 = dY.applyAsDouble(xPrev, yPrev);
            double k2 = dY.applyAsDouble(xPrev + step/2, yPrev + k1/2);
            double k3 = dY.applyAsDouble(xPrev + step/2, yPrev + k2/2);
            double k4 = dY.applyAsDouble(xPrev + step, yPrev + k3);
            yArray.add(i, yPrev + step * (k1 + 2*(k2 + k3) + k4) / 6);
        }
        return new Pair<>(xArray, yArray);
    }
    public static Pair<ArrayList<Double>, ArrayList<Double>> solveAddams(DoubleBinaryOperator dY, double y0, double xLeftBorder, double xRightBorder, double step, double accuracy) {
        ArrayList<Double> xArray = new ArrayList<>();
        xArray.add(0, xLeftBorder);
        for (int i = 1; i <= (xRightBorder - xLeftBorder) / step; i++) {
            xArray.add(i, xArray.get(i-1) + step);
        }
        ArrayList<Double> yArray = new ArrayList<>();
        yArray.add(0, y0);
        for (int i = 1; i <= 3; i++) {
            double xPrev = xArray.get(i-1);
            double yPrev = yArray.get(i-1);
            double k1 = dY.applyAsDouble(xPrev, yPrev);
            double k2 = dY.applyAsDouble(xPrev + step/2, yPrev + k1/2);
            double k3 = dY.applyAsDouble(xPrev + step/2, yPrev + k2/2);
            double k4 = dY.applyAsDouble(xPrev + step, yPrev + k3);
            yArray.add(i, yPrev + step * (k1 + 2*(k2 + k3) + k4) / 6);
        }
        for (int i = 4; i <= (xRightBorder - xLeftBorder) / step; i++) {
            double yPrev = yArray.get(i - 1);
            double fPrev = dY.applyAsDouble(xArray.get(i - 1), yArray.get(i - 1));
            double fPrev1 = dY.applyAsDouble(xArray.get(i - 2), yArray.get(i - 2));
            double fPrev2 = dY.applyAsDouble(xArray.get(i - 3), yArray.get(i - 3));
            double fPrev3 = dY.applyAsDouble(xArray.get(i - 4), yArray.get(i - 4));
            yArray.add(i, yPrev + step * (55 * fPrev - 59 * fPrev1 + 37 * fPrev2 - 9 * fPrev3) / 24);
        }
        return new Pair<>(xArray, yArray);
    }
}
