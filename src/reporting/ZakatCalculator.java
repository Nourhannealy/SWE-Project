package reporting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZakatCalculator {

    // Example: 2.5% zakat on total value
    public static double calculateZakat(ResultSet assets, double[] amount) throws SQLException
    {
        double assetsValue = 0;
        int i = 0;
        while (assets.next())
        {
            assetsValue  += assets.getDouble("price") * amount[i];
            i++;
        }

        return assetsValue * 0.025;
    }


    public static boolean generateReport(){
        return true;
    }
    
}