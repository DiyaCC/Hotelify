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
    <style> /* this is entirely for fun and was copied from chat GPT. We will definitely remove it, but it looks sooo nice :))))) - Diya
        /* Reset and base styles */
    body {
        margin: 0;
        padding: 0;
        font-family: Arial, sans-serif;
        background-color: #f2f2f2;
    }

    /* Navigation bar styling */
    #navBar {
        background-color: #4a90e2;
        color: white;
        padding: 15px 25px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    #navBar h3 {
        margin: 0;
        font-size: 24px;
    }

    #navBar .links {
        display: flex;
        gap: 20px;
    }

    #navBar .links a {
        color: white;
        text-decoration: none;
        font-weight: bold;
    }

    #navBar .links a:hover {
        text-decoration: underline;
    }

    /* SSN Form Container */
    .SSN-container {
        max-width: 400px;
        margin: 60px auto;
        background-color: #fff;
        padding: 25px 30px;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        text-align: center;
    }

    .SSN-container h2 {
        margin-bottom: 20px;
        color: #333;
    }

    /* Input field styling */
    .SSN_input {
        display: flex;
        flex-direction: column;
        margin-bottom: 20px;
        text-align: left;
    }

    .SSN_input label {
        margin-bottom: 8px;
        font-weight: bold;
        color: #333;
    }

    .SSN_input input {
        padding: 10px;
        font-size: 14px;
        border: 1px solid #ccc;
        border-radius: 5px;
        transition: border

    </style>

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
<div class="bookingID-container">
    <h2> Time to Book a Room! </h2>
    <form id="bookingIDEntry">
        <div class = "BookingID_input">
            <label for="bookingID">Booking ID</label>
            <input type="text" id="bookingID" name = "bookingID" placeholder = "Enter your Booking ID">

        </div>
        <button type = "submit">Go!</button>
    </form>

    <div id="response"></div>
</div>



</body>
</html>
