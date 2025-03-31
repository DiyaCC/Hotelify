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

@WebServlet("/getCurrentChainInfo")
public class GetCurrentChainInfoServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            int chainID = Integer.parseInt(request.getParameter("chainID"));


            String getHotelInfo = "select * from hotel_chain where chain_id = ?";
            PreparedStatement stmt = con.prepareStatement(getHotelInfo);
            stmt.setInt(1, chainID);
            ResultSet chain = stmt.executeQuery();

            while (chain.next()){
                String chainName = chain.getString("chain_name");

                out.println("<form action='updateChain' method='post'>");
                out.println("<input type='hidden' name='chainID' value='" + chainID + "'>");

                out.println("<label for='chainName'>Chain Name:</label><br>");
                out.println("<input type='text' name='chainName' id='chainName' value='" + chainName + "' required><br><br>");
                out.println("<input type='submit' value='Update Chain' >");

                out.println("</form>");

                out.println("<form action='deleteChain' method='post' onsubmit=\"return confirm('Are you sure you want to delete this chain and all of the corresponding bookings and rentings?');\">");
                out.println("<input type='hidden' name='chainID' value='" + chainID + "'>");
                out.println("<input type='submit' value='Delete Chain' >");
                out.println("</form>");


            }


        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Error</option>");

        }
    }}