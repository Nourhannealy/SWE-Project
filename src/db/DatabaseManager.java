package db;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager 
{
    private static String db = "jdbc:sqlite:resources/db/system.db";
    private static Connection connection;

    public static Connection connect()
    {
        try 
        {
            connection = DriverManager.getConnection(db);
            
            if (connection != null) {
                // Create a statement object
                Statement statement = connection.createStatement();

                // SQL to create a table if it doesn't exist
                String createTableUsers = "CREATE TABLE IF NOT EXISTS users (" +
                                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "username TEXT NOT NULL, " +
                                            "email TEXT NOT NULL, " +
                                            "password TEXT NOT NULL); ";

                String createTableAssets = "CREATE TABLE IF NOT EXISTS assets (" +
                                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "name TEXT NOT NULL, " +
                                            "type TEXT NOT NULL, " + 
                                            "price FLOAT NOT NULL); ";
                                            
                String createTableOwnedAssets = "CREATE TABLE IF NOT EXISTS owned_assets (" +
                                                "user_id INTEGER, " +
                                                "asset_id INTEGER, " + 
                                                "amount INT NOT NULL, " +
                                                "FOREIGN KEY (user_id) REFERENCES users(id), " + 
                                                "FOREIGN KEY (asset_id) REFERENCES assets(id));";


                statement.executeUpdate(createTableAssets);
                statement.executeUpdate(createTableUsers);
                statement.executeUpdate(createTableOwnedAssets);

                statement.close();
                    
                System.out.println("CREATED !!!");
            }

            return connection;

        }
        catch (SQLException e)
        {
            System.err.println("Error: " + e.getMessage());

            return null;
        }
    }

    public static void disconnect() {
        try 
        {
            if (connection != null && !connection.isClosed()) 
            {
                connection.close();
                connection = null;
                System.out.println("Disconnected !");
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String query) throws SQLException {

        if (connection == null || connection.isClosed()) 
        {
            throw new SQLException("No database connection");
        }

        // Create statement and execute query
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
