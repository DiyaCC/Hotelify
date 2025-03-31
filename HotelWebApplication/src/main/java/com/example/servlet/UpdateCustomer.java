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

@WebServlet("/UpdateCustomer")
public class UpdateCustomer extends HttpServlet {

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
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String searchSSN = "UPDATE person SET first_name=?, last_name=?, street_number=?, street_name=?, city=?, state=?, ZIP=? WHERE SSN=?";
            PreparedStatement stmt = con.prepareStatement(searchSSN);
            stmt.setString(1,firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, streetNumber);
            stmt.setString(4, streetName);
            stmt.setString(5, city);
            stmt.setString(6, state);
            stmt.setString(7, ZIP);
            stmt.setInt(8, SSN);
            stmt.executeUpdate();

            out.println("<h4>Information updated successfully!</h4>");
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();

            out.println("<option value=''>Error</option>");

        }
    }



}
