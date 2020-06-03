import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public void close(Stage primaryStage){


    }


    public static void main(String[] args) {
        launch(args);
    }
}
