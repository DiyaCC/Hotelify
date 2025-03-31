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

@WebServlet("/GetAllCapacity")
public class GetAllCapacity extends HttpServlet {

    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
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

            ps = con.prepareStatement("SELECT * FROM hotel_capacity");
            ResultSet rs = ps.executeQuery();

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
