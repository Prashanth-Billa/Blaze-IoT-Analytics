package IoT;

import java.net.ConnectException;
import java.sql.*;

public class HiveQueryExecutor {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static String executeQuery(String query) throws SQLException {
        try {Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "error :" + e.getMessage();
        }
        StringBuilder str = new StringBuilder();
        Connection con = null;
        Statement stmt = null;
        try{
            con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "hadoop", "");
            stmt = con.createStatement();
        }catch(SQLException e){
            return "Please check the Connection to HDFS and Hive. Please validate the query.";
        } catch(Exception e){
            return  "Please check the Connection to HDFS and Hive. Please validate the query.";
        }

        ResultSet res = null;
        ResultSetMetaData resMeta = null;
        if(query.contains("DROP TABLE") || query.contains("CREATE TABLE") || query.contains("LOAD DATA")){
            stmt.executeUpdate(query);
            stmt.close();
            con.close();
            return "";
        }else{
            res = stmt.executeQuery(query);
            resMeta = res.getMetaData();
            while (res.next()) {
                for (int i = 1; i <= resMeta.getColumnCount(); i++) {
                    str.append(res.getString(i));
                    str.append(" ");
                }
                str.append("<br/>");
            }
            res.close();
            stmt.close();
            con.close();

            return str.toString();
        }

    }
}
