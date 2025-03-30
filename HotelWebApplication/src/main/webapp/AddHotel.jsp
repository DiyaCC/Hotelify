<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-29
  Time: 7:45 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add Hotel</title>
  <script>
    fetch('getChains')
            .then(response => response.text())
            .then(data => {
              document.getElementById("chainID").innerHTML = data;
            })
            .catch(err => console.error("Error getting chains:", err));
  </script>
</head>

<body>
  <h2> Add a hotel </h2>

  <form action="addHotel" method = "post">
    <label for="chainID">Hotel Chains: </label><br>
    <select name="chainID" id="chainID" required>
    </select><br><br>

    <label for="hotelName">Hotel Name:</label><br>
    <input type="text" name="hotelName" id="hotelName"  required><br><br>

    <label for="streetNumber">Street Number:</label><br>
    <input type="text" name="streetNumber" id = "streetNumber" required><br><br>

    <label for="streetName">Street Name:</label><br>
    <input type="text" name="streetName" id = 'streetName' required><br><br>

    <label for="city">City:</label><br>
    <input type="text" name="city" id="city" required><br><br>

    <label for="state">State (2-letter):</label><br>
    <input type="text" name="state" id="state" maxlength="2" required><br><br>

    <label for="ZIP">ZIP Code:</label><br>
    <input type="text" name="ZIP" id="ZIP" maxlength="6" required><br><br>

    <label for="email">Contact Email:</label><br>
    <input type="email" name="email" id="email" required><br><br>

    <label for="phone">Contact Phone:</label><br>
    <input type="number" name="phone" id="phone" required><br><br>

    <label for="starRating">Star Rating:</label><br>
    <input type="text" name="starRating" id="starRating" min="1" max="5" required><br><br>

    <label for="numRooms">Number of Expected Rooms:</label><br>
    <input type="text" name="numRooms" id="numRooms" min="1" required><br><br>

    <input type="submit" value="Add Hotel">



  </form>


</body>
</html>
