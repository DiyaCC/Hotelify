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

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("fields").addEventListener("submit", function(event) {
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
                    document.getElementById("response").innerHTML = data;
                }).catch(error => console.error("Error:", error));
            });
        });


    </script>




</head>
<body>

    <div class = "Account-Container">
        <h3>Create an Account to Book a Room</h3>
        <form id="NewAccount">
            <div class="fields">
                <ul>
                    <li>
                        <label for="firstName">First Name</label>
                        <input type="text" id="firstName" name="firstName" placeholder="Enter your first name">
                    </li>
                    <li>
                        <label for="lastName">Last Name</label>
                        <input type="text" id="lastName" name="lastName" placeholder="Enter your last name">
                    </li>
                    <li>
                        <label for="streetName">Street Name</label>
                        <input type="text" id="streetName" name="streetName" placeholder="Enter your street name">
                    </li>
                    <li>
                        <label for="streetNumber">Street Number</label>
                        <input type="number" id="streetNumber" min="1" step="1" name="streetNumber" placeholder="Enter your street number">
                    </li>
                    <li>
                        <label for="city">City</label>
                        <input type="text" id="city" name="city" placeholder="Enter your city">
                    </li>
                    <li>
                        <label for="state">State/Province</label>
                        <input type="text" id="state" name="state" placeholder="Enter your state/province">
                    </li>
                    <li>
                        <label for="ZIP">Zip/Postal Code</label>
                        <input type="text" id="ZIP" name="ZIP" placeholder="Enter your ZIP/Postal Code">
                    </li>

                    <li>
                        <label for="SSN">Zip/Postal Code</label>
                        <input type="number" id="SSN" name="SSN" placeholder="Enter your SSN">
                    </li>
                </ul>
            </div>
            <button type = "submit"> Submit Information </button>
        </form>


    </div>



</body>
</html>
