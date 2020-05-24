package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.event.ActionEvent;
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
    private Button button, btnAnalizEt;


    private ObservableList<Record> data;

    private ObservableList<Uzaklıklar> uzaklıklar = FXCollections.observableArrayList();


    public void setData(ObservableList<Record> data) {
        this.data = data;
    }

    @FXML
    void initialize() {


    }

    @FXML
    void buttonClick(MouseEvent event) {
        uzaklıklarTableView.setVisible(true);
        btnAnalizEt.setVisible(true);
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

                System.out.println(xSutun.get(i) + "-----" + xSutun.get(j));
                System.out.println(ySutun.get(i) + "-----" + ySutun.get(j));

                double uzaklıkX = Math.abs(Double.parseDouble(xSutun.get(i)) - Double.parseDouble(xSutun.get(j)));
                double uzaklıkY = Math.abs(Double.parseDouble(ySutun.get(i)) - Double.parseDouble(ySutun.get(j)));

                double uzaklık = Math.sqrt(uzaklıkX + uzaklıkY);

                String sonuc = String.valueOf(uzaklık);


                uzaklıklar.add(new Uzaklıklar(sonuc));
                uzaklıklarTableView.setItems(uzaklıklar);
                uzaklıkColumn.setCellValueFactory(cellData -> cellData.getValue().uzaklıkProperty());

            }
            count++;

        }
    }
}