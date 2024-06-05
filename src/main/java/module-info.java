module web.calcmathlab6.calcmathlab6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens web.calcmathlab6 to javafx.fxml;
    exports web.calcmathlab6;
}