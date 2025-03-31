package com.example.servlet;
import com.example.util.DBConfig;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        Connection con = null;

        try {
            Class.forName("org.postgresql.Driver");

            int bookingID = Integer.parseInt(request.getParameter("bookingID"));

            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
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
                    boolean cancelled = true; // mark this one as cancelled in archive


                    String cancelledSQL = "UPDATE booking SET cancelled = ? WHERE booking_id = ?";
                    PreparedStatement cancelledStmt = con.prepareStatement(cancelledSQL);
                    cancelledStmt.setBoolean(1, cancelled);
                    cancelledStmt.setInt(2, bookingID);
                    cancelledStmt.executeUpdate();

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
