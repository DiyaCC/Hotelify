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
    private static final String JDBC_PASS = "";     // Change if needed
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
        Date checkin =Date.valueOf(request.getParameter("checkin"));
        Date checkout = Date.valueOf(request.getParameter("checkout"));
        int room_val = Integer.parseInt(request.getParameter("rooms"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            String amenities = "SELECT room_type_id, type\n" +
                    "FROM Room_Amenity NATURAL JOIN Amenity\n" +
                    "ORDER BY room_type_id";

            Statement stmt2 = con.createStatement();
            stmt2.executeQuery(amenities);

            ResultSet rs2 = stmt2.executeQuery(amenities);
            Dictionary<Integer, String> amenitiesDictionary = new Hashtable<>();

            while (rs2.next()) {
                int key = rs2.getInt("room_type_id");
                String value = amenitiesDictionary.get(key);
                if (value == null){
                    amenitiesDictionary.put(rs2.getInt(1), rs2.getString(2));
                } else {
                    amenitiesDictionary.remove(Integer.valueOf(key));
                    value+=" | "+rs2.getString(2);
                    amenitiesDictionary.put(key, value);
                }
            }

            String query = "SELECT room_type_id, booked_rooms, sea_view, mountain_view, description, capacity, price\n" +
                    "FROM\n" +
                    "\t(SELECT hotel_id, room_type_id, count(room_type_id) as booked_rooms\n" +
                    "\tFROM booking\n" +
                    "\tWHERE hotel_id=? AND NOT cancelled and NOT ((?>checkin_date AND ?>checkout_date) OR (?<checkin_date AND ?<checkout_date))\n" +
                    "\tgroup by hotel_id, room_type_id) as h\n" +
                    "\n" +
                    "\tNATURAL JOIN \n" +
                    "\t\t(SELECT r.hotel_id, r.room_type_id, r.sea_view, r.mountain_view, t.description, r.capacity, r.price\n" +
                    "\t\tFROM room as r NATURAL JOIN room_type as t)";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, hotel_id);
            stmt.setDate(2, checkin);
            stmt.setDate(3, checkin);
            stmt.setDate(4, checkout);
            stmt.setDate(5, checkout);

            ResultSet rs = stmt.executeQuery();

            /*
            Idea: for each new room_type_id, find booked_rooms
            Then skip over that many rs.next()
             */
            int prev_room_type = 0;
            int booked_rooms = 0;
            String html_to_append = "";
            int i =0;

            while (rs.next()) {
                if (i==0) {
                    //initially added stuff
                    prev_room_type = rs.getInt("room_type_id");
                }
                if (i % 3 == 0) {
                    html_to_append += "<div class='widgetRow row'>";
                }
                if (prev_room_type != rs.getInt("room_type_id")) {
                    prev_room_type = rs.getInt("room_type_id");
                    booked_rooms = rs.getInt("booked_rooms");
                }
                if (booked_rooms==0) {
                    String services = amenitiesDictionary.get(rs.getInt("room_type_id"));
                    html_to_append += "<div class=\"roomWidget col\">\n" +
                            "          <h2>"+rs.getString("description")+"</h2>\n" +
                            "          <h4>"+services+"</h4>\n" +
                            "          <h4>" + rs.getInt("price") + "</h4>\n" +
                            "          <button class=\"buttons bookRoomButton\">Book Now</button>\n" +
                            "        </div>";
                    i++;
                } else {
                    booked_rooms--;
                }
                if (i % 3 == 2) {
                    html_to_append += "</div>";
                }

            }

            if (i%3!=2){
                html_to_append += "</div>";
            }
            out.println(html_to_append);

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error loading rooms</option>");

        }
    }
}
