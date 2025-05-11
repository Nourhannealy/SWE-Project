package Reporting;

import db.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ReportGenerator {

    @FXML
    private Label reportArea;

    @FXML
    private Button save;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadUserAssets(); // generate report
    }

    private void loadUserAssets() {
        try {
            // Establish the connection to the database
            Connection conn = DatabaseManager.connect();

            if (conn == null) {
                reportArea.setText("Failed to connect to the database.");
                return;
            }

            System.out.println("Connected to the database successfully.");

            // Updated query to include username
            String query = "SELECT users.username, assets.name, assets.type, assets.price, owned_assets.amount " +
                           "FROM owned_assets " +
                           "JOIN assets ON owned_assets.asset_id = assets.id " +
                           "JOIN users ON owned_assets.user_id = users.id " +
                           "WHERE owned_assets.user_id = ?";

            PreparedStatement stmt = conn.prepareStatement(query);

            //YOU CAN TEST BY REPLACING userId WITH ANY USER_ID IN YOUR DATABASE
            stmt.setInt(1, 1);

            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                reportArea.setText("No assets found for the given user.");
                return;
            }

            StringBuilder report = new StringBuilder();
            double total = 0;

            rs.next(); 
            String username = rs.getString("username");

            report.append("Investment Report");
            report.append("\n");
            report.append("==================");
            report.append("\n");
            report.append(username).append("\n");
            report.append("==================\n");
            do {
                String name = rs.getString("name");
                String type = rs.getString("type");
                double price = rs.getDouble("price");
                int amount = rs.getInt("amount");

                double value = price * amount;
                total += value;

                report.append("Asset: ").append(name).append("\n")
                      .append("Type: ").append(type).append("\n")
                      .append("Price: ").append(price).append("\n")
                      .append("Amount: ").append(amount).append("\n")
                      .append("Value: ").append(value).append("\n")
                      .append("------------------------------\n");
            } while (rs.next());

            report.append("TOTAL VALUE: ").append(total);

            reportArea.setWrapText(true);
            reportArea.setText(report.toString());

        } catch (SQLException e) {
            reportArea.setText("Error loading report: " + e.getMessage());
        }
    }

    @FXML
    private void GeneratePDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        fileChooser.setInitialFileName("investment_report.txt");

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(reportArea.getText());
                System.out.println("Report saved to: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to save report: " + e.getMessage());
            }
        }
    }
}
