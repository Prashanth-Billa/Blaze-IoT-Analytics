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
    <meta charset="UTF-8">
    <title>IoT Analytics</title>
    <link rel="stylesheet" type="text/css" href="../html/css/style.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Cantarell" />
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="../html/js/home.js"></script>
    <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#historyData').DataTable({"pageLength": 5});
        } );
    </script>
</head>
<body>

<div align="center">
    <h1> <img src="../html/images/logo.png" width="150px"/> <br/>
        Blaze - An IoT Analytics framework</h1><br/><h2 style="font-family: Quicksand;">Using Hadoop, Hive and HBase</h2><br/>
</div>


<div id="tabs-container">
    <ul class="tabs-menu">
        <li class="current"><a href="#tab-1">Basic</a></li>
        <li><a href="#tab-2">Advanced</a></li>
        <li><a href="#tab-3">Dashboard</a></li>
    </ul>
    <div class="tab">
        <div id="tab-1" class="tab-content">
            <form method="post" action="processBasic.jsp">
                <b>Select the IoT Sensor to analyze</b>  <br/>
                <div  class="select-dropdown color-blue shape-blue-square">
                    <select style="color:black" id="select-sensor" onchange="showSensorActions()">
                        <option value="empty" selected>Select One Below</option>
                        <option value="fall-detection">Fall Detection</option>
                        <option value="seismic">Seismic Sensor</option>
                        <option value="humidity">Humidity Sensor</option>
                    </select>
                </div>
                <br/>
                <hr/>
                <br/><br/>
                <b>Select the task for analysis</b> <br>
                <div style="color:black" class="select-dropdown color-blue shape-blue-square">
                    <select style="color:black" id="sensor-actions" onclick="checkSensorSelected()">
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
            <b>Tasks Executed by you</b><br/><br/><br>
            <%@ page import="java.sql.*" %>
            <%
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs237",
                        "root", "root");
                Statement st = con.createStatement();
                ResultSet rs;
                String uid = session.getAttribute("userid").toString();
                rs = st.executeQuery("select * from queryHistory where username='" + uid + "' order by submit_time DESC");
                out.println("<table id='historyData' class='display' cellspacing='0' width='100%'");
                out.println("<thead><tr><th>Mode</th><th>File name</th><th>Query</th><th>Result</th><th>Submit Time</th></tr></thead><tbody>");
                while(rs.next()){
                    out.println("<tr><td>" + rs.getString("mode") + "</td><td>" + rs.getString("filename") + "</td><td>" + rs.getString("query") + "</td><td>" + rs.getString("result") + "</td><td>" + rs.getString("submit_time") + "</td></tr>");
                }
                out.println("</tbody></table>");
            %>
        </div>
    </div>
</div>



</body>
</html>