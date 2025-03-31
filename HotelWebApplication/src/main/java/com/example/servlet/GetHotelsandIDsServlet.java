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

@WebServlet("/getHotels")
public class GetHotelsandIDsServlet extends HttpServlet {

    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String SQL = "SELECT hotel_name,hotel_id from hotel";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();


            out.println("<form action='ModifyHotel.jsp' method='get'>");
            out.println("<p>Select a Hotel to Update</p><br>");
            out.println("<label>Hotel Name:</label><br>");
            out.println("<select name='hotelID' id='hotelID' required>");

            while (rs.next()) {
                String hotelName = rs.getString("hotel_name");
                int hotelID = rs.getInt("hotel_id");
                out.println("<option value='" + hotelID + "'>" + hotelName + "</option>");
            }

            out.println("</select><br><br>");
            out.println("<input type='submit' value='Submit'>");
            out.println("</form>");


        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}

