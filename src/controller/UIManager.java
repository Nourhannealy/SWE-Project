package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Users.signupController;
import db.DatabaseManager;
import transactions.AssetHistoryController;
import controller.Controller;
import model.AssetManager;


public class UIManager {
    private Stage stage;
    private Controller controller;

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
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setContentText("Failed to connect to the database.");
                alert.showAndWait();
                // Close JavaFX application
                Platform.exit();
            });
        }
    }

    public void showSignupView()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup.fxml"));
            Parent root = loader.load();

            signupController signupController = loader.getController();
            
            stage.setScene(new Scene(root));
            stage.setTitle("Investment Manager - Signup");
            stage.show();
        }
        catch (Exception e)
        {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setContentText("Failed to connect to the database.");
                alert.showAndWait();
                // Close JavaFX application
                Platform.exit();
            });
        }
    }

    public void showTransactionsView()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/transactions.fxml"));
            Parent root = loader.load();

            AssetHistoryController assetHistoryController = loader.getController();
            assetHistoryController.setAssetManager(controller.getAssetManager());
            assetHistoryController.setOwnedAssetManager(controller.getOwnedAssetsManager());
            assetHistoryController.setAssetHistoryManager(controller.getAssetHistoryManager());
            
            stage.setScene(new Scene(root));
            stage.setTitle("Investment Manager - Transactions");
            stage.show();
        }
        catch (Exception e)
        {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setContentText("Failed to connect to the database.");
                alert.showAndWait();
                // Close JavaFX application
                Platform.exit();
            });
        }
    }

    public static void populateTransactionTypes(ComboBox<String> transaction_type) 
    {
        transaction_type.getItems().addAll("Sell", "Buy");
    }

    public static void addAssetForm(ComboBox<String> assets_name, ResultSet allAssets)
        throws SQLException
    {

        ObservableList<String> items = FXCollections.observableArrayList();
        while (allAssets.next()) {
            String item = allAssets.getString("name");
            items.add(item);
        }

        assets_name.setItems(items);
    }

    public static void editAssetForm(ComboBox<String> assets_name, ResultSet allAssets)
        throws SQLException
    {

        ObservableList<String> items = FXCollections.observableArrayList();
        while (allAssets.next()) {
            String item = allAssets.getString("name");
            items.add(item);
        }

        assets_name.setItems(items);
    }

}
