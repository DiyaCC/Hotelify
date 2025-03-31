<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-29
  Time: 8:57 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add a Hotel Chain</title>
</head>
<body>

<form action="addChain" method = "post">

    <label for="chainName">Chain name:</label><br>
    <input type="text" name="chainName" id="chainName"  required><br><br>

    <label for="numHotels">Number of Expected Hotels:</label><br>
    <input type="text" name="numHotels" id="numHotels" min="1" required><br><br>

    <input type="submit" value="Add Chain">


</form>


</body>
</html>
