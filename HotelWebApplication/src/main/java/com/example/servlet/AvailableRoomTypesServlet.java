package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Dictionary;
import java.util.Hashtable;

@WebServlet("/availableRoomsForBook")
public class AvailableRoomTypesServlet extends HttpServlet  {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Thanks$%^g1ving";     // Change if needed
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
        Date checkin = Date.valueOf(request.getParameter("checkin"));
        Date checkout = Date.valueOf(request.getParameter("checkout"));
        int room_val = Integer.parseInt(request.getParameter("rooms"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            String query = "SELECT room_type_id, description, type\n" +
                    "FROM\n" +
                    "\t(SELECT DISTINCT\n" +
                    "\t\tA.room_type_id,\n" +
                    "\t\tB.description\n" +
                    "\tFROM ROOM A\n" +
                    "\tLEFT JOIN Room_Type B\n" +
                    "\t\tON B.room_type_id = A.room_type_id\n" +
                    "\tWHERE A.hotel_id =? AND NOT EXISTS (\n" +
                    "\t\tSELECT \n" +
                    "\t\t\tFROM Booking C\n" +
                    "\t\t\tWHERE C.hotel_id = A.hotel_id\n" +
                    "\t\t\tAND C.room_type_id = A.room_id\n" +
                    "\t\t\tAND (?, ?) OVERLAPS (C.checkin_date, C.checkout_date)\n" +
                    "\t))as D\n" +
                    "LEFT OUTER JOIN\n" +
                    "\t(SELECT ROOM_AMENITY.room_type_id as r, AMENITY.type\n" +
                    "\tFROM ROOM_AMENITY NATURAL JOIN AMENITY) as E\n" +
                    "ON D.room_type_id = E.r \n" +
                    "ORDER BY room_type_id";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, hotel_id);
            stmt.setDate(2, checkin);
            stmt.setDate(3, checkout);

            ResultSet rs = stmt.executeQuery();

            int prev_room_type = 0;
            String html_to_append = "";
            int i =0;

            while (rs.next()) {
                if (i%3==0){
                    html_to_append+="<div class='widgetRow row'>";
                }
                //for the first row
                if (prev_room_type==0){
                    prev_room_type=rs.getInt("room_type_id");
                    html_to_append+="<div class=\"roomWidget col\">\n" +
                            "          <h2>"+rs.getString("description")+"</h2>\n" +
                            "           <h4>";
                    html_to_append += rs.getString("type");
                }
                //for the remaining rows
                if (prev_room_type==rs.getInt("room_type_id") && i!=0) {
                    html_to_append += " | " + rs.getString("type");
                } else if (prev_room_type!=rs.getInt("room_type_id") && i!=0) {
                    html_to_append +="</h4>\n" +
                            "          <button class=\"buttons bookRoomButton\">Book Now</button>\n";
                    html_to_append+="</div>\n";
                    if (i%3==2){
                        html_to_append+="</div>\n<div class='widgetRow row'>\n";
                    }
                    html_to_append+="<div class='roomWidget col'>\n";
                    html_to_append+="<h2>"+rs.getString("description")+"</h2>\n";
                    html_to_append+="<h4>"+rs.getString("type");
                }
                i++;
            }
            html_to_append+="</h4>\n" +
                    "          <button class=\"buttons bookRoomButton\">Book Now</button>\n";
            html_to_append+="</div>\n";
            if (i%3!=2){
                html_to_append+="<div class='widgetRow row'>\n";
            }
            out.println(html_to_append);

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error loading rooms</option>");
        }
    }
}
