package IoT;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class AirPollutionHandler {

    private static String fileAirPollutionJson = "/home/srt/JSON/file_airPollution.json";
    public static int findPollutantsLevelInSpecificDuration(){
        boolean failed = false;
        FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("/home/hadoop/IdeaProjects/Blaze-IoT-Analytics/web/content/CSV/airPollution_1.csv");
                fileWriter.append("date,value");
                fileWriter.append("\n");
                try {
                    String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airpollution");
                    String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airpollution (json STRING)");
                    String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileAirPollutionJson + "' INTO TABLE airpollution");
                    String value = HiveQueryExecutor.executeQuery("SELECT\n" +
                            "  MONTH(get_json_object(airpollution.json, \"$.date\")),\n" +
                            "  SUM(get_json_object(airpollution.json, \"$.pollutants.value\"))\n" +
                            "FROM airpollution\n" +
                            "WHERE YEAR(get_json_object(airpollution.json, \"$.date\")) = 2006\n" +
                            "GROUP BY MONTH(get_json_object(airpollution.json, \"$.date\"))");
                    String[] tokens = value.split("<br/>");
                    float num = 0;
                    StringBuilder strbuilder = new StringBuilder();
                    for (int i = 0; i < tokens.length; i++) {
                        strbuilder.append("<br/>");
                        String[] val = tokens[i].split(" ");
                        fileWriter.append("2006-" + val[0]);
                        fileWriter.append(",");
                        num = Float.parseFloat(val[1]);
                        num = num;
                        fileWriter.append(String.valueOf(num));
                        fileWriter.append("\n");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    failed = true;
                }

            } catch (IOException e) {
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
                    return -1;
                }
            }

        return 0;
        }

        public static int findPollutantTypePercentage(){
            boolean failed = false;
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("/home/hadoop/IdeaProjects/Blaze-IoT-Analytics/web/content/CSV/airPollution_2.csv");
                fileWriter.append("pollutant,level");
                fileWriter.append("\n");
                try {
                    String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airQualityTable");
                    String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airQualityTable (json STRING)");
                    String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileAirPollutionJson + "' INTO TABLE airQualityTable");
                    String value = HiveQueryExecutor.executeQuery("SELECT get_json_object(airQualityTable.json, \"$.pollutants.pol\"), sum(get_json_object(airQualityTable.json, \"$.pollutants.value\")) FROM airQualityTable Group by get_json_object(airQualityTable.json, \"$.pollutants.pol\")");

                    String[] tokens = value.split("<br/>");
                    for(int i = 0; i < tokens.length; i++){
                        String[] val = tokens[i].split(" ");
                        fileWriter.append(val[0]);
                        fileWriter.append(",");
                        fileWriter.append(val[1]);
                        fileWriter.append("\n");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    failed = true;
                }

            } catch (IOException e) {
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
                    return -1;
                }
            }
        return 0;
    }

    public static int generateGraph() {
        return 0;
    }

    public static String findTopCitiesWithHighestPollutants() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airPollution");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airPollution (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileAirPollutionJson + "' INTO TABLE airQualityTable");
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

    public static String findCityWithMaximumAmountOfAllPollutants() {
        String value = "";
        StringBuilder strbuilder = new StringBuilder();
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airpollution");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airpollution (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileAirPollutionJson + "' INTO TABLE airpollution");
            value = HiveQueryExecutor.executeQuery("SELECT\n" +
                    "  get_json_object(airpollution.json, \"$.location.city\"),\n" +
                    "  sum(get_json_object(airpollution.json, \"$.pollutants.value\")) AS c\n" +
                    "FROM airpollution\n" +
                    "Group by  get_json_object(airpollution.json, \"$.location.city\")\n" +
                    "ORDER BY c DESC\n" +
                    "LIMIT 1");
            String[] tokens = value.split("<br/>");
            float num = 0;

            for(int i = 0; i < tokens.length; i++){
                strbuilder.append("<br/>");
                String[] val = tokens[i].split(" ");
                strbuilder.append("Most Polluted City (all polutant's value combined) : <b> " + val[0] + " </b>");
            }
        } catch (SQLException e) {
            return e.getLocalizedMessage();
        }
        return strbuilder.toString();
    }

    public static String findPollutantEmittedMostInAllCitiesCombined() {
        String value = "";
        StringBuilder strbuilder = new StringBuilder();
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airpollution");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airpollution (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileAirPollutionJson + "' INTO TABLE airpollution");
            value = HiveQueryExecutor.executeQuery("SELECT\n" +
                    "  col_1,\n" +
                    "  col_2\n" +
                    "FROM (SELECT\n" +
                    "  get_json_object(airpollution.json, \"$.pollutants.pol\") AS col_1,\n" +
                    "  MAX(get_json_object(airpollution.json, \"$.pollutants.value\")) AS col_2\n" +
                    "FROM airpollution\n" +
                    "GROUP BY get_json_object(airpollution.json, \"$.pollutants.pol\")\n" +
                    "ORDER BY col_2 DESC LIMIT 1) AS tbl");
            String[] tokens = value.split("<br/>");
            float num = 0;

            for(int i = 0; i < tokens.length; i++){
                strbuilder.append("<br/>");
                String[] val = tokens[i].split(" ");
                strbuilder.append("Pollutant : <b>");
                strbuilder.append(val[0]);
                strbuilder.append("</b>, Value: <b>");
                strbuilder.append(val[1]);
                strbuilder.append("mg/m^3 </b>");
            }
        } catch (SQLException e) {
            return e.getLocalizedMessage();
        }
        return strbuilder.toString();
    }
}
