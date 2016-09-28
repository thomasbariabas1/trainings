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

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		            
		
        PrintWriter out=response.getWriter(); 

HttpSession session = request.getSession(false);
String docType =
"<!doctype html public \"-//w3c//dtd html 4.0 " +
"transitional//en\">\n";
out.write(docType +
		"<html>\n"+
		"<head>\n"+
		"<meta http-equiv='Content-Type' content='text/html' charset='utf-8' />\n"+
		" <title>Addtrainings</title>\n"+
		"</head>\n"+
		"<body>\n"+
		"<div>\n"+
		"<table><thead><tr></tr><h2>Προγραμματισμενες Προπονησεις<h2></thead><tbody>"+
		"<tr bgcolor=\"#949494\">\n" +
        "<th>Rnd number</th><th>start</th><th>stop</th><th>cance</th></tr>\n" +
		getHTML(session)+
		"<table>"+
		 "<h2>You can have only 4 active Trainings<h2>\n"+
		"</div>  \n"+
		"</body>\n"+
		"</html>\n");
System.out.println("θωμας");
out.close();  
}  
	public String getHTML(HttpSession session){
		int k = DBConnection.getTrainings(Integer.parseInt((String) session.getAttribute("id")));
		 session.setAttribute("arithmos", k);
		 HashMap<String,Timestamp> map = new HashMap<String,Timestamp>();
		 HashMap<String,Integer> map2 = new HashMap<String,Integer>();
		 map=DBConnection.getStartStop(Integer.parseInt((String)session.getAttribute("id")));
		 map2=DBConnection.getRnd(Integer.parseInt((String)session.getAttribute("id")));
		String s="";
		
		 for(int i=0;i<k;i++){
			 s += "<tr><td><form action='CancelServlet' method='post' ><a href=#>"+map2.get("tuxaiosarithmos"+i)+"</a></td>\n";			
				 s += "<th>";
	    	     s +=  map.get("start"+i)+"\n";
	    	     s += "</th>";
	    	     s += "<th>";
	    	     s +=  map.get("stop"+i)+"\n";
	    	     s += "</th>";	    	    
	    	    s += "<td style='padding-top:10px;'><input type='submit' value='Akirosi' name='"+map2.get("id"+i)+"' ></form></td></tr>\n";			 
         }
		 s += "<tr><td><form action='AddTrainings' method='post' >\n";
		 s += "<input  type='submit'  value='submitTrain' name='submitt'>\n";
		 s +="</form></td>\n";
		 s += "<td><form  action='ShowStatistics' method='post'>\n";
		 s += " <input type='submit' value='Show statistics' >\n";
		 s += " </form></td> \n";
		 s += "<td><form  action='Disconnected' method='post'>\n";
		 s += "   <input type='submit' value='logout' >\n";
		 s += " </form></td></tr> </tbody>\n";
		 return s;
	}

}