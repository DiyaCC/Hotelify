package com.example.servlet;
import com.example.util.DBConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/checkinBooking")
public class GetCheckinInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {

        //debugging
                String bookingIDParam = request.getParameter("bookingID");
                String employeeIDParam = request.getParameter("employee_id");
        //debugging
                System.out.println("bookingID = " + bookingIDParam);
                System.out.println("employee_id = " + employeeIDParam);


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            int bookingID = Integer.parseInt(bookingIDParam);
            int employeeID = Integer.parseInt(employeeIDParam);

            String getCustName = null;
            String custFirstName = null;
            String custLastName = null;


            String getRoomType;
            String roomType = null;
            String currentRoomType = null;
            int roomTypeID = -1;
            int currentRoomTypeID = -1;

            int roomID = -1;
            int currentRoomID = -1;

            String getHotel = null;
            String hotelName = null;

            Date checkinDate = null;
            Date checkoutDate = null;


            String getAvailableRoomTypes = null;
            ResultSet availableRoomTypes = null;

            String getAvailableRooms = null;
            ResultSet availableRooms = null;

            String getBooking = "SELECT * FROM booking WHERE booking_id::TEXT = ?";
            PreparedStatement stmt = con.prepareStatement(getBooking);
            stmt.setString(1, String.valueOf(bookingID)); //replace the question mark with bookingID
            ResultSet booking = stmt.executeQuery();

            while (booking.next()){
                int custID = booking.getInt("customer_id");
                getCustName = "SELECT first_name, last_name FROM customer inner join person on customer.ssn = person.ssn and customer_id::TEXT = ?";

                PreparedStatement stmt2 = con.prepareStatement(getCustName);
                stmt2.setString(1, String.valueOf(custID));
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()){
                    custFirstName = rs2.getString("first_name");
                    custLastName = rs2.getString("last_name");
                }

                currentRoomTypeID = booking.getInt("room_type_id");
                getRoomType = "SELECT description FROM room_type WHERE room_type_id::TEXT = ? ";
                PreparedStatement stmt3 = con.prepareStatement(getRoomType);
                stmt3.setString(1, String.valueOf(currentRoomTypeID));
                ResultSet rs3 = stmt3.executeQuery();
                while (rs3.next()){
                    currentRoomType = rs3.getString("description");
                }


                int hotelID = booking.getInt("hotel_id");
                getHotel = "SELECT hotel_name FROM hotel WHERE hotel_id::TEXT = ? ";
                PreparedStatement stmt4 = con.prepareStatement(getHotel);
                stmt4.setString(1, String.valueOf(hotelID));
                ResultSet rs4 = stmt4.executeQuery();
                while (rs4.next()){
                    hotelName = rs4.getString("hotel_name");
                }


                checkinDate = booking.getDate("checkin_date");
                checkoutDate = booking.getDate("checkout_date");


                getAvailableRoomTypes =
                        "SELECT DISTINCT C.description, C.room_type_id " +
                                "FROM hotel A " +
                                "INNER JOIN room B " +
                                "ON B.hotel_id = A.hotel_id " +
                                "INNER JOIN room_type C " +
                                "ON C.room_type_id = B.room_type_id " +
                                "WHERE A.hotel_id::TEXT = ?";

                PreparedStatement stmt5 = con.prepareStatement(getAvailableRoomTypes);
                stmt5.setString(1, String.valueOf(hotelID));
                availableRoomTypes = stmt5.executeQuery();

                getAvailableRooms =
                        "SELECT DISTINCT " +
                                "   A.room_id, " +
                                "   A.room_number, " +
                                "   C.description AS room_type, " +
                                "   A.price, " +
                                "   A.capacity, " +
                                "   A.sea_view, " +
                                "   A.mountain_view, " +
                                "   STRING_AGG(distinct E.description, ', ') AS amenities, " +
                                "   STRING_AGG(distinct G.type, ', ') AS extendable " +
                                "FROM room A " +
                                "LEFT JOIN room_type C " +
                                "   ON A.room_type_id = C.room_type_id " +
                                "LEFT JOIN room_amenity D " +
                                "   ON A.room_type_id = D.room_type_id " +
                                "LEFT JOIN amenity E " +
                                "   ON D.amenity_id = E.amenity_id " +
                                "LEFT JOIN Is_Extendable F " +
                                "   ON A.room_type_id = F.room_type_id " +
                                "LEFT JOIN extension G " +
                                "   ON F.extension_id = G.extension_id " +
                                "WHERE A.hotel_id::TEXT = ? " +
                                "   AND A.room_type_id::TEXT = ? " +
                                "   AND A.room_id NOT IN (SELECT DISTINCT B.room_id " +
                                                            "FROM renting B " +
                                                            "WHERE B.hotel_id = A.hotel_id " +
                                                               "AND (?, ?) OVERLAPS (B.checkin_date, B.checkout_date)) " +
                                "GROUP BY A.room_id, A.room_number, C.description, A.price, A.capacity, A.sea_view, A.mountain_view";

                PreparedStatement stmt6 = con.prepareStatement(getAvailableRooms);
                stmt6.setString(1, String.valueOf(hotelID));
                stmt6.setString(2, String.valueOf(currentRoomTypeID));
                stmt6.setDate(3, checkinDate);
                stmt6.setDate(4, checkoutDate);
                availableRooms = stmt6.executeQuery();

            }

            //out.println("<form id='updateBookingForm' class='account'> ");
            out.println("<input type='hidden' name='bookingID' value='" + bookingID + "' />");
            out.println("<form id='updateBookingForm' class='account' method='post' action='ConfirmCheckinServlet'>");
            out.println("<input type='hidden' name='bookingID' value='" + bookingID + "' />");
            out.println("<input type='hidden' name='employee_id' value='" + employeeID + "' />");


            out.println("<h2 class='text'>" + "Hi " + custFirstName + " " + custLastName + "! Complete your checkin at " + hotelName + " below!" + "</h2><br>");

// Start room table
            out.println("<table border='1'>");
            out.println("<tr><th>Select</th><th>Room ID</th><th>Room Number</th><th>Room Type</th><th>Price</th><th>Capacity</th><th>Sea View</th><th>Mountain View</th><th>Amenities</th><th>Extendable</th></tr>");

            while (availableRooms != null && availableRooms.next()) {
                int roomId = availableRooms.getInt("room_id");
                String roomNumber = availableRooms.getString("room_number");
                String fetchedRoomType = availableRooms.getString("room_type");
                String price = availableRooms.getString("price");
                String capacity = availableRooms.getString("capacity");
                String sea_view = availableRooms.getString("sea_view");
                String mountain_view = availableRooms.getString("mountain_view");
                String amenities = availableRooms.getString("amenities");
                String extendable = availableRooms.getString("extendable");

                out.println("<tr>");
                out.println("<td><input type='radio' name='selectedRoom' value='" + roomId + "' required></td>");
                out.println("<td>" + roomId + "</td>");
                out.println("<td>" + roomNumber + "</td>");
                out.println("<td>" + fetchedRoomType + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("<td>" + capacity + "</td>");
                out.println("<td>" + sea_view + "</td>");
                out.println("<td>" + mountain_view + "</td>");
                out.println("<td>" + amenities + "</td>");
                out.println("<td>" + extendable + "</td>");
                out.println("</tr>");
            }

            // Payment Method dropdown
            out.println("<label for='paymentMethod'><b>Select Payment Method:</b></label><br>");
            out.println("<select name='paymentMethod' id='paymentMethod' required>");
            out.println("<option value='' disabled selected>Select one</option>");
            out.println("<option value='credit_card'>Credit Card</option>");
            out.println("<option value='debit_card'>Debit Card</option>");
            out.println("<option value='paypal'>PayPal</option>");
            out.println("<option value='cash'>Cash</option>");
            out.println("</select><br><br>");

            //out.println("<input type='submit' value='Confirm Check-in' />");
            out.println("</table><br>");

            out.println("<input type='submit' value='Confirm Check-in' />");
            out.println("</form>");

            out.println("<form method='post' action='CancelBookingServlet'>");
            out.println("<input type='hidden' name='bookingID' value='" + bookingID + "' />");
            out.println("<button type='submit' class='buttons' style='background-color:red;color:white;margin-top:10px;'>Cancel Booking</button>");
            out.println("</form>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error occurred while loading check-in page.</p>");
        }
    }




}
