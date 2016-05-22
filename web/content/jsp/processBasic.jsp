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
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Cantarell" />
    <script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
    <script src="../html/js/jquery.json-viewer.js"></script>
    <link href="../html/css/jquery.json-viewer.css" type="text/css" rel="stylesheet" />
    <Script>
        $(document).ready(function() {
            $.ajax({

                url : "../html/json/fall.json",
                dataType: "text",
                success : function (data) {
                    //$("#json-input").text(data);

                }
            });

            $(".tabs-menu a").click(function(event) {
                event.preventDefault();
                $(this).parent().addClass("current");
                $(this).parent().siblings().removeClass("current");
                var tab = $(this).attr("href");
                $(".tab-content").not(tab).css("display", "none");
                $(tab).fadeIn();
            });

                try {
                    var input = eval('(' + $('#json-input').val() + ')');
                    console.log(input);
                }
                catch (error) {
                    return alert("Cannot eval JSON: " + error);
                }
                var options = {
                    collapsed: $('#collapsed').is(':checked'),
                    withQuotes: $('#with-quotes').is(':checked')
                };
                $('#json-renderer').jsonViewer(input, options);



        });

    </Script>
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
                    <textarea id="json-input" style="display:none" autocomplete="off">[
   {
      "event":"SCALE_person_fall_detected_Android",
      "device":[
         {
            "id":"573f6e89616b43f2694fbd43",
            "type":"personfall",
            "version":1.2
         }
      ],
      "location":[
         {
            "lat":33.6432,
            "lon":-117.8418
         }
      ],
      "cond_freefall_threshold_val":"preset_freefall_threshold_val,",
      "cond_impact_threshold_val":"preset_impact_threshold_val",
      "value":"data",
      "prio_class":"very high",
      "prio_value":"3",
      "misc":null
   }
]</textarea>
                <pre id="json-renderer"></pre>
            </div>

            <div id="tab-3" class="tab-content">

            </div>
        </div>
    </div>
</div>

</body>
</html>
