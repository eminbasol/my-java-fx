package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Uzaklıklar {

    private final StringProperty uzaklık = new SimpleStringProperty(this, "uzaklık", null);
    private final StringProperty nokta = new SimpleStringProperty(this, "nokta", null);

    public Uzaklıklar() {
        this(null, null);
    }

    public Uzaklıklar(String uzaklık, String nokta) {

        this.uzaklık.set(uzaklık);
        this.nokta.set(nokta);
    }

    public final String getUzaklık() {
        return uzaklık.get();
    }

    public final void setUzaklık(String uzaklık) {
        uzaklıkProperty().set(uzaklık);
    }

    public final StringProperty uzaklıkProperty() {
        return uzaklık;
    }


    public final String getNokta() {
        return nokta.get();
    }

    public final void setNokta(String nokta) {
        uzaklıkProperty().set(nokta);
    }

    public final StringProperty noktaProperty() {
        return nokta;
    }
}
