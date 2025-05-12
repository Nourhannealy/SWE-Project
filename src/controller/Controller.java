package controller;

import java.sql.SQLException;

import model.AssetManager;
import model.AssetHistoryManager;
import model.OwnedAssetsManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class Controller extends BaseController{


    public OwnedAssetsManager getOwnedAssetsManager()
    {
        return BaseController.ownedAssetsManager;
    }

    public AssetManager getAssetManager()
    {
        return BaseController.assetManager;
    }

    public AssetHistoryManager getAssetHistoryManager()
    {
        return BaseController.assetHistoryManager;
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

    @FXML
    void logInBtnClicked(MouseEvent event) 
    {
        uiManager.switchToLogIn();
    }

}