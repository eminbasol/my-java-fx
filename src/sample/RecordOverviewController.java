package sample;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class RecordOverviewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Record> tableView;

    @FXML
    private TableColumn<Record, Number> ıdColumn;

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
    private Button btnSave, btnShowAll;

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
    private TextField txtFileName;

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

    @FXML
    private ListView<String> listView;

    @FXML
    private VBox vBox, vBoxSlider;

    @FXML
    private TextField txtPath;

    @FXML
    private TextField txtImageName;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField search;

    @FXML
    private Label lblY1;

    @FXML
    private Label lblX1;

    @FXML
    private Slider slider;

    @FXML
    private Button btnZoom, btnAnaliz;
    @FXML
    private Group zoomGroup, rootGroup;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ToggleGroup group1;
    @FXML
    private RadioButton radiobtn800, radiobtn1280;

    ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

    private static final int MIN_PIXELS = 10;

    FileChooser fc = new FileChooser();

    public ObservableList<Record> data;

    public FilteredList filteredList;

    @FXML
    public void initialize() {

        ıdColumn.visibleProperty().bind(boxId.selectedProperty());
        fileNameColumn.visibleProperty().bind(boxFileName.selectedProperty());
        xColumn.visibleProperty().bind(boxX.selectedProperty());
        yColumn.visibleProperty().bind(boxY.selectedProperty());

        showRecordInTextFields(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showRecordInTextFields(newValue));


        data = FXCollections.observableArrayList();
        ıdColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Record, Number> recordNumberCellDataFeatures) {
                return new ReadOnlyObjectWrapper(tableView.getItems().indexOf(recordNumberCellDataFeatures.getValue()) + "");
            }
        });

        radiobtn800.setToggleGroup(group1);
        radiobtn1280.setToggleGroup(group1);

        group1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton) group1.getSelectedToggle();

            }
        });
    }


    void showRecordInTextFields(Record record) {
        if (record != null) {
            txtFileName.setText(record.getFileName());
            txtX.setText(record.getX());
            txtY.setText(record.getY());

        } else {
            txtFileName.setText("");
            txtX.setText("");
            txtY.setText("");
        }
    }

    @FXML
    private void btnDeleteClick(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            if (filteredList == null) {
                tableView.getItems().remove(selectedIndex);
            } else {
                int sourceIndex = filteredList.getSourceIndexFor(data, selectedIndex);
                data.remove(sourceIndex);
                if (filteredList != null) {
                    zoomGroup.getChildren().clear();
                    zoomGroup.getChildren().add(imageView);
                    //pane.getChildren().addAll(vBoxSlider, btnZoom, btnAnaliz);
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
                    for (int j = 0; j < xSutun.size(); j++) {
                        Circle spot = new Circle(3);
                        spot.setFill(Color.RED);
                        spot.setCenterX(1.0f);
                        spot.setCenterY(1.0f);

                        spot.setLayoutX(Double.parseDouble(xSutun.get(j)));
                        spot.setLayoutY(Double.parseDouble(ySutun.get(j)));

                        zoomGroup.getChildren().add(spot);
                        lblX1.setText("");
                        lblY1.setText("");

                    }
                }
            }
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

        fc.setTitle("Load CSV File");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fc.showOpenDialog(null);

        if (file == null) {
            return;
        } else {
            data.clear();
            Path dirP = Paths.get(String.valueOf(file));
            InputStream in = Files.newInputStream(dirP);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            Scanner scan = new Scanner(reader);
            scan.useDelimiter("\\s*,\\s*");

            while (scan.hasNext()) {
                String fileName = scan.next();
                String x = scan.next();
                String y = scan.next();

                data.add(new Record(fileName, x, y));
                tableView.setItems(data);

                ıdColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Number>, ObservableValue<Number>>() {
                    @Override
                    public ObservableValue<Number> call(TableColumn.CellDataFeatures<Record, Number> recordNumberCellDataFeatures) {
                        return new ReadOnlyObjectWrapper(tableView.getItems().indexOf(recordNumberCellDataFeatures.getValue()) + "");
                    }

                });
                fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
                xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
                yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());
                srcInput.setText(file.getAbsolutePath());

            }
            scan.close();
        }
    }

    @FXML
    private void btnSaveClicked(ActionEvent e) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Text", "*.txt"));

        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH-mm");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String format = "tmp_" + currentDate + ".csv";

        String defaultSaveName = format;
        fileChooser.setInitialFileName(defaultSaveName);
        File file = fileChooser.showSaveDialog(null);

        Writer writer = null;
        try {
            if (file != null) {
                File dir = file.getParentFile();
                fileChooser.setInitialDirectory(dir);
            }
            writer = new BufferedWriter(new FileWriter(file));
            for (Record record : data) {

                String text = "," + record.getFileName() + "," + record.getX() + "," + record.getY();
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

        txtFileName.setText("");
        txtX.setText("");
        txtY.setText("");
    }

    @FXML
    private void btnLoadImageClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png", "*.tif", "*.gif"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        String fileName = txtImageName.getText();
        String[] test = fileName.split("/");
        fileName = test[(test.length) - 1];
        search.setText(fileName);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList = new FilteredList(data, e -> true);

            filteredList.setPredicate((Predicate<? super Record>) (Record record) -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                } else if (record.getFileName().contains(newValue)) {
                    return true;
                }
                return false;
            });
        });

        if (selectedFiles != null) {
            for (int i = 0; i < selectedFiles.size(); i++) {

                listView.getItems().add(selectedFiles.get(i).getAbsolutePath());

                listView.getSelectionModel().selectedItemProperty().addListener(
                        new ChangeListener<String>() {
                            public void changed(ObservableValue<? extends String> ov,
                                                String old_val, String new_val) {

                                txtImageName.setText(new_val);
                                txtPath.setText(new_val);

                            }
                        });
            }
        } else {
            System.out.println("File is not Valid");
        }

        listView.getSelectionModel().select(0);
        listViewImageClick();
    }

    private void listViewImageClick() throws IOException {
        imageView.setImage(null);
        if(group1.getSelectedToggle() == radiobtn800){
            int scaledWidth = 800;
            int scaledHeight = 600;

            String outputPath = txtPath.getText().substring(0, txtPath.getText().indexOf("."));
            String format = txtPath.getText().substring(txtPath.getText().lastIndexOf("."));

            RecordOverviewController.resize(txtPath.getText(), outputPath + "_resized_800x600" + format, scaledWidth, scaledHeight);

            Image image = new Image("file:" + outputPath + "_resized_800x600" + format);
            imageView.setImage(image);
        }else{
            int scaledWidth = 1280;
            int scaledHeight = 720;

            String outputPath = txtPath.getText().substring(0, txtPath.getText().indexOf("."));
            String format = txtPath.getText().substring(txtPath.getText().lastIndexOf("."));

            RecordOverviewController.resize(txtPath.getText(), outputPath + "_resized_1280x720" + format, scaledWidth, scaledHeight);

            Image image = new Image("file:" + outputPath + "_resized_1280x720" + format);
            imageView.setImage(image);
        }

        imageView.setFitHeight(363);
        imageView.setFitWidth(488);

        zoomGroup.getChildren().clear();
        zoomGroup.getChildren().add(imageView);
        //pane.getChildren().addAll(vBoxSlider, btnZoom, btnAnaliz);
        lblX1.setText("");
        lblY1.setText("");

        String fileName = txtImageName.getText();
        String[] test = fileName.split("/");
        fileName = test[(test.length) - 1];
        search.setText(fileName);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList = new FilteredList(data, e -> true);

            filteredList.setPredicate((Predicate<? super Record>) (Record record) -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                } else if (record.getFileName().contains(newValue)) {
                    return true;
                }
                return false;
            });
        });
        tableView.setItems(filteredList);
        if (filteredList != null) {
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
            for (int j = 0; j < xSutun.size(); j++) {
                Circle spot = new Circle(3);
                spot.setFill(Color.RED);
                spot.setCenterX(1.0f);
                spot.setCenterY(1.0f);

                spot.setLayoutX(Double.parseDouble(xSutun.get(j)));
                spot.setLayoutY(Double.parseDouble(ySutun.get(j)));

                zoomGroup.getChildren().add(spot);
            }

        }
    }


    @FXML
    public void listViewImageClick(MouseEvent click) throws IOException {
        listViewImageClick();
    }

    public static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {

        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }


    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    @FXML
    void imageViewOnMouseClicked(MouseEvent e) {
        if (txtImageName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resim Dosyası Seçiniz");
            alert.setHeaderText("Lütfen Önce Kullanmak İstediğiniz Resim Dosyasını Seçiniz");
            alert.setContentText("İşaretlemeler Resim Üzerinde Yapılacaktır");

            alert.showAndWait();

        } else {
            Circle spot = new Circle(3);
            spot.setFill(Color.RED);
            spot.setCenterX(1.0f);
            spot.setCenterY(1.0f);

            spot.setLayoutX(e.getX());
            spot.setLayoutY(e.getY());

            zoomGroup.getChildren().add(spot);

            String x = String.format("%.5s",e.getX());
            String y = String.format("%.5s",e.getY());
            String fileName = txtImageName.getText();
            String[] test = fileName.split("/");
            fileName = test[(test.length) - 1];

            data.add(new Record(fileName, x, y));

            tableView.setItems(filteredList);

            fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
            xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
            yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());

            double initX = e.getX();
            double initY = e.getY();

            if (lblX1.getText() != "" && lblY1.getText() != "") {
                Line line = new Line();
                line.setStartX(Double.parseDouble(lblX1.getText()));
                line.setStartY(Double.parseDouble(lblY1.getText()));
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                line.setFill(null);
                line.setStroke(Color.RED);
                line.setStrokeWidth(2);
                zoomGroup.getChildren().add(line);
            }
            lblX1.setText(String.valueOf(initX));
            lblY1.setText(String.valueOf(initY));
        }

    }

    @FXML
    void btnZoomClick(ActionEvent event) {
        zoomSlider();
    }

    private void zoomSlider() {
        zoomGroup.setScaleX(slider.getValue());
        zoomGroup.setScaleY(slider.getValue());
    }


    @FXML
    private void btnAnalizClick(ActionEvent event) {
        if (data == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CSV Dosyası Seçiniz");
            alert.setHeaderText("Lütfen Önce Kullanmak İstediğiniz CSV Dosyasını Seçiniz");
            alert.setContentText("Kayıtlar seçtiğiniz CSV dosyasından alınacak.");

            alert.showAndWait();


        } else if (filteredList == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Herhangi Bir Kayıt Yok");
            alert.setHeaderText("Lütfen Önce Resim Üzerine Tıklayarak Nokta Ekleyiniz");
            alert.setContentText("Kayıtlar tablodan alınacak.");

            alert.showAndWait();

        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Analiz.fxml"));
                Parent root1 = loader.load();

                AnalizController analizController = loader.getController();
                analizController.setData(filteredList);

                analizController.buttonClick();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1, 540, 290));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private double scale = 1;

    @FXML
    public void scrolling(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() > 0) {
            scale += 0.1;
        } else if (scrollEvent.getDeltaY() < 0) {
            scale -= 0.1;
        }
        zoomGroup.setScaleX(scale);
        zoomGroup.setScaleY(scale);
    }

    @FXML
    void btnShowAllClick(ActionEvent event) {
        tableView.setItems(data);
    }

}
