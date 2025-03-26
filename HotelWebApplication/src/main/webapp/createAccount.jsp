<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-21
  Time: 12:55 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create an Account</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("NewAccount").addEventListener("submit", function(event) {
                event.preventDefault();
                let firstName = document.getElementById("firstName").value.trim();
                let lastName = document.getElementById("lastName").value.trim();
                let streetName = document.getElementById("streetName").value.trim();
                let streetNumber = document.getElementById("streetNumber").value.trim();
                let city = document.getElementById("city").value.trim();
                let state = document.getElementById("state").value.trim();
                let ZIP = document.getElementById("ZIP").value.trim();
                let SSN = document.getElementById("SSN").value.trim();

                if (SSN === "" || firstName === "" || lastName === "" || streetName === "" || streetNumber === "" || city === ""
                    || state === "" || ZIP === "" || SSN === ""){
                    alert("Please fill all fields.");
                    return;
                }

                let bodyData =
                    "firstName=" + encodeURIComponent(firstName) +
                    "&lastName=" + encodeURIComponent(lastName) +
                    "&streetName=" + encodeURIComponent(streetName) +
                    "&streetNumber=" + encodeURIComponent(streetNumber) +
                    "&city=" + encodeURIComponent(city) +
                    "&state=" + encodeURIComponent(state) +
                    "&ZIP=" + encodeURIComponent(ZIP) +
                    "&SSN=" + encodeURIComponent(SSN);

                fetch("addCustomerAccountServlet", {
                    method: "POST", // this is a method used for sending data
                    headers: { "Content-Type": "application/x-www-form-urlencoded" }, // indicates the data is coming from a form
                    body:  bodyData
                }).then(response => response.text()).then(data => {
                    document.getElementById("results").innerHTML = data;

                }).catch(error => console.error("Error:", error));
            });
        });

        function redirectToBook(){
            var parameters = new URLSearchParams(window.location.search);
            var hotel_id = parameters.get("hotel_id")
            var roomtype = parameters.get("roomtype")
            var checkin = parameters.get("checkin")
            var checkout = parameters.get("checkout")
            var rooms=parameters.get("rooms")
            let SSN = document.getElementById("SSN").value.trim();
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
    <div class = "accountPage">
        <div class="account">
            <h3 class="text">Create an Account to Book a Room</h3>
            <form id="NewAccount">
                <div class="fields" id="fields">
                    <div class="field">
                        <label for="firstName">First Name</label>
                        <input class="field" type="text" id="firstName" name="firstName" placeholder="Enter your first name">
                    </div>
                    <div class="field">
                        <label for="lastName">Last Name</label>
                        <input class="field" type="text" id="lastName" name="lastName" placeholder="Enter your last name">
                    </div>
                    <div class="field">
                        <label for="streetName">Street Name</label>
                        <input class="field" type="text" id="streetName" name="streetName" placeholder="Enter your street name">
                    </div>
                    <div class="field">
                        <label for="streetNumber">Street Number</label>
                        <input class="field" type="number" id="streetNumber" min="1" step="1" name="streetNumber" placeholder="Enter your street number">
                    </div>
                    <div class="field">
                        <label for="city">City</label>
                        <input class="field" type="text" id="city" name="city" placeholder="Enter your city">
                    </div>
                    <div class="field">
                        <label for="state">State/Province</label>
                        <input class="field" type="text" id="state" name="state" maxlength = "10" placeholder="Enter your state/province using the code (ON, QU, etc.)">
                    </div>
                    <div class="field">
                        <label for="ZIP">Zip/Postal Code</label>
                        <input class="field" type="text" id="ZIP" name="ZIP" placeholder="Enter your ZIP/Postal Code">
                    </div>
                    <div class="field">
                        <label for="SSN">SSN</label>
                        <input class="field" type="number" id="SSN" name="SSN" placeholder="Enter your SSN">
                    </div>
                    <button type = "submit" class="buttons field"> Submit Information </button>
                </div>

            </form>
            <div id="results"></div>
        </div>

    </div>



</body>
</html>
