package controller;

import java.sql.SQLException;

import model.AssetManager;
import model.AssetHistoryManager;
import model.OwnedAssetsManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    private UIManager uiManager;
    private OwnedAssetsManager ownedAssetsManager;
    private AssetHistoryManager assetHistoryManager;
    private AssetManager assetManager;

    public void setDatabase() throws SQLException
    {
        this.ownedAssetsManager = new OwnedAssetsManager();
        this.assetHistoryManager = new AssetHistoryManager();
        this.assetManager = new AssetManager();
    }


    public void setUiManager(UIManager manager)
    {
        this.uiManager = manager;
    }

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
