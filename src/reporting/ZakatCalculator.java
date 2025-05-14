package reporting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZakatCalculator {

    // Example: 2.5% zakat on total value
    public double calculateZakat(ResultSet assets) throws SQLException
    {
        double assetsValue = 0;
        while (assets.next())
        {
            assetsValue += assets.getDouble("amount") * assets.getDouble("price");
        }
        return assetsValue * 0.025;
    }


    public static boolean generateReport(){
        return true;
    }
    
}