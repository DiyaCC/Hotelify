package com.example.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASS = "Volume9794";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        Connection con = null;

        try {
            Class.forName("org.postgresql.Driver");

            int bookingID = Integer.parseInt(request.getParameter("bookingID"));

            con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            con.setAutoCommit(false);

            try {
                String getBookingSQL = "SELECT * FROM booking WHERE booking_id = ?";
                PreparedStatement getBookingStmt = con.prepareStatement(getBookingSQL);
                getBookingStmt.setInt(1, bookingID);
                ResultSet booking = getBookingStmt.executeQuery();

                if (booking.next()) {
                    int customerID = booking.getInt("customer_id");
                    int roomTypeID = booking.getInt("room_type_id");
                    Timestamp confirmationDate = booking.getTimestamp("confirmation_date");
                    Date checkinDate = booking.getDate("checkin_date");
                    Date checkoutDate = booking.getDate("checkout_date");
                    Boolean cancelled = true; // mark this one as cancelled in archive

                    // Insert into Archive_Booking
                    String archiveSQL = "INSERT INTO Archive_Booking (booking_id, customer_id, room_type_id, confirmation_date, checkin_date, checkout_date, cancelled) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement archiveStmt = con.prepareStatement(archiveSQL);
                    archiveStmt.setInt(1, bookingID);
                    archiveStmt.setInt(2, customerID);
                    archiveStmt.setInt(3, roomTypeID);
                    archiveStmt.setTimestamp(4, confirmationDate);
                    archiveStmt.setDate(5, checkinDate);
                    archiveStmt.setDate(6, checkoutDate);
                    archiveStmt.setBoolean(7, cancelled);
                    archiveStmt.executeUpdate();

                    // Delete from booking
                    String deleteSQL = "DELETE FROM booking WHERE booking_id = ?";
                    PreparedStatement deleteStmt = con.prepareStatement(deleteSQL);
                    deleteStmt.setInt(1, bookingID);
                    deleteStmt.executeUpdate();

                    con.commit();
                    response.sendRedirect("checkinCancelled.jsp");

                } else {
                    con.rollback();
                    response.getWriter().println("<p>Booking not found.</p>");
                }

            } catch (Exception e) {
                con.rollback();
                e.printStackTrace();
                response.getWriter().println("<p>Error during cancellation: " + e.getMessage() + "</p>");
            }

        } catch (Exception outerEx) {
            outerEx.printStackTrace();
            response.getWriter().println("<p>Connection error: " + outerEx.getMessage() + "</p>");
        } finally {
            if (con != null) try { con.close(); } catch (SQLException ignore) {}
        }
    }
}
