/*
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LoadImageController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancel;

    @FXML
    private ImageView imageView;

    @FXML
    private Pane p;

    @FXML
    private Canvas canvas;

    @FXML
    Label lblPass;


    ArrayList list = new ArrayList();

    public void setLblPass (String text, String name ) {

        lblPass.setText(text);
        Image image = new Image( "file:"+lblPass.getText());
        imageView.setImage(image);
    }



    @FXML
    void initialize()  {

        p.getChildren().add(canvas);
        GraphicsContext aPen = canvas.getGraphicsContext2D();
        aPen.setStroke(Color.BLACK);
        aPen.setFill(Color.YELLOW);
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                */
/*aPen.setFont(Font.font ("Arial", 40));
                aPen.strokeText(".", mouseEvent.getX(), mouseEvent.getY());*//*

                aPen.setStroke(Color.BLACK);
                aPen.setFill(Color.YELLOW);
                aPen.setFont(Font.font("Stencil", 40));
                aPen.fillText(".", mouseEvent.getX(), mouseEvent.getY());
                aPen.strokeText(".", mouseEvent.getX(), mouseEvent.getY());
            }
        });


        p.setOnMouseClicked(new EventHandler<MouseEvent>() {  // mouseClick koordinatları
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X:" + event.getSceneX());
                System.out.println("Y:" + event.getSceneY());
                list.add(event.getSceneX());
                list.add(event.getSceneY());

                System.out.println(list);





        })
    }

    @FXML
    private void handleCancel() {
        btnCancel.setOnAction(actionEvent ->
        {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });
    }
}


*/
