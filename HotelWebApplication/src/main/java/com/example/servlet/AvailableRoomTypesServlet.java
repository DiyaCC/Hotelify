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
import java.util.Dictionary;
import java.util.Hashtable;

@WebServlet("/availableRoomsForBook")
public class AvailableRoomTypesServlet extends HttpServlet  {

    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
        Date checkin = Date.valueOf(request.getParameter("checkin"));
        String checkinString = request.getParameter("checkin");
        Date checkout = Date.valueOf(request.getParameter("checkout"));
        String checkoutString = request.getParameter("checkout");
        int room_val = Integer.parseInt(request.getParameter("rooms"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String query = "DROP TABLE IF EXISTS book;\n" +
                    "DROP TABLE IF EXISTS total_rooms;\n" +
                    "\n" +
                    "\n" +
                    "CREATE TEMPORARY TABLE book as\n" +
                    "\tSELECT DISTINCT r.room_type_id, q.booked\n" +
                    "\tFROM Room r\n" +
                    "\tLEFT JOIN\n" +
                    "\t(SELECT room_type_id, count(room_type_id) as booked\n" +
                    "\t\tFROM Booking \n" +
                    "\t\tWHERE hotel_id = ?\n" +
                    "\t\tAND (?, ?) OVERLAPS (checkin_date, checkout_date)\n" +
                    "\tGROUP BY room_type_id) as q\n" +
                    "\tON r.room_type_id=q.room_type_id\n" +
                    "\tWHERE r.hotel_id=?;";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, hotel_id);
            stmt.setDate(2, checkin);
            stmt.setDate(3, checkout);
            stmt.setInt(4, hotel_id);

            stmt.executeUpdate();

            String query2 = "UPDATE book SET booked=0 WHERE booked IS NULL;\n";
            stmt = con.prepareStatement(query2);
            stmt.executeUpdate();

            String query3= "CREATE TEMPORARY TABLE total_rooms as\n" +
                    "(SELECT *\n" +
                    "FROM\n" +
                    "\t(SELECT DISTINCT\n" +
                    "\t\tA.room_type_id,\n" +
                    "\t\tA.hotel_id,\n" +
                    "\t\tB.description,\n" +
                    "\t\tcount(A.room_type_id) as total_rooms\n" +
                    "\tFROM ROOM A\n" +
                    "\tLEFT JOIN Room_Type B\n" +
                    "\t\tON B.room_type_id = A.room_type_id\n" +
                    "\tGROUP BY A.room_type_id, B.description, A.hotel_id\n" +
                    "));";

            stmt = con.prepareStatement(query3);
            stmt.executeUpdate();

            String query4 = "SELECT *\n" +
                    "FROM \n" +
                    "\t((SELECT *\n" +
                    "\tFROM \n" +
                    "\ttotal_rooms NATURAL JOIN book\n" +
                    "\tWHERE hotel_id=? and total_rooms-booked>=?) as rooms\n" +
                    "\tLEFT OUTER JOIN\n" +
                    "\t\t(SELECT ROOM_AMENITY.room_type_id as r, AMENITY.type\n" +
                    "\t\tFROM ROOM_AMENITY NATURAL JOIN AMENITY) as E\n" +
                    "\tON rooms.room_type_id = E.r)\n" +
                    "\n";

            stmt = con.prepareStatement(query4);
            stmt.setInt(1, hotel_id);
            stmt.setInt(2, room_val);

            ResultSet rs = stmt.executeQuery();

            int prev_room_type = 0;
            String html_to_append = "";
            int i =0;

            while (rs.next()) {

                if (prev_room_type==0 ) {
                    html_to_append +="<div class='widgetRow row'>";
                    //if start of query, need to create the first widget
                    prev_room_type=rs.getInt("room_type_id");
                    html_to_append+="<div class=\"roomWidget col\">\n" +
                            "          <h2>"+rs.getString("description")+"</h2>\n" +
                            "           <h4>";
                    html_to_append += rs.getString("type");
                } else if (prev_room_type==rs.getInt("room_type_id")) {
                    html_to_append += " | " + rs.getString("type");
                } else {
                    html_to_append +="</h4>\n" +
                            "          <div class='col'>" +
                            "               <a href='EnterSSN.jsp?hotel_id="+hotel_id +"&roomtype="+prev_room_type+"&checkin="+checkinString+"&checkout="+checkoutString+"&rooms="+room_val+"'>" +
                            "                   <button class=\"buttons bookRoomButton\">Book Now</button>\n" +
                            "               </a>" +
                            "         </div>\n";
                    html_to_append+="</div>\n";
                    prev_room_type=rs.getInt("room_type_id");
                    html_to_append+="</div>\n";
                    html_to_append +="<div class='widgetRow row'>";
                    html_to_append+="<div class='roomWidget col'>\n";
                    html_to_append+="<h2>"+rs.getString("description")+"</h2>\n";
                    html_to_append+="<h4>"+rs.getString("type");
                }
            }
            if (!html_to_append.isEmpty()){
                html_to_append+="</h4>\n" +
                        "          <div class='col'>" +
                        "               <a href='EnterSSN.jsp?hotel_id="+hotel_id +"&roomtype="+prev_room_type+"&checkin="+checkinString+"&checkout="+checkoutString+"&rooms="+room_val +"'>" +
                        "                   <button class=\"buttons bookRoomButton\">Book Now</button>\n" +
                        "               </a>" +
                        "         </div>";
                html_to_append+="</div>\n";
                html_to_append+="</div>\n";
            }
            out.println(html_to_append);

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error loading rooms</option>");
        }
    }
}
