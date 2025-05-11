package model;

import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AssetManager extends DatabaseManager {

    private Connection db_connection;

    public AssetManager()
    {
        this.db_connection = connect();
        if (db_connection == null)
        {
            //  TODO: Print error message in gui
            System.out.println("Couldn't establish connection");
        }
    }

    // Caller must close pstmt and resultset
    public ResultSet fetchAsset(int AssetId)
    {
        try
        {
            String sql = "SELECT * FROM asset WHERE id = ?";
            PreparedStatement stmt = db_connection.prepareStatement(sql);
            stmt.setInt(1, AssetId);
            return stmt.executeQuery();
            
        }
        catch (Exception e)
        {
            System.out.println("Asset doesn't exist");
            return null;
        }
    }
    
}
