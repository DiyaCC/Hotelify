package com.example;
//package dbconnection;

import java.io.PrintStream;
import java.sql.*;
import java.util.logging.Logger;

import static java.lang.System.out;

public class ConnectionTest {
    private static final Logger logger = Logger.getLogger(ConnectionTest.class.getName());

    /* Database connection settings, change dbName, dbusername, dbpassword */
    private final String ipAddress = "127.0.0.1";
    private final String dbServerPort = "5432";
    private final String dbName = "postgres";
    private final String dbusername = "postgres";
    private final String dbpassword = "Matara!92222";


    private Connection con = null;

    /**
     * Establishes a connection with the database, initializes and returns
     * the Connection object.
     *
     * @return Connection, the Connection object
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels_db", "postgres", dbpassword);
            return con;
        } catch (Exception e) {

            throw new Exception("Could not establish connection with the Database Server: "
                    + e.getMessage());
        }

    }

    /**
     * Close database connection. It is very important to close the database connection
     * after it is used.
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            throw new SQLException("Could not close connection with the Database Server: "
                    + e.getMessage());
        }

    }

    public static void main (String[] args) throws Exception {
        ConnectionTest c = new ConnectionTest();

        Connection con = c.getConnection();


        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Booking");

        while (rs.next()) {
            System.out.println(rs.getString(7) + "\t" + rs.getString(2));
        }

        rs.close();
        st.close();

        con.close();
    }
}