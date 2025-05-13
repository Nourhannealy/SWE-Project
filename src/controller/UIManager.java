package controller;

import db.DatabaseManager;
import controller.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import Users.LoginController;
import Users.signupController;
import model.AssetManager;
import Users.signupController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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

    public void setUsername(String name)
    {
        BaseController.setUsername(name);
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
            BaseController.setUiManager(this);
            BaseController.setDatabase();

            stage.setScene(new Scene(root));
            stage.setTitle("Investment Manager");
            stage.show();
        }
        catch (Exception e)
        {
            errorMessage("Database error", "Unable to connect to db", e);
        }
    }

    public static void populateTransactionTypes(ComboBox<String> transaction_type) 
    {
        transaction_type.getItems().addAll("Edit", "Remove");
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

    public void switchToSignUp()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup.fxml"));
            Parent root = loader.load();
            signupController signUpCtrl = loader.getController();

            // Switching the view
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            errorMessage("Sign Up Error", "Unable to set controller", e);
        }
    }

    public void switchToLogIn()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LOGIN.fxml"));
            Parent root = loader.load();
            LoginController loginctrl = loader.getController();

            // Switching the view
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            errorMessage("Log in Error", "Unable to set controller", e);
        }
        
    }

    public void display()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
            Parent root = loader.load();
            MenuController menuCtrl = loader.getController();

            // Switching the view
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            errorMessage("Display Error", "Unable to set menu controller", e);
            System.out.println("Menu controller stack: ");
            e.printStackTrace();
        } 
    }

    public void switchToEditRemove()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editRemoveAsset.fxml"));
            Parent root = loader.load();
            AssetHistoryController assetHistoryCtrl = loader.getController();

            // Switching the view
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            errorMessage("Transaction Error", "Unable to open transaction window", e);
        }
    }
    
    public void switchToAdd()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addAsset.fxml"));
            Parent root = loader.load();
            AddAssetController addCtrl = loader.getController();

            // Switching the view
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            errorMessage("View Error", "Unable to set controller", e);
        }
    }

    public void switchToZakat()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/zakatCalculator.fxml"));
            Parent root = loader.load();
            ZakatCalculatorController rptCtrl = loader.getController();

            // Switching the view
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            errorMessage("View Error", "Unable to set controller", e);
        }
    }

    public void switchToReport()
    {
        System.out.println("Prompted to switch ");
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/investmentReport.fxml"));
            System.out.println("File accessed");
            Parent root = loader.load();
            InvestmentReportController rptCtrl = loader.getController();
            System.out.println("Got controller too !");
        

            // Switching the view
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e)
        {
            errorMessage("View Error", "Unable to set controller", e);
        }
    }

}
