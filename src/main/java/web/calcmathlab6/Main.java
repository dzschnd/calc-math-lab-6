package web.calcmathlab6;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.DoubleBinaryOperator;

import static java.lang.Math.pow;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    static final Label HEADER_MESSAGE = new Label("Выберите уравнение");
    static final ObservableList<String> EQUATION_OPTIONS = FXCollections.observableArrayList(
            "y' = x^2 + y^2",
                "y' = x - y",
                "y' = y * sin(x)"
    );
    static final ComboBox<String> EQUATION_INPUT = new ComboBox<>(EQUATION_OPTIONS);
    static final TextField Y_ZERO_INPUT = new TextField("-1");
    static final TextField X_LEFT_INPUT = new TextField("1");
    static final TextField X_RIGHT_INPUT = new TextField("1.5");
    static final HBox X_INPUT_BOX = new HBox(X_LEFT_INPUT, X_RIGHT_INPUT);
    static final TextField STEP_INPUT = new TextField("0.1");
    static final TextField ACCURACY_INPUT = new TextField("0.1");
    static final Button SOLVE_BUTTON = new Button("Решить");
    static final Label ERROR_MESSAGE = new Label();
    static final HBox TABLE_BOX = new HBox();
    static final HBox PLOT_BOX = new HBox();
    static final VBox ROOT_LAYOUT = new VBox();
    @Override
    public void start(Stage stage) {
        InputHandler inputHandler = new InputHandler();

        SOLVE_BUTTON.setOnAction(inputHandler);

        VBox.setMargin(HEADER_MESSAGE, new Insets(5));
        VBox.setMargin(Y_ZERO_INPUT, new Insets(5));
        VBox.setMargin(STEP_INPUT, new Insets(5));
        VBox.setMargin(ACCURACY_INPUT, new Insets(5));
        HBox.setMargin(X_LEFT_INPUT, new Insets(5));
        VBox.setMargin(TABLE_BOX, new Insets(5));
        VBox.setMargin(PLOT_BOX, new Insets(5));
        VBox.setMargin(ERROR_MESSAGE, new Insets(5));

        Y_ZERO_INPUT.setStyle("-fx-max-width: 205");
        STEP_INPUT.setStyle("-fx-max-width: 205");
        ACCURACY_INPUT.setStyle("-fx-max-width: 205");
        X_LEFT_INPUT.setStyle("-fx-max-width: 115");
        X_RIGHT_INPUT.setStyle("-fx-max-width: 115");
        TABLE_BOX.setStyle("-fx-min-height: 260; -fx-max-height: 260");
        HEADER_MESSAGE.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 24");
        ERROR_MESSAGE.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");

        X_INPUT_BOX.setAlignment(Pos.CENTER);
        TABLE_BOX.setAlignment(Pos.CENTER);
        PLOT_BOX.setAlignment(Pos.CENTER);
        ROOT_LAYOUT.setAlignment(Pos.TOP_CENTER);

        Y_ZERO_INPUT.setPromptText("Значение y в левой границе x");
        X_LEFT_INPUT.setPromptText("Левая граница x");
        X_RIGHT_INPUT.setPromptText("Правая граница x");
        STEP_INPUT.setPromptText("Шаг");
        ACCURACY_INPUT.setPromptText("Точность приближения");

        ROOT_LAYOUT.setStyle("-fx-background-color: #2d2d2d;");
        ROOT_LAYOUT.getChildren().addAll(HEADER_MESSAGE, EQUATION_INPUT, Y_ZERO_INPUT, X_INPUT_BOX, STEP_INPUT, ACCURACY_INPUT, SOLVE_BUTTON, ERROR_MESSAGE, TABLE_BOX, PLOT_BOX);

        Scene scene = new Scene(ROOT_LAYOUT, 1240, 780);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.setTitle("Вычмат...");
        stage.show();
    }
}