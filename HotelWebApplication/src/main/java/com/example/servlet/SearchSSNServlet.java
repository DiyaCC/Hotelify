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

@WebServlet("/searchSSN")
public class SearchSSNServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Matara!92222";     // Change if needed
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String SSN = request.getParameter("SSN");

        if (SSN == null || SSN.trim().isEmpty()) {
            out.print("Invalid SSN");
            return;
        }

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            String searchSSN = "SELECT SSN FROM customer WHERE SSN::TEXT = ?";
            PreparedStatement stmt = con.prepareStatement(searchSSN);
            stmt.setString(1,SSN); //replace the question mark with SSN
            ResultSet rs = stmt.executeQuery();


            if (rs.next()){ //this customer is already in our system
                String custName = "SELECT first_name FROM person WHERE SSN::TEXT = ?";
                PreparedStatement custStmt = con.prepareStatement(custName);
                custStmt.setString(1,SSN);
                ResultSet custRs = custStmt.executeQuery();

                while (custRs.next()){
                    String name = custRs.getString("first_name");
                    out.print("Welcome " + name + "! Let's book you that room!");
                    out.println("<button onclick='redirectToConfirm()'>Review Booking<button>");
                }

//                String firstName = rs.getString("first_name");
//                out.print("Hi " + firstName + "! Let's book you that room!");

            }else{
                out.print("<h5>It looks like we can't find you in our system. Make an account by clicking below!</h5>");
                out.print("<button onclick = 'redirectToCreateAccount()' class='buttons'>Create Account</button>");

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
