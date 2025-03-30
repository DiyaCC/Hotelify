
package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hotelRooms")
public class GetAllHotelRooms extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Matara!92222";     // Change if needed
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);


            String sql = "SELECT * FROM room NATURAL JOIN room_type WHERE hotel_id=? ORDER BY room_number";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, hotel_id);
            ResultSet rs = ps.executeQuery();

            int i=0;
            boolean row = true;
            while (rs.next()){
                if (i%3==0){
                    out.println("<div class='row'>");
                    row = true;
                }
                String div = "<div class=\"hotelChoice\" id="+rs.getInt("room_id")+">\n" +
                        "                <h3 class=\"hotelText\">Room number: "+ rs.getInt("room_number")+"</h3>\n" +
                        "                <h4 class=\"hotelText\">Room type: "+ rs.getString("description")+"</h4>\n" +
                        "                <h4 class=\"hotelText\">Capacity: "+ rs.getInt("capacity")+"</h4>\n" +
                        "                <h4 class=\"hotelText\">Price: $"+ rs.getFloat("price")+"</h4>\n" +
                        "                </div>";
                out.println(div);
                if (i%3==2){
                    out.println("</div>");
                    row=false;
                }
                i++;
            }
            if (row){
                out.println("</div>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}

