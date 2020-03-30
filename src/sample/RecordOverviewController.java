package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.dataList;

public class RecordOverviewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Record> tableView;

    @FXML
    private TableColumn<Record, String> c1;

    @FXML
    private TableColumn<Record, String> c2;

    @FXML
    private TableColumn<Record, String> c3;

    @FXML
    private TableColumn<Record, String> c4;

    @FXML
    private TableColumn<Record, String> c5;

    @FXML
    private TableColumn<Record, String> c6;

    @FXML
    private TableColumn<Record, String> c7;

    @FXML
    private TableColumn<Record, String> c8;

    @FXML
    private TableColumn<Record, String> c9;

    @FXML
    private TableColumn<Record, String> c10;

    @FXML
    private Button btnLoadImage;

    @FXML
    public void initialize() {
        c1.setCellValueFactory(
                new PropertyValueFactory<>("c1"));
        c2.setCellValueFactory(
                new PropertyValueFactory<>("c2"));
        c3.setCellValueFactory(
                new PropertyValueFactory<>("c3"));
        c4.setCellValueFactory(
                new PropertyValueFactory<>("c4"));
        c5.setCellValueFactory(
                new PropertyValueFactory<>("c5"));
        c6.setCellValueFactory(
                new PropertyValueFactory<>("c6"));
        c7.setCellValueFactory(
                new PropertyValueFactory<>("c7"));
        c8.setCellValueFactory(
                new PropertyValueFactory<>("c8"));
        c9.setCellValueFactory(
                new PropertyValueFactory<>("c9"));
        c10.setCellValueFactory(
                new PropertyValueFactory<>("c10"));

        tableView.setItems(dataList);
    }

    @FXML
    private void handleLoadImage() {
        btnLoadImage.setOnAction(event -> {

            try {
                Parent rootNode = FXMLLoader.load(getClass().getResource("LoadImage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Image");
                Scene scene = new Scene(rootNode);
                stage.setScene(scene);
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}



