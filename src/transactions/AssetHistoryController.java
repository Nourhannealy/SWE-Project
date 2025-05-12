package transactions;


import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.AssetHistoryManager;
import model.AssetManager;
import model.OwnedAssetsManager;
import controller.UIManager;


public class AssetHistoryController {

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

    private AssetManager assetManager;
    private OwnedAssetsManager ownedAssetsManager;
    private AssetHistoryManager assetHistoryManager;

    @FXML
    public void initialize() {
        UIManager.populateTransactionTypes(transaction_type);
    }

    public void setAssetManager(AssetManager asset_manager)
    {
        this.assetManager = asset_manager;
    }

    public void setOwnedAssetManager(OwnedAssetsManager owned_assets_manager)
    {
        this.ownedAssetsManager = owned_assets_manager;
    }

    public void setAssetHistoryManager(AssetHistoryManager asset_history_manager)
    {
        this.assetHistoryManager = asset_history_manager;
    }

    @FXML
    void addTransaction(ActionEvent event) throws SQLException{
        String transaction_type_input = transaction_type.getValue();
        String asset_name_input = assets_name.getValue();
        double amount_input = Double.parseDouble(amount.getText());

        assetHistoryManager.setOwnedAssetsManager(ownedAssetsManager);
        assetHistoryManager.addTransaction(1, asset_name_input, transaction_type_input, amount_input);
    }

    @FXML
    void populate_assets(ActionEvent event) throws SQLException {
        String transaction_type_input = transaction_type.getValue();

        if (transaction_type_input.equals("Buy"))
        {
            System.out.println("fetching all Assets....");
            ResultSet allAssets = assetManager.fetchAllAssetNames();
            System.out.println("All assets fetched OK");
            UIManager.addAssetForm(assets_name, allAssets);
            System.out.println("All assets has been loaded");
        }
        else if (transaction_type_input.equals("Sell"))
        {
            System.out.println("fetching all Assets....");
            ResultSet allOwnedAssets = ownedAssetsManager.getAllOwnedAssetsNames(1);
            System.out.println("All assets fetched OK");
            UIManager.editAssetForm(assets_name, allOwnedAssets);
            System.out.println("All assets has been loaded");
        }
    }

}

