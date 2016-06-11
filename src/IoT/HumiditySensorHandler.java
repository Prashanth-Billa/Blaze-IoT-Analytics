package IoT;

import java.sql.SQLException;

public class HumiditySensorHandler {
    public static int generateGraph() {
        return 0;
    }

    private static String fileHumidityJson = "/home/srt/JSON/file_humidity.json";

    public static String findOverallEventRate() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS humidityTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE humidityTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileHumidityJson + "' INTO TABLE humidityTable");
            value = HiveQueryExecutor.executeQuery("");
            String[] tokens = value.split("<br/>");
            float num = 0;
            StringBuilder strbuilder = new StringBuilder();
            for(int i = 0; i < tokens.length; i++){
                strbuilder.append("<br/>");
                String[] val = tokens[i].split(" ");
                for(int j = 0; j < val.length; j++){
                    strbuilder.append(" ");
                    strbuilder.append(val[j]);
                }
            }
        } catch (SQLException e) {
            return e.getLocalizedMessage();
        }
        return value;
    }

    public static String findFrequencyOfSprinkleronEvent() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS humidityTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE humidityTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileHumidityJson + "' INTO TABLE humidityTable");
            value = HiveQueryExecutor.executeQuery("");
            String[] tokens = value.split("<br/>");
            float num = 0;
            StringBuilder strbuilder = new StringBuilder();
            for(int i = 0; i < tokens.length; i++){
                strbuilder.append("<br/>");
                String[] val = tokens[i].split(" ");
                for(int j = 0; j < val.length; j++){
                    strbuilder.append(" ");
                    strbuilder.append(val[j]);
                }
            }
        } catch (SQLException e) {
            return e.getLocalizedMessage();
        }
        return value;
    }

    public static String findNumberOfHighPriorityClassEvents() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS humidityTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE humidityTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileHumidityJson + "' INTO TABLE humidityTable");
            value = HiveQueryExecutor.executeQuery("");
            String[] tokens = value.split("<br/>");
            float num = 0;
            StringBuilder strbuilder = new StringBuilder();
            for(int i = 0; i < tokens.length; i++){
                strbuilder.append("<br/>");
                String[] val = tokens[i].split(" ");
                for(int j = 0; j < val.length; j++){
                    strbuilder.append(" ");
                    strbuilder.append(val[j]);
                }
            }
        } catch (SQLException e) {
            return e.getLocalizedMessage();
        }
        return value;
    }
}
