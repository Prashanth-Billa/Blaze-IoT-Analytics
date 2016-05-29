<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
<%
    response.sendRedirect("index.jsp");
} else {
%>
Welcome <%=session.getAttribute("userid")%>
<a href='logout.jsp'><span style="color:white"><br><b>Log out</b></span></a>
<%
    }
%>
<html >
<head>
    <script src="http://code.jquery.com/jquery-1.12.3.min.js"></script>
    <script src="http://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
    <title>IoT Analytics</title>
    <link rel="stylesheet" type="text/css" href="../html/css/style.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Cantarell" >
    <script src="../html/js/home.js"></script>
    <script>

        function validateSensorSelection(){
            var value = document.forms["sensorActionForm"]["sensorType"].options[document.forms["sensorActionForm"]["sensorType"].selectedIndex].value;
            console.log(value);
            if(value == null || value == "" || value == "empty"){
                alert("Please select a valid sensor type")
                return false;
            }

            return true;
        }
    </script>


</head>
<body>

<div align="center">
    <h1> <img src="../html/images/logo.png" width="150px"/> <br/>
        Blaze - An IoT Analytics framework</h1><br/><h2 style="font-family: Quicksand;">Using Hadoop and Hive</h2><br/>
</div>


<div id="tabs-container">
    <ul class="tabs-menu">
        <li class="current"><a href="#tab-1">Basic</a></li>
        <li><a href="#tab-2">Advanced</a></li>
        <li><a href="#tab-3">Executed Tasks</a></li>
        <li><a href="#tab-4">Hadoop and Hive Status</a></li>
    </ul>
    <div class="tab">
        <div id="tab-1" class="tab-content">
            <form method="get" name="sensorActionForm" action="processBasic.jsp" id="sensorActionFormId" onsubmit="return validateSensorSelection()">
                <img src="../html/images/sensors.png" width="80px" style="float: right;"/>
                <b>Select the IoT Sensor to analyze</b>  <br/>
                <div  class="select-dropdown color-blue shape-blue-square">
                    <select name='sensorType' style="color:black" id="select-sensor" onchange="showSensorActions()">
                        <option value="empty" selected>Select One Below</option>
                        <option value="cross-correlation">Cross Correlation</option>
                        <option value="air-pollution">Air Pollution Sensor</option>
                        <option value="traffic">Traffic Management Sensor</option>
                        <option value="seismic">Seismic Sensor</option>
                        <option value="humidity">Humidity Sensor</option>
                        <option value="fall-detection">Fall Detection</option>
                    </select>
                </div>
                <br/>
                <hr/>
                <br/><br/>
                <img src="../html/images/task.png" width="80px" style="float: right;"/>
                <b>Select the task for analysis</b> <br>
                <div style="color:black; width: 500px" class="select-dropdown color-blue shape-blue-square" name="div-name">
                    <select style="color:black; width: 500px" id="sensor-actions" name='sensor-action-value' onclick="checkSensorSelected()">
                    </select>
                </div>
                <br/>
                <input type="submit" class="submit-button" value="Go!">

                <br/>
                <hr/>

            </form>
        </div>
        <div id="tab-2" class="tab-content">
            <form method="post" action="processAdvanced.jsp" enctype="multipart/form-data">
                <img src="../html/images/upload.png" width="80px" style="float: right;"/>
                <b>Upload Custom file:</b><br/><br/><br>
                <input type="file" name="file" size="50" /><br/><br/>
                <hr/>
                <p><b>Enter the Hive Query Below: </b></p><br/>
                <textarea id="query-area" rows="20" cols="20" name="user_query"></textarea>
                <br/><br/><br/>
                <input type="submit" class="submit-button"  value="Go!">
            </form>
        </div>

        <div id="tab-3" class="tab-content">
            <b>Tasks Executed by you</b><br/><img src="../html/images/tasks.png" width="80px"/><br/><br>
            <%@ page import="java.sql.*" %>
            <%@ page import="java.io.BufferedReader" %>
            <%@ page import="java.io.InputStreamReader" %>
            <%@ page import="java.io.FileReader" %>

            <%
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs237",
                        "root", "root");
                Statement st = con.createStatement();
                ResultSet rs;
                if(session.getAttribute("userid") == null || session.getAttribute("userid") == ""){

                }else{
                    String uid = session.getAttribute("userid").toString();
                    rs = st.executeQuery("select * from queryHistory where username='" + uid + "' order by submit_time DESC");
                    out.println("<table id='history' style='overflow:scroll' class='display' cellspacing='0' width='100%'>");
                    out.println("<thead><tr><th>Mode</th><th>File name</th><th>Query</th><th>Result</th><th>Submit Time</th></tr></thead><tbody>");
                    while(rs.next()){
                        out.println("<tr><td>" + rs.getString("mode") + "</td><td>" + rs.getString("filename") + "</td><td>" + rs.getString("query") + "</td><td>" + rs.getString("result") + "</td><td>" + rs.getString("submit_time") + "</td></tr>");
                    }
                    out.println("</tbody></table>");
                }

            %>
        </div>
        <div id="tab-4" class="tab-content">
                <br/>
            <h2>Hadoop and Hive Environment: </h2>
            <%@ page import="IoT.HiveQueryExecutor" %>
            <%@ page import="java.sql.*" %>
            <%
                try{
                    String returnedValue = IoT.HiveQueryExecutor.executeQuery("show tables");
                    if("Please check the Connection to HDFS and Hive".equals(returnedValue) || "Please check the Connection to HDFS and Hive. Please validate the query.".equals(returnedValue)){
                        out.println("<span style='color:red; font-weight: bold'>The environment is not setup. </span>");
                        out.println("<br/><br/><span style='color:red; font-weight: bold'>Execute ./start-dfs.sh in $HADOOP_HOME/sbin </span>");
                        out.println("<br/><br/><span style='color:red; font-weight: bold'>Execute ./start-yarn.sh in $HADOOP_HOME/sbin</span>");
                        out.println("<br/><br/><span style='color:red; font-weight: bold'>Execute 'hive --service hiveserver2'</span>");
                    }else{
                        out.println("<span style='color:green; font-weight: bold'>The environment is setup. Kudos!</span>");
                    }
                }catch(Exception e){

                }
            %>
        </div>
    </div>
</div>



</body>
<script>
    $(document).ready(function() {
        $('#history').dataTable({
            pageLength: 5
        });
    } );
</script>
</html>