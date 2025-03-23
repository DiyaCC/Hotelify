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

    <script>
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
                }).catch(error => console.error("Error:", error));
            });
        });
        function redirectToCreateAccount() {
            window.location.href = "createAccount.jsp";
        }
    </script>
</head>
<body>
    <div class="SSN-container">
        <h2> Time to Book a Room! </h2>
        <form id="SSNEntry">
            <div class = "SSN_input">
                <label for="SSN">SSN</label>
                <input type="text" id="SSN" name = "SSN" placeholder = "Enter your SSN">

            </div>
            <button type = "submit">Go!</button>
        </form>

        <div id="response"></div>
    </div>



</body>
</html>
