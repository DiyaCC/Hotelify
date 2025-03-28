package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GetAllCapacity")
public class GetAllCapacity extends HttpServlet {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "";     // Change if needed
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);
            String query = "CREATE OR REPLACE VIEW hotel_capacity AS \n" +
                    "\tSELECT h.hotel_id, h.hotel_name, COALESCE(c.total_capacity, 0) AS total_capacity\n" +
                    "\tFROM \n" +
                    "\t\t(SELECT hotel_id, sum(capacity) as total_capacity\n" +
                    "\t\t\tFROM room\n" +
                    "\t\t\tGROUP BY hotel_id) as c\n" +
                    "\t\tRIGHT OUTER JOIN Hotel as h\n" +
                    "\t\tON h.hotel_id=c.hotel_id;\n";

            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();

            ps = con.prepareStatement("SELECT hotel_name, total_capacity FROM hotel_capacity");
            ResultSet rs = ps.executeQuery();
            rs.next();

            out.println("<table><tr><th>Hotel</th><th>Total Capacity</th></tr>");
            while (rs.next()){
                String div = "<tr><td>"+rs.getString("hotel_name")+"</td><td>"+rs.getInt("total_capacity")+"</td></tr>";
                out.println(div);
            }
            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error loading rooms</option>");

        }
    }


}
