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

@WebServlet("/deleteHotel")
public class DeleteHotelServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int hotelID = Integer.parseInt(request.getParameter("hotelID"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

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

            String deleteHotel = "DELETE from hotel where hotel_id = ?";
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

