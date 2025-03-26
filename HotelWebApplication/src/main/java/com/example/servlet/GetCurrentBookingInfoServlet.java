package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getCurrentBooking")
public class GetCurrentBookingInfoServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Matara!92222";     // Change if needed
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            int bookingID = Integer.parseInt(request.getParameter("bookingID"));

            String getCustName = null;
            String custFirstName = null;
            String custLastName = null;


            String getRoomType;
            String roomType = null;

            String getHotel = null;
            String hotelName = null;

            Date checkinDate = null;
            Date checkoutDate = null;


            String getAvailableRoomTypes = null;
            ResultSet availableRoomTypes = null;



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

                int roomTypeID = booking.getInt("room_type_id");
                getRoomType = "SELECT description FROM room_type WHERE room_type_id::TEXT = ? ";
                PreparedStatement stmt3 = con.prepareStatement(getRoomType);
                stmt3.setString(1, String.valueOf(roomTypeID));
                ResultSet rs3 = stmt3.executeQuery();
                while (rs3.next()){
                    roomType = rs3.getString("description");
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
                        "SELECT DISTINCT C.description " +
                        "FROM hotel A " +
                                "INNER JOIN room B " +
                                "ON B.hotel_id = A.hotel_id " +
                                "INNER JOIN room_type C " +
                                "ON C.room_type_id = B.room_type_id " +
                                "WHERE A.hotel_id::TEXT = ?";

                PreparedStatement stmt5 = con.prepareStatement(getAvailableRoomTypes);
                stmt5.setString(1, String.valueOf(hotelID));
                availableRoomTypes = stmt5.executeQuery();


            }

            out.println("<form method='post' action='updateBooking'>");

            out.println("<input type='hidden' name='bookingID' value='" + bookingID + "' />");

            out.println("<label>First Name:</label><br>");
            out.println("<input type='text' name='FirstName' value='" + custFirstName + "' required><br><br>");

            out.println("<label>Last Name:</label><br>");
            out.println("<input type='text' name='LastName' value='" + custLastName + "' required><br><br>");


//

            out.println("<label>Room Type:</label><br>");
            out.println("<select name='roomType' required>");

            out.println("<option selected>" + roomType + "</option>");

            while (availableRoomTypes.next()) {
                String availableRoomType = availableRoomTypes.getString("description");
                out.println("<option value='" + availableRoomType + "'>" + availableRoomType + "</option>");
            }

            out.println("</select><br><br>");

            out.println("<label>Check-in Date:</label><br>");
            out.println("<input type='date' name='Check-in Date' value='" + checkinDate + "' required><br><br>");

            out.println("<label>Check-out Date:</label><br>");
            out.println("<input type='date' name='Check out Date' value='" + checkoutDate + "' required><br><br>");


            out.println("<button type='submit'>Update Booking</button>");
            out.println("</form>");
            out.println("</body></html>");





        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}
