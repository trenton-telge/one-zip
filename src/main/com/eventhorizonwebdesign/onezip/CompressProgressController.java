package com.eventhorizonwebdesign.onezip;

import com.eventhorizonwebdesign.jfail.JFail;
import com.eventhorizonwebdesign.onezip.util.Compressor;
import com.eventhorizonwebdesign.onezip.util.Extractor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

/**
 * Created by Trenton on 9/8/2016.
 */
public class CompressProgressController {


    @FXML
    private ProgressBar compressProgress;

    @FXML
    private Button doneButton;


    @FXML
    private void initialize(){
        doneButton.setOnAction(e -> {
            System.exit(0);
        });
        Runnable extractRunnable = () ->
        {
            if (Compressor.smartCompress(Main.workingFiles, Main.workingDestination, Main.selectedExtension)){
                Platform.runLater(() -> {
                    compressProgress.setProgress(1);
                    doneButton.setText("Finished");
                });
            } else {
                try {
                    throw new Exception("Compression Failed");
                } catch (Exception e){
                    e.printStackTrace();
                    JFail.handleError(e, true);
                }
            }
        };
        new Thread(extractRunnable).start();
    }

}
