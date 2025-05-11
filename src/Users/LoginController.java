package Users;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void Login(ActionEvent event) {
        String entered_Username=username.getText();
        String entered_Password=password.getText();
        UserLogin login = new UserLogin();
        boolean successfullLogin =login.validataLoginCredentials(entered_Username, entered_Password);
        
        Alert alert;

        if(successfullLogin){
            alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Welcome, "+entered_Username+ "!");


        }
        else{
            alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
        }   

        alert.showAndWait();
    }

}

