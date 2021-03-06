package View;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Model.Kullanıcılar;
import Model.Record;
import Util.VeriTabaniUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminController {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Kullanıcılar> tableView;

    @FXML
    private TableColumn<Kullanıcılar, Integer> userIDColumn;

    @FXML
    private TableColumn<Kullanıcılar, String> usernameColumn;

    @FXML
    private TableColumn<Kullanıcılar, String> passwordColumn;

    @FXML
    private TableColumn<Kullanıcılar, Boolean> statusColumn;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnSignIn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TableView<Record> tableView1;

    @FXML
    private TableColumn<Record, String> fileNameColumn;

    @FXML
    private TableColumn<Record, String> xColumn;

    @FXML
    private TableColumn<Record, String> yColumn;

    @FXML
    private TableColumn<Record, String> usernameColumn1;

    @FXML
    private Label lblAllData;

    ObservableList<Record> allData = FXCollections.observableArrayList();


    public AdminController() {
        baglanti = VeriTabaniUtil.Baglan();
    }

    String sql = "";
    Connection baglanti = null;
    PreparedStatement sorguIfadesi = null;
    ResultSet getirilen = null;

    ObservableList<Kullanıcılar> kullanıcılarData = FXCollections.observableArrayList();

    public ObservableList<Boolean> status = FXCollections.observableArrayList(true, false);


    @FXML
    void initialize() {

    }

    @FXML
    void btnSignInClick(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        sql = "SELECT username,password FROM admin WHERE username =? AND password =?";

        try {
            sorguIfadesi = baglanti.prepareStatement(sql);
            sorguIfadesi.setString(1, username);
            sorguIfadesi.setString(2, password);

            getirilen = sorguIfadesi.executeQuery();


            if (getirilen.next()) {

                txtPassword.setVisible(false);
                txtUsername.setVisible(false);
                btnSignIn.setVisible(false);

                tableView.setVisible(true);
                btnSave.setVisible(true);
                tableView1.setVisible(true);
                lblAllData.setVisible(true);

                Node source = (Node) event.getSource();
                Stage primaryStage = (Stage) source.getScene().getWindow();
                primaryStage.setWidth(950);
                primaryStage.setResizable(false);
                primaryStage.getOwner();

                DegerleriGetir();
                DegerleriGetirRecord();



            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hatalı Giriş");
                alert.setHeaderText("Giriş Başarısız");
                alert.setContentText("Kullanıcı Adı ve Şifrenizi Kontrol Ediniz");

                alert.showAndWait();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnCancelClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent rootNode = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(rootNode, 800, 450));
        stage.setResizable(false);
        stage.show();

        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();

    }

    @FXML
    void btnSaveClick(ActionEvent event) {
        sql = "UPDATE login SET status=? WHERE id=?";

        try {
            for (Kullanıcılar kullanıcılar : kullanıcılarData) {

                sorguIfadesi = baglanti.prepareStatement(sql);
                sorguIfadesi.setBoolean(1, kullanıcılar.getStatus());
                sorguIfadesi.setInt(2, kullanıcılar.getUserID());
                sorguIfadesi.executeUpdate();

            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Güncelleme Başarılı");
            alert.showAndWait();

        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    public void DegerleriGetir() {
        sql = "select * from login";

        try {
            sorguIfadesi = baglanti.prepareStatement(sql);
            getirilen = sorguIfadesi.executeQuery();
            while (getirilen.next()) {
                kullanıcılarData.add(new Kullanıcılar(getirilen.getInt("id"), getirilen.getString("username"), getirilen.getString("password"), getirilen.getBoolean("status")));
            }

            tableView.setItems(kullanıcılarData);

            userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
            usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
            passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
            statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

            statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
            statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(status));

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void DegerleriGetirRecord() {
        sql = " SELECT * FROM Record ";

        try {
            sorguIfadesi = baglanti.prepareStatement(sql);
            getirilen = sorguIfadesi.executeQuery();

            while (getirilen.next()) {
                allData.add(new Record(getirilen.getString("filename"), getirilen.getString("x"), getirilen.getString("y"), getirilen.getString("username")));
            }
            tableView1.setItems(allData);

            fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
            xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
            yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());
            usernameColumn1.setCellValueFactory((cellData ->cellData.getValue().usernameProperty()));

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

}
