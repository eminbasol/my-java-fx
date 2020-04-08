package sample;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
    private VBox vBox;

    @FXML
    private TextField txtPath;

    @FXML
    private TextField txtImageName;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField search;

    @FXML
    private Pane pane;


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
    }

    @FXML
    void btnUpdateClick(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        Record record1 = new Record(txtFileName.getText(), txtX.getText(), txtY.getText());
        if (selectedIndex >=0) {
            if (filteredList == null) {
                tableView.getItems().set(selectedIndex, record1);
            } else {
                int sourceIndex = filteredList.getSourceIndexFor(data, selectedIndex);
                data.set(sourceIndex,record1);
            }

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
        }

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
    private void btnAddClick(ActionEvent event) {
        if (data != null) {
            if (isInputValid()) {
                data.add(new Record(txtFileName.getText(), txtX.getText(), txtY.getText()));

                txtFileName.clear();
                txtY.clear();
                txtX.clear();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CSV Dosyası Seçiniz");
            alert.setHeaderText("Lütfen Önce Kullanmak İstediğiniz CSV Dosyasını Seçiniz");
            alert.setContentText("Kayıtlar seçtiğiniz CSV dosyasına kaydedilecek");

            alert.showAndWait();

        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

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
    private void btnLoadImageClick() {
        if (data == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CSV Dosyası Seçiniz");
            alert.setHeaderText("Lütfen Önce Kullanmak İstediğiniz CSV Dosyasını Seçiniz");
            alert.setContentText("Kayıtlar seçtiğiniz CSV dosyasına kaydedilecek");

            alert.showAndWait();
        } else {
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
        }
    }

    @FXML
    public void listViewImageClick(MouseEvent click) {

        imageView.setImage(null);
        Image image = new Image("file:" + txtPath.getText());
        imageView.setImage(image);

        double width = image.getWidth();
        double height = image.getHeight();

        reset(imageView, width / 2, height / 2);


        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());

        pane.getChildren().clear();
        pane.getChildren().add(imageView);

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


    }


    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    @FXML
    void imageViewOnMouseClicked(MouseEvent e) {
        if (data == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CSV Dosyası Seçiniz");
            alert.setHeaderText("Lütfen Önce Kullanmak İstediğiniz CSV Dosyasını Seçiniz");
            alert.setContentText("Kayıtlar seçtiğiniz CSV dosyasına kaydedilecek");

            alert.showAndWait();


        } else if (txtImageName.getText().isEmpty()) {
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

            //çemberin farenin tıklandığı yerde gözükmesi için, kaydederken orjinal noktalar kaydediliyor
            spot.setLayoutX(e.getX() - 3);
            spot.setLayoutY(e.getY() - 3);

            pane.getChildren().add(spot);

            String x = String.valueOf(e.getX());
            String y = String.valueOf(e.getY());
            String fileName = txtImageName.getText();
            String[] test = fileName.split("/");
            fileName = test[(test.length) - 1];

            data.add(new Record(fileName, x, y));

            tableView.setItems(filteredList);

            fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
            xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
            yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());
        }
    }


    @FXML
    void imageViewOnMouseDragged(MouseEvent event) {
        if (data != null && !txtImageName.getText().isEmpty()) {
            Point2D dragPoint = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));
            shift(imageView, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(imageView, new Point2D(event.getX(), event.getY())));
        }
    }

    @FXML
    void imageViewOnMousePressed(MouseEvent event) {
        if (data != null && !txtImageName.getText().isEmpty()) {
            Point2D mousePress = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));
            mouseDown.set(mousePress);
        }

    }

    public Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    public double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth();
        double height = imageView.getImage().getHeight();

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }


    @FXML
    public void imageViewOnScroll(ScrollEvent e) {
        if (data != null && !txtImageName.getText().isEmpty()) {
            double delta = e.getDeltaY();
            Rectangle2D viewport = imageView.getViewport();


            double width = imageView.getImage().getWidth();

            double height = imageView.getImage().getHeight();


            double scale = clamp(Math.pow(1.01, delta),
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())
            );

            Point2D mouse = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        }
    }
}














