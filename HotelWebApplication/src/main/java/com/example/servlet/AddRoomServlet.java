
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

@WebServlet("/addRoom")
public class AddRoomServlet extends HttpServlet {
    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int roomnumber = Integer.parseInt(request.getParameter("roomnumber"));
        float price= Float.parseFloat(request.getParameter("price"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        int roomType = Integer.parseInt(request.getParameter("roomType"));
        boolean seaview = Boolean.parseBoolean(request.getParameter("seaview"));
        boolean mountainview = Boolean.parseBoolean(request.getParameter("mountainview"));
        int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String create_view = "SELECT * FROM ROOM WHERE room_number=?";
            PreparedStatement pst = con.prepareStatement(create_view);
            pst.setInt(1, roomnumber);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                out.println("Room number already exists");
            } else {
                String createRoom = "INSERT INTO room (hotel_id, room_number, room_type_id, price, capacity, sea_view, mountain_view)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst1 = con.prepareStatement(createRoom);
                pst1.setInt(1, hotel_id);
                pst1.setInt(2, roomnumber);
                pst1.setInt(3, roomType);
                pst1.setFloat(4, price);
                pst1.setInt(5, capacity);
                pst1.setBoolean(6, seaview);
                pst1.setBoolean(7, mountainview);
                try{
                    pst1.executeUpdate();

                    String updateNumRooms = "UPDATE hotel SET num_rooms=num_rooms+1;";
                    PreparedStatement pst2 = con.prepareStatement(updateNumRooms);
                    pst2.executeUpdate();

                    out.println("Room added successfully");
                } catch (SQLException e) {
                    out.println("Something went wrong. Try again later");
                    System.out.println(e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            out.println("Something went wrong. Try again later");

        }
    }



}

