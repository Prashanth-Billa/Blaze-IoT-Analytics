<%@ page import ="java.sql.*" %>
<%
    String userName = request.getParameter("uname");
    String userPassword = request.getParameter("pass");
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String emailAddress = request.getParameter("emailAddress");

    boolean flag = true;
    if("".equals(userName.trim())){
        flag = false;
    }
    if("".equals(emailAddress.trim())){
        flag = false;
    }
    if("".equals(firstName.trim())){
        flag = false;
    }

    if(flag == true){
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs237",
                "root", "root");
        Statement st = con.createStatement();
        //ResultSet rs;
        int i = st.executeUpdate("insert into members(first_name, last_name, email, uname, pass, regdate) values ('" + firstName + "','" + lastName + "','" + emailAddress + "','" + userName + "','" + userPassword + "', CURDATE())");
        if (i > 0) {
            session.setAttribute("userid", userName);
            response.sendRedirect("home.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
    }else{
        out.println("<script>alert('invalid details')</script>");
        response.setHeader("Refresh", "0.1; URL=index.jsp");
    }


%>