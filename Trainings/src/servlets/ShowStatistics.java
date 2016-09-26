package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbconnection.DBConnection;

public class ShowStatistics extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 809716386143051942L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
		

response.setContentType("text/html");              
PrintWriter out=response.getWriter(); 

HttpSession session = request.getSession(false);
 if(session!=null){
	 
	 int temp = 0;
	 try {
		temp= DBConnection.getUsers();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 session.setAttribute("arithmos",temp);
	 for(int j=1;j<temp+1;j++){
		
		 session.setAttribute(String.valueOf("count"+j),j);
		 try {
			session.setAttribute(String.valueOf("name"+j),DBConnection.getName(j));
		
		 session.setAttribute(String.valueOf("email"+j),DBConnection.getEmail(j));
		 session.setAttribute(String.valueOf("completed"+j), DBConnection.getCompleted(j));
		 session.setAttribute(String.valueOf("notcompleted"+j), DBConnection.getNotcompleted(j));
		 session.setAttribute(String.valueOf("last"+j), DBConnection.getLastclick(j));
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
   
     request.getRequestDispatcher("/statistics.jsp").include(request, response);
 }else{
	 request.getRequestDispatcher("index.jsp").include(request, response);
 }



out.close();  
}  
}
