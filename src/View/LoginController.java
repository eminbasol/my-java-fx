package View;

import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Util.VeriTabaniUtil;


public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView1;

    @FXML
    private Button btnSignIn;

    @FXML
    private Label lblSignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ImageView imageView, imageViewBackground;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblAdmin;

    public LoginController() {
        baglanti = VeriTabaniUtil.Baglan();
    }

    String sql = "";
    Connection baglanti = null;
    PreparedStatement sorguIfadesi = null;
    ResultSet getirilen = null;

    @FXML
    void initialize() throws IOException {

        URL url = new URL("https://img.icons8.com/ios/500/000000/mesh.png");
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();
        Image image = new Image(in);
        imageView.setImage(image);
        imageView1.setImage(image);
        URL url1 = new URL("https://images.wallpaperscraft.com/image/polygon_triangles_convexity_128553_1280x720.jpg");
        URLConnection conn1 = url1.openConnection();
        InputStream in1 = conn1.getInputStream();
        Image image1 = new Image(in1);
        imageViewBackground.setImage(image1);
    }

    @FXML
    void btnSignInClick(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        sql = "SELECT username,password,status FROM login WHERE username =? AND password =? AND status =?";

        try {
            sorguIfadesi = baglanti.prepareStatement(sql);
            sorguIfadesi.setString(1, username);
            sorguIfadesi.setString(2, password);
            sorguIfadesi.setBoolean(3, true);

            getirilen = sorguIfadesi.executeQuery();


            if (getirilen.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RecordOverview.fxml"));
                Parent rootNode = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("AnalysisApp");
                stage.setScene(new Scene(rootNode, 1200, 420));
                stage.show();

                Node source = (Node) event.getSource();
                Stage primaryStage = (Stage) source.getScene().getWindow();
                primaryStage.close();

                RecordOverviewController recordOverviewController = loader.getController();
                recordOverviewController.setlblUsernamePass(username);


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
    void lblSignUpClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registration.fxml"));
        Parent rootNode = loader.load();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Registration Form");
        stage.setScene(new Scene(rootNode, 337, 447));
        stage.show();

        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();


    }

    @FXML
    void lblAdminClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        Parent rootNode = loader.load();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Admin Dashboard");
        stage.setScene(new Scene(rootNode, 400, 350));
        stage.show();

        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();

    }


}