package controller;

import java.sql.SQLException;
import java.util.Optional;

import Users.UserManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.AssetHistoryManager;
import model.AssetManager;
import model.OwnedAssetsManager;

public class BaseController {
    
    protected static UIManager uiManager;
    protected static UserManager userManager;
    protected static OwnedAssetsManager ownedAssetsManager;
    protected static AssetHistoryManager assetHistoryManager;
    protected static AssetManager assetManager;
    protected static String username;
    protected static int userId;

    public void errorMessage(String title, String details, Exception e)
    {
        Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setContentText(details);
                alert.showAndWait();
            });

    }

    public static void setUsername(String user)
    {
        username = user;
        try
        {
            userId = userManager.getUserId(user);
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setContentText("Couldn't set up users");
                alert.showAndWait();
                // Close JavaFX application
                Platform.exit();
            });

        }
    }

    public static void setDatabase() throws SQLException
    {
        BaseController.ownedAssetsManager = new OwnedAssetsManager();
        BaseController.assetHistoryManager = new AssetHistoryManager();
        BaseController.assetManager = new AssetManager();
        BaseController.userManager = new UserManager();
        assetHistoryManager.setOwnedAssetsManager(ownedAssetsManager);
    }



    public static void setUiManager(UIManager manager)
    {
        BaseController.uiManager = manager;
    }


}
