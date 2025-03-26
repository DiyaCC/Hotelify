<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Allhotels</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <script>
        function backToRooms(hotel_id){

            var checkin = document.getElementById("checkin").value || '0000-00-00'
            var checkout = document.getElementById("checkout").value || '0000-00-00'

            var rooms=0

            fetch(`${window.location.origin}<%= request.getContextPath() %>/hotelID?hotel_id=${hotel_id}`).then(response => response.text()).then(data => {
                console.log(encodeURIComponent(data.trim()))
                var url = `room.jsp?hotel_id=${encodeURIComponent(hotel_id)}&hotel_name=${encodeURIComponent(data.trim())}&checkin=${encodeURIComponent(checkin)}&checkout=${encodeURIComponent(checkout)}&rooms=${encodeURIComponent(rooms)}`
                window.location.href = url
            })

        }
        function fetchAllHotels(){
            var url = window.location.origin + "/HotelWebApplication/allHotels";
            fetch(url).then(response => response.text()).then(data=>{
              document.getElementById("results").innerHTML=data
            }).catch(error => console.error("Error finding rooms:", error))
        }
        window.onload = fetchAllHotels;

        function applyFilters(){
            var destination = document.getElementById("destination").value
            var chain = document.getElementById("chain").value
            var checkin = document.getElementById("checkin").value
            var checkout = document.getElementById("checkout").value
            var min = document.getElementById("minprice").value
            var max = document.getElementById("maxprice").value
            var rooms = document.getElementById("hotelcapacity").value
            var capacity = document.getElementById("roomcapacity").value
            var star = document.getElementById("stars").value
            var url = `${window.location.origin}<%= request.getContextPath() %>/allFilters?city=${destination}&hotelChain=${chain}&checkin=${checkin}&checkout=${checkout}&minprice=${min}&maxprice=${max}&totalHotelRooms=${rooms}&roomSize=${capacity}&stars=${star}`
            fetch(url).then(response => response.text()).then(data => {
                document.getElementById("results").innerHTML=data;
            }).catch(error => console.error("Error finding rooms:", error))


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
        </div>
    </div>
    <div class="row content">
        <div class="col filters">
            <label for="destination">Area</label>
            <input type="text" id="destination" placeholder="New York"/>
            <label for="chain">Hotel Chain</label>
            <input type="text" id="chain" placeholder="Marriot"/>
            <div class="row">
                <div class="col">
                    <label for="checkin">Check in</label>
                    <input type="date" id="checkin"/>
                </div>
                <div class="col">
                    <label for="checkout">Check out</label>
                    <input type="date" id="checkout"/>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <label for="minprice">Minimum Price</label>
                    <input type="number" id="minprice" placeholder="100">

                </div>
                <div class="col">
                    <label for="maxprice">Maximum Price</label>
                    <input type="number" id="maxprice" placeholder="500">
                </div>
            </div>
            <label for="hotelcapacity" >Total hotel rooms</label>
            <input id="hotelcapacity" placeholder="5" type="number">
            <label for="roomcapacity">Room size</label>
            <input id="roomcapacity" type="number" placeholder="2">
            <label for="stars">Stars</label>
            <input id="stars" type="number" min="1" max="5" placeholder="5">
            <button class="buttons" onclick="applyFilters()">Apply Filters</button>
        </div>
        <div class="col resultsAllHotels" id="results">

        </div>
    </div>
</body>
</html>
