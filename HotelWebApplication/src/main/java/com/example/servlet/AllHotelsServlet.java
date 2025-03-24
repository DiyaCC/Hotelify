
package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/allHotels")
public class AllHotelsServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "";     // Change if needed
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            String create_view = "CREATE OR REPLACE VIEW allHotels as \n" +
                    "SELECT hotel_id, hotel_name, chain_name, num_rooms, city, star_rating, price, capacity\n" +
                    "FROM\n" +
                    "(SELECT hotel_id, chain_name, star_rating, hotel_name, num_rooms, city\n" +
                    "FROM HOTEL NATURAL JOIN Hotel_chain)\n" +
                    "NATURAL JOIN\n" +
                    "ROOM";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(create_view);

            String sql = "SELECT DISTINCT hotel_id, hotel_name, chain_name, star_rating\n" +
                    "FROM allHotels";

            stmt.executeQuery(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                String div = "<div class=\"hotelChoice\" id="+rs.getInt("hotel_id")+">\n" +
                        "                <h3 class=\"hotelText\">"+ rs.getString("hotel_name")+"</h3>\n" +
                        "                <h4 class=\"hotelText\">"+ rs.getString("chain_name")+"</h4>\n" +
                        "                <h4 class=\"hotelText\">"+ rs.getInt("star_rating")+" Stars</h4>\n" +
                        "                <button class=\"buttons\">Book now</button>\n" +
                        "            </div>";
                out.println(div);
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}

