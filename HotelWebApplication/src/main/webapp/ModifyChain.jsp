<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-29
  Time: 9:26 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Modify Chain</title>
  <script >
    const parameters = new URLSearchParams(window.location.search);
    const chainID = parameters.get("chainID");

    fetch('getCurrentChainInfo',{
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'},
      body: 'chainID=' + encodeURIComponent(chainID)
    })
            .then(response => response.text())
            .then(data => {
              document.getElementById("formContainer").innerHTML = data;
            })
            .catch(err => console.error(err));

  </script>
</head>
<body>
<div id="formContainer"></div>



</body>
</html>

