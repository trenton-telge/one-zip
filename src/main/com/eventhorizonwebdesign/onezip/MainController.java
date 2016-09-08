package com.eventhorizonwebdesign.onezip;
import com.eventhorizonwebdesign.jfail.JFail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;



public class MainController{
    @FXML
    private Button extractButton;

    @FXML
    private Button compressButton;

    @FXML
    private void initialize(){

        extractButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Archive");
            ExtensionFilter ff = new ExtensionFilter("Archive Files", "*.zip");
            fileChooser.setSelectedExtensionFilter(ff);
            Main.workingArchive = fileChooser.showOpenDialog(new Stage());
            Node node=(Node) e.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            try {
                Main.root = FXMLLoader.load(getClass().getResource("simple-extract.fxml"));
            } catch (Exception e1){
                e1.printStackTrace();
                JFail.handleError(e1, true);
            }
            Scene scene = new Scene(Main.root);
            stage.setScene(scene);
            stage.show();
        });

        compressButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            Main.workingFiles = fileChooser.showOpenMultipleDialog(new Stage());
            Node node=(Node) e.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            try {
                Main.root = FXMLLoader.load(getClass().getResource("compress.fxml"));
            } catch (Exception e1){
                e1.printStackTrace();
                JFail.handleError(e1, true);
            }
            Scene scene = new Scene(Main.root);
            stage.setScene(scene);
            stage.show();
        });

    }
}
