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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


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

    public OwnedAssetsManager getOwnedAssetsManager()
    {
        return this.ownedAssetsManager;
    }

    public AssetManager getAssetManager()
    {
        return this.assetManager;
    }

    public AssetHistoryManager getAssetHistoryManager()
    {
        return this.assetHistoryManager;
    }

    public void setUiManager(UIManager manager)
    {
        this.uiManager = manager;
    }

    @FXML
    private Label InvestmentManagerLabel;

    @FXML
    private Button LogInButton;

    @FXML
    private Button SignUpButton;

    @FXML
    void SignUpbtnClicked(MouseEvent event) 
    {
        uiManager.switchToSignUp();
    }

}