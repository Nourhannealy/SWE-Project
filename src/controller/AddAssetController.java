package controller;

import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class AddAssetController extends BaseController{
    

    @FXML
    private Button addAssetBtn;

    @FXML
    private TextField amountIn;

    @FXML
    private Label amountLabel;

    @FXML
    private Label assetCategoryLabel;

    @FXML
    private ComboBox<String> assetCategoryMenu;

    @FXML
    private Label assetLabel;

    @FXML
    private ComboBox<String> assetNameMenu;

    @FXML
    private Button backBtn;

    @FXML
    private Label message;

    public void initialize()
    {
        try
        {
            ResultSet availableAssets = assetManager.fetchAllAssetTypes();
            ObservableList<String> assetCategories = FXCollections.observableArrayList();
            // assetNameMenu.setItems(assetCategories); 
            while (availableAssets.next())
            {
                assetCategories.add(availableAssets.getString("type"));
            }

            assetCategoryMenu.setItems(assetCategories);
            if (assetCategories.isEmpty())
            {
                assetCategoryMenu.setPromptText("No Categories available");
            }

            assetNameMenu.setPromptText("Choose a category first");
            message.setAlignment(Pos.CENTER);

        } catch (Exception e) {
            errorMessage("Database Error", "Couldn't get data from the db", e);
        }
        

    }

    @FXML
    void addAssetBtnClicked(MouseEvent event) {
        if (amountIn.getText() == null || amountIn.getText().trim().isEmpty() || assetNameMenu.getValue() == null) 
        {
            message.setTextFill(Color.RED);
            message.setText("You must choose an asset and an amount");
        }
        else
        {
            try
            {
                double amount = Double.parseDouble(amountIn.getText());
                if (amount <= 0) 
                    throw new NumberFormatException();

                assetHistoryManager.addBuyTransaction(userId, assetNameMenu.getValue(), "BUY", amount);
                message.setTextFill(Color.GREEN);
                message.setText("Asset added Successfully!");


            } catch (NumberFormatException  e) {
                message.setTextFill(Color.RED);
                message.setText("You must enter a positive mumeric value");
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage("Database Error", "Couldn't get data from the db", e);
            }
        }

    }

    @FXML
    void assetCatChosen(ActionEvent event) {
        try
        {
            String category = assetCategoryMenu.getValue();
            ResultSet fetchedAssetNames = assetManager.fetchAllAssetNames(category);
            ObservableList<String> assetNames = FXCollections.observableArrayList();

            while (fetchedAssetNames.next())
            {
                assetNames.add(fetchedAssetNames.getString("name"));
            }

            assetNameMenu.setItems(assetNames);
            if (assetNames.isEmpty())
            {
                assetCategoryMenu.setPromptText("No Categories available");
            }
        } catch (Exception e) {
            errorMessage("Database Error", "Couldn't get data from the db", e);
        }

    }

    @FXML
    void assetChosen(ActionEvent event) {

    }

    @FXML
    void backBtnClicked(MouseEvent event)
    {
        uiManager.display();
    }

}
