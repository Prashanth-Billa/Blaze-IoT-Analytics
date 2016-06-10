<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../html/css/style.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Cantarell" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <title>Blaze - IoT Analytics Engine using Hadoop and Hive</title>
</head>
<body style="background: #2F0B3A; color: white">
<form method="post" action="login.jsp">
    <center>
        <div align="center">
            <img src="../html/images/logo.png" width="190px"/><br/>
            <h1 style="font-family: Quicksand;">Blaze - An IoT Analytics framework</h1><br/><h2 style="font-family: Quicksand;">Using Hadoop, Hive and HBase</h2><br/>
            <h3 style="font-family: Quicksand;">~ Under Prof. Nalini Venkatasubramanian</h3><br/>
        </div>
        <div align="center" style="border: solid 2px grey; width: 500px; color: white; font-family: Quicksand; background: #E5E4E2; color: black; font-weight: bold">


        <table  width="30%" cellpadding="3">
            <thead>
            <tr>
                <th colspan="2">Login Below</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>User Name</td>
                <td><input type="text" name="uname" value="" autocomplete="off"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="pass" value="" autocomplete="off"/></td>
            </tr>
            <tr>
                <td><input class="submit-button" type="submit" value="Login" /></td>
                <td colspan="2"><b><a href="register.jsp" class="submit-button">Not Registered?</a></b></td>
            </tr>
            </tbody>
        </table>
        </div>
    </center>
</form>
</body>
</html>