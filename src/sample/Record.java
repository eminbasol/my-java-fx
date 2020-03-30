package sample;

import javafx.beans.property.SimpleStringProperty;

public class Record {

    private SimpleStringProperty c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;

    public String getC1() {
        return c1.get();
    }

    public String getC2() { return c2.get(); }

    public String getC3() {
        return c3.get();
    }

    public String getC4() {
        return c4.get();
    }

    public String getC5() {
        return c5.get();
    }

    public String getC6() {
        return c6.get();
    }

    public String getC7() {
        return c7.get();
    }

    public String getC8() {
        return c8.get();
    }

    public String getC9() {
        return c9.get();
    }

    public String getC10() {
        return c10.get();
    }

    Record(String c1, String c2, String c3, String c4,
           String c5, String c6, String c7, String c8, String c9, String c10) {
        this.c1 = new SimpleStringProperty(c1);
        this.c2 = new SimpleStringProperty(c2);
        this.c3 = new SimpleStringProperty(c3);
        this.c4 = new SimpleStringProperty(c4);
        this.c5 = new SimpleStringProperty(c5);
        this.c6 = new SimpleStringProperty(c6);
        this.c7 = new SimpleStringProperty(c7);
        this.c8 = new SimpleStringProperty(c8);
        this.c9 = new SimpleStringProperty(c9);
        this.c10 = new SimpleStringProperty(c10);
    }
}


