
import controller.UIManager;
import javafx.application.Application;
// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {
            // This is just a basic program used while testing 
            // root = FXMLLoader.load(getClass().getResource("/views/mainWindow.fxml"));
            // DatabaseManager.connect();

            // Scene scene = new Scene(root);
        
            // primaryStage.setTitle("Hello World!");
            // primaryStage.setScene(scene);
            // primaryStage.show();
            UIManager uiManager = new UIManager(primaryStage);
            uiManager.showMainView();

        } catch (Exception e) {
            // System.out.println("OPS");
            // e.printStackTrace();
            // System.out.println("FXML file location: " + getClass().getClassLoader().getResource("views/mainWindow.fxml"));
            System.err.println("Error: " + e);

        }
  
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}