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

@WebServlet("/showData")
public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public static final String sqlQuery = "SELECT id,name,email,mobile,dob,city,gender FROM users";

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
		out.println("<style>");
        out.println(".data-container {");
        out.println("    margin: auto;");
        out.println("    width: 80%;");
        out.println("    margin-top: 50px;");
        out.println("    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);");
        out.println("    border-radius: 10px;");
        out.println("    overflow: hidden;");
        out.println("}");
        out.println(".table-container {");
        out.println("    overflow-x: auto;");
        out.println("}");
        out.println("th, td {");
        out.println("    text-align: center;");
        out.println("    vertical-align: middle;");
        out.println("}");
        out.println(".btn-home {");
        out.println("    margin-top: 20px;");
        out.println("    text-align: center;");
        out.println("}");
        out.println("</style>");
		out.println("<marquee><h2 class='text-primary'>User Data</h2></marquee>");
		// set up database connection
		try {
			connection = JdbcUtils.getConnection();

			if (connection != null) {
				preparedStatement = connection.prepareStatement(sqlQuery);
			}

			if (preparedStatement != null) {
				resultSet = preparedStatement.executeQuery();
			}
			out.println("<div class='data-container'>");
			out.println("<div class='table-container'>");
			out.println("<table class='table table-bordered table-hover table-striped'>");
			out.println("<thead class='table-dark'>");
			out.println("<tr>");
			out.println("<th>ID</th>");
			out.println("<th>Name</th>");
			out.println("<th>Email</th>");
			out.println("<th>Mobile No.</th>");
			out.println("<th>DOB</th>");
			out.println("<th>City</th>");
			out.println("<th>Gender</th>");
			out.println("<th>Edit</th>");
			out.println("<th>Delete</th>");
			out.println("</tr>");
			out.println("</thead>");
			out.println("</tbody>");			
			if (resultSet != null) {
				while (resultSet.next()) {
					out.println("<tr>");
					out.println("<td>" + resultSet.getInt(1) + "</td>");
					out.println("<td>" + resultSet.getString(2) + "</td>");
					out.println("<td>" + resultSet.getString(3) + "</td>");
					out.println("<td>" + resultSet.getString(4) + "</td>");
					out.println("<td>" + resultSet.getDate(5) + "</td>");
					out.println("<td>" + resultSet.getString(6) + "</td>");
					out.println("<td>" + resultSet.getString(7) + "</td>");
					out.println("<td><a href='editurl?id="+resultSet.getInt(1)+"' class='btn btn-outline-warning btn-sm'>Edit</a></td>");
					out.println("<td><a href='deleteurl?id="+resultSet.getInt(1)+"' class='btn btn-outline-danger btn-sm'>Delete</a></td>");
					out.println("</tr>");
				}
			}
			out.println("</tbody>");
			out.println("</table>");
			out.println("</div>");
		} catch (SQLException e) {
			out.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
			e.printStackTrace();
		} catch (Exception e) {
			out.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
			e.printStackTrace();
		} finally {
			try {
				JdbcUtils.closeConnection(connection, preparedStatement, resultSet);
				out.println("<div class='btn-home'>");
				out.println("<a href='home.html' class='btn btn-outline-primary'>Home</a>");
				out.println("</div>");
				out.println("</div>");
				out.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
