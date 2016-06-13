package IoT;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class CrossCorrelation {

    private static String fileAirPollutionJson = "/home/srt/JSON/file_airPollution.json";
    private static String fileTrafficJson = "/home/srt/JSON/file_traffic.json";

    public static int generateGraph() {
        boolean failed = false;
        FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("/home/hadoop/IdeaProjects/Blaze-IoT-Analytics/web/content/CSV/airPollution_traffic.csv");
                fileWriter.append("State, CO Level, CO2 Level, Ozone Level, Traffic Congestion");
                fileWriter.append("\n");
                try {
                    String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airpollution");
                    String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airpollution (json STRING)");
                    String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileAirPollutionJson + "' INTO TABLE airpollution");

                    String ret4 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS traffic");
                    String ret5 = HiveQueryExecutor.executeQuery("CREATE TABLE traffic (json STRING)");
                    String ret6 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileTrafficJson + "' INTO TABLE traffic");

                    String value = HiveQueryExecutor.executeQuery("SELECT state1, pol1, pol_value1, pol2, pol_value2, pol3, pol_value3, t_print, t_traffic FROM (SELECT state1, pol1, pol_value1, pol2, pol_value2, pol3, pol_value3 FROM (SELECT state1, pol1, pol_value1, pol2, pol_value2 FROM (SELECT get_json_object(airpollution.json, \"$.location.state\") AS state1, get_json_object(airpollution.json, \"$.pollutants.pol\") AS pol1, SUM(get_json_object(airpollution.json, \"$.pollutants.value\")) AS pol_value1 FROM airpollution WHERE get_json_object(airpollution.json, \"$.pollutants.pol\") = 'CO' GROUP BY get_json_object(airpollution.json, \"$.location.state\"), get_json_object(airpollution.json, \"$.pollutants.pol\")) AS temp1 LEFT JOIN (SELECT get_json_object(airpollution.json, \"$.location.state\") AS state2, get_json_object(airpollution.json, \"$.pollutants.pol\") AS pol2, SUM(get_json_object(airpollution.json, \"$.pollutants.value\")) AS pol_value2 FROM airpollution WHERE get_json_object(airpollution.json, \"$.pollutants.pol\") = 'CO2' GROUP BY get_json_object(airpollution.json, \"$.location.state\"), get_json_object(airpollution.json, \"$.pollutants.pol\")) AS temp2 ON temp1.state1 = temp2.state2) AS temp3 LEFT JOIN (SELECT get_json_object(airpollution.json, \"$.location.state\") AS state3, get_json_object(airpollution.json, \"$.pollutants.pol\") AS pol3, SUM(get_json_object(airpollution.json, \"$.pollutants.value\")) AS pol_value3 FROM airpollution WHERE get_json_object(airpollution.json, \"$.pollutants.pol\") = 'Ozone' GROUP BY get_json_object(airpollution.json, \"$.location.state\"), get_json_object(airpollution.json, \"$.pollutants.pol\")) AS temp4 ON temp3.state1 = temp4.state3) AS temp5 LEFT JOIN (SELECT get_json_object(traffic.json, \"$.location.state\") AS state, 'traffic congestion' AS t_print, COUNT(get_json_object(traffic.json, \"$.time\")) t_traffic FROM traffic WHERE get_json_object(traffic.json, \"$.type\") = 2 GROUP BY get_json_object(traffic.json, \"$.location.state\")) AS traffic_temp ON traffic_temp.state = temp5.state1 LIMIT 15");
                    String[] tokens = value.split("<br/>");
                    float num = 0;
                    for(int i = 0; i < tokens.length; i++){
                        String[] val = tokens[i].split(" ");
                        fileWriter.append(val[0]);
                        fileWriter.append(",");
                        fileWriter.append(val[2]);
                        fileWriter.append(",");
                        fileWriter.append(val[4]);
                        fileWriter.append(",");
                        fileWriter.append(val[6]);
                        fileWriter.append(",");
                        num = Float.parseFloat(val[8]);
                        num = num * 50;
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

    public static int findAirQualityWithTrafficInEachHour() {
        return 0;
    }

    public static String findCitiesWithMaximumTrafficAndAirPollutantsEmitted() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airPolTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airPolTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_air_quality.json' INTO TABLE airPolTable");

            String ret4 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS trafficTable");
            String ret5 = HiveQueryExecutor.executeQuery("CREATE TABLE trafficTable (json STRING)");
            String ret6 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_traffic.json' INTO TABLE trafficTable");

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

    public static String findCitiesWithMaximumTrafficAndRelatePolutionHumidity() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airQualityTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airQualityTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_air_quality.json' INTO TABLE airQualityTable");

            String ret4 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS trafficTable");
            String ret5 = HiveQueryExecutor.executeQuery("CREATE TABLE trafficTable (json STRING)");
            String ret6 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_traffic.json' INTO TABLE trafficTable");
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
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airQualityTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airQualityTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_air_quality.json' INTO TABLE airQualityTable");

            String ret4 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS trafficTable");
            String ret5 = HiveQueryExecutor.executeQuery("CREATE TABLE trafficTable (json STRING)");
            String ret6 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_traffic.json' INTO TABLE trafficTable");
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

    public static String findMostCongestedCitiesAndRelateWithSeismicScale() {
        String value = "";
        String value1 = "";
        StringBuilder strbuilder = new StringBuilder();
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS airpollution");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE airpollution (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileAirPollutionJson + "' INTO TABLE airpollution");

            String ret4 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS traffic");
            String ret5 = HiveQueryExecutor.executeQuery("CREATE TABLE traffic (json STRING)");
            String ret6 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileTrafficJson + "' INTO TABLE traffic");

            HashSet cities1 =  new HashSet();
            HashSet cities2 =  new HashSet();

            value = HiveQueryExecutor.executeQuery("SELECT get_json_object(seismic.json, \"$.location.city\"), get_json_object(seismic.json, \"$.location.state\"), get_json_object(seismic.json, \"$.location.lat\"), get_json_object(seismic.json, \"$.location.lon\"), COUNT(get_json_object(seismic.json, \"$.location.city\")) AS col FROM seismic WHERE get_json_object(seismic.json, \"$.prio_value\") = 3 AND get_json_object(seismic.json, \"$.duration.duration\") >= 7 GROUP BY get_json_object(seismic.json, \"$.location.city\"), get_json_object(seismic.json, \"$.location.state\"), get_json_object(seismic.json, \"$.location.lat\"), get_json_object(seismic.json, \"$.location.lon\") ORDER BY col DESC LIMIT 100");
            String[] tokens = value.split("<br/>");
            for(int i = 0; i < tokens.length; i++){
                String[] val = tokens[i].split(" ");
                cities1.add(val[0] + " " + val[1]);


            }

            value1 = HiveQueryExecutor.executeQuery("SELECT get_json_object(traffic.json, \"$.location.city\"), get_json_object(traffic.json, \"$.location.state\"), COUNT (get_json_object (traffic.json, \"$.type\")) as col FROM traffic WHERE get_json_object(traffic.json, \"$.type\") = 2 GROUP BY get_json_object(traffic.json, \"$.location.city\"), get_json_object(traffic.json, \"$.location.state\") ORDER BY col DESC LIMIT 100");
            String[] tokens1 = value1.split("<br/>");
            for(int i = 0; i < tokens1.length; i++){
                String[] val = tokens1[i].split(" ");
                cities2.add(val[0] + " " + val[1]);
            }

            cities1.retainAll(cities2);

            Iterator it = cities1.iterator();
            while(it.hasNext()){
                strbuilder.append("<br/>");
                strbuilder.append("<b>" + it.next() + "</b>");
            }

        } catch (SQLException e) {
            return e.getLocalizedMessage();
        }
        return strbuilder.toString();
    }
}
