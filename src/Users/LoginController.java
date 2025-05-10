package Users;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button Login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void submit(ActionEvent event) {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        String url = "jdbc:sqlite:resources/db/system.db"; // Adjust path if needed

        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, enteredUsername);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String correctPassword = rs.getString("password");

                if (enteredPassword.equals(correctPassword)) {
                    System.out.println("Login successful!");
                    // Optionally: proceed to the next scene or show a success message
                } else {
                    System.out.println("Incorrect password.");
                }
            } else {
                System.out.println("Username not found.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

}
