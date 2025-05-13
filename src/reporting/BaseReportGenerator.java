package reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class BaseReportGenerator 
{
    
    public abstract String generateReport(ResultSet assets, double value) throws SQLException;

    public void generateTxtReport(String content, File file) throws IOException {
            if (!file.getName().endsWith(".txt")) {
                throw new IllegalArgumentException("File must have .txt extension");
            }
            
            // Create parent directories if they don't exist
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            
            // Write content with proper resource handling
            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
                writer.write(content);
            }
    }

    public void generateCSVReport(ResultSet rs, File file) throws IOException, SQLException
    {
        if (!file.getName().endsWith(".csv")) 
        {
            throw new IllegalArgumentException("File must have .csv extension");
        }
        
        // Create parent directories if they don't exist
        File parent = file.getParentFile();
        if (parent != null)
        {
            parent.mkdirs();
        }
        
        // Write CSV header
        try (FileWriter writer = new FileWriter(file))
        {
            writer.write("Asset Name,Type,Price,Amount,Value\n");

            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                double price = rs.getDouble("price");
                double amount = rs.getDouble("amount");
                double value = price * amount;

                // CSV line
                writer.write(String.format("%s,%s,%.2f,%.2f,%.2f\n",
                        name, type, price, amount, value));
            }
        
        }
    }

}

// import javafx.stage.FileChooser;
// import javafx.stage.Window;
// import java.io.File;

// public class ReportExporterUI {
//     private final FileService fileService;
    
//     public ReportExporterUI(FileService fileService) {
//         this.fileService = fileService;
//     }
    
//     /**
//      * Shows save dialog and triggers file save
//      * @param reportContent The text content to save
//      * @param ownerWindow Parent window for the dialog
//      */
//     public void exportReport(String reportContent, Window ownerWindow) {
//         File file = showSaveDialog(ownerWindow);
//         if (file != null) {
//             try {
//                 fileService.saveTextToFile(reportContent, enforceTxtExtension(file));
//                 showSuccessMessage(file);
//             } catch (IOException e) {
//                 showErrorMessage("Failed to save file", e);
//             }
//         }
//     }
    
//     private File showSaveDialog(Window ownerWindow) {
//         FileChooser fileChooser = new FileChooser();
//         fileChooser.setTitle("Save Report");
//         fileChooser.getExtensionFilters().add(
//             new FileChooser.ExtensionFilter("Text Files", "*.txt")
//         );
//         fileChooser.setInitialFileName("report_" + System.currentTimeMillis() + ".txt");
//         return fileChooser.showSaveDialog(ownerWindow);
//     }
    
//     private File enforceTxtExtension(File file) {
//         String path = file.getAbsolutePath();
//         return path.endsWith(".txt") ? file : new File(path + ".txt");
//     }
    
//     private void showSuccessMessage(File file) {
//         // Show JavaFX alert or status message
//     }
    
//     private void showErrorMessage(String message, Exception e) {
//         // Show JavaFX error dialog
//     }
// }