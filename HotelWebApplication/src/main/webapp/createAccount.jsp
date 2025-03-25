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
    <style> /* this is entirely for fun and completely copied from chatGPT. We will remove it, but it makes the form look soooo niceeeee - Diya
        /* Remove bullets and reset list styles */
        .fields ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        /* Style each list item like a field block */
        .fields li {
            margin-bottom: 15px;
            display: flex;
            flex-direction: column;
        }

        /* Style labels */
        .fields label {
            margin-bottom: 5px;
            font-weight: bold;
            font-size: 14px;
            color: #333;
        }

        /* Style input fields */
        .fields input {
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
            transition: border 0.3s;
        }

        .fields input:focus {
            border-color: #4a90e2;
            outline: none;
        }

        /* Style submit button */
        button[type="submit"] {
            background-color: #4a90e2;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button[type="submit"]:hover {
            background-color: #357ab7;
        }

        /* Container styling */
        .Account-Container {
            max-width: 500px;
            margin: 40px auto;
            padding: 25px;
            background-color: #f9f9f9;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            font-family: Arial, sans-serif;
        }

        .Account-Container h3 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }

    </style>

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
                        <input type="text" id="state" name="state" maxlength = "10" placeholder="Enter your state/province using the code (ON, QU, etc.)">
                    </li>
                    <li>
                        <label for="ZIP">Zip/Postal Code</label>
                        <input type="text" id="ZIP" name="ZIP" placeholder="Enter your ZIP/Postal Code">
                    </li>

                    <li>
                        <label for="SSN">SSN</label>
                        <input type="number" id="SSN" name="SSN" placeholder="Enter your SSN">
                    </li>
                </ul>
            </div>
            <button type = "submit"> Submit Information </button>
        </form>


    </div>



</body>
</html>
