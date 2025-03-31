package com.example.servlet;
import com.example.util.DBConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/searchBookingID")
public class SearchBookingIDServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String bookingID = request.getParameter("bookingID");

        if (bookingID == null || bookingID.trim().isEmpty()) {
            out.print("Invalid Booking ID");
            return;
        }

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String searchBookingID = "SELECT booking_id FROM Booking WHERE booking_id::TEXT = ?";
            PreparedStatement stmt = con.prepareStatement(searchBookingID);
            stmt.setString(1,bookingID); //replace the question mark with bookingID
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){ //this booking is already in our system
                //response.sendRedirect("EditBooking.jsp");
                out.print("REDIRECT:EditBooking.jsp?bookingID=" + bookingID);

            }else{
                out.print("<p>It looks like we can't find your booking in our system. Verify your Booking ID.</p>");
                //out.print("<button onclick = 'redirectToCreateAccount()'>Create Account</button>");

            }
            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}
