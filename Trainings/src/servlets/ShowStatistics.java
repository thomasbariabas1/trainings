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

public class ShowStatistics extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 809716386143051942L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
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
	/* session.setAttribute("arithmos",temp);
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
	 }*/
	 
	 String docType =
	 "<!doctype html public \"-//w3c//dtd html 4.0 " +
	 "transitional//en\">\n";
	 try {
		out.write(docType +
		 		"<html>\n"+
		 		"<head>\n"+
		 		"<meta http-equiv='Content-Type' content='text/html' charset='utf-8' />\n"+
		 		" <title>Addtrainings</title>\n"+
		 		"</head>\n"+
		 		"<body>\n"+
		 		"<div>\n"+
		 		"<table><thead><tr></tr><h2><h2></thead><tbody>"+
		 		"<tr bgcolor=\"#949494\">\n" +
		         "<th>#</th><th>Ονομα</th><th>Email</th><th>Προπ.</th><th>Μη ολοκλ. Προπ.</th><th>Τελ. Αλ.</th></tr>\n" +
		 		getHTML(session)+
		 		"<table>"+
		 		 "<h2>You can have only 4 active Trainings<h2>\n"+
		 		"</div>  \n"+
		 		"</body>\n"+
		 		"</html>\n");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 out.close();
   
   
 }else{
	 request.getRequestDispatcher("index.html").include(request, response);
 }



out.close();  
}  
	public String getHTML(HttpSession session) throws Exception{
		int k =  DBConnection.getUsers();;
		
		 HashMap<String,Timestamp> map = new HashMap<String,Timestamp>();
		 HashMap<String,Integer> map2 = new HashMap<String,Integer>();
		 map=DBConnection.getStartStop(Integer.parseInt((String)session.getAttribute("id")));
		 map2=DBConnection.getRnd(Integer.parseInt((String)session.getAttribute("id")));
		String s="";
		
		 for(int i=1;i<=k;i++){
			 s += "<tr>\n";	
			 s += "<th>";
    	     s +=  i+"\n";
    	     s += "</th>";
		     s += "<th>";
	    	 s +=  DBConnection.getName(i)+"\n";
	    	 s += "</th>";
	    	 s += "<th>";
	    	 s +=  DBConnection.getEmail(i)+"\n";
	    	 s += "</th>";	    	
	    	 s += "<th>";
	    	 s +=  DBConnection.getCompleted(i)+"\n";
	    	 s += "</th>";	 
	    	 s += "<th>";
	    	 s +=  DBConnection.getNotcompleted(i)+"\n";
	    	 s += "</th>";	 
	         s += "<th>";
	    	 s +=  DBConnection.getLastclick(i)+"\n";
	    	 s += "</th>";	 
	    	 s += "</tr>\n";			 
        }
		 s += "<tr><td><form action='Trainings' method='post' >\n";
		 s += "<input  type='submit'  value='Back' name='submitt'>\n";
		 s +="</form></td></tr> </tbody>\n";
		 
		 return s;
	}
}
