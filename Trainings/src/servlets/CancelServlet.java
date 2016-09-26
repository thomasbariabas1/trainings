package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnection.DBConnection;

public class CancelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2437966470702323986L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
		
		 DBConnection.lasttime(Integer.parseInt(LoginServlet.i));
		 System.out.println(request.getParameterNames().nextElement());
		 DBConnection.cancelTrain(Integer.parseInt(LoginServlet.i), Integer.parseInt(request.getParameterNames().nextElement()));
	     
		 request.getRequestDispatcher("/Trainings").include(request, response); 
	
	}
}
