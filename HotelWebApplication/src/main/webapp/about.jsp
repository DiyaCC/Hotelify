<%--
  Created by IntelliJ IDEA.
  User: amybr
  Date: 2025-03-26
  Time: 3:50 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>About us</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <script>
        function fetchView(){
            const checkin = document.getElementById("checkin").value;
            const checkout = document.getElementById("checkout").value;

            const url = `${window.location.origin}<%= request.getContextPath() %>/areas?checkin=${checkin}&checkout=${checkout}`
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
    <div>
        <div class="row">
            <div class="col">
                <img class="about" src="assets/Images/seaside.png">
            </div>
            <div class="col pageContent">
                <h3>Our Vision</h3>
                <p>Our goal is to connect you to your dream vacation. By partnering with many big name franchises, such as Marriott or Hilton, we strive to book high value rooms for low price. We aim to take the troubles of planning out of your day to day life, so you can look forward to some much needed resting and relaxing. </p>
                <h3>Our Partners</h3>
                <p>Hotelify connects you to hotels in multiple cities to ensure your dream vacation. Select some dates to see the number of rooms available per area</p>
                <br>
                <div class="searchBarDestinations">
                    <h2>Dates and Preferences</h2>
                    <div class="row">
                        <div class="col param">
                            <label for="checkin">Check-in</label>
                            <input type="date" id="checkin" class="inputFieldsDestinations" placeholder="YYYY-MM-DD"/>
                        </div>
                        <div class="col param">
                            <label for="checkout">Check-out</label>
                            <input type="date" id="checkout" class="inputFieldsDestinations" placeholder="YYYY-MM-DD"/>
                        </div>
                        <div class="col param">
                            <button class="buttons" onclick="fetchView()">View Areas</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
