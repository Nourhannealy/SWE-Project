package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DatabaseManager;
import model.OwnedAssetsManager;

public class AssetHistoryManager extends DatabaseManager{
    
    private Connection db_connection;
    private OwnedAssetsManager OwnedsAssetsM;

    public AssetHistoryManager() throws SQLException
    {
        this.db_connection = connect();
    }

    public void setOwnedAssetsManager(OwnedAssetsManager OAM)
    {
        this.OwnedsAssetsM = OAM;
    }
 
    public void addTransaction(int userId, int assetId, String transactionType, double amount)
        throws SQLException
    {
        String addtransaction = "INSERT INTO assets_history (user_id, asset_id, transaction_type, amount)" + 
                                "VALUES (?, ?, ?, ?);";
        PreparedStatement pstmnt = db_connection.prepareStatement(addtransaction);
        pstmnt.setString(1, String.valueOf(userId));
        pstmnt.setString(1, String.valueOf(assetId));
        pstmnt.setString(1, transactionType);
        pstmnt.setString(1, String.valueOf(amount));

        pstmnt.executeUpdate();

        if (transactionType.equals("Buy"))
        {
            OwnedsAssetsM.addAsset(userId, assetId, amount);
        }

    }
}