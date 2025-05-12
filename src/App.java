import java.io.IOException;
import java.sql.Connection;

import controller.UIManager;
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

        UIManager uibla = new UIManager(primaryStage);
        uibla.showMainView();
        uibla.showTransactionsView();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
