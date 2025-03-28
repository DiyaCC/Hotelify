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

@WebServlet("/findCustomer")
public class FetchCustomer extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "";     // Change if needed
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String SSN = request.getParameter("SSN");

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            String searchSSN = "SELECT SSN FROM customer WHERE SSN::TEXT = ?";
            PreparedStatement stmt = con.prepareStatement(searchSSN);
            stmt.setString(1,SSN); //replace the question mark with SSN
            ResultSet rs = stmt.executeQuery();


            if (rs.next()){ //this customer is already in our system
                String custName = "SELECT * FROM person WHERE SSN::TEXT = ?";
                PreparedStatement custStmt = con.prepareStatement(custName);
                custStmt.setString(1,SSN);
                ResultSet custRs = custStmt.executeQuery();

                while (custRs.next()){
                    String html = "<div id=\"CustomerData\">\n" +
                            "          <div class=\"row\">\n" +
                            "            <div class=\"col\">\n" +
                            "              <label for=\"firstName\" >First Name</label>\n" +
                            "              <input type=\"text\" id=\"firstName\" name = \"First\" placeholder = \"Enter your first name\" value="+custRs.getString("first_name") +">\n" +
                            "            </div>\n" +
                            "            <br>\n" +
                            "            <div class=\"col\">\n" +
                            "              <label for=\"lastName\" >Last Name</label>\n" +
                            "              <input type=\"text\" id=\"lastName\" name = \"First\" placeholder = \"Enter your last name\" value="+custRs.getString("last_name") +">\n" +
                            "            </div>\n" +
                            "            <br>\n" +
                            "          </div>\n" +
                            "        <br>\n" +
                            "          <label for=\"streetNumber\" >Street Number</label>\n" +
                            "          <input type=\"number\" id=\"streetNumber\" name = \"First\" placeholder = \"Enter your street number\" value="+custRs.getInt("street_number") +">\n" +
                            "          <br>\n" +
                            "          <label for=\"streetName\" >Street Name</label>\n" +
                            "          <input type=\"text\" id=\"streetName\" name = \"First\" placeholder = \"Enter your street name\" value="+custRs.getString("street_name") +">\n" +
                            "          <br>\n" +
                            "          <label for=\"city\" >City</label>\n" +
                            "          <input type=\"text\" id=\"city\" name = \"First\" placeholder = \"Enter your city\" value="+custRs.getString("city") +">\n" +
                            "          <br>\n" +
                            "          <label for=\"state\" >State</label>\n" +
                            "          <input type=\"text\" id=\"state\" name = \"First\" placeholder = \"Enter your state\" value="+custRs.getString("state") +">\n" +
                            "          <br>\n" +
                            "          <label for=\"ZIP\" >ZIP</label>\n" +
                            "          <input type=\"text\" id=\"ZIP\" name = \"First\" placeholder = \"Enter your ZIP\" value="+custRs.getString("ZIP") +">\n" +
                            "          <br>\n" +
                            "          <div class=\"row\">\n" +
                            "            <button class=\"buttons submit\" onclick=\"updateInfo()\">Update Information</button>\n" +
                            "            <button class=\"buttons submit\" onclick=\"deleteInfo()\">Delete Information</button>\n" +
                            "          </div>\n" +
                            "\n" +
                            "        </div>";
                    out.println(html);
                }

//                String firstName = rs.getString("first_name");
//                out.print("Hi " + firstName + "! Let's book you that room!");

            }else{
                out.print("<h5>It looks like we can't find you in our system. ");
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
