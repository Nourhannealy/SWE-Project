import java.io.IOException;

import db.DatabaseManager;
import javafx.application.Application;
// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.URL;
// import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
            // This is just a basic program used while testing 
            root = FXMLLoader.load(getClass().getResource("/views/mainWindow.fxml"));
            DatabaseManager.connect();

            Scene scene = new Scene(root);
        
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();


            

        } catch (IOException e) {
            // System.out.println("OPS");
            // e.printStackTrace();
            System.out.println("FXML file location: " + getClass().getClassLoader().getResource("views/mainWindow.fxml"));

        }
  
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}