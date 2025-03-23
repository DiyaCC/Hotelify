<%@ page import="java.sql.Date" %><%--
  Created by IntelliJ IDEA.
  User: amybr
  Date: 2025-03-19
  Time: 8:13 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%
  int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
  String hotel_name = request.getParameter("hotel_name");
  int rooms = Integer.parseInt(request.getParameter("rooms"));
  Date checkin = Date.valueOf(request.getParameter("checkin"));
  Date checkout = Date.valueOf(request.getParameter("checkout"));
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="assets/css/styles.css" >
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <script>
      function fetchRooms() {
        var parameters = new URLSearchParams(window.location.search);
        var hotel_id = parameters.get("hotel_id")
        var rooms = parameters.get("rooms")
        var checkin = new Date(parameters.get("checkin"))
        var checkout = new Date(parameters.get("checkout"))
        fetch(window.location.origin + "<%= request.getContextPath() %>/availableRoomsForBook").then(response => response.text()).then(data=>{
          console.log(data)
        }).catch(error => console.error("Error finding rooms:", error))
      }
      window.onload = fetchRooms;
    </script>
</head>
<body>
<div id="navBar">
  <h3>HOTELS</h3>
  <a href="employee.jsp">EmployeePage</a>
</div>
<div id="room">
  <img src="assets/Images/room2.png" class="homePhoto"/>
  <div class="pageContent" id =<%=hotel_id%> >
    <h2>Rooms Available in <%= hotel_name %> from <%= String.valueOf(checkin) %> to <%= String.valueOf(checkout)%></h2>
    <div class="searchBarDestinations">
      <h2>Dates and Preferences</h2>
      <div class="row">
        <div class="col param">
          <label for="Check-in-room">Check-in</label>
          <input type="text" id="Check-in-room" class="inputFieldsDestinations" placeholder="YYYY/MM/DD"/>
        </div>
        <div class="col param">
          <label for="Check-out-room">Check-out</label>
          <input type="text" id="Check-out-room" class="inputFieldsDestinations" placeholder="YYYY/MM/DD"/>
        </div>
        <div class="col param">
          <label for="Rooms-room">Rooms</label>
          <input type="text" id="Rooms-room" class="inputFieldsDestinations" placeholder="1"/>
        </div>
        <div class="col param">
          <button class="buttons">Change Search</button>
        </div>
      </div>
    </div>
    <div class="groupRoomsWidgets">
      <div class="widgetRow row">
        <div class="roomWidget col">
          <h2>Standard Room</h2>
          <h4>Free WIFI | TV | Air Conditioning</h4>
          <h4>$200 per night</h4>
          <button class="buttons bookRoomButton">Book Now</button>
        </div>
        <div class="roomWidget col">
          <h2>Standard Room</h2>
          <h4>Free WIFI | TV | Air Conditioning</h4>
          <h4>$200 per night</h4>
          <button class="buttons bookRoomButton">Book Now</button>
        </div>
        <div class="roomWidget col">
          <h2>Standard Room</h2>
          <h4>Free WiFi | TV | Air Conditioning</h4>
          <h4>$200 per night</h4>
          <button class="buttons bookRoomButton">Book Now</button>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
