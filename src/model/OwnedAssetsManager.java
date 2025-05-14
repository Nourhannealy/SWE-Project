package model;

import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnedAssetsManager {

    private Connection db_connection;

    public OwnedAssetsManager() throws SQLException
    {
        this.db_connection = DatabaseManager.connect();
    }

    public boolean isAssetOwned(int userId, int assetId) throws SQLException
    {
        String sql = "SELECT 1 FROM owned_assets WHERE user_id = ? AND asset_id = ?";

        try (PreparedStatement pstmt = db_connection.prepareStatement(sql)) 
        {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, assetId);
        
            try (ResultSet res = pstmt.executeQuery()) 
            {
                return res.next();
            }
        }
    }

    public void addAsset(int userId, int assetId, double amount) throws SQLException
    {
        String sql = "INSERT INTO owned_assets (user_id, asset_id, amount) VALUES (?,?,?)";

        try (PreparedStatement pstmt = db_connection.prepareStatement(sql)) 
        {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, assetId);
            pstmt.setDouble(3, amount);
        
            int tmp = pstmt.executeUpdate();
            if (tmp == 0)
            {
                throw new SQLException("Nothing was added");
            }
        }

    }

    // public void main(String[] args)
    // {
    //     try{
    //         addAsset(1, 1, 5);
    //         System.out.println("Success");
    //     }catch (Exception e){
    //         System.err.println("Couldn't add asset");
    //         System.err.println(e.getMessage());
    //     }
    // }

    public void editAsset(int userId, int assetId, double amount) throws SQLException
    {
        String sql_Update = "UPDATE owned_assets SET amount = ? WHERE user_id = ? AND asset_id = ?";

        String sql_check = "SELECT amount FROM owned_assets WHERE user_id = ? AND asset_id = ? ";
        try (PreparedStatement stmt = db_connection.prepareStatement(sql_check))
        {
            stmt.setInt(1, userId);
            stmt.setInt(2, assetId);
            try(ResultSet res = stmt.executeQuery())
            {
                if (res.next())
                {
                    if (res.getDouble("amount") == amount)
                    {
                        throw new SQLException("This is your current owned amount");
                    }
                }
            }
        }
        
        try (PreparedStatement pstmt = db_connection.prepareStatement(sql_Update)) 
        {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, assetId);
            
            int tmp = pstmt.executeUpdate();
            if (tmp == 0)
            {
                throw new SQLException("Update failed");
            }
        }

    }

    // Resultset and PreparedStatement need to be closed by caller
    public ResultSet getAllOwnedAssets(int userId) throws SQLException
    {
        // String sql = "SELECT * FROM owned_assets WHERE user_id = ? ";
        String sql_stmt = "SELECT assets.name, assets.type, owned_assets.amount, assets.price " +
             "FROM assets " +
             "JOIN owned_assets ON owned_assets.asset_id = assets.id " +
             "WHERE owned_assets.user_id = ?";
        PreparedStatement pstmt = db_connection.prepareStatement(sql_stmt);
        
        pstmt.setInt(1, userId);
        return pstmt.executeQuery();
        
    }

    public ResultSet getAllOwnedAssetsNames(int userId) throws SQLException
    {
        String sql = "SELECT name FROM assets WHERE assets.id IN (SELECT asset_id FROM owned_assets WHERE user_id = ?);";

        PreparedStatement pstmt = db_connection.prepareStatement(sql);
        
        pstmt.setInt(1, userId);
        return pstmt.executeQuery();
        
    }

    public void deleteAsset(int userId, int assetId) throws SQLException
    {
        String sql_Update = "DELETE FROM owned_assets WHERE user_id = ? AND asset_id = ?";

        try (PreparedStatement pstmt = db_connection.prepareStatement(sql_Update)) 
        {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, assetId);
        
            int tmp = pstmt.executeUpdate();
            if (tmp == 0)
            {
                throw new SQLException("Nothing was deleted");
            }
        }

    }  
    
    public int getAssetId(String assetName)
        throws SQLException
    {
        String assetIdQ = "SELECT id FROM assets WHERE name = ?;" ;

        PreparedStatement pstmnt1 = db_connection.prepareStatement(assetIdQ);
        pstmnt1.setString(1, assetName);
        ResultSet rs = pstmnt1.executeQuery();
        
        int assetId = rs.getInt("id");
        rs.close();
        pstmnt1.close();

        return assetId;
    }

    public double getAssetOwnedAmount(int userId, int assetId) 
        throws SQLException
    {
        String checkAmountQ = "SELECT amount FROM owned_assets WHERE user_id = ? AND asset_id = ?";
        PreparedStatement checkStmt = db_connection.prepareStatement(checkAmountQ);
        checkStmt.setInt(1, userId);
        checkStmt.setInt(2, assetId);
        ResultSet checkRs = checkStmt.executeQuery();
        
        checkRs.next(); 
        double currentAmount = checkRs.getDouble("amount");
        checkRs.close();
        checkStmt.close();

        return currentAmount;
    }
}
    
