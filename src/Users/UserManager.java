package Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseManager;

public class UserManager {

    private Connection conn;

    public UserManager() throws SQLException
    {
        this.conn = DatabaseManager.connect();
    }

    protected boolean isValidUsername(String username)
    {
        if(username.length() < 4 || username == "")
        {
            return false;
        }
        return true;
    }

    protected boolean isValidPassword(String password)
    {
        if (password.length() < 8 || password == "")
        {
            return false;
        }
        return true;
    }

    public int getUserId(String username) throws SQLException
    {
        String sql = "SELECT id FROM users WHERE username = ? LIMIT 1";
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, username);
            try(ResultSet res = stmt.executeQuery())
            {
                return res.getInt("id");
            }
        }
    }
    
}
