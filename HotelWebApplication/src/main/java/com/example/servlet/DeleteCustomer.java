package com.example.servlet;

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

@WebServlet("/DeleteCustomer")
public class DeleteCustomer extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "";     // Change if needed
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int SSN = Integer.parseInt(request.getParameter("SSN"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            //Find customer information to get their ID
            String customerID = "SELECT customer_id FROM Customer WHERE SSN=?";
            PreparedStatement pst = con.prepareStatement(customerID);
            pst.setInt(1, SSN);
            ResultSet rs = pst.executeQuery();

            rs.next();
            int customer_id = rs.getInt("customer_id");

            //find all bookings made by the customer
            String booking = "SELECT * FROM booking WHERE customer_id=?";
            PreparedStatement pst2 = con.prepareStatement(booking);
            pst2.setInt(1, customer_id);
            ResultSet rs2 = pst2.executeQuery();

            while (rs2.next()) {

                //check if booking is in archive
                String exists = "SELECT * FROM Archive_Booking WHERE booking_id=?";
                PreparedStatement pst7 = con.prepareStatement(exists);
                pst7.setInt(1, rs2.getInt("booking_id"));

                ResultSet rs7 = pst7.executeQuery();

                //if it isn't, add to archive booking
                if (!rs7.next()) {
                    String archive = "INSERT INTO Archive_Booking (booking_id, customer_id, room_type_id, confirmation_date, checkin_date, checkout_date, cancelled) " +
                            "VALUES (?,?,?,?,?,?,?)";
                    PreparedStatement pst3 = con.prepareStatement(archive);
                    pst3.setInt(1, rs2.getInt("booking_id"));
                    pst3.setInt(2, customer_id);
                    pst3.setInt(3, rs2.getInt("room_type_id"));
                    pst3.setDate(4, rs2.getDate("confirmation_date"));
                    pst3.setDate(5, rs2.getDate("checkin_date"));
                    pst3.setDate(6, rs2.getDate("checkout_date"));
                    pst3.setBoolean(7, rs2.getBoolean("cancelled"));
                    pst3.executeUpdate();
                }
            }
            //get list of rentings
            String renting = "SELECT * FROM renting WHERE customer_id=?";
            PreparedStatement pst4 = con.prepareStatement(renting);
            pst4.setInt(1, customer_id);
            ResultSet rs4 = pst4.executeQuery();

            while (rs4.next()) {

                //check if renting is in archive
                String exists = "SELECT * FROM Archive_Renting WHERE renting_id=?";
                PreparedStatement pst8 = con.prepareStatement(exists);
                pst8.setInt(1, rs4.getInt("renting_id"));
                ResultSet rs8 = pst8.executeQuery();

                //if it isn't, add it
                if (!rs8.next()) {
                    String archive = "INSERT INTO Archive_Renting (renting_id, employee_id, booking_id, customer_id, room_type_id, room_id, checkin_date, checkout_date, cancelled) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst5 = con.prepareStatement(archive);
                    pst5.setInt(1, rs4.getInt("renting_id"));
                    pst5.setInt(2, rs4.getInt("employee_id"));
                    pst5.setInt(3, rs4.getInt("booking_id"));
                    pst5.setInt(4, rs4.getInt("customer_id"));
                    pst5.setInt(5, rs4.getInt("room_type_id"));
                    pst5.setInt(6, rs4.getInt("room_id"));
                    pst5.setDate(7, rs4.getDate("checkin_date"));
                    pst5.setDate(8, rs4.getDate("checkout_date"));
                    pst5.setBoolean(9, rs4.getBoolean("cancelled"));
                    pst5.executeUpdate();
                }
            }

            //delete customer
            String customer = "DELETE FROM customer WHERE customer_id=?";
            PreparedStatement pst6 = con.prepareStatement(customer);
            pst6.setInt(1, customer_id);
            pst6.executeUpdate();

            //delete person
            String person = "DELETE FROM person WHERE SSN=?";
            PreparedStatement pst7 = con.prepareStatement(person);
            pst7.setInt(1, SSN);

            out.println("<h4>Your account has been deleted!</h4>");

        } catch (Exception e) {
            e.printStackTrace();

            out.println("<option value=''>Error</option>");

        }
    }



}
