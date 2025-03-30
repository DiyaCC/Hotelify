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

@WebServlet("/deleteHotel")
public class DeleteHotelServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "";     // Change if needed
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int hotelID = Integer.parseInt(request.getParameter("hotelID"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            // get chainID
            String getChainID = "SELECT chain_id FROM hotel where hotel_id=" + hotelID;
            Statement stmt = con.createStatement();
            ResultSet chain = stmt.executeQuery(getChainID);
            chain.next();
            int chainID = chain.getInt("chain_id");

            // reduce the number of hotels in the chain
            // get the current number of hotels
            String reduceNumHotels = "SELECT num_hotels FROM hotel_chain where chain_id = ?";
            PreparedStatement pst5 = con.prepareStatement(reduceNumHotels);
            pst5.setInt(1, chainID);
            ResultSet num = pst5.executeQuery();
            num.next();
            int numHotels = num.getInt("num_hotels");

            // update the chain
            String updateNumHotels = "UPDATE hotel_chain SET num_hotels = ? WHERE chain_id = ?";
            PreparedStatement pst1 = con.prepareStatement(updateNumHotels);
            pst1.setInt(1, numHotels -1);
            pst1.setInt(2, chainID);
            pst1.executeUpdate();


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


            String deleteHotel = "DELETE from hotel where hotel_id=?";
            PreparedStatement ps = con.prepareStatement(deleteHotel);
            ps.setInt(1, hotelID);
            ps.executeUpdate();


            out.print("Hotel deleted.");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}

