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
    <title>Enter SSN</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>

    <script>
        var parameters = new URLSearchParams(window.location.search);
        var hotel_id = parameters.get("hotel_id")
        var roomtype = parameters.get("roomtype")
        var checkin = parameters.get("checkin")
        var checkout = parameters.get("checkout")
        var rooms=parameters.get("rooms")

        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("SSNEntry").addEventListener("submit", function(event) {
                event.preventDefault();
                let SSN = document.getElementById("SSN").value.trim();

                if (SSN === ""){
                    alert("Please provide a valid SSN");
                    return;
                }

                fetch("searchSSN", {
                    method: "POST", // this is a method used for sending data
                    headers: { "Content-Type": "application/x-www-form-urlencoded" }, // indicates the data is coming from a form
                    body: "SSN=" + encodeURIComponent(SSN) // send the data from the input field
                }).then(response => response.text()).then(data => {
                    document.getElementById("response").innerHTML = data;
                    document.getElementById("SSNEntry").classList.add("hidden")
                }).catch(error => console.error("Error:", error));
            });
        });
        function redirectToCreateAccount() {
            window.location.href = `createAccount.jsp?hotel_id=${hotel_id}&roomtype=${roomtype}&checkin=${checkin}&checkout=${checkout}&rooms=${rooms}`;
        }
        function redirectToConfirm(){
            SSN = document.getElementById("SSN").value.trim();
            window.location.href = `ConfirmBooking.jsp?hotel_id=${hotel_id}&roomtype=${roomtype}&checkin=${checkin}&checkout=${checkout}&rooms=${rooms}&customerid=${SSN}`
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
        <div class="row">
            <div class="SSNContainer">
                <h2> Time to Book a Room! </h2>
                <form id="SSNEntry">
                    <div class="SSNContainer">
                        <label for="SSN" >SSN</label>
                        <input type="text" id="SSN" name = "SSN" placeholder = "Enter your SSN">
                    </div>
                    <button class="buttons submit" type = "submit">Go!</button>
                </form>
                <div id="response"></div>
            </div>
        </div>

    </div>



</body>
</html>
