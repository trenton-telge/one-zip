package com.eventhorizonwebdesign.onezip;

import com.eventhorizonwebdesign.jfail.JFail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;



/**
 * Created by Trenton on 8/23/2016.
 */
public class SimpleExtractController {

    @FXML
    TextField archiveField;

    @FXML
    TextField destinationField;

    @FXML
    Button chooseDestButton;

    @FXML
    Button startButton;



    @FXML
    private void initialize(){
        archiveField.setText(Main.workingArchive.getAbsolutePath());
        chooseDestButton.setOnAction(e -> {
            DirectoryChooser df = new DirectoryChooser();
            df.setTitle("Choose destination...");
            Main.workingDestination = df.showDialog(new Stage());
            destinationField.setText(Main.workingDestination.getAbsolutePath());
        });
        startButton.setOnAction(e -> {
            if (Main.workingDestination != null){
                Node node=(Node) e.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                try {
                    Main.root = FXMLLoader.load(getClass().getResource("extract-progress.fxml"));
                } catch (Exception e1){
                    e1.printStackTrace();
                    JFail.handleError(e1, true);
                }
                Scene scene = new Scene(Main.root);
                stage.setScene(scene);
                stage.show();
            } else {
                destinationField.setStyle("-fx-prompt-text-fill: crimson;");
            }
        });
    }
}
