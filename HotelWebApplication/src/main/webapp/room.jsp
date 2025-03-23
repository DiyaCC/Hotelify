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
        var checkin = parameters.get("checkin")
        var checkout = parameters.get("checkout")
        var url = `${window.location.origin}<%= request.getContextPath() %>/availableRoomsForBook?hotel_id=${encodeURIComponent(hotel_id)}&checkin=${encodeURIComponent(checkin)}&checkout=${encodeURIComponent(checkout)}&rooms=${encodeURIComponent(rooms)}`;
        fetch(url).then(response => response.text()).then(data=>{
          const widget = document.getElementById("groupRoomsWidgets")
          if (data==""){
            widget.innerHTML="<h1>No results found :(</h1>";
          }
          else {
            widget.innerHTML=data;
          }
        }).catch(error => console.error("Error finding rooms:", error))
        var capacityURl = `${window.location.origin}<%= request.getContextPath() %>/capacity?hotel_id=${encodeURIComponent(hotel_id)}`
        fetch(capacityURl).then(response => response.text()).then(data => {
          document.getElementById("totalCapacity").innerHTML=data;
        }).catch(error => console.error("Error finding rooms:", error))
      }
      window.onload = fetchRooms;

      function reload() {
        const checkin = document.getElementById('checkin').value;
        const checkout = document.getElementById('checkout').value;
        const rooms = document.getElementById('rooms').value;
        const hotel_id = <%=hotel_id%>
        const widget = document.getElementById("groupRoomsWidgets");
        widget.innerHTML=""
        var url = `${window.location.origin}<%= request.getContextPath() %>/availableRoomsForBook?hotel_id=${encodeURIComponent(hotel_id)}&checkin=${encodeURIComponent(checkin)}&checkout=${encodeURIComponent(checkout)}&rooms=${encodeURIComponent(rooms)}`;
        console.log(url)
        fetch(url).then(response => response.text()).then(data=>{
          if (data==""){
            widget.innerHTML="<h1>No results found :(</h1>";
          }
          else {
            widget.innerHTML=data;
          }
          document.getElementById("roomAvailabilityStatement").innerHTML=`Rooms Available in <%= hotel_name %> from ${checkin} to ${checkout}`
        }).catch(error => console.error("Error finding rooms:", error))
      }
    </script>
</head>
<body>
<div id="navBar">
  <h3>HOTELIFY</h3>
  <a href="index.jsp">Home</a>
  <br>
  <a href="employee.jsp">EmployeePage</a>
  <br>
  <a href="EnterSSN.jsp">Book a Room</a>
</div>
<div id="room">
  <img src="assets/Images/room2.png" class="homePhoto"/>
  <div class="pageContent" id =<%=hotel_id%> >
    <h3 id="roomAvailabilityStatement">Rooms Available in <%= hotel_name %> from <%= String.valueOf(checkin) %> to <%= String.valueOf(checkout)%></h3>
    <div id="totalCapacity">

    </div>
    <div class="searchBarDestinations">
      <h2>Dates and Preferences</h2>
      <div class="row">
        <div class="col param">
          <label for="checkin">Check-in</label>
          <input type="text" id="checkin" class="inputFieldsDestinations" placeholder="YYYY-MM-DD"/>
        </div>
        <div class="col param">
          <label for="checkout">Check-out</label>
          <input type="text" id="checkout" class="inputFieldsDestinations" placeholder="YYYY-MM-DD"/>
        </div>
        <div class="col param">
          <label for="rooms">Rooms</label>
          <input type="text" id="rooms" class="inputFieldsDestinations" placeholder="1"/>
        </div>
        <div class="col param">
          <button class="buttons" onclick="reload()">Change Search</button>
        </div>
      </div>
    </div>
    <div class="groupRoomsWidgets" id="groupRoomsWidgets">
    </div>
  </div>
</div>
</body>
</html>
