<%@ page import="java.util.Enumeration" %>
<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
<%
    response.sendRedirect("index.jsp");
} else {
%>
Welcome <%=session.getAttribute("userid")%>
<a href='logout.jsp'><span style="color:white"><br><b>Log out</b></span></a>
<br/><a href='home.jsp'><span style="color:white"><br><b>Back</b></span></a>
<%
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results</title>
    <link rel="stylesheet" type="text/css" href="../html/css/style.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Cantarell"/>
    <script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>

    <link href="../html/css/json-tree.css" type="text/css" rel="stylesheet"/>
    <script src="../html/js/queries.js"></script>
    <script src="../html/js/jquery-json-tree.js"></script>
    <script src="../html/js/json-tree.js"></script>
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
</head>
<body>
<div align="center">
    <h1>Results</h1><br/><img src="../html/images/analytics.png" width="150px"/><br/>
    <div id="tabs-container">
        <ul class="tabs-menu">
            <li class="current"><a href="#tab-1">Result</a></li>
            <li><a href="#tab-2">Sensor Information</a></li>
            <!--<li><a href="#tab-3">Logs</a></li>-->
        </ul>
        <div class="tab">
            <div id="tab-1" style="width:1000px" class="tab-content">
                <div id="myDiv"></div>
                <%@ page import="IoT.HiveQueryExecutor" %>
                <%@ page import="java.sql.*" %>
                <%@ page import="java.io.FileWriter" %>
                <%@ page import="java.io.File" %>
                <%
                    String sensor = request.getParameter("sensorType").toString();
                    String sensorAction = request.getParameter("sensor-action-value").toString();


                    if("seismic".equals(sensor)){
                        if("0".equals(sensorAction)){

                            FileWriter fileWriter = new FileWriter("/home/hadoop/IdeaProjects/IoT Analytics/web/content/CSV/seismicIntensity.csv");
                            fileWriter.append("name,val,lat,lon");
                            fileWriter.append("\n");
                            String ret1 = IoT.HiveQueryExecutor.executeQuery("DROP TABLE IF EXISTS seismicTable");
                            String ret2 = IoT.HiveQueryExecutor.executeQuery("CREATE TABLE seismicTable (json STRING)");
                            String ret3 = IoT.HiveQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/hadoop/uploads/JSON/file_seismic.json' INTO TABLE seismicTable");

                            String value = IoT.HiveQueryExecutor.executeQuery("SELECT get_json_object(seismicTable.json, \"$.location.lat\"), get_json_object(seismicTable.json, \"$.location.lon\") FROM seismicTable");
                            String[] tokens = value.split("<br/>");
                            for(int i = 0; i < tokens.length; i++){
                                String[] val = tokens[i].split(" ");
                                fileWriter.append("CS237!!!!!!!!");
                                fileWriter.append(",");
                                fileWriter.append("8287238");
                                fileWriter.append(",");
                                fileWriter.append(val[0]);
                                fileWriter.append(",");
                                fileWriter.append(val[1]);
                                fileWriter.append("\n");
                                break;

                            }
                            fileWriter.flush();
                            fileWriter.close();
                        }
                    }
                %>
            </div>
            <div id="tab-2" class="tab-content">
                    <textarea id='inputJSON' type='text' style='width:100%;height:150px;margin-bottom:10px;display: none'>

                    </textarea>
                <button id='btnVisualize' class='btn btn-primary' style='width:20%; display: none'>Visualize</button>

                <div id='top'></div>
                <pre id="json-renderer"></pre>
            </div>

            <div id="tab-3" class="tab-content">

            </div>
        </div>
    </div>
</div>
<script>
    Plotly.d3.csv('../CSV/seismicIntensity.csv', function(err, rows){
        function unpack(rows, key) {
            return rows.map(function(row) { return row[key]; });
        }
        var cityName = unpack(rows, 'name'),
                cityPop = unpack(rows, 'val'),
                cityLat = unpack(rows, 'lat'),
                cityLon = unpack(rows, 'lon'),
                color = [,"rgb(255,65,54)","rgb(133,20,75)","rgb(255,133,27)","lightgrey"],
                citySize = [],
                hoverText = [],
                scale = 50000;

        for ( var i = 0 ; i < cityPop.length; i++) {
            var currentSize = cityPop[i] / scale;
            var currentText = cityName[i] + "<br>Population: " + cityPop[i];
            citySize.push(currentSize);
            hoverText.push(currentText);
        }

        var data = [{
            type: 'scattergeo',
            locationmode: 'USA-states',
            lat: cityLat,
            lon: cityLon,
            text: hoverText,
            hoverinfo: 'text',
            marker: {
                size: citySize,
                line: {
                    color: 'black',
                    width: 2
                },

            }
        }];

        var layout = {
            title: 'Seismic Sensor Plot',
            showlegend: false,
            geo: {
                scope: 'usa',
                projection: {
                    type: 'albers usa'
                },
                showland: true,
                landcolor: 'rgb(217, 217, 217)',
                subunitwidth: 1,
                countrywidth: 1,
                subunitcolor: 'rgb(255,255,255)',
                countrycolor: 'rgb(255,255,255)'
            },
        };

        Plotly.plot(myDiv, data, layout, {showLink: false});
    });
</script>
</body>
</html>
