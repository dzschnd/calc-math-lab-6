package web.calcmathlab6;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Arrays;

import static web.calcmathlab6.Main.PLOT_BOX;

public class PlotDrawer {
    public static void drawPlot(ArrayList<Double> xArray, ArrayList<ArrayList<Double>> yArrays) {
        for (int i = 0; i < yArrays.size(); i++) {
            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("x");
            yAxis.setLabel("y");
            xAxis.setAutoRanging(true);
            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(-5);
            yAxis.setUpperBound(5);

            LineChart<Number, Number> plot = new LineChart<>(xAxis, yAxis);
            XYChart.Series<Number, Number> dotSeries = new XYChart.Series<>();
            // dotSeries.setName("Приближение");

            for (int j = 0; j < xArray.size(); j++) {
                double x = xArray.get(j);
                double y = yArrays.get(i).get(j);
                dotSeries.getData().add(new XYChart.Data<>(x, y));
            }

            plot.getData().add(dotSeries);
            PLOT_BOX.getChildren().add(plot);
        }
    }
}
