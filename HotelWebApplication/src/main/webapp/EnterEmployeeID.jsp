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
    <title>Enter Employee ID</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("Employee_ID_Entry").addEventListener("submit", function(event) {
                event.preventDefault();
                let Employee_ID = document.getElementById("Employee_ID").value.trim();

                if (Employee_ID === ""){
                    alert("Please provide a valid Employee ID");
                    return;
                }

                fetch("search_Employee_ID", {
                    method: "POST", // this is a method used for sending data
                    headers: { "Content-Type": "application/x-www-form-urlencoded" }, // indicates the data is coming from a form
                    body: "Employee_ID=" + encodeURIComponent(Employee_ID) // send the data from the input field
                }).then(response => response.text()).then(data => {
                    document.getElementById("response").innerHTML = data;
                }).catch(error => console.error("Error:", error));
            });
        });
        function redirectToEmployeeBookings(hotelID, Employee_ID) {
            window.location.href = `employee.jsp?hotel_id=${hotelID}&employee_id=${Employee_ID}`;
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
        <a href="Hotels.jsp" class="link">Hotels</a>
    </div>
</div>
<div class="pageContent">
    <h2> Time to Login! </h2>
    <form id="Employee_ID_Entry">
        <div class = "Employee_ID_input">
            <label for="Employee_ID">Employee_ID</label>
            <input type="text" id="Employee_ID" name = "Employee_ID" placeholder = "Enter your Employee_ID">

        </div>
        <button type = "submit">Go!</button>
    </form>

    <div id="response"></div>
</div>
</body>
</html>
