package transactions;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import db.DatabaseManager;

public class AssetTransactionManager {

    @FXML
    private ComboBox<String> assets_name;

    @FXML
    private Button confirm_transaction;

    @FXML
    private ComboBox<String> transaction_type;

    @FXML
    private Label message;

    private Connection connection = DatabaseManager.connect();
    
    @FXML
    public void initialize() {
        transaction_type.getItems().addAll("Sell", "Buy");
    }

    @FXML
    void populate_assets(ActionEvent event) {
        String transaction_type_input = transaction_type.getValue();

        if (transaction_type_input.equals("Buy"))
        {
            String getAssetsQ = "SELECT name from assets;";
            Statement getAssets;
            try {
                getAssets = connection.createStatement();
                ResultSet allAssets = getAssets.executeQuery(getAssetsQ);

                ObservableList<String> items = FXCollections.observableArrayList();
                while (allAssets.next()) {
                    String item = allAssets.getString("name");
                    items.add(item);
                }

                assets_name.setItems(items);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to get all assets");
            }
        }
        else if (transaction_type_input.equals("Sell"))
        {
            try {

                String getUserAssetsQ = "SELECT name from assets WHERE assets.id IN " + 
                                    "(SELECT asset_id FROM owned_assets WHERE user_id = ?);";
                PreparedStatement getUserAssets = connection.prepareStatement(getUserAssetsQ);
                getUserAssets.setString(1, "1");

                ResultSet allUserAssets = getUserAssets.executeQuery();

                ObservableList<String> items = FXCollections.observableArrayList();
                while (allUserAssets.next()) {
                    String item = allUserAssets.getString("name");
                    items.add(item);
                }

                assets_name.setItems(items);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to get all assets");
            }
        }
    }

    
}
