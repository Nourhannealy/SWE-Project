package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Users.UserManager;
import db.DatabaseManager;
// import UserManager;

public class UserSignUp extends UserManager{
    
    private Connection db_connection;

    public UserSignUp() throws SQLException
    {
        this.db_connection = DatabaseManager.connect();
    }

    public boolean validateSignupCredentials(String username, String email, String password, String confirmPassword) 
        throws SQLException
    {
        String usernameExistsQ = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
        String emailExistsQ = "SELECT 1 FROM users WHERE email = ? LIMIT 1";

        boolean usernameExists = false;
        boolean emailExists = false;

        try (PreparedStatement usernameExistsPstat = db_connection.prepareStatement(usernameExistsQ)) {
                usernameExistsPstat.setString(1, username);
            try (ResultSet usernameExistsResult = usernameExistsPstat.executeQuery()) {
                usernameExists = usernameExistsResult.isBeforeFirst() && usernameExistsResult.next();
            }
        }

        try (PreparedStatement emailExistsPstat = db_connection.prepareStatement(emailExistsQ)) {
                emailExistsPstat.setString(1, email);
            try (ResultSet emailExistsResult = emailExistsPstat.executeQuery()) {
                emailExists = emailExistsResult.isBeforeFirst() && emailExistsResult.next();
            }
        }

        if (!isValidUsername(username))
            throw new SQLException("Username should be at least 4 characters long.");

        if (usernameExists)
            throw new SQLException("Username already exists");
        
        if (emailExists)
            throw new SQLException("Email already exists");

        if (!password.equals(confirmPassword))
            throw new SQLException("Passwords don't match");

        if (!isValidPassword(password))
            throw new SQLException("Password should be at least 8 characters long.");

        return isValidUsername(username) && isValidPassword(password)
            && !usernameExists && !emailExists && password.equals(confirmPassword);
     
    } 

    public void savaUserCredentials(String username, String email, String password)
        throws SQLException
    {
        String insertUser = "INSERT INTO users (username, email, password)" +
                "VALUES (?, ?, ?)";
        
        try (PreparedStatement preparedStatement = db_connection.prepareStatement(insertUser)) 
        {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
        }
    }
}
