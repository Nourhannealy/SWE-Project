package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MenuController extends BaseController{

    @FXML
    private Button addAssetBtn;

    @FXML
    private Button editAssetBtn;

    @FXML
    private Button finReportBtn;

    @FXML
    private Label hiTxt;

    @FXML
    private void initialize()
    {
        hiTxt.setText("Hi " + username + '!');
    }

    @FXML
    private Label message;

    @FXML
    private Button zakatBtn;

    @FXML
    void addAssetBtnClicked(MouseEvent event) {
        uiManager.switchToAdd();
    }

    @FXML
    void editAssetBtnClicked(MouseEvent event) {
        uiManager.switchToEditRemove();
    }

    @FXML
    void finReportClicked(MouseEvent event) {

    }

    @FXML
    void zakatBtnClicked(MouseEvent event) {

    }

}

