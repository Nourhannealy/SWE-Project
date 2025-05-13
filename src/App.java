import controller.UIManager;
import javafx.application.Application;

import controller.UIManager;

import javafx.application.Application;
import javafx.stage.Stage;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {
            UIManager uiManager = new UIManager(primaryStage);
            uiManager.showMainView();

        } catch (Exception e) {
            System.err.println("Error: " + e);

        }
  
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
