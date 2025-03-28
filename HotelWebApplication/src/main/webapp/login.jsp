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
    document.addEventListener("DOMContentLoaded", function() {
      document.getElementById("SSNEntry").addEventListener("submit", function(event) {
        event.preventDefault();
        let SSN = document.getElementById("SSN").value.trim();

        if (SSN === "") {
          alert("Please provide a valid SSN");
          return;
        } else {
          var url=`${window.location.origin}<%= request.getContextPath() %>/findCustomer?SSN=${SSN}`
          fetch(url).then(response => response.text()).then(data => {
            document.getElementById("SSNEntry").classList.add("hidden");
            document.getElementById("response").innerHTML = data;
          }).catch(error => console.error("Error:", error));
        }
      })
    })
    function updateInfo(){
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
      console.log(bodyData)
      setTimeout(10000)
      fetch("UpdateCustomer", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body:  bodyData
      }).then(response => response.text()).then(data => {
        console.log("success")
        document.getElementById("response").innerHTML = data;
      }).catch(error => console.error("Error:", error));
    }
    function deleteInfo(){

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
      <h2> Manage Customer Data </h2>
      <form id="SSNEntry">
        <div class="SSNContainer">
          <label for="SSN" >SSN</label>
          <input type="text" id="SSN" name = "SSN" placeholder = "Enter your SSN">
        </div>
        <button class="buttons submit" type = "submit">Go!</button>
      </form>
      <div id="response">
      </div>
    </div>
  </div>

</div>



</body>
</html>
