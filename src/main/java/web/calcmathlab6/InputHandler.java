package web.calcmathlab6;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import static web.calcmathlab6.Main.*;

public class InputHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource().equals(SOLVE_BUTTON)) {
            try {
                double y0 = Double.parseDouble(Y_ZERO_INPUT.getText());
                double xLeftBorder = Double.parseDouble(X_LEFT_INPUT.getText());
                double xRightBorder = Double.parseDouble(X_RIGHT_INPUT.getText());
                double step = Double.parseDouble(STEP_INPUT.getText());
                double accuracy = Double.parseDouble(ACCURACY_INPUT.getText());

                if (xLeftBorder >= xRightBorder) {
                    ERROR_MESSAGE.setText("Левая граница должна быть меньше правой");
                    return;
                }
                if (xRightBorder - xLeftBorder < step) {
                    ERROR_MESSAGE.setText("Шаг не может быть больше диапазона аргументов");
                    return;
                }
                if (step <= 0) {
                    ERROR_MESSAGE.setText("Шаг должен быть положительным");
                    return;
                }

                ERROR_MESSAGE.setText("");
                TABLE_BOX.getChildren().clear();
                Pair<ArrayList<Double>, ArrayList<Double>> modifiedEulerSolution = null;
                Pair<ArrayList<Double>, ArrayList<Double>> rungeKuttaSolution = null;
                Pair<ArrayList<Double>, ArrayList<Double>> addamsSolution = null;
                Pair<ArrayList<Double>, ArrayList<Double>> exactSolution = null;

                ArrayList<ArrayList<Double>> yArrays = new ArrayList<>();

                if (EQUATION_INPUT.getValue().equals("y' = x^2 + y^2")) {
                    modifiedEulerSolution = DiffEquationSolver.solveModifiedEuler(DiffEquationSolver.diffEquation1, y0, xLeftBorder, xRightBorder, step, accuracy);
                    rungeKuttaSolution = DiffEquationSolver.solveRungeKuttaFourthOrder(DiffEquationSolver.diffEquation1, y0, xLeftBorder, xRightBorder, step, accuracy);
                    addamsSolution = DiffEquationSolver.solveAddams(DiffEquationSolver.diffEquation1, y0, xLeftBorder, xRightBorder, step, accuracy);
                    exactSolution = DiffEquationSolver.solveEquation1(y0, xLeftBorder, xRightBorder, step);
                }
                else if (EQUATION_INPUT.getValue().equals("y' = x - y")) {
                    modifiedEulerSolution = DiffEquationSolver.solveModifiedEuler(DiffEquationSolver.diffEquation2, y0, xLeftBorder, xRightBorder, step, accuracy);
                    rungeKuttaSolution = DiffEquationSolver.solveRungeKuttaFourthOrder(DiffEquationSolver.diffEquation2, y0, xLeftBorder, xRightBorder, step, accuracy);
                    addamsSolution = DiffEquationSolver.solveAddams(DiffEquationSolver.diffEquation2, y0, xLeftBorder, xRightBorder, step, accuracy);
                    exactSolution = DiffEquationSolver.solveEquation2(y0, xLeftBorder, xRightBorder, step);
                }
                else if (EQUATION_INPUT.getValue().equals("y' = y * sin(x)")) {
                    modifiedEulerSolution = DiffEquationSolver.solveModifiedEuler(DiffEquationSolver.diffEquation3, y0, xLeftBorder, xRightBorder, step, accuracy);
                    rungeKuttaSolution = DiffEquationSolver.solveRungeKuttaFourthOrder(DiffEquationSolver.diffEquation3, y0, xLeftBorder, xRightBorder, step, accuracy);
                    addamsSolution = DiffEquationSolver.solveAddams(DiffEquationSolver.diffEquation3, y0, xLeftBorder, xRightBorder, step, accuracy);
                    exactSolution = DiffEquationSolver.solveEquation3(y0, xLeftBorder, xRightBorder, step);
                }

                createTable(exactSolution, "Точное решение");
                createTable(modifiedEulerSolution, "Модифицированный метод Эйлера");
                createTable(rungeKuttaSolution, "Метод Рунге-Кутта 4 порядка");
                createTable(addamsSolution, "Метод Аддамса");

                yArrays.add(exactSolution.getValue());
                yArrays.add(modifiedEulerSolution.getValue());
                yArrays.add(rungeKuttaSolution.getValue());
                yArrays.add(addamsSolution.getValue());
                drawPlot(xLeftBorder, xRightBorder, modifiedEulerSolution.getKey(), yArrays);
            } catch (NumberFormatException e) {
                ERROR_MESSAGE.setText("Поля должны быть заполнены числами");
            } catch (NullPointerException e) {
                ERROR_MESSAGE.setText("Уравнение не выбрано");
            }
        }
    }
    public void createTable(Pair<ArrayList<Double>, ArrayList<Double>> dots, String label) {
        ObservableList<TableRowData> tableData = FXCollections.observableArrayList();

        ArrayList<Double> xValues = dots.getKey();
        ArrayList<Double> yValues = dots.getValue();

        for (int i = 0; i < xValues.size(); i++) {
            tableData.add(new TableRowData(xValues.get(i), yValues.get(i)));
        }

        TableView<TableRowData> table = new TableView<>();
        table.setItems(tableData);

        TableColumn<TableRowData, Double> xColumn = new TableColumn<>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        table.getColumns().add(xColumn);

        TableColumn<TableRowData, Double> yColumn = new TableColumn<>("y");
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        table.getColumns().add(yColumn);

        table.getSortOrder().clear();
        xColumn.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(xColumn);
        table.sort();

        Label tableLabel = new Label(label);
        tableLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");

        VBox tableWithLabel = new VBox(tableLabel, table);
        HBox.setMargin(tableWithLabel, new Insets(20));

        TABLE_BOX.getChildren().add(tableWithLabel);
    }
    private static void drawPlot(double xLeftBorder, double xRightBorder, ArrayList<Double> xArray, ArrayList<ArrayList<Double>> yArrays) {
        PLOT_BOX.getChildren().clear();
        PlotDrawer.drawPlot(xArray, yArrays);
    }
}