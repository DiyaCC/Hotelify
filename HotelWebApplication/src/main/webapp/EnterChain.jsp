<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-29
  Time: 9:42 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Select a Chain to Update</title>

  <script>
    fetch('getChains').then(response => response.text()).then(data => {
      document.getElementById("chainsContainer").innerHTML = data;
    })

  </script>


</head>
<body>
<div id="chainsContainer"></div>


</body>
</html>