<%--
  Created by IntelliJ IDEA.
  User: amybr
  Date: 2025-03-25
  Time: 6:38 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirm Booking</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <script>
        var parameters = new URLSearchParams(window.location.search);
        var hotel_id = parameters.get("hotel_id")
        var roomtype = parameters.get("roomtype")
        var rooms = parameters.get("rooms")
        var checkin = parameters.get("checkin")
        var checkout = parameters.get("checkout")
        var customerid = parameters.get("customerid")

        document.addEventListener("DOMContentLoaded", function() {
            var url = `${window.location.origin}<%= request.getContextPath() %>/confirmDetails?roomtype=${roomtype}&hotel_id=${hotel_id}&checkin=${checkin}&checkout=${checkout}&rooms=${rooms}`
            fetch(url).then(response => response.text()).then(data => {
                document.getElementById("details").innerHTML=data
                document.getElementById("details").innerHTML+=`<h4>SSN: ${customerid}</h4>`
                document.getElementById("details").innerHTML+="<button class='buttons' onclick='bookNow()'>Book now</button>"
            })


        });
        function bookNow(){
            var url = `${window.location.origin}<%= request.getContextPath() %>/bookNow?customerid=${customerid}&rooms=${rooms}&checkin=${checkin}&checkout=${checkout}&hotel_id=${hotel_id}&room_type=${roomtype}`
            var formData = new URLSearchParams();
            formData.append("customerid", customerid);
            formData.append("rooms", rooms);
            formData.append("checkin", checkin);
            formData.append("checkout", checkout);
            formData.append("hotel_id", hotel_id);
            formData.append("room_type", roomtype);


            fetch(url, {
                method: "POST",
                body: `customerid=${customerid}&rooms=${rooms}&checkin=${checkin}&checkout=${checkout}&hotel_id=${hotel_id}&room_type=${roomtype}`,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }).then(response => response.text()).then(data => {
                document.getElementById("details").innerHTML=data
            })
        }
        function returnHome() {
            window.location.href = `index.jsp`
        }

    </script>
</head>
<body>
    <div id="navBar">
        <h3>HOTELIFY</h3>
        <div class="links row">
            <a href="index.jsp" class="link">Home</a>
            <br>
            <a href="employee.jsp" class="link">Employee Page</a>
            <br>
            <a href="Hotels.jsp" class="link">Search For Hotels</a>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <h2>Reserve your Booking</h2>
        </div>
        <div class="row save">
            <div class="col">
                <img class="confirmSea" src="assets/Images/seaside.png">
            </div>
            <div class="col" id="details">
            </div>

        </div>
    </div>
</body>
</html>
