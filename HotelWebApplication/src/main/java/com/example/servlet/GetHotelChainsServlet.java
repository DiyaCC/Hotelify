
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

@WebServlet("/getChains")
public class GetHotelChainsServlet extends HttpServlet {

    private Connection con = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);

            String getChains = "SELECT chain_id, chain_name FROM hotel_chain";
            PreparedStatement pst = con.prepareStatement(getChains);
            ResultSet rs = pst.executeQuery();

            out.println("<form action='ModifyChain.jsp' method='get'>");
            out.println("<p>Select a Chain to Update</p><br>");
            out.println("<label>Chain Name:</label><br>");
            out.println("<select name='chainID' id='chainID' required>");

            while (rs.next()) {
                String chainName = rs.getString("chain_name");
                int chainID = rs.getInt("chain_id");
                out.println("<option value='" + chainID + "'>" + chainName + "</option>");
            }

            out.println("</select><br><br>");
            out.println("<input type='submit' value='Submit'>");
            out.println("</form>");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            out.println("Something went wrong. Try again later");

        }
    }



}

