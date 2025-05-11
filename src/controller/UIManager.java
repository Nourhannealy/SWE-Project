package controller;

import db.DatabaseManager;
import controller.Controller;

import java.io.IOException;
import javafx.application.Application;
// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UIManager {
    private Stage stage;

    public UIManager(Stage primaryStage)
    {
        this.stage = primaryStage;
    }

    public void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainWindow.fxml"));
        Parent root = loader.load();

        // Get the controller and inject UIManager or other data
        Controller controller = loader.getController();
        controller.setUiManager(this);  // pass reference to self

        stage.setScene(new Scene(root));
        stage.show();
    }


    
}
