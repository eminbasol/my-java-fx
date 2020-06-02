package sample.View;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Record;
import sample.Util.VeriTabaniUtil;

public class AllDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Record> tableView;

    @FXML
    private TableColumn<Record, String> fileNameColumn;

    @FXML
    private TableColumn<Record, String> xColumn;

    @FXML
    private TableColumn<Record, String> yColumn;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblUsername;

    public AllDataController() {
        baglanti = VeriTabaniUtil.Baglan();
    }

    String sql = "";
    Connection baglanti = null;
    PreparedStatement sorguIfadesi = null;
    ResultSet getirilen = null;

    ObservableList<Record> allData = FXCollections.observableArrayList();



    public void setlblUsernamePass(String username) {
        lblUsername.setText(username);
    }

    @FXML
    void btnCancelClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void initialize() {
    }

    public void DegerleriGetir() {
        sql = " SELECT * FROM Record WHERE username=? ";

        try {
            sorguIfadesi = baglanti.prepareStatement(sql);

            sorguIfadesi.setString(1, lblUsername.getText());

            getirilen = sorguIfadesi.executeQuery();

            while (getirilen.next()) {
                allData.add(new Record(getirilen.getString("filename"), getirilen.getString("x"), getirilen.getString("y"),getirilen.getString("username")));
            }

            tableView.setItems(allData);

            fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
            xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
            yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}