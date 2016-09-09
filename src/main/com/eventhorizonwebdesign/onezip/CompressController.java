package com.eventhorizonwebdesign.onezip;

import com.eventhorizonwebdesign.jfail.JFail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Created by Trenton on 8/25/2016.
 */
public class CompressController {

    @FXML
    TextField filesField;

    @FXML
    TextField destinationField;

    @FXML
    Button chooseDestButton;

    @FXML
    RadioButton zipRadio;

    @FXML
    RadioButton zipxRadio;

    @FXML
    RadioButton rarRadio;

    @FXML
    RadioButton tarRadio;

    @FXML
    RadioButton tgzRadio;

    @FXML
    RadioButton isoRadio;

    @FXML
    Button startButton;


    @FXML
    private void initialize(){
        filesField.setText(Main.workingFiles.toArray()[1].toString());
        if (Main.workingFiles.size() > 1){
            filesField.setText(filesField.getText() + " and " + (Main.workingFiles.size() - 1) + " others...");
        }
        chooseDestButton.setOnAction(e -> {
            FileChooser df = new FileChooser();
                    df.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Archive File", "*.zip", "*.zipx", "*.rar", "*.tar", "*.tar", "*.tgz", "*.iso"));
            df.setTitle("Choose destination...");
            df.setInitialFileName("archive." + Main.selectedExtension);
            Main.workingDestination = df.showSaveDialog(new Stage());
            destinationField.setText(Main.workingDestination.getAbsolutePath());
            Main.selectedExtension = FilenameUtils.getExtension(Main.workingDestination.toString());
        });
        zipRadio.setOnAction(e -> {
            updateExtensionFromRadio("zip");
        });
        zipxRadio.setOnAction(e -> {
            updateExtensionFromRadio("zipx");
        });
        rarRadio.setOnAction(e -> {
            updateExtensionFromRadio("rar");
        });
        tarRadio.setOnAction(e -> {
            updateExtensionFromRadio("tar");
        });
        tgzRadio.setOnAction(e -> {
            updateExtensionFromRadio("tgz");
        });
        isoRadio.setOnAction(e -> {
            updateExtensionFromRadio("iso");
        });
        startButton.setOnAction(e -> {
            if (Main.workingDestination != null){
                Main.workingDestination = new File(Main.workingDestination.toString() + "." + Main.selectedExtension);
                Node node=(Node) e.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                try {
                    Main.root = FXMLLoader.load(getClass().getResource("compress-progress.fxml"));
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

    private void updateExtensionFromRadio(String recentlyPressedExt){
        zipRadio.setSelected(false);
        zipxRadio.setSelected(false);
        rarRadio.setSelected(false);
        tarRadio.setSelected(false);
        tgzRadio.setSelected(false);
        isoRadio.setSelected(false);
        switch (recentlyPressedExt){
            case "zip":
                Main.selectedExtension = "zip";
                zipRadio.setSelected(true);
                break;
            case "zipx":
                Main.selectedExtension = "zipx";
                zipxRadio.setSelected(true);
                break;
            case "rar":
                Main.selectedExtension = "rar";
                rarRadio.setSelected(true);
                break;
            case "tar":
                Main.selectedExtension = "tar";
                tarRadio.setSelected(true);
                break;
            case "tgz":
                Main.selectedExtension = "tgz";
                tgzRadio.setSelected(true);
                break;
            case "iso":
                Main.selectedExtension = "iso";
                isoRadio.setSelected(true);
                break;
        }
    }
}
