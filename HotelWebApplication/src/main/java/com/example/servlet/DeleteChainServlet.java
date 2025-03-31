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
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/deleteChain")
public class DeleteChainServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int chainID = Integer.parseInt(request.getParameter("chainID"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String getHotels = "SELECT hotel_id FROM hotel where chain_id = ?";
            PreparedStatement doStmt = con.prepareStatement(getHotels);
            doStmt.setInt(1, chainID);
            ResultSet hotels = doStmt.executeQuery();

            while(hotels.next()){
                int hotelID = hotels.getInt("hotel_id");

                // set cancelled booking to true
                String getHotelBookings = "SELECT * FROM booking WHERE hotel_id = ?";
                PreparedStatement pst = con.prepareStatement(getHotelBookings);
                pst.setInt(1, hotelID);
                ResultSet rs = pst.executeQuery();
                while(rs.next()){

                    int bookingID = rs.getInt("booking_id");
                    boolean cancelled = true;

                    String cancelledSQL = "UPDATE booking SET cancelled = ? WHERE booking_id = ?";
                    PreparedStatement cancelledStmt = con.prepareStatement(cancelledSQL);
                    cancelledStmt.setBoolean(1, cancelled);
                    cancelledStmt.setInt(2, bookingID);
                    cancelledStmt.executeUpdate();
                }

                // set cancelled renting to true
                String getHotelRentings = "SELECT * FROM renting WHERE hotel_id = ?";
                PreparedStatement pst3 = con.prepareStatement(getHotelRentings);
                pst3.setInt(1, hotelID);
                ResultSet rentings = pst3.executeQuery();
                while(rentings.next()){

                    int rentingID = rentings.getInt("renting_id");
                    boolean cancelled = true;

                    String cancelledSQL2 = "UPDATE renting SET cancelled = ? WHERE renting_id = ?";
                    PreparedStatement cancelledStmt2 = con.prepareStatement(cancelledSQL2);
                    cancelledStmt2.setBoolean(1, cancelled);
                    cancelledStmt2.setInt(2, rentingID);
                    cancelledStmt2.executeUpdate();
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

