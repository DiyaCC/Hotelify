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

@WebServlet("/DeleteCustomer")
public class DeleteCustomer extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int SSN = Integer.parseInt(request.getParameter("SSN"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

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
                int bookingID = rs2.getInt("booking_id");
                boolean cancelled = true;

                String cancelledSQL = "UPDATE booking SET cancelled = ? WHERE booking_id = ?";
                PreparedStatement cancelledStmt = con.prepareStatement(cancelledSQL);
                cancelledStmt.setBoolean(1, cancelled);
                cancelledStmt.setInt(2, bookingID);
                cancelledStmt.executeUpdate();
            }

            //get list of rentings
            String renting = "SELECT * FROM renting WHERE customer_id=?";
            PreparedStatement pst4 = con.prepareStatement(renting);
            pst4.setInt(1, customer_id);
            ResultSet rs4 = pst4.executeQuery();

            while (rs4.next()) {

                int rentingID = rs4.getInt("renting_id");
                boolean cancelled = true;

                String cancelledSQL2 = "UPDATE renting SET cancelled = ? WHERE renting_id = ?";
                PreparedStatement cancelledStmt2 = con.prepareStatement(cancelledSQL2);
                cancelledStmt2.setBoolean(1, cancelled);
                cancelledStmt2.setInt(2, rentingID);
                cancelledStmt2.executeUpdate();
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
