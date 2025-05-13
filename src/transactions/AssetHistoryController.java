package transactions;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import controller.UIManager;
import controller.Controller;

public class AssetHistoryController extends Controller {

    @FXML
    private TextField amount;

    @FXML
    private ComboBox<String> assets_name;

    @FXML
    private Button confirm_transaction;

    @FXML
    private Label message;

    @FXML
    private ComboBox<String> transaction_type;

    @FXML
    private Label amount_label;

    @FXML
    public void initialize() {
        try 
        {
            UIManager.populateTransactionTypes(transaction_type);
            
            amount_label.setVisible(false);
            amount.setVisible(false);
            message.setAlignment(Pos.CENTER);
            
            ResultSet allOwnedAssets = ownedAssetsManager.getAllOwnedAssetsNames(1);
            UIManager.editAssetForm(assets_name, allOwnedAssets);

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            message.setText("Error loading assets: " + e.getMessage());
        }
    }


    @FXML
    void addTransaction(ActionEvent event) {
        try 
        {
            String transaction_type_input = transaction_type.getValue();
            String asset_name_input = assets_name.getValue();
            
            if (transaction_type_input == null || asset_name_input == null || amount.getText().trim().isEmpty()) 
            {
                message.setText("Please fill in all fields");
                return;
            }

            double amount_input;
            try 
            {
                amount_input = Double.parseDouble(amount.getText());
                if (amount_input < 0) 
                {
                    message.setText("Amount cannot be negative");
                    return;
                }
            } 
            catch (NumberFormatException e) 
            {
                message.setText("Please enter a valid number for amount");
                return;
            }

            assetHistoryManager.setOwnedAssetsManager(ownedAssetsManager);
            assetHistoryManager.addTransaction(1, asset_name_input, transaction_type_input, amount_input);
            message.setText("Transaction completed successfully");
                        
            
        } 
        catch (SQLException e) 
        {
            message.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void add_remove_asset_action(ActionEvent event) 
    {
        String transaction_type_input = transaction_type.getValue();
        String asset_name_input = assets_name.getValue();

        if (transaction_type_input.equals("Edit"))
        {
            amount.setVisible(true);
            amount_label.setVisible(true);
        }
        else if (transaction_type_input.equals("Remove"))
        {
            amount.setVisible(false);
            amount_label.setVisible(false);

            try {
                assetHistoryManager.setOwnedAssetsManager(ownedAssetsManager);
                assetHistoryManager.addTransaction(1, asset_name_input, "", 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void edit_action(ActionEvent event) 
    {
        String asset_name_input = assets_name.getValue();
        Double amount_input = Double.parseDouble(amount.getText());
        if (amount_input < 0) 
        {
            message.setText("Amount cannot be negative");
            return;
        }

        try {
            assetHistoryManager.addTransaction(1, asset_name_input, "", amount_input);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

