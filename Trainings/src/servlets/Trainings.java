package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbconnection.DBConnection;

public class Trainings extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3286194447556247060L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

response.setContentType("text/html");              
PrintWriter out=response.getWriter(); 

HttpSession session = request.getSession(false);
 if(session!=null){
	 int i = DBConnection.getTrainings(Integer.parseInt((String) session.getAttribute("id")));
	 
	 session.setAttribute("arithmos", i);
	 HashMap<String,Timestamp> map = new HashMap<String,Timestamp>();
	 HashMap<String,Integer> map2 = new HashMap<String,Integer>();
	 map=DBConnection.getStartStop(Integer.parseInt((String)session.getAttribute("id")));
	 map2=DBConnection.getRnd(Integer.parseInt((String)session.getAttribute("id")));
	 for(int j=0;j<i;j++){
		 session.setAttribute(String.valueOf("start"+j),String.valueOf(map.get("start"+j) ));
		 session.setAttribute(String.valueOf("stop"+j),String.valueOf(map.get("stop"+j)) );
		 session.setAttribute(String.valueOf("rand"+j), String.valueOf(map2.get("tuxaiosarithmos"+j)));
		 session.setAttribute(String.valueOf("id"+j), String.valueOf(map2.get("id"+j)));
		 System.out.println(session.getAttribute("rand"+j));
		System.out.println("Start: "+String.valueOf(session.getAttribute("start"+j))+" Stop: "+String.valueOf(session.getAttribute("stop"+j)));
	 }
   
     request.getRequestDispatcher("/link.jsp").include(request, response);
 }else{
	 request.getRequestDispatcher("index.jsp").include(request, response);
 }



out.close();  
}  

}
