package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseManager;

public class AssetHistoryManager{
    
    private Connection db_connection;
    private OwnedAssetsManager ownedAssetsM;

    public AssetHistoryManager()
    {
        try {
            this.db_connection = DatabaseManager.connect();
        } catch (SQLException e) {
            System.out.println("Couldn't open database");
        }
    }

    public void setOwnedAssetsManager(OwnedAssetsManager OAM)
    {
        System.out.println("setOwnedAssetsManager");
        this.ownedAssetsM = OAM;
    }
    
 
    public void addTransaction(int userId, String assetName, String transactionType, double amount)
        throws SQLException
    {
        String assetIdQ = "SELECT id FROM assets WHERE name = ?;" ;

        PreparedStatement pstmnt1 = db_connection.prepareStatement(assetIdQ);
        pstmnt1.setString(1, assetName);
        ResultSet rs = pstmnt1.executeQuery();
        
        int assetId = rs.getInt("id");
        rs.close();
        pstmnt1.close();

        String checkAmountQ = "SELECT amount FROM owned_assets WHERE user_id = ? AND asset_id = ?";
        PreparedStatement checkStmt = db_connection.prepareStatement(checkAmountQ);
        checkStmt.setInt(1, userId);
        checkStmt.setInt(2, assetId);
        ResultSet checkRs = checkStmt.executeQuery();
        
        checkRs.next(); 
        double currentAmount = checkRs.getDouble("amount");
        checkRs.close();
        checkStmt.close();

        if (amount == 0) // Remove
        {
            transactionType = "SELL";
            ownedAssetsM.deleteAsset(userId, assetId);
        }
        else if (amount == currentAmount)
        {
            throw new SQLException("This is your current amount already!");
        }
        else if (currentAmount < amount) // Buy
        {
            transactionType = "BUY";
            ownedAssetsM.editAsset(userId, assetId, amount);
        }
        else if (currentAmount > amount)
        {
            transactionType = "SELL";
            ownedAssetsM.editAsset(userId, assetId, amount);
        }


        // Add the transaction history
        String addTransaction = "INSERT INTO assets_history (user_id, asset_id, transaction_type, amount)" + 
                                "VALUES (?, ?, ?, ?);";
        PreparedStatement pstmnt2 = db_connection.prepareStatement(addTransaction);
        pstmnt2.setInt(1, userId);
        pstmnt2.setInt(2, assetId);
        pstmnt2.setString(3, transactionType);
        pstmnt2.setDouble(4, amount);

        pstmnt2.executeUpdate();
        pstmnt2.close();

    }

    public ResultSet getAllTransactions(int userId) 
        throws SQLException
    {
        String allTransactionsQ = "SELECT * FROM asset_history WHERE user_id = ?";

        PreparedStatement pstmnt = db_connection.prepareStatement(allTransactionsQ);
        ResultSet allTransactions = pstmnt.executeQuery();

        return allTransactions;
    }
}
