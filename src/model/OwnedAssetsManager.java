package model;

import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnedAssetsManager extends DatabaseManager {

    private Connection db_connection;

    public OwnedAssetsManager() throws SQLException
    {
        this.db_connection = connect();
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
        String sql = "INSERT INTO owned_assets (user_id, asset_id, amount) VALUES (?,?,?);";

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

    public void editAsset(int userId, int assetId, double amount) throws SQLException
    {
        String sql_Update = "UPDATE owned_assets SET amount = ? WHERE user_id = ? AND asset_id = ?";

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
        String sql = "SELECT * FROM owned_assets WHERE user_id = ?";
        PreparedStatement pstmt = db_connection.prepareStatement(sql);
        
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

        
}
    
