package com.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.JdbcUtils;

@WebServlet("/editurl")
public class EditScreenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public static final String sqlQuery = "SELECT name, email, mobile, dob, city, gender FROM users WHERE id=?";

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
		// get the id
		int id = Integer.parseInt(request.getParameter("id"));
		// link the bootstrap
		out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		out.println("<div class='container mt-5'>");
		out.println("<h2 class='text-center text-primary mb-4'>Edit User Data</h2>");
		// set up database connection
		try {
			connection = JdbcUtils.getConnection();

			if (connection != null) {
				preparedStatement = connection.prepareStatement(sqlQuery);
			}

			if (preparedStatement != null) {
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
			}
			
			if (resultSet != null) {
				if (resultSet.next()) {					
					out.println("<div class='card p-4 shadow-lg' style='max-width: 600px; margin: auto;'>");
					out.println("<form action='edit?id="+id+"' method='post'>");
					out.println("<table class='table table-borderless'>");
					out.println("<tr>");
					out.println("<td><strong>Name</strong></td>");
					out.println("<td><input type='text' class='form-control' name='userName' value='"+resultSet.getString(1)+"'/></td>");
					out.println("</tr>");					
					
					out.println("<tr>");
					out.println("<td><strong>Email</strong></td>");
					out.println("<td><input type='email' class='form-control' name='email' value='"+resultSet.getString(2)+"'/></td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td><strong>Mobile</strong></td>");
					out.println("<td><input type='text' class='form-control' name='mobile' value='"+resultSet.getString(3)+"'/></td>");
					out.println("</tr>");
					
					out.println("<tr>");					
					out.println("<td><strong>DOB</strong></td>");
					out.println("<td><input type='date' class='form-control' name='dob' value='"+resultSet.getDate(4)+"'/></td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td><strong>City</strong></td>");
					out.println("<td><input type='text' class='form-control' name='city' value='"+resultSet.getString(5)+"'/></td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<td><strong>Gender</strong></td>");
					out.println("<td><input type='text' class='form-control' name='gender' value='"+resultSet.getString(6)+"'/></td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<td><button class='btn btn-outline-success' type='submit'>Edit</button></td>");
		            out.println("<td><button class='btn btn-outline-danger' type='reset'>Reset</button></td>");
					out.println("</tr>");
					out.println("</table>");
					out.println("</form>");
					out.println("</div>");
				}
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
				out.println("<div class='text-center mt-3'>");
		        out.println("<a href='home.html' class='btn btn-outline-primary'>Back to Home</a>");
		        out.println("</div>");
		        out.println("</div>");
				out.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}