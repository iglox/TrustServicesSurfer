package com.ids.trustservicesurfer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SurfApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SurfApplication.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
            stage.setTitle("Trust services surfer");
            stage.setScene(scene);
            stage.show();
        }catch(IOException e) {
            System.out.println("Failed to start");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}