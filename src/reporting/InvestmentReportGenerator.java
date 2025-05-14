package reporting;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class InvestmentReportGenerator extends BaseReportGenerator{

    @Override
    public String generateReport(ResultSet assets, double value) throws SQLException {
        StringBuilder report = new StringBuilder();

        assets.next();
        
        report.append("Investment Report");
        report.append("\n");
        report.append("==================");
        report.append("\n");
        report.append("Total Portfolio Value: ").append(value);
        report.append("\n==================\n");
        do {
            String name = assets.getString("name");
            String type = assets.getString("type");
            double price = assets.getDouble("price");
            double amount = assets.getDouble("amount");

            report.append("Asset: ").append(name).append("\n")
                    .append("Type: ").append(type).append("\n")
                    .append("Price: ").append(price).append("\n")
                    .append("Amount: ").append(amount).append("\n")
                    .append("Value: ").append(price * amount).append("\n")
                    .append("------------------------------\n");
        } while (assets.next());
        return report.toString();
    }
    
}
