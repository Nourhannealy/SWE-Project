package reporting;

import model.AssetManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.util.Pair;
import java.util.List;

public class NetworthCalculator {
    
    public double calculateNetworth(List<Pair<Double, Double>> assets) throws SQLException
    {
        double worth = 0;
        for (Pair<Double, Double> asset : assets)
        {
            worth += asset.getKey() + asset.getValue();
        }

        return worth;
    }
    
}
