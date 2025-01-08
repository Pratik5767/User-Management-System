package com.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.JdbcUtils;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	public static final String sqlQuery = "DELETE FROM users WHERE id = ?";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set content type
		response.setContentType("text/html");
		// get the PrintWriter
		PrintWriter out = response.getWriter();
		// link the bootstrap
		out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		//get the value
		int id = Integer.parseInt(request.getParameter("id"));
		// set up database connection
		try {
			connection = JdbcUtils.getConnection();

			if (connection != null) {
				preparedStatement = connection.prepareStatement(sqlQuery);
			}
			
			if (preparedStatement != null) {
				preparedStatement.setInt(1, id);
			}
			
			int count = preparedStatement.executeUpdate();
			out.println("<div class='card' style='margin: auto; width: 900px; margin-top: 100px;'>");
			if (count == 1) {
				out.println("<h2 class='bg-success text-light text-center'>Record Deleted Successfully</h2>");
			} else {
				out.println("<h2 class='bg-danger text-light text-center'>Record Not Deleted</h2>");
			}
		} catch (SQLException e) {
			out.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
			e.printStackTrace();
		} catch (Exception e) {
			out.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
			e.printStackTrace();
		} finally {
			try {
				JdbcUtils.closeConnection(connection, preparedStatement, null);
				out.println("<a href='home.html'><button class='btn btn-outline-primary'>Home</button></a>");
				out.println("&nbsp; &nbsp;");
				out.println("<a href='showData'><button class='btn btn-outline-primary'>Show Users</button></a>");
				out.println("</div>");
				out.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
