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
    <script src="http://d3js.org/d3.v3.min.js"></script>
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
                <%@ page import="java.sql.*" %>
                <%@ page import="java.io.FileWriter" %>
                <%@ page import="java.io.IOException" %>
                <%@ page import="IoT.*" %>
                <%
                    String sensor = request.getParameter("sensorType").toString();
                    String sensorAction = request.getParameter("sensor-action-value").toString();
                    int returnValue = 0;

                    String output = "";
                    if("seismic".equals(sensor)){
                        if("0".equals(sensorAction)){
                            returnValue = IoT.SeismicSensorHandler.generateMapWithSeismicIntensities();
                            if(returnValue < 0){
                                out.println("Error in generating graph as the data file could not be generated");
                            }
                        }

                        if("1".equals(sensorAction)){
                            output = IoT.SeismicSensorHandler.findMostActiveRegions();
                            out.println(output);
                        }

                        if("2".equals(sensorAction)){
                            output = IoT.SeismicSensorHandler.findRegionsWithLargeTremors();
                            out.println(output);
                        }

                        if("3".equals(sensorAction)){
                            output = IoT.SeismicSensorHandler.findRegionsWithLargeDurationForTremors();
                            out.println(output);
                        }

                        if("4".equals(sensorAction)){
                            output = IoT.SeismicSensorHandler.findCrossOverRegionsActiveLargeLongDuration();
                            out.println(output);
                        }


                    }else if("air-pollution".equals(sensor)) {
                        if ("0".equals(sensorAction)) {
                            returnValue = IoT.AirPollutionHandler.findPollutantsLevelInSpecificDuration();
                            if (returnValue < 0) {
                                out.println("Error in generating graph as the data file could not be generated");
                            }
                        }

                        if("1".equals(sensorAction)){
                            output = IoT.AirPollutionHandler.findTopCitiesWithHighestPollutants();
                            out.println(output);
                        }

                        if("2".equals(sensorAction)){
                            output = IoT.AirPollutionHandler.findCityWithMaximumNumberOfPollutants();
                            out.println(output);
                        }

                        if("3".equals(sensorAction)){
                            output = IoT.AirPollutionHandler.findPollutantEmittedInMaximumNumberOfCities();
                            out.println(output);
                        }
                    }else if("humidity".equals(sensor)) {
                        if ("0".equals(sensorAction)) {
                            returnValue = HumiditySensorHandler.generateGraph();
                            if (returnValue < 0) {
                                out.println("Error in generating graph as the data file could not be generated");
                            }
                        }

                        if("1".equals(sensorAction)){
                            output = HumiditySensorHandler.findTopCitiesWithHighestPollutants();
                            out.println(output);
                        }

                        if("2".equals(sensorAction)){
                            output = HumiditySensorHandler.findCityWithMaximumNumberOfPollutants();
                            out.println(output);
                        }

                        if("3".equals(sensorAction)){
                            output = HumiditySensorHandler.findPollutantEmittedInMaximumNumberOfCities();
                            out.println(output);
                        }
                    }else if("traffic".equals(sensor)) {
                        if ("0".equals(sensorAction)) {
                            returnValue = TrafficSensorHandler.findMostCongestedCities();
                            if (returnValue < 0) {
                                out.println("Error in generating graph as the data file could not be generated");
                            }
                        }

                        if("1".equals(sensorAction)){
                            output = TrafficSensorHandler.findCongestionLevelInEachHourInACity();
                            out.println(output);
                        }

                        if("2 ".equals(sensorAction)){
                            output = TrafficSensorHandler.findPeakCongestionTime();
                            out.println(output);
                        }

                    }else if("cross-correlation".equals(sensor)) {
                        if ("0".equals(sensorAction)) {
                            returnValue = CrossCorrelation.findAirQualityWithTrafficInEachHour();
                            if (returnValue < 0) {
                                out.println("Error in generating graph as the data file could not be generated");
                            }
                        }

                        if("1".equals(sensorAction)){
                            output = CrossCorrelation.findCitiesWithMaximumTrafficAndAirPollutantsEmitted();
                            out.println(output);
                        }

                        if("2".equals(sensorAction)){
                            output = CrossCorrelation.findCitiesWithMaximumTrafficAndRelatePolutionHumidity();
                            out.println(output);
                        }

                        if("3".equals(sensorAction)){
                            output = CrossCorrelation.findPollutantEmittedInMaximumNumberOfCities();
                            out.println(output);
                        }

                        if("4".equals(sensorAction)){
                            output = CrossCorrelation.findMostCongestedCitiesAndRelateWithSeismicScale();
                            out.println(output);
                        }
                    }else if("fall-detection".equals(sensor)){
                        if("0".equals(sensorAction)){
                            output = FallDetectionSensorHandler.findFallsHappenningInGivenRegion();
                            out.println(output);
                        }

                        if("1".equals(sensorAction)){
                            output = FallDetectionSensorHandler.findRegionWithMajorNumberOfFalls();
                            out.println(output);
                        }

                        if("2".equals(sensorAction)){
                            output = FallDetectionSensorHandler.findTimeSlotAreFallsHappenningMostFrequently();
                            out.println(output);
                        }
                    }

                %>
                <script src="../html/js/graphPlotHelper.js"></script>
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

</body>
</html>
