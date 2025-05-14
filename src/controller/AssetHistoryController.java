package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

public class AssetHistoryController extends Controller{

    @FXML
    private TextField amount;

    @FXML
    private Label amount_label;

    @FXML
    private ComboBox<String> assets_name;

    @FXML
    private Button backBtn;

    @FXML
    private Button confirm_transaction;

    @FXML
    private Label message;

    @FXML
    private ComboBox<String> transaction_type;

    @FXML
    public void initialize() {
        try 
        {
            UIManager.populateTransactionTypes(transaction_type);
            
            amount_label.setVisible(false);
            amount.setVisible(false);
            message.setAlignment(Pos.CENTER);
            message.setTextFill(Color.RED);
            
            ResultSet allOwnedAssets = ownedAssetsManager.getAllOwnedAssetsNames(userId);
            UIManager.editAssetForm(assets_name, allOwnedAssets);

        } 
        catch (SQLException e) 
        {
            message.setText("Error loading assets");
        }
    }

    @FXML
    void addTransaction(ActionEvent event) {
        try 
        {
            String transaction_type_input = transaction_type.getValue();
            String asset_name_input = assets_name.getValue();
            double amount_input = 0;

            if (transaction_type_input == null || asset_name_input == null) 
            {
                message.setText("Please fill in all fields");
                return;
            }

            if (transaction_type_input.equals("Edit"))
            {
                if (amount.getText().trim().isEmpty()) 
                {
                    message.setText("Please fill in all fields");
                    return;
                }

                try 
                {
                    amount_input = Double.parseDouble(amount.getText());
                    if (amount_input < 0) 
                    {
                        message.setText("Please enter a valid positive number for amount.");
                        return;
                    }
                } 
                catch (NumberFormatException e) 
                {
                    return;
                }
            }
            
            assetHistoryManager.setOwnedAssetsManager(ownedAssetsManager);
            assetHistoryManager.addTransaction(userId, asset_name_input, "", amount_input);
            message.setTextFill(Color.GREEN);
            message.setText("Transaction completed successfully");
                        
            ResultSet allOwnedAssets = ownedAssetsManager.getAllOwnedAssetsNames(userId);
            UIManager.editAssetForm(assets_name, allOwnedAssets);
            amount.clear();
            displayOwnedAmount(event);
            
        } 
        catch (SQLException e) 
        {
            message.setTextFill(Color.RED);
            message.setText("Couldn't complete the transaction !");
        }
    }

    @FXML
    void add_remove_asset_action(ActionEvent event) {
        String transaction_type_input = transaction_type.getValue();
        
        if (transaction_type_input.equals("Edit"))
        {
            displayOwnedAmount(event);
            amount.setVisible(true);
            amount_label.setVisible(true);
        }
        else if (transaction_type_input.equals("Remove"))
        {
            amount.setVisible(false);
            amount_label.setVisible(false);
        }
    }

    @FXML
    void backBtnClicked(MouseEvent event) {
        uiManager.display();

    }

    @FXML
    void displayOwnedAmount(ActionEvent event) {
        String asset_name_input = assets_name.getValue();
        String transaction_type_input = transaction_type.getValue();

        if (asset_name_input != null && transaction_type_input != null)
        {
            try {
                int assetId = ownedAssetsManager.getAssetId(asset_name_input);
                double currentAmount = ownedAssetsManager.getAssetOwnedAmount(userId, assetId);

                amount.setPromptText("Cuurently owned amount: " + currentAmount);
            } catch (SQLException e) {
                
            }    
        }
    }

}
