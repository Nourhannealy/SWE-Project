package Reporting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ReportController extends ReportGenerator {
    private int userId; // assume this is passed after login

    @FXML
    private Button ZakatReport;

    @FXML
    private Button financialReport;

    @FXML
    void ZakatReport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Zakatreport.fxml"));
            Parent root = loader.load();

            ReportGenerator controller = loader.getController();
            controller.setUserId(userId);

            Stage stage = new Stage();
            stage.setTitle("User Financial Report");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void financialReport(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/financialreport.fxml"));
            Parent root = loader.load();

            ReportGenerator controller = loader.getController();
            controller.setUserId(userId);

            Stage stage = new Stage();
            stage.setTitle("User Financial Report");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}