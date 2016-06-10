package IoT;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class AirPollutionHandler {

    public static int findPollutantsLevelInSpecificDuration(){
        boolean failed = false;
        FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("/home/hadoop/IdeaProjects/Blaze-IoT-Analytics/web/content/CSV/airPollution_1.csv");
                fileWriter.append("date,value");
                fileWriter.append("\n");
                try {
                    String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airQualityTable");
                    String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airQualityTable (json STRING)");
                    String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_air_quality.json' INTO TABLE airQualityTable");
                    String value = HiveQueryExecutor.executeQuery("SELECT get_json_object(airQualityTable.json, \"$.pollutants.value\"), get_json_object(airQualityTable.json, \"$.date\") FROM airQualityTable");
                    String[] tokens = value.split("<br/>");
                    for(int i = 0; i < tokens.length; i++){
                        String[] val = tokens[i].split(" ");
                        String[] t = val[1].split("-");
                        if("2006".equals(t[0]) || "2009".equals(t[0])){
                            fileWriter.append(t[0] + "-" + t[1]);
                            fileWriter.append(",");
                            fileWriter.append(val[0]);
                            fileWriter.append("\n");
                        }
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
                    String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_air_quality.json' INTO TABLE airQualityTable");
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
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_seismic.json' INTO TABLE airPollution");
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

    public static String findCityWithMaximumNumberOfPollutants() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airPollution");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airPollution (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_seismic.json' INTO TABLE airPollution");
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

    public static String findPollutantEmittedInMaximumNumberOfCities() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airPollution");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airPollution (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_seismic.json' INTO TABLE airPollution");
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
