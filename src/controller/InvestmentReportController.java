package controller;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import reporting.InvestmentReportGenerator;
import reporting.NetworthCalculator;

public class InvestmentReportController extends BaseController{

    @FXML
    private Button backBtn;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextArea reportTxt;

    @FXML
    private Button saveExcelBtn;

    @FXML
    private Button saveTxtBtn;

    private String report;
    private InvestmentReportGenerator generator;

    @FXML
    public void initialize()
    {
        try
        {
            NetworthCalculator nt = new NetworthCalculator();
            double worth = nt.calculateNetworth(ownedAssetsManager.getAllOwnedAssets(userId));
            this.generator = new InvestmentReportGenerator();
            this.report = generator.generateReport(ownedAssetsManager.getAllOwnedAssets(userId), worth);
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
        fileChooser.setInitialFileName("investment_report.csv");
        
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
        fileChooser.setInitialFileName("investment_report.txt");
        
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

