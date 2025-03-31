
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

@WebServlet("/areas")
public class AreasServlet extends HttpServlet {
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String checkin =request.getParameter("checkin");
        System.out.println(checkin);
        String checkout = request.getParameter("checkout");

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String create_view = "CREATE OR REPLACE VIEW allAreas as\n" +
                    "\tSELECT city, sum(available) as rooms\n" +
                    "\tFROM\n" +
                    "\t\t(SELECT DISTINCT Hotel_Chain.chain_name, rooms.hotel_id, rooms.hotel_name, rooms.num_rooms-rooms.booked_count as available , rooms.star_rating, rooms.city\n" +
                    "\t\tFROM\n" +
                    "\t\t(select h.chain_id, h.hotel_id, h.hotel_name, coalesce(m.count, 0) as booked_count, h.num_rooms, h.star_rating, h.city\n" +
                    "\t\tfrom (select hotel_id, count(booking_id)\n" +
                    "\t\tfrom booking\n" +
                    "\t\twhere NOT cancelled and ('"+checkin+"', '"+checkout+"') OVERLAPS (checkin_date, checkout_date)\n" +
                    "\t\tgroup by hotel_id, booking_id) as m\n" +
                    "\t\tright outer join hotel h\n" +
                    "\t\ton h.hotel_id = m.hotel_id) as rooms\n" +
                    "\t\tNATURAL JOIN Hotel_chain)\n" +
                    "\tgroup by city";
            Statement stmt = con.createStatement();
            System.out.println(stmt.toString());
            stmt.executeUpdate(create_view);

            String sql = "SELECT * FROM allAreas;";
            Statement stm = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table><tr><th>City</th><th>Total Available Rooms</th></tr>");
            while (rs.next()){
                String div = "<tr><td>"+rs.getString("city")+"</td><td>"+rs.getInt("rooms")+"</td></tr>";
                out.println(div);
            }
            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}

