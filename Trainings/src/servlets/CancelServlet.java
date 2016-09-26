package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnection.DBConnection;

public class CancelServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {

		 DBConnection.lasttime(Integer.parseInt(LoginServlet.i));
	}
}
