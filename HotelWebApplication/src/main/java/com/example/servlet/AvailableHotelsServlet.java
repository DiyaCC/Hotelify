package com.example.servlet;

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

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Thanks$%^g1ving";     // Change if needed
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
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);

            Statement stmt1 = con.createStatement();
            stmt1.executeUpdate("drop view if exists availableRooms;");
            stmt1.executeUpdate("drop table if exists temp;");
            String sql_temp =
                    "create table temp as\n" +
                    "\tselect h.hotel_name, h.hotel_id, h.num_rooms, m.count, h.star_rating, h.city\n" +
                    "\tfrom (select hotel_id, count(booking_id)\n" +
                    "\t\t\tfrom booking\n" +
                    "\t\t\twhere NOT cancelled and NOT ((?, ?) OVERLAPS (checkin_date, checkout_date))" +
                    "\t\t\tgroup by hotel_id, booking_id) as m\n" +
                    "\tright outer join hotel h\n" +
                    "\ton h.hotel_id = m.hotel_id;\n" +
                    "\n";
            PreparedStatement stmt2 = con.prepareStatement(sql_temp);
            stmt2.setDate(1, checkin);
            stmt2.setDate(2, checkout);

            stmt2.executeUpdate();

            Statement stmt3 = con.createStatement();
            stmt3.executeUpdate("UPDATE temp SET count=0 WHERE count IS NULL;");
            stmt3.executeUpdate("ALTER TABLE temp ADD COLUMN available_rooms int;");
            stmt3.executeUpdate("UPDATE temp SET available_rooms=num_rooms-count;");
            stmt3.executeUpdate("ALTER TABLE temp DROP COLUMN num_rooms;");
            stmt3.executeUpdate("ALTER TABLE temp DROP COLUMN count;");
            stmt3.executeUpdate("CREATE OR REPLACE VIEW availableRooms AS select * FROM temp;");

            // Debugging: Check if availableRooms has data
            Statement debugStmt = con.createStatement();
            ResultSet debugRs = debugStmt.executeQuery("SELECT * FROM availableRooms;");

            String sql_query = "SELECT * from availableRooms WHERE city=? and star_rating>=? and available_rooms>=?;";
            PreparedStatement stmt4 = con.prepareStatement(sql_query);
            stmt4.setString(1, city_val);
            stmt4.setInt(2, star);
            stmt4.setInt(3, room_val);
            ResultSet rs = stmt4.executeQuery();
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
                        "                                        <h3>Hilton express</h3>\n" +
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
