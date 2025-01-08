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
		out.println("<marquee><h2 class='text-primary'>User Data</h2></marquee>");
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
					out.println("<div style='margin:auto; width:500px; margin-top:100px'>");
					out.println("<form action='edit?id="+id+"' method='post'>");
					out.println("<table class='table table-hover table-striped'>");
					out.println("<tr>");
					out.println("<td>Name</td>");
					out.println("<td><input type='text' name='userName' value='"+resultSet.getString(1)+"'/></td>");
					out.println("</tr>");					
					
					out.println("<tr>");
					out.println("<td>Email</td>");
					out.println("<td><input type='email' name='email' value='"+resultSet.getString(2)+"'/></td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td>Mobile</td>");
					out.println("<td><input type='text' name='mobile' value='"+resultSet.getString(3)+"'/></td>");
					out.println("</tr>");
					
					out.println("<tr>");					
					out.println("<td>DOB</td>");
					out.println("<td><input type='date' name='dob' value='"+resultSet.getDate(4)+"'/></td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td>City</td>");
					out.println("<td><input type='text' name='city' value='"+resultSet.getString(5)+"'/></td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<td>Gender</td>");
					out.println("<td><input type='text' name='gender' value='"+resultSet.getString(6)+"'/></td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<td><button class='btn btn-outline-success' type='submit'>Edit</button</td>");
					out.println("<td><button class='btn btn-outline-danger' type='reset'>Cancel</button></td>");
					out.println("</tr>");
					out.println("</table>");
					out.println("</form>");
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
				out.println("<a href='home.html'><button class='btn btn-outline-primary'>Home</button></a>");
				out.println("</div>");
				out.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}