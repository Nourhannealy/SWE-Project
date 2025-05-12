package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseManager;

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
 
    public void addTransaction(int userId, String assetName, String transactionType, double amount)
        throws SQLException
    {
        String addTransaction = "INSERT INTO assets_history (user_id, asset_id, transaction_type, amount)" + 
                                "VALUES (?, ?, ?, ?);";
        String assetIdQ = "SELECT id FROM assets WHERE name = ?;" ;

        PreparedStatement pstmnt1 = db_connection.prepareStatement(assetIdQ);
        PreparedStatement pstmnt2 = db_connection.prepareStatement(addTransaction);

        pstmnt1.setString(1, assetName);
        int assetId = Integer.parseInt(pstmnt1.executeQuery().getString("id"));

        pstmnt2.setInt(1, userId);
        pstmnt2.setString(2, assetName);
        pstmnt2.setString(3, transactionType);
        pstmnt2.setDouble(4, amount);

        pstmnt2.executeUpdate();

        if (transactionType.equals("Buy"))
        {
            OwnedsAssetsM.addAsset(userId, assetId, amount);
        }
        else if (transactionType.equals("Sell"))
        {
            if (amount == 0) OwnedsAssetsM.deleteAsset(userId, assetId);
            else OwnedsAssetsM.editAsset(userId, assetId, amount);
        }
    }

    public ResultSet getAllTransactions(int userId) 
        throws SQLException
    {
        String allTransactionsQ = "SELECT * FROM asset_history WHERE user_id = ?;";

        PreparedStatement pstmnt = db_connection.prepareStatement(allTransactionsQ);
        ResultSet allTransactions = pstmnt.executeQuery();

        return allTransactions;
    }
}
