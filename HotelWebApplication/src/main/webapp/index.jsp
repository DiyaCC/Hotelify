<html>
    <head>
        <link rel="stylesheet" href="assets/css/styles.css" >
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    <body>
        <div id="navBar">
            <h2>HOTELS</h2>
        </div>
        <div id="home">
            <img src="assets/Images/HomePhoto.png" class="homePhoto">
            <div class="pageContent">
                <div class="searchBarDestinations">
                    <div class="row">
                        <h2>Your Next Adventure Awaits</h2>
                    </div>
                    <div class="row inputDestinations">
                        <div class="col param">
                            <label for="destination">Destination</label>
                            <input type="text" id="destination" class="inputFieldsDestinations" placeholder="Punta Cana"/>
                        </div>
                        <div class="col param">
                            <label for="Check-in">Check-in</label>
                            <input type="text" id="Check-in" class="inputFieldsDestinations" placeholder="YYYY/MM/DD"/>
                        </div>
                        <div class="col param">
                            <label for="Check-out">Check-out</label>
                            <input type="text" id="Check-out" class="inputFieldsDestinations" placeholder="YYYY/MM/DD"/>
                        </div>
                        <div class="col param">
                            <label for="Rooms">Guests</label>
                            <input type="text" id="Rooms" class="inputFieldsDestinations" placeholder="2"/>
                        </div>
                        <div class="col param">
                            <label for="Star Rating">Star Rating</label>
                            <div class="row" id="Star Rating">
                                <select class="star">
                                    <option value="1">1 star</option>
                                    <option value="2">2 stars</option>
                                    <option value="3">3 stars</option>
                                    <option value="4">4 stars</option>
                                    <option value="5">5 stars</option>
                                </select>
                            </div>
                        </div>
                        <div class="col param">
                            <button class="buttons">Search</button>
                        </div>
                    </div>
                </div>
                <div class="results">
                    <div class="row">
                        <div class="col">
                            <div class="hotelWidget">
                                <img src="assets/Images/Hotelroom.png"/>
                                <div class="row">
                                    <div class="col">
                                        <h2>Hotel California</h2>
                                        <h3>Hilton express</h3>
                                    </div>
                                    <div class="col">
                                        <button class="buttons">View Rooms</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="hotelWidget">
                                <img src="assets/Images/Hotelroom.png"/>
                                <div class="row">
                                    <div class="col">
                                        <h2>Hotel California</h2>
                                        <h3>Hilton express</h3>
                                    </div>
                                    <div class="col">
                                        <button class="buttons">View Rooms</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="room" class="hidden">
            <img src="assets/Images/room2.png" class="homePhoto"/>
            <div class="pageContent" >
                <h2>Rooms Available in {Hotel} from {Checkin} to {Checkout}</h2>
                <div class="searchBarDestinations">
                    <h2>Dates and Preferences</h2>
                    <div class="row">
                        <div class="col param">
                            <label for="Check-in-room">Check-in</label>
                            <input type="text" id="Check-in-room" class="inputFieldsDestinations" placeholder="YYYY/MM/DD"/>
                        </div>
                        <div class="col param">
                            <label for="Check-out-room">Check-out</label>
                            <input type="text" id="Check-out-room" class="inputFieldsDestinations" placeholder="YYYY/MM/DD"/>
                        </div>
                        <div class="col param">
                            <label for="Rooms-room">Guests</label>
                            <input type="text" id="Rooms-room" class="inputFieldsDestinations" placeholder="2"/>
                        </div>
                        <div class="col param">
                            <button class="buttons">Change Search</button>
                        </div>
                    </div>
                </div>
                <div class="groupRoomsWidgets">
                    <div class="widgetRow row">
                        <div class="roomWidget col">
                            <h2>Standard Room</h2>
                            <h4>Free WIFI | TV | Air Conditioning</h4>
                            <h4>$200 per night</h4>
                            <button class="buttons bookRoomButton">Book Now</button>
                        </div>
                        <div class="roomWidget col">
                            <h2>Standard Room</h2>
                            <h4>Free WIFI | TV | Air Conditioning</h4>
                            <h4>$200 per night</h4>
                            <button class="buttons bookRoomButton">Book Now</button>
                        </div>
                        <div class="roomWidget col">
                            <h2>Standard Room</h2>
                            <h4>Free WiFi | TV | Air Conditioning</h4>
                            <h4>$200 per night</h4>
                            <button class="buttons bookRoomButton">Book Now</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
