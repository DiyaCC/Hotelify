//package com.example;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.*;
//
//public class ChangeDatesTriggerDB {
//
//
//
//    public static void main(String[] args) {
//        String JDBC_URL = "jdbc:postgresql://localhost:5432/hotels_db";
//        String JDBC_USER = "postgres"; // Change if needed
//        String JDBC_PASS = "";     // Change if needed
//        Connection con = null;
//
//        try {
//            Class.forName("org.postgresql.Driver");
//            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", JDBC_PASS);
//            Statement stmt = con.createStatement();
//
//            String datesTriggerFunction =
//                    "CREATE OR REPLACE FUNCTION prevent_invalid_booking_dates() " +
//                    "RETURNS TRIGGER AS $$ " +
//                    "BEGIN " +
//                    "IF NEW.checkin_date < CURRENT_DATE OR NEW.checkout_date < CURRENT_DATE THEN " +
//                    "RAISE EXCEPTION 'Booking dates are invalid.'; " +
//                    "END IF; " +
//                    "RETURN NEW; " +
//                    "END; " +
//                    "$$ LANGUAGE plpgsql;";
//
//            String createTrigger =
//                    "CREATE TRIGGER update_booking_dates " +
//                    "BEFORE UPDATE ON booking " +
//                    "FOR EACH ROW " +
//                    "EXECUTE FUNCTION prevent_invalid_booking_dates();";
//
//
//            stmt.execute(datesTriggerFunction);
//            stmt.execute(createTrigger);
//
//            System.out.println("The Dates Trigger has been created.");
//
//
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
