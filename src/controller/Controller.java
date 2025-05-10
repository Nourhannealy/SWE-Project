package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Label title;

    @FXML
    private TextField txTitle;

    @FXML
    void btnClicked(ActionEvent event) {
        Stage mainWindow = (Stage) txTitle.getScene().getWindow();
        String title = txTitle.getText();
        mainWindow.setTitle(title); 
    }

}
