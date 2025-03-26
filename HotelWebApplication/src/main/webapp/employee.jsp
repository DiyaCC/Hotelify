<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Employee Room Booking</title>
</head>
<body>
<div id="navBar">
  <h3>HOTELIFY</h3>
  <div class="links row">
    <a href="index.jsp">Home</a>
    <br>
    <a href="Hotels.jsp" class="link">Hotels</a>
  </div>
</div>
<h2>Confirm Room Bookings</h2>
  <div id = "roomSelect"> </div>
  <script>
    function loadBookings() {
      fetch("availableRooms").then(response => response.text()).then( data => {
        document.getElementById("roomSelect").innerHTML = data;
      }).catch(error => console.error("Error finding rooms:", error))
    }
    window.onload = loadBookings;
  </script>

</body>
</html>
