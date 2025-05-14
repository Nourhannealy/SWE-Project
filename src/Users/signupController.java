package Users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import controller.BaseController;
import model.UserSignUp;


public class signupController extends BaseController {

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
    
    @FXML
    void signup(ActionEvent event)
    {
        try
        {
            String usernameInput = username.getText();
            String emailInput = email.getText();
            String passwordInput = password.getText();
            String confirmPasswordInput = confirm_password.getText();
            UserSignUp signupModel = new UserSignUp();
            message.setStyle("-fx-text-fill: red;"); // Default color
            message.setAlignment(Pos.CENTER);

            System.out.println(usernameInput);
            System.out.println(emailInput);
            System.out.println(passwordInput);
            System.out.println(confirmPasswordInput);

            if (signupModel.validateSignupCredentials(usernameInput, emailInput, passwordInput, confirmPasswordInput))
            {
                signupModel.savaUserCredentials(usernameInput, emailInput, passwordInput);
                message.setStyle("-fx-text-fill: green;");
                message.setText("Signed up successfully");
                System.out.println("Validation done !!");
                uiManager.setUsername(usernameInput);
                uiManager.display();
            }
            else
            {
                message.setText("Signup failed: username or email may already be in use, or passwords do not match.");
            }
        } catch (Exception e) {
            errorMessage("Database Error", "Couldn't connect/edit the db", e);
        }

    }
}
