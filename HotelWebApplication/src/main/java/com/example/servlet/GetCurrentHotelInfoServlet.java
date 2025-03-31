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

@WebServlet("/getCurrentHotelInfo")
public class GetCurrentHotelInfoServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

           int hotelID = Integer.parseInt(request.getParameter("hotelID"));


           String getHotelInfo = "select * from hotel where hotel_id = ?";
           PreparedStatement stmt = con.prepareStatement(getHotelInfo);
           stmt.setInt(1, hotelID);
           ResultSet hotel = stmt.executeQuery();

           while (hotel.next()){
               int chainID = hotel.getInt("chain_id");
               String hotelName = hotel.getString("hotel_name");
               String streetNumber = hotel.getString("street_number");
               String streetName = hotel.getString("street_name");
               String city = hotel.getString("city");
               String state = hotel.getString("state");
               String ZIP = hotel.getString("zip");
               String email = hotel.getString("contact_email");
               int starRating = hotel.getInt("star_rating");
               int num_rooms = hotel.getInt("num_rooms");

               out.println("<form action='updateHotel' method='post'>");

               out.println("<label for='streetNumber'>Street Number:</label><br>");
               out.println("<input type='text' name='streetNumber' id='streetNumber' value='" + streetNumber + "' required><br><br>");

               out.println("<label for='streetName'>Street Name:</label><br>");
               out.println("<input type='text' name='streetName' id='streetName' value='" + streetName + "' required><br><br>");

               out.println("<label for='city'>City:</label><br>");
               out.println("<input type='text' name='city' id='city' value='" + city + "' required><br><br>");

               out.println("<label for='state'>State:</label><br>");
               out.println("<input type='text' name='state' maxlength = '2' id='state' value='" + state + "' required><br><br>");

               out.println("<label for='ZIP'>ZIP Code:</label><br>");
               out.println("<input type='text' name='ZIP' id='ZIP' maxlength = '6' value='" + ZIP + "' required><br><br>");

               out.println("<label for='email'>Contact Email:</label><br>");
               out.println("<input type='email' name='email' id='email' value='" + email + "' required><br><br>");

               out.println("<label for='starRating'>Star Rating:</label><br>");
               out.println("<input type='number' name='starRating' id='starRating' min='1' max='5' value='" + starRating + "' required><br><br>");

               out.println("<label for='num_rooms'>Number of Rooms:</label><br>");
               out.println("<input type='number' name='num_rooms' id='num_rooms' min='1' value='" + num_rooms + "' required><br><br>");

               out.println("<input type='hidden' name='hotelID' value='" + hotelID + "'>");

               out.println("<input type='submit' value='Update Hotel'>");

               out.println("</form>");

               out.println("<form action='deleteHotel' method='post' onsubmit=\"return confirm('Are you sure you want to delete this hotel?');\">");
               out.println("<input type='hidden' name='hotelID' value='" + hotelID + "'>");
               out.println("<input type='submit' value='Delete Hotel' >");
               out.println("</form>");


           }


        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
}}