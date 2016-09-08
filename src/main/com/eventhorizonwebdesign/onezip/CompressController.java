package com.eventhorizonwebdesign.onezip;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
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

    private String selectedExtension = "zip";

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
            df.setInitialFileName("archive." + selectedExtension);
            Main.workingDestination = df.showSaveDialog(new Stage());
            destinationField.setText(Main.workingDestination.getAbsolutePath());
            selectedExtension = FilenameUtils.getExtension(Main.workingDestination.toString());
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
                selectedExtension = "zip";
                zipRadio.setSelected(true);
                break;
            case "zipx":
                selectedExtension = "zipx";
                zipxRadio.setSelected(true);
                break;
            case "rar":
                selectedExtension = "rar";
                rarRadio.setSelected(true);
                break;
            case "tar":
                selectedExtension = "tar";
                tarRadio.setSelected(true);
                break;
            case "tgz":
                selectedExtension = "tgz";
                tgzRadio.setSelected(true);
                break;
            case "iso":
                selectedExtension = "iso";
                isoRadio.setSelected(true);
                break;
        }
        Main.workingDestination = new File(Main.workingDestination.toString().concat())
    }
    private void updateExtensionFromText(){

    }
}
