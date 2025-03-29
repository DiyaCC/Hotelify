<%--
  Created by IntelliJ IDEA.
  User: amybr
  Date: 2025-03-29
  Time: 10:21 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Rooms</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var url= `${window.location.origin}<%= request.getContextPath() %>/dropdownHotels`;
            fetch(url).then(response => response.text()).then(data => {
                document.getElementById("dropdown").innerHTML = data;
            }).catch(error => console.error("Error:", error));
        })
        function searchForRooms(){
            const hotel_id = document.getElementById("hotel_choice").value;
            var url= `${window.location.origin}<%= request.getContextPath() %>/hotelRooms?hotel_id=${hotel_id}`;
            fetch(url).then(response => response.text()).then(data => {
                document.getElementById("modifications").classList.remove("hidden")
                document.getElementById("hotelSelect").classList.add("hidden");
                document.querySelector(".SSNContainer").classList.add("hidden");
                document.getElementById("roomSelect").innerHTML=data;
            }).catch(error => console.error("Error:", error));
        }
        function addRoom(){
            var url= `${window.location.origin}<%= request.getContextPath() %>/dropdownRoomTypes`;
            fetch(url).then(response => response.text()).then(data => {
                document.getElementById("modifications").classList.add("hidden")
                document.getElementById("popup").classList.remove("hidden")
                document.getElementById("chooseroomtype").innerHTML = data;
            }).catch(error => console.error("Error:", error));

        }
        function closePopup(){
            document.getElementById("modifications").classList.remove("hidden")
            document.getElementById("popup").classList.add("hidden")
            document.getElementById("room_number").value = ""
            document.getElementById("price").value = ""
            document.getElementById("capacity").value = ""
            document.getElementById("roomTypeChoice").value = ""
            document.getElementById("seaview").checked = false
            document.getElementById("mountainview").checked = false
            document.getElementById("hotel_choice").value = ""
        }
        function uploadRoom(){
            var roomnumber = document.getElementById("room_number").value
            var price = document.getElementById("price").value
            var capacity = document.getElementById("capacity").value
            var roomType = document.getElementById("roomTypeChoice").value
            var seaview = document.getElementById("seaview").checked
            var mountainview = document.getElementById("mountainview").checked
            var hotel_id = document.getElementById("hotel_choice").value;
            if (roomnumber=="" || price=="" || capacity == "" || roomType == ""){
                alert("Please fill out all required fields");
                return;
            } else {
                let bodyData =
                    "roomnumber="+encodeURIComponent(roomnumber)+
                    "&price="+encodeURIComponent(price)+
                    "&capacity="+encodeURIComponent(capacity)+
                    "&roomType="+encodeURIComponent(roomType)+
                    "&seaview="+encodeURIComponent(seaview)+
                    "&mountainview="+encodeURIComponent(mountainview)+
                    "&hotel_id="+encodeURIComponent(hotel_id);
                fetch("addRoom", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body:  bodyData
                }).then(response => response.text()).then(data => {
                    alert(data);
                    searchForRooms();
                    closePopup();
                }).catch(error => console.error("Error:", error));
            }
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
      <div class="SSNbook">
        <div class="SSNContainer" id="hotels">
            <div id="hotelSelect">
                <h3 class="text">Select a hotel</h3>
                <div id="dropdown"></div>
                <br>
                <button class="buttons" onclick="searchForRooms()">Search</button>
            </div>
        </div>
          <div id="modifications" class="hidden container">
              <div class="row">
                  <button class="buttons" onclick="addRoom()">Add room</button>
              </div>
              <br>
              <div class="searchResults">
                  <div id="roomSelect"></div>
              </div>
          </div>
          <div class="hidden" id="popup">
              <div class="popupContainer">
                  <h3 class="text">Add a room</h3>
                  <label for="room_number">Room Number</label>
                  <input type="number" required id="room_number" min="1">
                  <br>
                  <label for="price">Price</label>
                  <input type="number" required id="price" min="1">
                  <br>
                  <label for="capacity">Capacity</label>
                  <input type="number" required id="capacity" min="1">
                  <br>
                  <div id="chooseroomtype"></div>
                  <br>
                  <div class="row">
                      <label for="seaview">Sea view?</label>
                      <input type="checkbox" id="seaview">
                  </div>
                  <br>
                  <div class="row">
                      <label for="mountainview">Mountain view?</label>
                      <input type="checkbox" id="mountainview">
                  </div>
                  <br>
                  <button class="buttons" onclick="uploadRoom()">Add Room</button>
                  <br>
                  <button class="buttons" onclick="closePopup()">Close</button>
              </div>
          </div>
      </div>
    </body>
</html>
