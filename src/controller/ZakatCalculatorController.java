package controller;

import java.io.File;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import reporting.ZakatCalculator;
import reporting.ZakatReportGenerator;

public class ZakatCalculatorController extends BaseController {

    @FXML
    private Button backBtn;

    @FXML
    private Label reportLabel;

    @FXML
    private Button saveExcelBtn;

    @FXML
    private Button saveTxtBtn;

    @FXML
    private TextArea reportTxt;

    @FXML
    private AnchorPane mainAnchor;

   private String report;
    private ZakatReportGenerator generator;

    @FXML
    public void initalize()
    {
        try
        {
            ZakatCalculator zakat = new ZakatCalculator();
            ResultSet assets = ownedAssetsManager.getAllOwnedAssets(userId);
            double worth = zakat.calculateZakat(assets);
            this.generator = new ZakatReportGenerator();
            this.report = generator.generateReport(assets, worth);
            reportTxt.setText(report);
            reportTxt.setEditable(false);
            reportTxt.setWrapText(true);

        } catch (Exception e) {
            errorMessage("Data Error", "You have not data in the database", e);
            reportTxt.setText("No data to show");
        }
        
    }
    @FXML
    void backBtnClicked(MouseEvent event) {
        uiManager.display();

    }

    @FXML
    void saveExcelBtnClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
    
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Set default file name
        fileChooser.setInitialFileName("zakat_report.csv");
        
        // Show save file dialog
        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null)
        {
            try
            {
                generator.generateCSVReport(ownedAssetsManager.getAllOwnedAssets(userId), file);
            } catch (Exception e) {
                errorMessage("File Error", "Error generating and opening file", e);
            }
        }
    }

    @FXML
    void saveTxtBtnClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
    
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Set default file name
        fileChooser.setInitialFileName("zakat_report.txt");
        
        // Show save file dialog
        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null)
        {
            try
            {
                generator.generateTxtReport(report, file);
            } catch (Exception e) {
                errorMessage("File Error", "Error generating and opening file", e);
            }
        }


    }

}
