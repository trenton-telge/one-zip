package com.eventhorizonwebdesign.onezip;

import com.eventhorizonwebdesign.jfail.JFail;
import com.eventhorizonwebdesign.onezip.util.Extractor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

/**
 * Created by Trenton on 8/23/2016.
 */
public class ExtractProgressController{

    @FXML
    private ProgressBar extractProgress;

    @FXML
    private Button doneButton;


    @FXML
    private void initialize(){
        doneButton.setOnAction(e -> {
            System.exit(0);
        });
        Runnable extractRunnable = () ->
        {
                if (Extractor.smartExtract(Main.workingArchive, Main.workingDestination)){
                    Platform.runLater(() -> {
                            extractProgress.setProgress(1);
                            doneButton.setText("Finished");
                    });
                } else {
                    try {
                        throw new Exception("Extraction Failed");
                    } catch (Exception e){
                        e.printStackTrace();
                        JFail.handleError(e, true);
                    }
                }
        };
        new Thread(extractRunnable).start();
    }


}
