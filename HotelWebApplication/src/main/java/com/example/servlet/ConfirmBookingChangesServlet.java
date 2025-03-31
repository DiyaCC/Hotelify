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

@WebServlet("/confirmBookingChanges")
public class ConfirmBookingChangesServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);


            int bookingID = Integer.parseInt(request.getParameter("bookingID"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            int roomTypeID = Integer.parseInt(request.getParameter("roomTypeID"));
            Date checkinDate = Date.valueOf(request.getParameter("checkinDate"));
            Date checkoutDate = Date.valueOf(request.getParameter("checkoutDate"));


            String changeBookingQuery = "UPDATE booking " +
                    "SET room_type_id = ?, checkin_date = ?, checkout_date = ? " +
                    "WHERE booking_id = ?";

            PreparedStatement stmt = con.prepareStatement(changeBookingQuery);
            stmt.setInt(1, roomTypeID);
            stmt.setDate(2, checkinDate);
            stmt.setDate(3, checkoutDate);
            stmt.setInt(4, bookingID);
            stmt.executeUpdate();

            out.println("Booking has been confirmed");


        } catch (SQLException triggerException) {
            //out.println("Invalid booking dates. Please try again.");
            out.println(triggerException.getMessage());



        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");
        }
    }



}
