package web.calcmathlab6;

import javafx.beans.property.SimpleDoubleProperty;

public class TableRowData {
    private final SimpleDoubleProperty x;
    private final SimpleDoubleProperty y;

    public TableRowData(double x, double y) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
    }

    public double getX() {
        return x.get();
    }

    public SimpleDoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public SimpleDoubleProperty yProperty() {
        return y;
    }
}