package Users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseManager;
// import UserManager;


public class signup {

    @FXML
    private PasswordField confirm_password;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup;

    @FXML
    private TextField username;

    @FXML
    private Label message;

    private Connection connection = DatabaseManager.connect();
    
    @FXML
    boolean validateSignupCredentials(ActionEvent event) {
        String usernameInput = username.getText();
        String emailInput = email.getText();
        String passwordInput = password.getText();
        String confirmPasswordInput = confirm_password.getText();
        message.setStyle("-fx-text-fill: red;"); // Default color
        message.setAlignment(Pos.CENTER);

        if (connection != null)
        {
            String usernameExistsQ = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
            String emailExistsQ = "SELECT 1 FROM users WHERE email = ? LIMIT 1";

            try {
                PreparedStatement usernameExistsPstat = connection.prepareStatement(usernameExistsQ);
                usernameExistsPstat.setString(1, usernameInput);
                ResultSet usernameExistsResult = usernameExistsPstat.executeQuery();

                PreparedStatement emailExistsPstat = connection.prepareStatement(emailExistsQ);
                emailExistsPstat.setString(1, emailInput);
                ResultSet emailExistsResult = emailExistsPstat.executeQuery();

                boolean usernameExists = usernameExistsResult.isBeforeFirst() && usernameExistsResult.next();
                boolean emailExists = emailExistsResult.isBeforeFirst() && emailExistsResult.next();

                if (/*isValidUsername(usernameInput) && isValidPassword(passwordInput)
                    &&*/ !usernameExists && !emailExists && passwordInput.equals(confirmPasswordInput))
                {
                    savaUserCredentials(usernameInput, emailInput, passwordInput);
                    message.setStyle("-fx-text-fill: green;");
                    message.setText("Signed up successfully");

                    return true;
                }
    

                if (usernameExists) message.setText("Username already exists !");
                else if (emailExists) message.setText("Email already exists !");
                else if (!passwordInput.equals(confirmPasswordInput)) message.setText("Passwords don't match !");

                // Closed open resources
                DatabaseManager.disconnect();
                usernameExistsPstat.close();
                emailExistsPstat.close();
                usernameExistsResult.close();
                emailExistsResult.close();

                return false;
                
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("User signup failed");
                return false;
            }

        }
        
        return false;
    }

    void savaUserCredentials(String username, String email, String password)
    {
        String insertUser = "INSERT INTO users (username, email, password)" +
                "VALUES (?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertUser);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can't save user credentials :(");
        }
    }
}
