
package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/allFilters")
public class FiltersServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Matara!92222";     // Change if needed
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String city = request.getParameter("city");
        System.out.println(city);
        String hotelChain = request.getParameter("hotelChain");
        String checkin =request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        String totalHotelRooms = request.getParameter("totalHotelRooms");
        String roomSize = request.getParameter("roomSize");
        String star = request.getParameter("stars");

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);
            Statement stmt = con.createStatement();

            String drop_existing = "DROP TABLE IF EXISTS tempFiltered;";
            stmt.executeUpdate(drop_existing);

            String createTemp = "CREATE TEMP TABLE tempFiltered as\n" +
                    "SELECT * FROM allHotels;" ;
            stmt.executeUpdate(createTemp);

            if (city!=null && !city.isEmpty()) {
                String cities = "DELETE FROM tempFiltered\n" +
                        "WHERE city!='"+city+"';";
                stmt.executeUpdate(cities);
            }
            if (hotelChain!=null && !hotelChain.isEmpty()) {
                String chain = "DELETE FROM tempFiltered WHERE chain_name!='"+hotelChain+"';";
                stmt.executeUpdate(chain);
            }
            if (minprice!=null && !minprice.isEmpty()) {
                String min = "DELETE FROM tempFiltered WHERE price<"+minprice+";";
                stmt.executeUpdate(min);
            }
            if (maxprice!=null && !maxprice.isEmpty()) {
                String max = "DELETE FROM tempFiltered WHERE price>"+maxprice+";";
                stmt.executeUpdate(max);
            }
            if (totalHotelRooms!=null && !totalHotelRooms.isEmpty()) {
                String total = "DELETE FROM tempFiltered WHERE num_rooms<"+totalHotelRooms+";";
                stmt.executeUpdate(total);
            }
            if (roomSize!=null && !roomSize.isEmpty()) {
                String room = "DELETE FROM tempFiltered WHERE capacity<"+roomSize+";";
                stmt.executeUpdate(room);
            }
            if (checkin!=null && !checkin.isEmpty() && checkout!=null &&  !checkout.isEmpty() ) {
                String sql_temp =
                        "create or replace view availableRoomsForHotels as\n" +
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
                stmt.executeUpdate(sql_temp);
                String dates = "DELETE FROM tempFiltered\n" +
                        "WHERE hotel_id in (\n" +
                        "\tSELECT hotel_id \n" +
                        "\tFROM availableRoomsForHotels\n" +
                        "\tWHERE available=0)";
                stmt.executeUpdate(dates);
            }
            if (star!=null && !star.isEmpty()) {
                String stars = "DELETE FROM tempFiltered WHERE star_rating<"+star+";";
                stmt.executeUpdate(stars);
            }

            String sql = "SELECT DISTINCT hotel_id, hotel_name, chain_name, star_rating\n" +
                    "FROM tempFiltered";

            stmt.executeQuery(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                String div = "<div class=\"hotelChoice\" id=\"hotelChoice\">\n" +
                        "                <h3 class=\"hotelText\">"+ rs.getString("hotel_name")+"</h3>\n" +
                        "                <h4 class=\"hotelText\">"+ rs.getString("chain_name")+"</h4>\n" +
                        "                <h4 class=\"hotelText\">"+ rs.getInt("star_rating")+" Stars</h4>\n" +
                        "                <button class=\"buttons\" onclick='backToRooms("+rs.getInt("hotel_id")+")'>Book now</button>\n" +
                        "            </div>";
                out.println(div);
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }



}

