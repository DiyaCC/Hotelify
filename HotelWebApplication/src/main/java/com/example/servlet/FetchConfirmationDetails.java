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
// this is no longer needed since I had made this for a test - Diya
@WebServlet("/confirmDetails")
public class FetchConfirmationDetails extends HttpServlet {

    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int roomtype = Integer.parseInt(request.getParameter("roomtype"));
        int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String rooms = request.getParameter("rooms");

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String sql = "SELECT hotel_name FROM hotel WHERE hotel_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, hotel_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String name = rs.getString("hotel_name");

            sql = "SELECT description FROM Room_Type WHERE room_type_id=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, roomtype);
            rs = stmt.executeQuery();
            rs.next();
            String roomName = rs.getString("description");

            String htmlToAdd = "<h3>Your Booking</h3>\n" +
                    "                    <h4>Hotel: "+name+"</h4>\n" +
                    "                    <h4>Checkin: "+checkin+"</h4>\n" +
                    "                    <h4>Checkout: "+checkout+"</h4>\n" +
                    "                    <h4>Room Type: "+roomName+"</h4>\n" +
                    "                    <h4>Rooms: "+rooms +"</h4>\n";
            out.println(htmlToAdd);
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error loading rooms</option>");

        }out.println("</select>");
    }

}
