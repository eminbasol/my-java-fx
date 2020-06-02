package sample.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Util.VeriTabaniUtil;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword1;

    @FXML
    private Button btnCancel;

    String sql = "";
    Connection baglanti = null;
    PreparedStatement sorguIfadesi = null;
    ResultSet getirilen = null;


    public RegistrationController() {
        baglanti = VeriTabaniUtil.Baglan();
    }

    @FXML
    void initialize() throws IOException {
        URL url = new URL("https://img.icons8.com/ios/500/000000/mesh.png");
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();
        Image image = new Image(in);
        imageView.setImage(image);

    }

    @FXML
    void btnRegisterClick(ActionEvent event) {
        sql = "insert into login(username,password,status) values(?,?,?)";
        try {
            if (txtUsername.getText().trim().isEmpty() == false && txtPassword.getText().trim().equals(txtPassword1.getText()) && txtPassword.getText().trim().isEmpty() == false) {
                sorguIfadesi = baglanti.prepareStatement(sql);
                sorguIfadesi.setString(1, txtUsername.getText().trim());
                sorguIfadesi.setString(2, txtPassword.getText().trim());
                sorguIfadesi.setBoolean(3, false);
                sorguIfadesi.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Kayıt Başarılı");
                alert.setHeaderText("Kayıt Başarılı Bir Şekilde Oluşturuldu.");
                alert.setContentText("Admin Onayladıktan Sonra Giriş Yapabilirsiniz");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alanları Lütfen Kontrol Ediniz");
                alert.setHeaderText("Tekrar Kontrol Ediniz");
                alert.setContentText("Kullanıcı Adı Boş Bırakılamaz ve Şifreler Aynı Olmalıdır");

                alert.showAndWait();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
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
}
