package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Trainings extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3286194447556247060L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
String message;
response.setContentType("text/html");              
PrintWriter out=response.getWriter(); 

HttpSession session = request.getSession(false);
 if(session!=null){
	 message="helloooooooooooooo";
	 System.out.println("trainings");
     request.setAttribute("start", message);
     request.setAttribute("stop", message);
     request.getRequestDispatcher("/link.jsp").forward(request, response);
 }else{
	 request.getRequestDispatcher("index.jsp").include(request, response);
 }



out.close();  
}  

}
