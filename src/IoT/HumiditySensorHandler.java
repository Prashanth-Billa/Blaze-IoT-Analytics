package IoT;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class HumiditySensorHandler {
    public static int generateGraph() {
        return 0;
    }

    private static String fileHumidityJson = "/home/srt/JSON/file_humidity.json";

    public static String findOverallEventRate() {
        String value = "";
        boolean failed = false;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("/home/hadoop/IdeaProjects/Blaze-IoT-Analytics/web/content/CSV/humidityEventRate.csv");
            fileWriter.append("date,value");
            fileWriter.append("\n");
            try {
                String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS humidity");
                String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE humidity (json STRING)");
                String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileHumidityJson + "' INTO TABLE humidity");
                value = HiveQueryExecutor.executeQuery("SELECT\n" +
                        "  MONTH(get_json_object(humidity.json, \"$.date\")),\n" +
                        "  COUNT(get_json_object(humidity.json, \"$.event\"))\n" +
                        "FROM humidity\n" +
                        "WHERE YEAR(get_json_object(humidity.json, \"$.date\")) = 2003\n" +
                        "GROUP BY MONTH(get_json_object(humidity.json, \"$.date\"))");
                String[] tokens = value.split("<br/>");
                float num = 0;
                StringBuilder strbuilder = new StringBuilder();
                for (int i = 0; i < tokens.length; i++) {
                    strbuilder.append("<br/>");
                    String[] val = tokens[i].split(" ");
                    for (int j = 0; j < val.length; j++) {
                        fileWriter.append("2003-" + val[0]);
                        fileWriter.append(",");
                        fileWriter.append(val[1]);
                        fileWriter.append("\n");
                    }
                }
            } catch (SQLException e) {
                failed = true;
                return e.getLocalizedMessage();
            }
        }catch (IOException e) {
            e.printStackTrace();
            failed = true;
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                failed = true;
            }

            if(failed){
                return "";
            }
        }
        return value;
    }

    public static String findNumberOfHighPriorityClassEvents() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS humidity");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE humidity (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileHumidityJson + "' INTO TABLE humidity");
            value = HiveQueryExecutor.executeQuery("SELECT get_json_object(humidity.json, \"$.location.city\") as city, get_json_object(humidity.json, \"$.location.state\") as state, count(get_json_object(humidity.json, \"$.event\")) as cnt FROM humidity WHERE get_json_object(humidity.json, \"$.event\")=\"SCALE_low_humidity_RPi\" GROUP BY get_json_object(humidity.json, \"$.location.city\"), get_json_object(humidity.json, \"$.location.state\")");
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
