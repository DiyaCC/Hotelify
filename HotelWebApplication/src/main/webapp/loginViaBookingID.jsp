<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-20
  Time: 8:22 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter BookingID</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("bookingIDEntry").addEventListener("submit", function(event) {
                event.preventDefault();
                let bookingID = document.getElementById("bookingID").value.trim();

                if (bookingID === ""){
                    alert("Please provide a valid Booking ID");
                    return;
                }

                fetch("searchBookingID", {
                    method: "POST", // this is a method used for sending data
                    headers: { "Content-Type": "application/x-www-form-urlencoded" }, // indicates the data is coming from a form
                    body: "bookingID=" + encodeURIComponent(bookingID) // send the data from the input field
                }).then(response => response.text()).then(data => {
                    if (data.startsWith("REDIRECT:")) {
                        let url = data.replace("REDIRECT:", ""); // change this

                        window.location.href = url;
                    } else {
                        document.getElementById("response").innerHTML = data;
                    }
                }).catch(error => console.error("Error:", error));
            });
        });
        function redirectToEditBooking() {
            window.location.href = "EditBooking.jsp";
        }
    </script>
</head>
<body>
<div id="navBar">
    <h3>HOTELIFY</h3>
    <div class="links row">
        <a href="index.jsp">Home</a>
        <br>
        <a href="employee.jsp" class="link">Employee Page</a>
        <br>
        <a href="Hotels.jsp" class="link">Hotels</a>
    </div>
</div>
<div>
    <div class="pageContent editBooking">
        <div class="editBooking-container">
            <h2> Time to Book a Room! </h2>
            <form id="bookingIDEntry" class="editForm">
                <div class = "BookingID_input">
                    <label for="bookingID" class="editFormContents">Booking ID</label>
                    <input type="text" id="bookingID" name = "bookingID" placeholder = "Enter your Booking ID" class="editFormContents">
                </div>
                <button type = "submit" class="buttons editFormContents">Go!</button>
            </form>
            <div id="response"></div>
        </div>
    </div>

</div>



</body>
</html>
