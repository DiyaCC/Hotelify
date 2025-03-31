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

@WebServlet("/bookNow")
public class BookNowServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int ssn = Integer.parseInt(request.getParameter("customerid"));
        int rooms = Integer.parseInt(request.getParameter("rooms"));
        Date checkin = Date.valueOf(request.getParameter("checkin"));
        Date checkout = Date.valueOf(request.getParameter("checkout"));
        int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
        int room_type = Integer.parseInt(request.getParameter("room_type"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String getID = "SELECT customer_id FROM customer WHERE SSN = ?";
            PreparedStatement pst = con.prepareStatement(getID);
            pst.setInt(1, ssn);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int customer_id = rs.getInt("customer_id");

            String SQL = "INSERT INTO booking (customer_id, hotel_id, room_type_id, checkin_date, checkout_date) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, customer_id);
            ps.setInt(2, hotel_id);
            ps.setInt(3, room_type);
            ps.setDate(4, checkin);
            ps.setDate(5, checkout);
            for (int i=0; i<rooms; i++){
                ps.executeUpdate();
            }

            String select = "SELECT lastval() from booking";
            Statement stmt = con.createStatement();
            ResultSet rs2 = stmt.executeQuery(select);
            ResultSetMetaData metaData = rs2.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column names and types
            System.out.println("Columns in the ResultSet:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println(metaData.getColumnName(i) + " - " + metaData.getColumnTypeName(i));
            }
            rs2.next();

            String thanks = "<h2>Thank you for your booking</h2>"+
                    "<h4>We look forward to seeing you soon! </h4> "+
                    "<h4>Booking ID: "+rs2.getInt("lastval")+"</h4>"+
                    "<button onclick='returnHome()' class='buttons'>Return Home</button>";
            out.println(thanks);
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}
