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
    <script>

        function getUrlVars() {
            var vars = {};
            var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,
                    function(m,key,value) {
                        vars[key] = value;
                    });
            return vars;
        }

    </script>
    <script src="../html/js/jquery-json-tree.js"></script>
    <script src="../html/js/json-tree.js"></script>
</head>
<body>
<div align="center">
    <h1>Results</h1><br/><img src="../html/images/analytics.png" width="150px"/><br/>
    <div id="tabs-container">
        <ul class="tabs-menu">
            <li class="current"><a href="#tab-1">Result</a></li>
            <li><a href="#tab-2">Sensor Information</a></li>
            <li><a href="#tab-3">Logs</a></li>
        </ul>
        <div class="tab">
            <div id="tab-1" class="tab-content">

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
