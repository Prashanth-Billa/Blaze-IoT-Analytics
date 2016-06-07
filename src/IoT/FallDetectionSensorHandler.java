package IoT;

import java.sql.SQLException;

public class FallDetectionSensorHandler {
    public static String findFallsHappenningInGivenRegion() {

        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS fallDetectorTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE fallDetectorTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_seismic.json' INTO TABLE fallDetectorTable");
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

    public static String findRegionWithMajorNumberOfFalls() {

        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS fallDetectorTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE fallDetectorTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_seismic.json' INTO TABLE fallDetectorTable");
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

    public static String findTimeSlotAreFallsHappenningMostFrequently() {
        String value = "";
        try {
            String ret1 = HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS fallDetectorTable");
            String ret2 = HiveQueryExecutor.executeQuery("CREATE TABLE fallDetectorTable (json STRING)");
            String ret3 = HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_seismic.json' INTO TABLE fallDetectorTable");
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
