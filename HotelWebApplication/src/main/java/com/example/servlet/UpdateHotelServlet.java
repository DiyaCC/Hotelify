
package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateHotel")
public class UpdateHotelServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "";     // Change if needed
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int hotelID = Integer.parseInt(request.getParameter("hotelID"));
        String streetNumber = request.getParameter("streetNumber");
        String streetName = request.getParameter("streetName");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("ZIP");
        String email = request.getParameter("email");
        int starRating = Integer.parseInt(request.getParameter("starRating"));
        int numRooms = Integer.parseInt(request.getParameter("num_rooms"));


        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);


            String updateHotel = "UPDATE hotel SET street_number=?,street_name=?, city=?, state=?, zip=?, contact_email=?, star_rating=?, num_rooms=? WHERE hotel_id=?";
            PreparedStatement ps = con.prepareStatement(updateHotel);
            ps.setString(1, streetNumber);
            ps.setString(2, streetName);
            ps.setString(3, city);
            ps.setString(4, state);

            ps.setString(5, zip);
            ps.setString(6, email);
            ps.setInt(7, starRating);
            ps.setInt(8, numRooms);
            ps.setInt(9, hotelID);


            ps.executeUpdate();
            con.close();

            out.print("Successfully updated hotel");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Update Failed.</option>");

        }
    }



}

