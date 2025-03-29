<%--
  Created by IntelliJ IDEA.
  User: diyac
  Date: 2025-03-25
  Time: 4:45 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Room as a Customer</title>
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    <link rel="stylesheet" href="assets/css/styles.css">
    <script>

        const parameters = new URLSearchParams(window.location.search);
        const bookingID = parameters.get("bookingID");
        const employeeID = parameters.get("employee_id");

        fetch('checkinBooking', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/x-www-form-urlencoded'
            },
            body: 'bookingID=' + bookingID


        }).then(response => response.text()).then( html => {
            document.getElementById('checkinBookingForm').innerHTML = html;


            const updateForm = document.getElementById('updateCheckinBookingForm');
            if (updateForm){

                updateForm.addEventListener('submit', function(event){
                    event.preventDefault();

                    const roomTypeID = document.getElementById('roomTypeID')?.value;
                    const checkinDate = document.getElementById('checkinDate')?.value;
                    const checkoutDate = document.getElementById('checkoutDate')?.value;

                    const bodyData =
                        'bookingID=' + encodeURIComponent(bookingID) + '&' +
                        'employee_id=' + encodeURIComponent(employeeID) + '&' +
                        // 'firstName=' + encodeURIComponent(firstName) + '&' +
                        // 'lastName=' + encodeURIComponent(lastName) + '&' +
                        'roomTypeID=' + encodeURIComponent(roomTypeID) + '&' +
                        'checkinDate=' + encodeURIComponent(checkinDate) + '&' +
                        'checkoutDate=' + encodeURIComponent(checkoutDate);

                    fetch('confirmCheckin',{
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: bodyData
                    }).then(response => response.text()).then(result => {
                        alert(result)
                    })
                })
            }




        }).catch(error => {
            console.error('Fetch error:', error);
            document.getElementById('bookingForm').innerHTML
        });

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
    <div id="checkinBookingForm"></div>
</div>

</body>
</html>
