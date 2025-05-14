package reporting;

import model.AssetManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.util.Pair;
import java.util.List;

public class NetworthCalculator {
    
    public double calculateNetworth(ResultSet assets) throws SQLException
    {
        double worth = 0;
        while (assets.next())
        {
            worth += assets.getDouble("amount") * assets.getDouble("price");
        }

        return worth;
    }
    
}
