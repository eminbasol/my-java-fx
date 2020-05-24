package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record {

    private final StringProperty fileName = new SimpleStringProperty(this, "fileName", null);
    private final StringProperty x = new SimpleStringProperty(this, "x", null);
    private final StringProperty y = new SimpleStringProperty(this, "y", null);


    public Record() {
        this(null, null, null);
    }

    public Record(String fileName, String x, String y) {
        this.fileName.set(fileName);
        this.x.set(x);
        this.y.set(y);
    }

    public final String getFileName() {//2
        return fileName.get();
    }

    public final void setFileName(String fileName) {//2
        fileNameProperty().set(fileName);
    }

    public final StringProperty fileNameProperty() {//2
        return fileName;
    }

    public final String getX() {//3
        return x.get();
    }

    public final void setX(String x) {//3
        xProperty().set(x);
    }

    public final StringProperty xProperty() {
        return x;
    }

    public final String getY() {
        return y.get();
    }

    public final void setY(String y) {
        yProperty().set(y);
    }

    public final StringProperty yProperty() {
        return y;
    }

}


