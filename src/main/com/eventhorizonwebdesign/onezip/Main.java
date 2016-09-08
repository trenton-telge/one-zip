package com.eventhorizonwebdesign.onezip;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Main extends Application {

    static Parent root;
    public static File workingArchive;
    public static List<File> workingFiles;
    public static File workingDestination;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(this.getClass().getResource("main.fxml"));
        primaryStage.setTitle("One Zip");
        primaryStage.setScene(new Scene(root, 600, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
