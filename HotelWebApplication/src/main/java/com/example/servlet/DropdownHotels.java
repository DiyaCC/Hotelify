
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

@WebServlet("/dropdownHotels")
public class DropdownHotels extends HttpServlet {

    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String sql= "SELECT hotel_id, hotel_name\n" +
                    "FROM hotel";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            String html = "<select id=\"hotel_choice\" required>";
            while (rs.next()){
                html += "<option value="+rs.getInt("hotel_id")+">"+ rs.getInt("hotel_id")+" - "+rs.getString("hotel_name") +"</option>";
            }
            html += "</select>";
            out.println(html);

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}

