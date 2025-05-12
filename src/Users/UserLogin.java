package Users;

import java.sql.*;

import db.DatabaseManager;


public class UserLogin {

    private Connection connection;

    public UserLogin() throws SQLException 
    {
        this.connection = DatabaseManager.connect();
    }

    public boolean validataLoginCredentials(String username, String password) throws SQLException{
        String S_username= fetchUsernameFromDb(username);
        if(S_username  == null){
            System.out.println("Username not found");// for debging
            return false;
        }

        String S_Password = fetchPasswordFromDb(username);
        if(password.equals(S_Password)){
            System.out.println("Login successful");
            return true;
        }else{
            System.out.println("Password is incorrect");
            return false;
        }

    }
    

    private String fetchUsernameFromDb(String username) throws SQLException {
        String query = "SELECT username FROM users WHERE username = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        }
        return null;
    }

    private String fetchPasswordFromDb(String username) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ? LIMIT 1";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getString("password") : null;
            }
        }
    }
   
}
