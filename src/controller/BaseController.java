package controller;

import controller.UIManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class BaseController {
    
    protected UIManager uiManager;

    public void errorMessage(String title, String details, Exception e)
    {
        Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setContentText(details);
                alert.showAndWait();
                // Close JavaFX application
                Platform.exit();
            });

    }

    public void setUiManager(UIManager manager)
    {
        this.uiManager = manager;
    }


}
