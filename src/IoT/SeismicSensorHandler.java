package IoT;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class SeismicSensorHandler {

    private static String fileSeismicJson = "/home/srt/JSON/file_seismic.json";

    public static int generateMapWithSeismicIntensities(){
        boolean failed = false;
        FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("/home/hadoop/IdeaProjects/Blaze-IoT-Analytics/web/content/CSV/seismicIntensity.csv");
                fileWriter.append("name,val,lat,lon");
                fileWriter.append("\n");
                try {
                    String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS seismic");
                    String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE seismic (json STRING)");
                    String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileSeismicJson + "' INTO TABLE seismic");
                    String value = HiveQueryExecutor.executeQuery("SELECT\n" +
                            "  get_json_object(seismic.json, \"$.location.city\"),\n" +
                            "get_json_object(seismic.json, \"$.location.state\"),\n" +
                            "get_json_object(seismic.json, \"$.location.lat\"),\n" +
                            "get_json_object(seismic.json, \"$.location.lon\"),\n" +
                            "  COUNT(get_json_object(seismic.json, \"$.location.city\"))\n" +
                            "FROM seismic\n" +
                            "WHERE get_json_object(seismic.json, \"$.prio_value\") = 3\n" +
                            "GROUP BY get_json_object(seismic.json, \"$.location.city\"),\n" +
                            "get_json_object(seismic.json, \"$.location.state\"),\n" +
                            "get_json_object(seismic.json, \"$.location.lat\"),\n" +
                            "get_json_object(seismic.json, \"$.location.lon\")\n" +
                            " ORDER BY col DESC LIMIT 200");
                    String[] tokens = value.split("<br/>");
                    float num = 0;
                    for(int i = 0; i < tokens.length; i++){
                        String[] val = tokens[i].split(" ");
                        fileWriter.append(val[0] + val[1]);
                        fileWriter.append(",");
                        num = Float.parseFloat(val[4]);
                        num = num * 80000;
                        fileWriter.append(String.valueOf(num));
                        fileWriter.append(",");
                        fileWriter.append(val[2]);
                        fileWriter.append(",");
                        fileWriter.append(val[3]);
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

    public static String findMostActiveRegions() {
        boolean failed = false;
        FileWriter fileWriter = null;
        String value = "";
        try {
            fileWriter = new FileWriter("/home/hadoop/IdeaProjects/Blaze-IoT-Analytics/web/content/CSV/seismicIntensity.csv");
            fileWriter.append("name,val,lat,lon");
            fileWriter.append("\n");
            try {
                String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS seismicTable");
                String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE seismicTable (json STRING)");
                String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileSeismicJson + "' INTO TABLE seismicTable");
                value = HiveQueryExecutor.executeQuery("SELECT get_json_object(seismic.json, \"$.location.city\"), get_json_object(seismic.json, \"$.location.state\"), get_json_object(seismic.json, \"$.location.lat\"), get_json_object(seismic.json, \"$.location.lon\"), COUNT(get_json_object(seismic.json, \"$.location.city\")) AS col FROM seismic GROUP BY get_json_object(seismic.json, \"$.location.city\"), get_json_object(seismic.json, \"$.location.state\"), get_json_object(seismic.json, \"$.location.lat\"), get_json_object(seismic.json, \"$.location.lon\") ORDER BY col DESC LIMIT 200");
                String[] tokens = value.split("<br/>");
                int num = 0;

                for(int i = 0; i < tokens.length; i++){
                    String[] val = tokens[i].split(" ");

                    fileWriter.append(val[0] + "   " + val[1]);
                    num = Integer.parseInt(val[4]);
                    fileWriter.append(",");
                    num = num * 10000;
                    fileWriter.append(String.valueOf(num));
                    fileWriter.append(",");
                    fileWriter.append(val[2]);
                    fileWriter.append(",");
                    fileWriter.append(val[3]);
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
                return "";
            }
        }
        return "";
    }

    public static String findRegionsWithLargeTremors() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS seismicTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE seismicTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileSeismicJson + "' INTO TABLE seismicTable");
            value = HiveQueryExecutor.executeQuery("Select * from airpollution");
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

    public static String findRegionsWithLargeDurationForTremors() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS seismicTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE seismicTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileSeismicJson + "' INTO TABLE seismicTable");
            value = HiveQueryExecutor.executeQuery("SELECT get_json_object(seismicTable.json, \"$.location.lat\"), get_json_object(seismicTable.json, \"$.location.lon\"), get_json_object(seismicTable.json, \"$.value\"), get_json_object(seismicTable.json, \"$.location.state\"), get_json_object(seismicTable.json, \"$.location.street\") FROM seismicTable");
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

    public static String findCrossOverRegionsActiveLargeLongDuration() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS seismicTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE seismicTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '" + fileSeismicJson + "' INTO TABLE seismicTable");
            value = HiveQueryExecutor.executeQuery("SELECT get_json_object(seismicTable.json, \"$.location.lat\"), get_json_object(seismicTable.json, \"$.location.lon\"), get_json_object(seismicTable.json, \"$.value\"), get_json_object(seismicTable.json, \"$.location.state\"), get_json_object(seismicTable.json, \"$.location.street\") FROM seismicTable");
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
