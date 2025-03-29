<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-29
  Time: 7:43 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Select a Hotel to Update</title>

    <script>
        fetch('getHotels').then(response => response.text()).then(data => {
          document.getElementById("hotelsContainer").innerHTML = data;
        })

    </script>


</head>
<body>
  <div id="hotelsContainer"></div>


</body>
</html>
