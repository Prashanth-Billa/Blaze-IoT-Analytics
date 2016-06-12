package IoT;

import java.sql.SQLException;

public class TrafficSensorHandler {
    private static String fileTrafficJson = "/home/srt/JSON/file_traffic.json";
    public static String findMostCongestedCities() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS traffic");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE traffic (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileTrafficJson + "' INTO TABLE traffic");
            value = HiveQueryExecutor.executeQuery("SELECT\n" +
                    "  get_json_object(traffic.json, \"$.location.city\"),\n" +
                    "  COUNT (get_json_object (traffic.json, \"$.type\")) as col\n" +
                    "FROM traffic\n" +
                    "WHERE get_json_object(traffic.json, \"$.type\") = 2\n" +
                    "GROUP BY get_json_object(traffic.json, \"$.location.city\")\n" +
                    "ORDER BY col DESC\n" +
                    "LIMIT 10");
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

    public static String findCongestionSpreadInDayAcrossCities() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS traffic");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE traffic (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileTrafficJson + "' INTO TABLE traffic");
            value = HiveQueryExecutor.executeQuery("SELECT COUNT(get_json_object(traffic.json, \"$.type\")) as c, hour(get_json_object(traffic.json, \"$.time\")) \n" +
                    "FROM traffic\n" +
                    "WHERE get_json_object(traffic.json, \"$.type\")=2\n" +
                    "GROUP BY hour(get_json_object(traffic.json, \"$.time\"))\n" +
                    "ORDER BY c");
            String[] tokens = value.split("<br/>");
            float num = 0;
            StringBuilder strbuilder = new StringBuilder();
            strbuilder.append("<br/>");
            strbuilder.append("Hour (Sorted by highest to least)");
            strbuilder.append("<br/>");
            for(int i = 0; i < tokens.length; i++){
                strbuilder.append("<br/>");
                String[] val = tokens[i].split(" ");
                strbuilder.append(val[1]);
            }
        } catch (SQLException e) {
            return e.getLocalizedMessage();
        }
        return value;
    }

    public static String findAccidentSpreadInDayAcrossCities() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS traffic");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE traffic (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileTrafficJson + "' INTO TABLE traffic");
            value = HiveQueryExecutor.executeQuery("SELECT COUNT(get_json_object(traffic.json, \"$.type\")) as c, hour(get_json_object(traffic.json, \"$.time\")) \n" +
                    "FROM traffic\n" +
                    "WHERE get_json_object(traffic.json, \"$.type\")=1\n" +
                    "GROUP BY hour(get_json_object(traffic.json, \"$.time\"))\n" +
                    "ORDER BY c");
            String[] tokens = value.split("<br/>");
            float num = 0;
            StringBuilder strbuilder = new StringBuilder();
            strbuilder.append("<br/>");
            strbuilder.append("Hour (Sorted by highest to least)");
            strbuilder.append("<br/>");
            for(int i = 0; i < tokens.length; i++){
                strbuilder.append("<br/>");
                String[] val = tokens[i].split(" ");
                strbuilder.append(val[1]);
            }
        } catch (SQLException e) {
            return e.getLocalizedMessage();
        }
        return value;
    }

    public static String findPeakCongestionTime() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS trafficTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE trafficTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileTrafficJson + "' INTO TABLE trafficTable");
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
