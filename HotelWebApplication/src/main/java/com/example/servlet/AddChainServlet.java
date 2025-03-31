
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

@WebServlet("/addChain")
public class AddChainServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String chainName = request.getParameter("chainName");
        int numHotels = Integer.parseInt(request.getParameter("numHotels"));



        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String addHotel = "INSERT INTO hotel_chain (chain_name, num_hotels) VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(addHotel);
            ps.setString(1, chainName);
            ps.setInt(2, numHotels);

            ps.executeUpdate();
            ps.close();

            out.print(chainName + " has been added.");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            out.println("Something went wrong. Try again later");

        }
    }



}

