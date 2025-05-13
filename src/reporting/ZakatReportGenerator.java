package reporting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZakatReportGenerator extends BaseReportGenerator
{
    @Override
    public String generateReport(ResultSet assets, double value) throws SQLException {
        StringBuilder report = new StringBuilder();

        assets.next(); 

        report.append("Investment Report");
        report.append("\n");
        report.append("==================");
        report.append("\n");
        report.append("Total Zakat Amount: ").append(value);
        report.append("==================\n");
        do {
            String name = assets.getString("name");
            String type = assets.getString("type");
            double price = assets.getDouble("price");
            double amount = assets.getDouble("amount");
            double ttl = price * amount;

            report.append("Asset: ").append(name).append("\n")
                    .append("Type: ").append(type).append("\n")
                    .append("Price: ").append(price).append("\n")
                    .append("Amount: ").append(amount).append("\n")
                    .append("Value: ").append(ttl).append("\n")
                    .append("Zakat: ").append(ttl * 0.025)
                    .append("------------------------------\n");
        } while (assets.next());
        return report.toString();
    }

    @Override
    public void generateCSVReport(ResultSet rs, File file) throws SQLException, IOException
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
            writer.write("Asset Name,Type,Price,Amount,Value,Zakat\n");

            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                double price = rs.getDouble("price");
                int amount = rs.getInt("amount");
                double value = price * amount;
                double zakat = value * 0.025;
                // CSV line
                writer.write(String.format("%s,%s,%.2f,%d,%.2f,%.2f\n",
                        name, type, price, amount, value, zakat));
            }
        
        }
    }
}