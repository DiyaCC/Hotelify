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

@WebServlet("/search_Employee_ID")
public class SearchEmployeeIDServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Volume9794";     // Change if needed
    private Connection con = null;
    //private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String Employee_ID = request.getParameter("Employee_ID");

        if (Employee_ID == null || Employee_ID.trim().isEmpty()) {
            out.print("Invalid Employee ID");
            return;
        }

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            String search_Employee_ID = "SELECT hotel_id, employee_id as Employee_ID FROM employee WHERE employee_id::TEXT = ?";
            PreparedStatement stmt = con.prepareStatement(search_Employee_ID);
            stmt.setString(1, Employee_ID); //replace the question mark with SSN
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){ //this customer is already in our system
                String hotelID = rs.getString("hotel_id");


                String employeeName = "SELECT A.first_name FROM person A INNER JOIN employee B ON B.SSN = A.SSN WHERE employee_id::TEXT = ?";
                PreparedStatement custStmt = con.prepareStatement(employeeName);
                custStmt.setString(1,Employee_ID);
                ResultSet custRs = custStmt.executeQuery();

                while (custRs.next()){
                    String name = custRs.getString("first_name");
                    out.print("Welcome " + name + "!");
                    //out.println("<button onclick='redirectToEmployeeBookings(" + hotelID + ", " + Employee_ID +")'>Login</button>");
                    out.println("<button onclick='redirectToEmployeeBookings(\"" + hotelID + "\", \"" + Employee_ID + "\")'>Login</button>");

                }

//                String firstName = rs.getString("first_name");
//                out.print("Hi " + firstName + "! Let's book you that room!");

            }else{
                out.print("<p>It looks like we can't find you in our system.</p>");
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
