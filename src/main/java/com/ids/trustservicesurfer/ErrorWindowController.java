package com.ids.trustservicesurfer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorWindowController {

    @FXML
    private Button okButton;
    @FXML
    public void onOkButtonPressed(ActionEvent event) {
        System.out.println("[!] Resetting filters and results");
        Platform.exit();
    }
}
