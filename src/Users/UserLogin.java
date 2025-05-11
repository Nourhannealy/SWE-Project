package Users;

import java.sql.*;

import db.DatabaseManager;


public class UserLogin {



    private Connection connection = DatabaseManager.connect();

    public boolean validataLoginCredentials(String username, String password){
        String S_username= fetchUsernameFromDb(username);
        if(S_username  == null){
            System.out.println("Username not found");// for debging
            return false;
        }

        String S_Password=fetchPasswordFromDb(username);
        if(password.equals(S_Password)){
            System.out.println("Login successful");
            return true;
        }else{
            System.out.println("Password is incorrect");
            return false;
        }

    }
    

    private String fetchUsernameFromDb(String username) {
        String url = "jdbc:sqlite:resources/db/system.db";
        String query = "SELECT username FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return null;
    }

    private String fetchPasswordFromDb(String username) {
        String url = "jdbc:sqlite:resources/db/system.db";
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return null;
    }
   
}
