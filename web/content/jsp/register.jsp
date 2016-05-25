<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../html/css/style.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Cantarell" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <title>Registration</title>
    <script>

        function invalidName(name){
            if(name == "" || name == empty || name == null || name.length < 3){
                return true;
            }

            return false;
        }

        function validateRegistration(){
            var firstName = document.getElementById("fname").value;
            var lastName = document.getElementById("lname").value;
            var email = document.getElementById("email").value;
            var userName = document.getElementById("uname").value;
            var password = document.getElementById("pass").value;

            if(invalidName(firstName) || invalidName(userName) || invalidName(email)){
                alert("Invalid First name, email or User name. Enter atleast 3 characters for each.")
                return false;
            }

            if(password == "" || email == "" || email == empty || email == null){
                alert("Invalid email or password. They cannot be empty.")
                return false;
            }

            return true;

        }
    </script>
</head>
<body style="background: #2F0B3A; color: white">
<form method="post" action="registerHandler.jsp" onsubmit="return validateRegistration()" name="registrationForm">

    <center>
        <div align="center" style="border: solid 2px grey; width: 500px; font-family: Quicksand; background: #E5E4E2; color: black; font-weight: bold">
        <table width="30%" cellpadding="5">
            <thead>
            <tr>
                <th colspan="2">Register</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>First Name</td>
                <td><input type="text" name="fname" id="fname" value="" autocomplete="off"/></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><input type="text" name="lname" id="lname" value="" autocomplete="off"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="email" name="email" id="email" value="" autocomplete="off" required/></td>
            </tr>
            <tr>
                <td>User Name</td>
                <td><input type="text" name="uname" id="uname" value="" autocomplete="off"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="pass" id="pass" value="" autocomplete="off"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit" class="submit-button"/></td>
                <td><input type="reset" value="Reset" class="submit-button"/></td>
            </tr>
            <tr>
                <td colspan="2">Did you register Already? Then <a href="index.jsp">Login Here</a></td>
            </tr>
            </tbody>
        </table>
            </div>
    </center>
</form>
</body>
</html>