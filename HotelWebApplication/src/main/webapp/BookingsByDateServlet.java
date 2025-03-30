package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookingsByDate")
public class BookingsByDateServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
    private static final String JDBC_USER = "postgres"; // Change if needed
    private static final String JDBC_PASS = "Volume9794";     // Change if needed
    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Servlet triggered with hotel_id=" + request.getParameter("hotel_id") + ", checkin=" + request.getParameter("checkin"));
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
        int employee_id = Integer.parseInt(request.getParameter("employee_id"));
        // Date checkin =Date.valueOf(request.getParameter("checkin"));
        // Date checkout = Date.valueOf(request.getParameter("checkout"));
        // int room_val = Integer.parseInt(request.getParameter("rooms"));
        // int star = Integer.parseInt(request.getParameter("star"));

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);
            System.out.println("SQL ATTEMPT triggered with hotel_id=" + request.getParameter("hotel_id") + ", checkin=" + request.getParameter("checkin"));
            Statement dropStmt = con.createStatement();
            dropStmt.execute("DROP VIEW IF EXISTS bookingsByDate");

            String sql_temp =
                    "create or replace view bookingsByDate as\n" +
                            "\tSELECT DISTINCT A.booking_id, A.customer_id, C.first_name, C.last_name, A.hotel_id, E.hotel_name, E.chain_id, F.chain_name, A.room_type_id, D.description as room_type, A.confirmation_date, A.checkin_date, A.checkout_date, A.cancelled\n" +
                            "\tFROM booking A\n" +
                            "\tLEFT JOIN customer B\n" +
                            "\t     ON B.customer_id = A.customer_id\n" +
                            "\tLEFT JOIN person C\n" +
                            "\t     ON C.SSN = B.SSN\n" +
                            "\tLEFT JOIN room_type D\n" +
                            "\t     ON D.room_type_id = A.room_type_id\n" +
                            "\tLEFT JOIN hotel E\n" +
                            "\t     ON E.hotel_id = A.hotel_id\n" +
                            "\tLEFT JOIN hotel_chain F\n" +
                            "\t     ON F.chain_id = E.chain_id\n" +
                            "\tWHERE A.booking_id NOT IN (SELECT DISTINCT X.booking_id " +
                                                        "FROM renting X);";
            PreparedStatement stmt = con.prepareStatement(sql_temp);
            stmt.executeUpdate();

            String checkinParam = request.getParameter("checkin");
            boolean filterByDate = (checkinParam != null && !checkinParam.isEmpty());

            String sql_query;
            PreparedStatement stmt2;

            if (filterByDate) {
                Date checkin = Date.valueOf(checkinParam);
                sql_query = "SELECT * FROM bookingsByDate WHERE hotel_id = ? AND checkin_date = ?";
                stmt2 = con.prepareStatement(sql_query);
                stmt2.setInt(1, hotel_id);
                stmt2.setDate(2, checkin);
            } else {
                sql_query = "SELECT * FROM bookingsByDate WHERE hotel_id = ?";
                stmt2 = con.prepareStatement(sql_query);
                stmt2.setInt(1, hotel_id);
            }

            ResultSet rs = stmt2.executeQuery();

            if (!rs.isBeforeFirst()) {
                out.println("<p>No bookings found for the given hotel ID and check-in date.</p>");
                return;
            }

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
                        "                                        <h3>"+rs.getString("room_type")+"</h3>\n" +
                        "                                        <h4>"+rs.getString("checkin_date")+" - "+rs.getString("checkout_date")+"</h3>\n" +
                        "                                        <h4>"+rs.getString("first_name")+" "+rs.getString("last_name")+"</h3>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"col\">\n" +

                        //"                                       <form method='post' action='checkinBooking'>" +
                        //"                                       <input type='hidden' name='bookingID' value='" + rs.getInt("booking_id") + "' />" +
                        //"                                       <input type='hidden' name='employee_id' value='" + employee_id + "' />" +
                        //"                                       <button type='submit' class='buttons'>Manage Booking</button>" +
                        //"                                       </form>" +

                        "                                        <a href='checkinBooking?bookingID="+String.valueOf(rs.getInt("booking_id"))+"&employee_id="+employee_id+"'>"+
                        "                                        <button class=\"buttons\">Manage Booking</button>\n" +

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
