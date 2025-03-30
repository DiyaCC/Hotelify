<html>
<head>
  <link rel="stylesheet" href="assets/css/styles.css" >
  <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
  <script>
    function searchForBookings(){

      document.getElementById("invalidInputCheckin").classList.add("hidden")
      var checkin = document.getElementById("Check-in").value;
      var valid = validate(checkin)

      if (valid){
        var url = `bookingsByDate?hotel_id=${encodeURIComponent(hotel_id)}&checkin=${encodeURIComponent(checkin)}&employee_id=${encodeURIComponent(employee_id)}`
        fetch(url).then(response => response.text()).then(data => {
          if (!data){
            const notFound = `<div class="widgetRow row"><h4>No Results Found :(</h4></div>`
            document.getElementById("results").innerHTML = notFound;

          } else {
            document.getElementById("results").innerHTML = data;
          }
        }).catch(error => console.error("Error finding rooms:", error))
      }
    }
    function validate(hotel_id, checkin){
      var valid = true;
      try{
        new Date(checkin);
        if (checkin===""){
          document.getElementById("invalidInputCheckin").classList.remove("hidden")
        }
      } catch {
        valid=false;
        document.getElementById("invalidInputCheckin").classList.remove("hidden")
      }
      return valid
    }

  </script>


</head>
<body>

<script>
  // Get URL parameters from the current page URL
  const urlParams = new URLSearchParams(window.location.search);
  const hotel_id = urlParams.get('hotel_id');  // Retrieve the hotel_id parameter
  const employee_id = urlParams.get('employee_id');  // Retrieve the employee_id parameter
</script>

<div id="navBar">
  <h3>HOTELIFY</h3>
  <%--
  <div class="links row">
    <a href="employee.jsp" class="link">Employee Page</a>
    <br>
    <a href="EnterSSN.jsp" class="link">Book a Room</a>
    <br>
    <a href="Hotels.jsp" class="link">Hotels</a>
  </div>
  --%>
</div>
<div id="home">
  <img src="assets/Images/HomePhoto.png" class="homePhoto">
  <div class="pageContent">
    <div class="searchBarDestinations">
      <div class="row">
        <h2>Manage Bookings</h2>
      </div>
      <div class="row inputDestinations">
        <%--
        <div class="col param">
          <div class="row">
            <img src="assets/Images/Error.png" class="hidden invalidInput" id="invalidInputDestination">
            <label for="destination">Destination</label>
          </div>
          <input type="text" id="destination" class="inputFieldsDestinations" placeholder="Punta Cana"/>
        </div>
        --%>
        <div class="col param">
          <div class="row">
            <img src="assets/Images/Error.png" class="hidden invalidInput" id="invalidInputCheckin">
            <label for="Check-in">Check-in</label>
          </div>
          <input type="text" id="Check-in" class="inputFieldsDestinations" placeholder="YYYY-MM-DD"/>
        </div>
        <%--
        <div class="col param">
          <div class="row">
            <img src="assets/Images/Error.png" class="hidden invalidInput" id="invalidInputCheckout">
            <label for="Check-out">Check-out</label>
          </div>
          <input type="text" id="Check-out" class="inputFieldsDestinations" placeholder="YYYY-MM-DD"/>
        </div>
        <div class="col param">
          <div class="row">
            <img src="assets/Images/Error.png" class="hidden invalidInput" id="invalidInputRooms">
            <label for="Rooms">Rooms</label>
          </div>
          <input type="text" id="Rooms" class="inputFieldsDestinations" placeholder="1"/>
        </div>
        <div class="col param">
          <label for="Star Rating">Star Rating</label>
          <div class="row" id="Star Rating">
            <select class="star" id="stardrop">
              <option value="1">1 star</option>
              <option value="2">2 stars</option>
              <option value="3">3 stars</option>
              <option value="4">4 stars</option>
              <option value="5">5 stars</option>
            </select>
          </div>
        </div>
        --%>
        <div class="col param">
          <button class="buttons" id="searchForBookings" onClick="searchForBookings()">Search</button>
        </div>
      </div>
    </div>
    <div class="results" id="results"></div>
  </div>
</div>
</body>
</html>
