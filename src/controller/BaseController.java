package controller;

import java.sql.SQLException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.AssetHistoryManager;
import model.AssetManager;
import model.OwnedAssetsManager;

public class BaseController {
    
    protected static UIManager uiManager;
    protected static OwnedAssetsManager ownedAssetsManager;
    protected static AssetHistoryManager assetHistoryManager;
    protected static AssetManager assetManager;
    protected static String username;

    public static void setUsername(String user)
    {
        username = user;
    }

    public static void setDatabase() throws SQLException
    {
        BaseController.ownedAssetsManager = new OwnedAssetsManager();
        BaseController.assetHistoryManager = new AssetHistoryManager();
        BaseController.assetManager = new AssetManager();
    }

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

    public static void setUiManager(UIManager manager)
    {
        BaseController.uiManager = manager;
    }


}
