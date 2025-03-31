
package com.example.servlet;
import com.example.util.DBConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import com.example.util.DBConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addHotel")
public class AddHotelServlet extends HttpServlet {
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int chainID = Integer.parseInt(request.getParameter("chainID"));
        String hotelName = request.getParameter("hotelName");
        String streetNumber = request.getParameter("streetNumber");
        String streetName = request.getParameter("streetName");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String ZIP = request.getParameter("ZIP");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int starRating = Integer.parseInt(request.getParameter("starRating"));
        int numRooms = Integer.parseInt(request.getParameter("numRooms"));


        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String addHotel = "INSERT INTO Hotel (" +
                    "    chain_id, hotel_name, street_number, street_name,\n" +
                    "    city, state, zip, contact_email, star_rating, num_rooms\n" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(addHotel);
            ps.setInt(1, chainID);
            ps.setString(2, hotelName);
            ps.setString(3, streetNumber);
            ps.setString(4, streetName);
            ps.setString(5, city);
            ps.setString(6, state);
            ps.setString(7, ZIP);
            ps.setString(8, email);
            ps.setInt(9, starRating);
            ps.setInt(10, numRooms);
            ps.executeUpdate();
            ps.close();

            // increase the hotels for that chain by 1
            String reduceNumHotels = "SELECT num_hotels FROM hotel_chain where chain_id = ?";
            PreparedStatement pst5 = con.prepareStatement(reduceNumHotels);
            pst5.setInt(1, chainID);
            ResultSet num = pst5.executeQuery();
            num.next();
            int numHotels = num.getInt("num_hotels");

            // update the chain
            String updateNumHotels = "UPDATE hotel_chain SET num_hotels = ? WHERE chain_id = ?";
            PreparedStatement pst1 = con.prepareStatement(updateNumHotels);
            pst1.setInt(1, numHotels + 1);
            pst1.setInt(2, chainID);
            pst1.executeUpdate();

            out.print(hotelName + " has been added.");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            out.println("Something went wrong. Try again later");

        }
    }



}

