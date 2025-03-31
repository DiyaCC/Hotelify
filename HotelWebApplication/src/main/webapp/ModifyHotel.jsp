<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-29
  Time: 8:28 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modify Hotel</title>
    <script >
        const parameters = new URLSearchParams(window.location.search);
        const hotelID = parameters.get("hotelID");

        fetch('getCurrentHotelInfo',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'},
            body: 'hotelID=' + encodeURIComponent(hotelID)
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
