package com.example.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ConfirmCheckoutServlet")
public class ConfirmCheckoutServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASS = "Volume9794";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int rentalID = Integer.parseInt(request.getParameter("renting_id"));
        int bookingID = Integer.parseInt(request.getParameter("booking_id"));

        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            con.setAutoCommit(false);

            try {
                // 1. Get renting info
                String getSQL = "SELECT * FROM renting WHERE renting_id = ?";
                PreparedStatement getStmt = con.prepareStatement(getSQL);
                getStmt.setInt(1, rentalID);
                ResultSet rs = getStmt.executeQuery();

                if (rs.next()) {
                    // Insert into Archive_Renting
                    String insertSQL = "INSERT INTO Archive_Renting (renting_id, employee_id, booking_id, customer_id, room_type_id, room_id, checkin_date, checkout_date, cancelled) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = con.prepareStatement(insertSQL);
                    insertStmt.setInt(1, rs.getInt("renting_id"));
                    insertStmt.setInt(2, rs.getInt("employee_id"));
                    insertStmt.setInt(3, rs.getInt("booking_id"));
                    insertStmt.setInt(4, rs.getInt("customer_id"));
                    insertStmt.setInt(5, rs.getInt("room_type_id"));
                    //insertStmt.setInt(5, rs.getInt("hotel_id"));
                    insertStmt.setInt(6, rs.getInt("room_id"));
                    insertStmt.setTimestamp(7, rs.getTimestamp("checkin_date"));
                    insertStmt.setTimestamp(8, rs.getTimestamp("checkout_date"));
                    insertStmt.setBoolean(9, rs.getBoolean("cancelled"));
                    insertStmt.executeUpdate();

                    // Delete from renting
                    PreparedStatement delRenting = con.prepareStatement("DELETE FROM renting WHERE renting_id = ?");
                    delRenting.setInt(1, rentalID);
                    delRenting.executeUpdate();

                    // Delete from booking
                    PreparedStatement delBooking = con.prepareStatement("DELETE FROM booking WHERE booking_id = ?");
                    delBooking.setInt(1, bookingID);
                    delBooking.executeUpdate();

                    con.commit();
                    response.sendRedirect("checkoutSuccessful.jsp");
                } else {
                    con.rollback();
                    response.getWriter().println("<p>Rental not found.</p>");
                }

            } catch (Exception e) {
                con.rollback();
                response.getWriter().println("<p>Error: " + e.getMessage() + "</p>");
            }

        } catch (Exception outerEx) {
            response.getWriter().println("<p>Connection error: " + outerEx.getMessage() + "</p>");
        }
    }
}
