<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-25
  Time: 4:45 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Room as a Customer</title>
    <script>

        var parameters = new URLSearchParams(window.location.search);
        var bookingID = parameters.get("bookingID")

        fetch('getCurrentBooking', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/x-www-form-urlencoded'
            },
            body: 'bookingID=' + bookingID


        }).then(response => response.text()).then( html => {
            document.getElementById('bookingForm').innerHTML = html;
        }).catch(error => {
            console.error('Fetch error:', error);
            document.getElementById('bookingForm').innerHTML
        });

    </script>

</head>

<body>
    <div id="bookingForm"></div>

</body>
</html>
