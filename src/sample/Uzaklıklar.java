package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Uzaklıklar {

    private final StringProperty uzaklık = new SimpleStringProperty(this, "uzaklık", null);

    public Uzaklıklar() {
        this(null);
    }

    public Uzaklıklar(String uzaklık) {
        this.uzaklık.set(uzaklık);
    }

    public final String getUzaklık() {//2
        return uzaklık.get();
    }

    public final void setUzaklık(String uzaklık) {//2
        uzaklıkProperty().set(uzaklık);
    }

    public final StringProperty uzaklıkProperty() {//2
        return uzaklık;
    }
}
