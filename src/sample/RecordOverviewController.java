package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;


public class RecordOverviewController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Record> tableView;

    @FXML
    private TableColumn<Record, String> ıdColumn;

    @FXML
    private TableColumn<Record, String> fileNameColumn;

    @FXML
    private TableColumn<Record, String> xColumn;

    @FXML
    private TableColumn<Record, String> yColumn;

    @FXML
    private Button btnLoadCsv;

    @FXML
    private Button btnLoadImage;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtX;

    @FXML
    private TextField txtY;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtFileName;

    @FXML
    private ListView listView;

    @FXML
    private CheckBox boxId;

    @FXML
    private CheckBox boxFileName;

    @FXML
    private CheckBox boxX;

    @FXML
    private CheckBox boxY;

    @FXML
    private TextField srcInput;


    FileChooser fc = new FileChooser();

    public ObservableList<Record> data;


    @FXML
    void btnUpdateClick(ActionEvent event) {

        if (tableView.getSelectionModel().getSelectedIndex() != -1) {

            Record record1 = new Record(txtId.getText(), txtFileName.getText(), txtX.getText(), txtY.getText());
            int sira = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(sira, record1);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Record Selected");
            alert.setContentText("Please select a record in the table.");

            alert.showAndWait();
        }
    }

    void showRecordInTextFields(Record record) {
        if (record != null) {
            txtId.setText(record.getId());
            txtFileName.setText(record.getFileName());
            txtX.setText(record.getX());
            txtY.setText(record.getY());

        } else {
            txtId.setText("");
            txtFileName.setText("");
            txtX.setText("");
            txtY.setText("");
        }
    }

    @FXML
    private void btnDeleteClick(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableView.getItems().remove(selectedIndex);
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Record Selected");
            alert.setContentText("Please select a record in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void btnLoadCsvClick(ActionEvent e) throws IOException {

        data = FXCollections.observableArrayList();

        File file = fc.showOpenDialog(null);
        if (file == null) {
            return;
        }

        Path dirP = Paths.get(String.valueOf(file));
        InputStream in = Files.newInputStream(dirP);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Scanner scan = new Scanner(reader);
        scan.useDelimiter("\\s*,\\s*");

        while (scan.hasNext()) {
            String ıd = scan.next();
            String fileName = scan.next();
            String x = scan.next();
            String y = scan.next();

            data.add(new Record(ıd, fileName, x, y));
            tableView.setItems(data);

            ıdColumn.setCellValueFactory(cellData -> cellData.getValue().ıdProperty());
            fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
            xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
            yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());
            srcInput.setText(file.getAbsolutePath());
        }
        scan.close();
    }


    @FXML
    public void initialize() {

        ıdColumn.visibleProperty().bind(boxId.selectedProperty());
        fileNameColumn.visibleProperty().bind(boxFileName.selectedProperty());
        xColumn.visibleProperty().bind(boxX.selectedProperty());
        yColumn.visibleProperty().bind(boxY.selectedProperty());


        showRecordInTextFields(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showRecordInTextFields(newValue));
    }

    @FXML
    private void btnSaveClicked(ActionEvent e) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Text", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        Writer writer = null;
        try {

            writer = new BufferedWriter(new FileWriter(file));
            for (Record record : data) {

                String text = "," + record.getId() + "," + record.getFileName() + "," + record.getX() + "," + record.getY();
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            writer.flush();
            writer.close();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Data Saved");
        alert.showAndWait();

        txtId.setText("");
        txtFileName.setText("");
        txtX.setText("");
        txtY.setText("");
        tableView.getItems().clear();
    }

    @FXML
    private void btnAddClick(ActionEvent event) {
        if (isInputValid()) {
            data.add(new Record(txtId.getText(), txtFileName.getText(), txtX.getText(), txtY.getText()));

            txtId.clear();
            txtFileName.clear();
            txtY.clear();
            txtX.clear();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (txtId.getText() == null || txtId.getText().length() == 0) {
            errorMessage += "No valid Id!\n";
        }
        if (txtFileName.getText() == null || txtFileName.getText().length() == 0) {
            errorMessage += "No valid file name!\n";
        }
        if (txtX.getText() == null || txtX.getText().length() == 0) {
            errorMessage += "No valid x!\n";
        }

        if (txtY.getText() == null || txtY.getText().length() == 0) {
            errorMessage += "No valid y!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    @FXML
    private void handleLoadImage() {
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
    }

}



