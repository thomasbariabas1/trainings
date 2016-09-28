package servlets;
    import java.io.IOException;  
    import java.io.PrintWriter;  
      
    import javax.servlet.ServletException;  
    import javax.servlet.http.HttpServlet;  
    import javax.servlet.http.HttpServletRequest;  
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;

import dbconnection.DBConnection;


    public class LoginServlet extends HttpServlet {  
         
    
		private static final long serialVersionUID = 9211568140687018262L;
 
		   public static String i;

		
		protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                        throws ServletException, IOException {  
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");              
            PrintWriter out=response.getWriter(); 
          
            
            String name=request.getParameter("name");  
            String password=request.getParameter("password");  
            HttpSession session = request.getSession();
            
            try {
				i=String.valueOf(dbconnection.DBConnection.CheckInfo(name, password));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(!i.equals("-1")){  
            	
            session.setAttribute("id", i);
            
            DBConnection.lasttime(Integer.parseInt(i));
            request.getRequestDispatcher("/Trainings").include(request, response);  
          
          
              
            }  
            else{  
                out.print("Sorry, username or password error!");  
                request.getRequestDispatcher("index.html").include(request, response);  
            }  
            out.close();  
        }  
    }  