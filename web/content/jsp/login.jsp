<%@ page import ="java.sql.*" %>
<%
    String userName = request.getParameter("uname");
    String userPassword = request.getParameter("pass");
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs237",
            "root", "root");
    Statement st = con.createStatement();
    ResultSet rs;
    rs = st.executeQuery("select * from members where uname='" + userName + "' and pass='" + userPassword + "'");
    if (rs.next()) {
        session.setAttribute("userid", userName);
        response.sendRedirect("home.jsp");
    } else {
        out.println("Invalid password <a href='index.jsp'>try again</a>");
    }
%>