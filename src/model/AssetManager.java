package model;

import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    
}
