import java.io.IOException;
import java.sql.Connection;

import db.DatabaseManager;
import javafx.application.Application;
// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
            // This is just a basic program used while testing 
            System.out.println(getClass().getResource("/views/transactions.fxml"));
            root = FXMLLoader.load(getClass().getResource("/views/transactions.fxml"));

            Scene scene = new Scene(root);
        
            primaryStage.setTitle("Signup");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println("OPS");
        }
  
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
