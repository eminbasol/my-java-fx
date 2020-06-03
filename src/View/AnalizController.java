package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import Model.Record;
import Model.Uzaklıklar;

import java.util.ArrayList;
import java.util.List;

public class AnalizController {


    @FXML
    private TableView<Record> tableView;
    @FXML
    private TableColumn<Record, String> xColumn;
    @FXML
    private TableColumn<Record, String> yColumn;
    @FXML
    private TableView<Uzaklıklar> uzaklıklarTableView;
    @FXML
    private TableColumn<Uzaklıklar, String> uzaklıkColumn;
    @FXML
    private TableColumn<Uzaklıklar, String> noktaColumn;
    @FXML
    private Button button, btnAnalizEt;
    @FXML
    private Label lblOrtalama;
    @FXML
    private Label lblStdSapma, lblVariance, lblMax, lblMin, lblMod;


    private ObservableList<Record> data;

    private ObservableList<Uzaklıklar> uzaklıklar = FXCollections.observableArrayList();


    public void setData(ObservableList<Record> data) {
        this.data = data;
    }

    @FXML
    void initialize() {

    }

    void buttonClick() {

        tableView.setItems(data);

        xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
        yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());
    }

    @FXML
    void btnAnalizEtClick(MouseEvent event) {
        TableColumn<Record, String> column1 = xColumn;
        List<String> xSutun = new ArrayList<>();
        for (Record item : tableView.getItems()) {
            xSutun.add(column1.getCellObservableValue(item).getValue());
        }
        TableColumn<Record, String> column2 = yColumn;
        List<String> ySutun = new ArrayList<>();
        for (Record item : tableView.getItems()) {
            ySutun.add(column2.getCellObservableValue(item).getValue());
        }
        int count = 1;
        for (int i = 0; i < xSutun.size() - 1; i++) {
            for (int j = count; j < ySutun.size(); j++) {

                double uzaklıkX = Math.abs(Double.parseDouble(xSutun.get(i)) - Double.parseDouble(xSutun.get(j)));
                double uzaklıkY = Math.abs(Double.parseDouble(ySutun.get(i)) - Double.parseDouble(ySutun.get(j)));

                double uzaklık = Math.sqrt(uzaklıkX + uzaklıkY);

                String sonuc = String.format("%.5s", uzaklık);
                String nokta = "p" + i + "-p" + j;


                uzaklıklar.add(new Uzaklıklar(sonuc, nokta));
                uzaklıklarTableView.setItems(uzaklıklar);
                uzaklıkColumn.setCellValueFactory(cellData -> cellData.getValue().uzaklıkProperty());
                noktaColumn.setCellValueFactory(cellData -> cellData.getValue().noktaProperty());

            }
            count++;
        }
        ortalama();
        varianceStdSapma();

    }

    private void varianceStdSapma() {

        TableColumn<Uzaklıklar, String> column = uzaklıkColumn;

        List<String> columnData = new ArrayList<>();
        for (Uzaklıklar item : uzaklıklarTableView.getItems()) {
            columnData.add(column.getCellObservableValue(item).getValue());
        }
        double total = 0;

        for (int i = 0; i < columnData.size(); i++) {
            total = total + Double.parseDouble(columnData.get(i));
        }
        double average = total / columnData.size();

        double sqDiff = 0;
        for (int i = 0; i < columnData.size(); i++) {
            sqDiff += (Double.parseDouble(columnData.get(i)) - average) *
                    (Double.parseDouble(columnData.get(i)) - average);
        }
        double variance = sqDiff / columnData.size();

        lblVariance.setText(String.format("%.5s", variance));

        double standartSapma = Math.sqrt(variance);

        lblStdSapma.setText(String.format("%.5s", standartSapma));


    }

    private void ortalama() {
        TableColumn<Uzaklıklar, String> column1 = noktaColumn;

        List<String> noktaData = new ArrayList<>();
        for (Uzaklıklar item : uzaklıklarTableView.getItems()) {
            noktaData.add(column1.getCellObservableValue(item).getValue());
        }


        TableColumn<Uzaklıklar, String> column = uzaklıkColumn;

        List<String> columnData = new ArrayList<>();
        for (Uzaklıklar item : uzaklıklarTableView.getItems()) {
            columnData.add(column.getCellObservableValue(item).getValue());
        }

        double total = 0;
        double average = 0;

        for (int i = 0; i < columnData.size(); i++) {
            total = total + Double.parseDouble(columnData.get(i));
        }
        average = total / columnData.size();

        double max = Double.parseDouble(columnData.get(0));
        double min = Double.parseDouble(columnData.get(0));
        int indexMax = 0;
        int indexMin = 0;

        for (int i = 0; i < columnData.size(); i++) {
            if (Double.parseDouble(columnData.get(i)) > max) {
                max = Double.parseDouble(columnData.get(i));
                indexMax = i;
            } else if (Double.parseDouble(columnData.get(i)) < min) {
                min = Double.parseDouble(columnData.get(i));
                indexMin = i;
            }
        }

        lblOrtalama.setText(String.format("%.5s", average));
        lblMax.setText(max + " -- " + noktaData.get(indexMax));
        lblMin.setText(min + " -- " + noktaData.get(indexMin));


        double number = Double.parseDouble(columnData.get(0));
        double mode = number;
        int count = 1;
        int countMode = 1;

        for (int i = 0; i < columnData.size(); i++) {
            if (Double.parseDouble(columnData.get(i)) == number) {
                count++;
            } else {
                if (count > countMode) {
                    countMode = count;
                    mode = number;
                }
                count = 1;
                number = Double.parseDouble(columnData.get(i));
            }
        }

        lblMod.setText(String.valueOf(mode));
    }
}