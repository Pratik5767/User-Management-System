package com.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.JdbcUtils;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	public static final String sqlQuery = "UPDATE users SET name=?, email=?, mobile=?, dob=?, city=?, gender=? WHERE id=?";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set content type
		response.setContentType("text/html");
		// get the PrintWriter
		PrintWriter out = response.getWriter();
		// get the value
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("userName");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String dob = request.getParameter("dob");
		String city = request.getParameter("city");
		String gender = request.getParameter("gender");
		Date sdob = null;
		// link the bootstrap
		out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		// set up database connection
		try {
			connection = JdbcUtils.getConnection();

			if (connection != null) {
				preparedStatement = connection.prepareStatement(sqlQuery);
			}

			if (preparedStatement != null) {
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, email);
				preparedStatement.setString(3, mobile);
				
				if (dob != null) {
					sdob = Date.valueOf(dob);
				}
				
				preparedStatement.setDate(4, sdob);
				preparedStatement.setString(5, city);
				preparedStatement.setString(6, gender);	
				preparedStatement.setInt(7, id);
			}
			
			int count = preparedStatement.executeUpdate();
			out.println("<div class='card' style='margin: auto; width: 900px; margin-top: 100px;'>");
			if (count == 1) {
				out.println("<h2 class='bg-success text-light text-center'>Record Edited Successfully</h2>");
			} else {
				out.println("<h2 class='bg-danger text-light text-center'>Record Not Edited</h2>");
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
