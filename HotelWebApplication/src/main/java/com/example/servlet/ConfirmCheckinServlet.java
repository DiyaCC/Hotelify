package com.example.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ConfirmCheckinServlet")
public class ConfirmCheckinServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASS = "Matara!92222";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        try {
            Class.forName("org.postgresql.Driver");

            int bookingID = Integer.parseInt(request.getParameter("bookingID"));
            int employeeID = Integer.parseInt(request.getParameter("employee_id"));
            int roomID = Integer.parseInt(request.getParameter("selectedRoom"));
            String paymentMethod = request.getParameter("paymentMethod");

            try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
                // 1. Fetch booking info
                String getBookingSQL = "SELECT * FROM booking WHERE booking_id = ?";
                PreparedStatement getBookingStmt = con.prepareStatement(getBookingSQL);
                getBookingStmt.setInt(1, bookingID);
                ResultSet booking = getBookingStmt.executeQuery();

                if (booking.next()) {
                    int customerID = booking.getInt("customer_id");
                    int hotelID = booking.getInt("hotel_id");
                    int roomTypeID = booking.getInt("room_type_id");
                    Timestamp checkinDate = booking.getTimestamp("checkin_date");
                    Timestamp checkoutDate = booking.getTimestamp("checkout_date");
                    Timestamp confirmationDate= booking.getTimestamp("confirmation_date");
                    Boolean cancelled= booking.getBoolean(  "cancelled");

                    // 2. Insert into renting
                    String insertSQL = "INSERT INTO renting (employee_id, booking_id, customer_id, room_type_id, hotel_id, room_id, checkin_date, checkout_date, payment_method, payment_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
                    PreparedStatement insertStmt = con.prepareStatement(insertSQL);
                    insertStmt.setInt(1, employeeID);
                    insertStmt.setInt(2, bookingID);
                    insertStmt.setInt(3, customerID);
                    insertStmt.setInt(4, roomTypeID);
                    insertStmt.setInt(5, hotelID);
                    insertStmt.setInt(6, roomID);
                    insertStmt.setTimestamp(7, checkinDate);
                    insertStmt.setTimestamp(8, checkoutDate);
                    insertStmt.setString(9, paymentMethod);

                    int rowsInserted = insertStmt.executeUpdate();

                    String archiveSQL = "INSERT INTO Archive_Booking (booking_id, customer_id, room_type_id, confirmation_date, checkin_date, checkout_date, cancelled) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement archiveStmt = con.prepareStatement(archiveSQL);
                    archiveStmt.setInt(1, bookingID);
                    archiveStmt.setInt(2, customerID);
                    archiveStmt.setInt(3, roomTypeID);
                    archiveStmt.setTimestamp(4, confirmationDate);
                    archiveStmt.setTimestamp(5, checkinDate);
                    archiveStmt.setTimestamp(6, checkoutDate);
                    archiveStmt.setBoolean(7, cancelled);
                    archiveStmt.executeUpdate();

                    if (rowsInserted > 0) {
                        response.sendRedirect("checkinSuccess.jsp");
                    } else {
                        response.getWriter().println("<p>Failed to create renting record.</p>");
                    }

                } else {
                    response.getWriter().println("<p>Booking not found.</p>");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<p>Error during check-in process: " + e.getMessage() + "</p>");
        }
    }
}
