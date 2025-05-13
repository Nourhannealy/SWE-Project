
import controller.UIManager;
import javafx.application.Application;
// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import controller.UIManager;

import javafx.application.Application;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {
            UIManager uiManager = new UIManager(primaryStage);
            uiManager.showMainView();
            uiManager.switchToEditRemove();

        } catch (Exception e) {
            System.err.println("Error: " + e);

        }
  
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
