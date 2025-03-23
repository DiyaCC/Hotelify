
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

@WebServlet("/addCustomerAccountServlet")
public class AddCustomerAccountServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Matara!92222";     // Change if needed
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String streetName = request.getParameter("streetName");
        int streetNumber = Integer.parseInt(request.getParameter("streetNumber"));
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String ZIP = request.getParameter("ZIP");
        int SSN = Integer.parseInt(request.getParameter("SSN"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            String addPerson = "INSERT INTO Person (first_name, last_name, street_number, street_name, street_number, city, zip, SSN) VALUES (?, ?, ?, ?, ?. ?, ?, ?) ";
            PreparedStatement stmt = con.prepareStatement(addPerson);
            stmt.setString(1,firstName);
            stmt.setString(2,lastName);
            stmt.setString(3,streetName);
            stmt.setInt(4,streetNumber);
            stmt.setString(5,city);
            stmt.setString(6,state);
            stmt.setString(7,ZIP);
            stmt.setInt(8,SSN);

            stmt.executeUpdate();

            //updating customer table
            String addCustomer = "INSERT INTO Customer (SSN) VALUES (?)";
            stmt = con.prepareStatement(addCustomer);
            stmt.setInt(1,SSN);
            stmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}
