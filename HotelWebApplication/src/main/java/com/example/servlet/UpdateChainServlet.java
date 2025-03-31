
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

@WebServlet("/updateChain")
public class UpdateChainServlet extends HttpServlet {

    private Connection con = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) // switch to doPost b/c form data is being sent
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int chainID = Integer.parseInt(request.getParameter("chainID"));
        String chainName = request.getParameter("chainName");


        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);


            String updateChain = "UPDATE hotel_chain SET chain_name=? WHERE chain_id=?";
            PreparedStatement ps = con.prepareStatement(updateChain);
            ps.setString(1, chainName);
            ps.setInt(2, chainID);

            ps.executeUpdate();
            con.close();

            out.print("Successfully updated " + chainName.trim());

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<option value=''>Update Failed.</option>");

        }
    }



}

