package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnection.DBConnection;

public class AddTrainings extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -284873300221913604L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		 DBConnection.lasttime(Integer.parseInt(LoginServlet.i));
		int j;
		j=DBConnection.getTrainings(Integer.valueOf(LoginServlet.i));
		if(j==4){
			
			 out.print("<script> alert('Only 4 Active Trainings!');</script>"); 
			 request.getRequestDispatcher("/Trainings").include(request, response);  
		}else{
		
				try {
					DBConnection.addtrain(Integer.parseInt(LoginServlet.i), j);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 request.getRequestDispatcher("/Trainings").include(request, response); 
			
		}
	}
}