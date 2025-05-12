package model;

import db.DatabaseManager;

import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class AssetManager extends DatabaseManager {

    private Connection db_connection;

    public AssetManager() throws SQLException
    {
        this.db_connection = connect();
    }

    // Caller must close pstmt and resultset
    public ResultSet fetchAsset(int AssetId) throws SQLException
    {
        String sql = "SELECT * FROM asset WHERE id = ?";
        PreparedStatement stmt = db_connection.prepareStatement(sql);
        stmt.setInt(1, AssetId);
        return stmt.executeQuery();
        
    }

    public ResultSet fetchAssets(int[] assetId) throws SQLException
    {
        String placeholders = String.join(",", Collections.nCopies(assetId.length, "?"));
        String sql = "SELECT * FROM assets WHERE id IN (" + placeholders + ")";
        
        // Create a prepared statement
        PreparedStatement statement = db_connection.prepareStatement(sql);
        
        // Set each parameter value
        for (int i = 0; i < assetId.length; i++) {
            statement.setInt(i + 1, assetId[i]);
        }
        
        // Execute query and return ResultSet
        return statement.executeQuery();
    }
    
}
