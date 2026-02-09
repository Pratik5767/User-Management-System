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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection = null;
	PreparedStatement preparedStatement = null;

	public static final String sqlQuery = "INSERT INTO users (name,email,mobile,dob,city,gender)VALUES(?,?,?,?,?,?)";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
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
        out.println(".response-card {");
        out.println("margin: auto;");
        out.println("width: 60%;");
        out.println("margin-top: 50px;");
        out.println("box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);");
        out.println("border-radius: 10px;");
        out.println("overflow: hidden;");
        out.println("}");
        out.println(".response-card h2 {");
        out.println("padding: 20px;");
        out.println("}");
        out.println(".btn-home {");
        out.println("margin-top: 20px;");
        out.println("}");
        out.println("</style>");
        
		//get the values
		String name = request.getParameter("userName");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String dob = request.getParameter("dob");
		String city = request.getParameter("city");
		String gender = request.getParameter("gender");
		Date sdob = null; 
		//set up database connection
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
			}
				
			int count = preparedStatement.executeUpdate();
			out.println("<div class='card response-card'>");
			if (count == 1) {
				out.println("<h2 class='bg-success text-light text-center'>Record Registered Successfully</h2>");
			} else {
				out.println("<h2 class='bg-danger text-light text-center'>Record Not Registered</h2>");
			}
		} catch(SQLException e) {
			out.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
			e.printStackTrace();
		} catch(Exception e) {
			out.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
			e.printStackTrace();
		} finally {
			try {
				JdbcUtils.closeConnection(connection, preparedStatement, null);
				out.println("<div class='text-center btn-home'>");				
				out.println("<a href='home.html'><button class='btn btn-outline-primary'>Home</button></a>");
				out.println("</div>");
				out.println("</div>");
				out.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
