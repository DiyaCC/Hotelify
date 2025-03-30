package com.example.servlet;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/RentalsByDateServlet")
public class RentalsByDateServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASS = "Volume9794";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String checkoutParam = request.getParameter("checkout");
        int hotelID = Integer.parseInt(request.getParameter("hotel_id"));

        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String query;
            PreparedStatement stmt;

            if (checkoutParam != null && !checkoutParam.isEmpty()) {
                query = "SELECT * FROM renting WHERE hotel_id = ? AND checkout_date = ?";
                stmt = con.prepareStatement(query);
                stmt.setInt(1, hotelID);
                stmt.setDate(2, Date.valueOf(checkoutParam));
            } else {
                query = "SELECT * FROM renting WHERE hotel_id = ?";
                stmt = con.prepareStatement(query);
                stmt.setInt(1, hotelID);
            }

            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                out.println("<p>No rentals found.</p>");
                return;
            }

            out.println("<table border='1'>");
            out.println("<tr><th>Rental ID</th><th>Booking ID</th><th>Room</th><th>Check-in</th><th>Check-out</th><th>Action</th></tr>");

            while (rs.next()) {
                int rentalID = rs.getInt("renting_id");
                int bookingID = rs.getInt("booking_id");
                int roomID = rs.getInt("room_id");
                Date checkinDate = rs.getDate("checkin_date");
                Date checkoutDate = rs.getDate("checkout_date");

                out.println("<tr>");
                out.println("<td>" + rentalID + "</td>");
                out.println("<td>" + bookingID + "</td>");
                out.println("<td>" + roomID + "</td>");
                out.println("<td>" + checkinDate + "</td>");
                out.println("<td>" + checkoutDate + "</td>");
                out.println("<td>");
                out.println("<form method='post' action='ConfirmCheckoutServlet'>");
                out.println("<input type='hidden' name='renting_id' value='" + rentalID + "' />");
                out.println("<input type='hidden' name='booking_id' value='" + bookingID + "' />");
                out.println("<button type='submit'>Check Out</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}
