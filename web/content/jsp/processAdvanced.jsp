<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
<%
    response.sendRedirect("index.jsp");
} else {
%>
Welcome <%=session.getAttribute("userid")%>
<a href='logout.jsp'><span style="color:white"><br><b>Log out</b></span></a>
<br/><a href='home.jsp'><span style="color:white"><br><b>Home</b></span></a>
<%
    }
%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page import="com.jcraft.jsch.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Results</title>
    <link rel="stylesheet" type="text/css" href="../html/css/style.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Cantarell" />
    <script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
    <link href="../html/css/json-tree.css" type="text/css" rel="stylesheet"/>
    <script src="../html/js/jquery-json-tree.js"></script>


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
            <div id="tab-1" class="tab-content">
                <%@ page import="IoT.HiveAdvancedQueryExecutor" %>
                <%@ page import="java.sql.*" %>
                <%
                    String SFTPHOST = "blazeEngine-ssh.azurehdinsight.net";
                    int    SFTPPORT = 22;
                    String SFTPUSER = "srt";
                    String SFTPPASS = "January@2016";
                    String SFTPWORKINGDIR = "/home/srt/uploads/";

                    Session     sess     = null;
                    Channel     channel     = null;
                    ChannelSftp channelSftp = null;

                    File file ;
                    String jsonSensorDataLine = "";
                    String hiveQuery = "";
                    int fileSizeAllowed = 20000 * 1024;
                    String fileName = "";
                    String filePath = "";
                    int maxMemSize = 20000 * 1024;
                    String uploadedPath = "";
                    String contentType = request.getContentType();
                    if (contentType != "" && contentType != null && (contentType.indexOf("multipart/form-data") >= 0)) {

                        DiskFileItemFactory factory = new DiskFileItemFactory();
                        factory.setSizeThreshold(maxMemSize);
                        factory.setRepository(new File("/home/hadoop/uploadsTemp"));
                        ServletFileUpload upload = new ServletFileUpload(factory);
                        upload.setSizeMax(fileSizeAllowed);
                        boolean fileThere = false;

                        try{
                            List fileItems = upload.parseRequest(request);
                            Iterator i = fileItems.iterator();
                            out.println("<html>");
                            out.println("<body>");
                            while ( i.hasNext () )
                            {
                                FileItem fi = (FileItem)i.next();
                                if ( !fi.isFormField () )  {
                                    fileThere = true;

                                    String fieldName = fi.getFieldName();
                                    fileName = fi.getName();
                                    boolean isInMemory = fi.isInMemory();
                                    long sizeInBytes = fi.getSize();
                                    try
                                    {
                                        JSch jsch = new JSch();
                                        sess = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
                                        sess.setPassword(SFTPPASS);
                                        java.util.Properties conf = new java.util.Properties();
                                        conf.put("StrictHostKeyChecking", "no");
                                        sess.setConfig(conf);
                                        sess.connect();
                                        channel = sess.openChannel("sftp");
                                        channel.connect();
                                        channelSftp = (ChannelSftp)channel;
                                        channelSftp.cd(SFTPWORKINGDIR);
                                        File f = new File("/home/hadoop/Desktop/" + fileName);
                                        channelSftp.put(new FileInputStream(f), f.getName());
                                        String hiveTableName = "custom_" + fileName.substring(0, fileName.lastIndexOf('.'));
                                        String ret1 = IoT.HiveAdvancedQueryExecutor.executeQuery("DROP TABLE IF EXISTS " + hiveTableName);
                                        String ret2 = IoT.HiveAdvancedQueryExecutor.executeQuery("CREATE TABLE " + hiveTableName + " (json STRING)");
                                        String ret3 = IoT.HiveAdvancedQueryExecutor.executeQuery("LOAD DATA LOCAL INPATH '/home/srt/uploads/" + fileName + "' INTO TABLE " + hiveTableName);
                                        out.println("<span style='color:green'>Uploaded File into Azure: </span><span style='color:blue'>" + fileName + "</span><br>");
                                    }
                                    catch (Exception e)
                                    {
                                        out.println("<br/><span style='color:red'>File Not Attached : " + e.getLocalizedMessage() + "</span>");
                                        fileThere = false;

                                    }
                                }
                                if (fi.isFormField()) {
                                    hiveQuery = fi.getString();
                                    break;
                                }

                            }
                            out.println("</body>");
                            out.println("</html>");
                        }catch(Exception ex) {
                            System.out.println(ex);
                        }
                    }else{
                        out.println("<html>");
                        out.println("<body>");
                        out.println("<span style='color:red'>File empty ");
                        out.println("</body>");
                        out.println("</html>");
                    }
                %>

                <%
                    String temp = hiveQuery;
                    if(hiveQuery != "" && hiveQuery != null){
                        hiveQuery = hiveQuery.replaceAll(";", " ");

                        try{
                            String returnedValue = IoT.HiveAdvancedQueryExecutor.executeQuery(hiveQuery);
                            out.println("<br/><span style='color:green'>Query Successfully submitted: </span><span style='color:blue'>" + temp + "</span><br/>");
                            out.println("<div align='left'><span style='color:green'><br/><br/>Output: <br/><br/><br/><b>" + returnedValue + "</b></div>");
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs237", "root", "root");
                            Statement st = con.createStatement();
                            int i = st.executeUpdate("insert into queryHistory (username, mode, filename, query, result, submit_time) values ('" + session.getAttribute("userid").toString() + "','Advanced','" + fileName + "','" + temp + "','" + returnedValue + "', NOW())");
                            if (i > 0) {
                                out.println("<br/><br/><span style='color:green'>Successfully entered the result into database</span><br/>");
                            } else {
                                out.println("<br/><br/><span style='color:red'>Result not entered into database</span><br/>");
                            }

                        }catch(Exception ex){
                            out.println("<span style='color:red'>Submit a valid query / file: " + ex.getLocalizedMessage());
                        }
                    }else {
                        out.println("<span style='color:red'>Submit a valid query / file");
                    }
                %>
            </div>
            <div id="tab-2" class="tab-content">
                 <textarea id='inputAdvancedJSON' type='text' style='width:100%;height:150px;margin-bottom:10px;display: none'>

                    </textarea>
                <button id='btnVisualize' class='btn btn-primary' style='width:20%; display: none'>Visualize</button>

                <div id='top'></div>
            </div>

            <div id="tab-3" class="tab-content">

            </div>
        </div>
    </div>
</div>

</body>
<script src="../html/js/json-tree-advanced.js"></script>
</html>