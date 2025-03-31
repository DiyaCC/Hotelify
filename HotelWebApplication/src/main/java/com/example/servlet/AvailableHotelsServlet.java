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

@WebServlet("/availableHotels")
public class AvailableHotelsServlet extends HttpServlet {

    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String city_val = request.getParameter("city");
        Date checkin =Date.valueOf(request.getParameter("checkin"));
        Date checkout = Date.valueOf(request.getParameter("checkout"));
        int room_val = Integer.parseInt(request.getParameter("rooms"));
        int star = Integer.parseInt(request.getParameter("star"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String sql_temp =
                    "create or replace view availableRooms as\n" +
                            "\tSELECT DISTINCT Hotel_Chain.chain_name, rooms.hotel_id, rooms.hotel_name, rooms.num_rooms-rooms.booked_count as available , rooms.star_rating, rooms.city\n" +
                            "\tFROM\n" +
                            "\t\t(select h.chain_id, h.hotel_id, h.hotel_name, coalesce(m.count, 0) as booked_count, h.num_rooms, h.star_rating, h.city\n" +
                            "\t\tfrom (select hotel_id, count(booking_id)\n" +
                            "\t\t\t\tfrom booking\n" +
                            "\t\t\t\twhere NOT cancelled and ('"+checkin+"','"+checkout+"') OVERLAPS (checkin_date, checkout_date)\n" +
                            "\t\t\t\tgroup by hotel_id, booking_id) as m\n" +
                            "\t\tright outer join hotel h\n" +
                            "\t\ton h.hotel_id = m.hotel_id) as rooms\n" +
                            "\tNATURAL JOIN Hotel_chain;\n";
            PreparedStatement stmt = con.prepareStatement(sql_temp);
            stmt.executeUpdate();

            String sql_query = "SELECT * from availableRooms WHERE city=? and star_rating>=? and available>=?;";
            stmt = con.prepareStatement(sql_query);
            stmt.setString(1, city_val);
            stmt.setInt(2, star);
            stmt.setInt(3, room_val);
            ResultSet rs = stmt.executeQuery();
            int i = 0;

            while (rs.next()) {
                if (i%3==0) {
                    out.println("<div class='row widgetRow'>");
                }
                out.println("<div class='column'>" +
                        "   <div class=\"hotelWidget\" id='"+ String.valueOf(rs.getInt("hotel_id"))+"'>\n" +
                        "                              <img src=\"assets/Images/Hotelroom.png\"/>\n" +
                        "                                <div class=\"row\">\n" +
                        "                                    <div class=\"col\">\n" +
                        "                                        <h2>"+ rs.getString("hotel_name")+"</h2>\n" +
                        "                                        <h3>"+rs.getString("chain_name")+"</h3>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"col\">\n" +
                        "                                        <a href='room.jsp?hotel_id="+String.valueOf(rs.getInt("hotel_id"))+"&hotel_name="+String.valueOf(rs.getString("hotel_name"))+"&checkin="+String.valueOf(checkin)+"&checkout="+String.valueOf(checkout)+"&rooms="+String.valueOf(room_val)+"'>"+
                        "                                        <button class=\"buttons\">View Rooms</button>\n" +
                        "                                        </a>"+
                        "                                    </div>\n" +
                        "                                </div>\n" +
                        "                            </div>");

                if (i%3==2){
                    out.println("</div>");
                }
                i++;
            }
            if (i%3!=2){
                out.println("</div>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error loading rooms</option>");

        }
    }
}
