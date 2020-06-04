package View;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import Model.Record;
import Util.VeriTabaniUtil;

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
    private TableColumn<Record, String> usernameColumn;

    @FXML
    private Button btnCancel, btnDownlaod;

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
                allData.add(new Record(getirilen.getString("filename"), getirilen.getString("x"), getirilen.getString("y"), getirilen.getString("username")));
            }

            tableView.setItems(allData);

            fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
            xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
            yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());
            usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @FXML
    void btnDownlaodClick(ActionEvent event) throws DbxException, IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory == null) {

        } else {

            final String ACCESS_TOKEN = "u8niALod3VAAAAAAAAAAWqFtMalMT72vhkZyUfAtZhWX1UIuscCE8IcRfDLQBB9T";

            DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

            TableColumn<Record, String> column = fileNameColumn;
            List<String> fileNamelist = new ArrayList<>();
            for (Record item : tableView.getItems()) {
                fileNamelist.add(column.getCellObservableValue(item).getValue());
            }

            for (int i = 0; i < fileNamelist.size(); i++) {
                String fileName = fileNamelist.get(i);
                String[] test = fileName.split("/");
                fileName = test[(test.length) - 1];
                try {
                    OutputStream downloadFile = new FileOutputStream(selectedDirectory.getAbsolutePath() + "/" + fileName);
                    try {
                        client.files().downloadBuilder("/" + lblUsername.getText() + "/" + fileName)
                                .download(downloadFile);
                    } finally {
                        downloadFile.close();
                    }
                } catch (DbxException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Unable to download file to local system\n Error:" + e);
                    alert.showAndWait();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Unable to download file to local system\n Error: " + e);
                    alert.showAndWait();
                }
            }

        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Downloaded Images");
        alert.showAndWait();


    }
}




