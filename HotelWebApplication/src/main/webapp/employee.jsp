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
    <a href="EnterSSN.jsp" class="link">Book a Room</a>
    <br>
    <a href="Hotels.jsp" class="link">Hotels</a>
    <br>
    <a href="#" id="manageBookingsLink" class="link">Manage Bookings</a>
    <br>
    <a href="#" id="manageRentalsLink" class="link">Manage Rentals</a>
  </div>
</div>

<script>
  // Get URL parameters from the current page URL
  const urlParams = new URLSearchParams(window.location.search);
  const hotelID = urlParams.get('hotel_id');
  const employeeID = urlParams.get('employee_id');

  // Check if hotelID and employeeID are present
  if (hotelID && employeeID) {
    // Get the "Manage Bookings" link element by ID
    const manageBookingsLink = document.getElementById('manageBookingsLink');
    const manageRentalsLink = document.getElementById('manageRentalsLink');
    // Update its href to include the hotel_id and employee_id as query parameters
    manageBookingsLink.href = `manageBookings.jsp?hotel_id=${hotelID}&employee_id=${employeeID}`;
    manageRentalsLink.href = `manageRentals.jsp?hotel_id=${hotelID}&employee_id=${employeeID}`;
  }
</script>

<%--
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
--%>
</body>
</html>
