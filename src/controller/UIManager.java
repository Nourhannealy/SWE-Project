package controller;

import db.DatabaseManager;
import controller.Controller;
import Users.signupController;
import model.AssetManager;
import Users.signupController;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class UIManager {
    private Stage stage;
    private Controller controller;

    private void errorMessage(String title, String details, Exception e)
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

    public UIManager(Stage primaryStage)
    {
        this.stage = primaryStage;
    }

    public void showMainView() {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainWindow.fxml"));
            Parent root = loader.load();

            // Get the controller and inject UIManager or other data
            this.controller = loader.getController();
            this.controller.setUiManager(this);
            this.controller.setDatabase();

            stage.setScene(new Scene(root));
            stage.setTitle("Investment Manager");
            stage.show();
        }
        catch (Exception e)
        {
            errorMessage("Database error", "Unable to connect to db", e);
        }
    }

    public void switchToSignUp()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup.fxml"));
            Parent root = loader.load();
            signupController signUpCtrl = loader.getController();

            // 3. Switch scene
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            errorMessage("Sign Up error", "Unable to set controller", e);
        }
    }
}
