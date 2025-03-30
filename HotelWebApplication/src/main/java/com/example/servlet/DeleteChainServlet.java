package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/deleteChain")
public class DeleteChainServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Matara!92222";     // Change if needed
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int chainID = Integer.parseInt(request.getParameter("chainID"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            String getHotels = "SELECT hotel_id FROM hotel where chain_id = ?";
            PreparedStatement doStmt = con.prepareStatement(getHotels);
            doStmt.setInt(1, chainID);
            ResultSet hotels = doStmt.executeQuery();

            while(hotels.next()){
                int hotelID = hotels.getInt("hotel_id");

                // send the deleted bookings to archive
                String getHotelBookings = "SELECT * FROM booking WHERE hotel_id = ?";
                PreparedStatement pst = con.prepareStatement(getHotelBookings);
                pst.setInt(1, hotelID);
                ResultSet rs = pst.executeQuery();
                while(rs.next()){

                    int bookingID = rs.getInt("booking_id");
                    int customerID = rs.getInt("customer_id");
                    int roomTypeID = rs.getInt("room_type_id");
                    Date checkinDate = rs.getDate("checkin_date");
                    Date checkoutDate = rs.getDate("checkout_date");
                    boolean cancelled = false;
                    Timestamp confirmationDate = Timestamp.valueOf(LocalDateTime.now());


                    String addToArchive = "INSERT INTO archive_booking (booking_id, customer_id, room_type_id, confirmation_date, checkin_date, checkout_date, cancelled) VALUES (?,?,?,?,?,?,?)";
                    PreparedStatement pst2 = con.prepareStatement(addToArchive);
                    pst2.setInt(1, bookingID);
                    pst2.setInt(2, customerID);
                    pst2.setInt(3, roomTypeID);
                    pst2.setTimestamp(4, confirmationDate);
                    pst2.setDate(5, checkinDate);
                    pst2.setDate(6, checkoutDate);
                    pst2.setBoolean(7, cancelled);
                    pst2.executeUpdate();
                }

                // send the deleted rentings to archive
                String getHotelRentings = "SELECT * FROM renting WHERE hotel_id = ?";
                PreparedStatement pst3 = con.prepareStatement(getHotelRentings);
                pst3.setInt(1, hotelID);
                ResultSet rentings = pst3.executeQuery();
                while(rentings.next()){

                    int rentingID = rentings.getInt("renting_id");
                    int employeeID = rentings.getInt("employee_id");
                    int bookingID = rentings.getInt("booking_id");
                    int customerID = rentings.getInt("customer_id");
                    int roomTypeID = rentings.getInt("room_type_id");
                    int roomID = rentings.getInt("room_id");
                    Date checkinDate = rentings.getDate("checkin_date");
                    Date checkoutDate = rentings.getDate("checkout_date");
                    boolean cancelled = false;

                    String addToArchive = "INSERT INTO archive_renting VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst4 = con.prepareStatement(addToArchive);
                    pst4.setInt(1, rentingID);
                    pst4.setInt(2, employeeID);
                    pst4.setInt(3, bookingID);
                    pst4.setInt(4, customerID);
                    pst4.setInt(5, roomTypeID);
                    pst4.setInt(6, roomID);
                    pst4.setDate(7, checkinDate);
                    pst4.setDate(8, checkoutDate);
                    pst4.setBoolean(9, cancelled);
                    pst4.executeUpdate();
                }
            }

            // deleting all the hotels associated to this chain
            String deleteHotels = "DELETE FROM hotel WHERE chain_id = ?";
            PreparedStatement deletingStmt = con.prepareStatement(deleteHotels);
            deletingStmt.setInt(1, chainID);
            deletingStmt.executeUpdate();

            // delete the chain iteslef
            String deleteChain = "DELETE from hotel_chain where chain_id=?";
            PreparedStatement ps = con.prepareStatement(deleteChain);
            ps.setInt(1, chainID);
            ps.executeUpdate();


            out.print("Chain has been deleted.");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}

