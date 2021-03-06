package servlets;
    import java.io.IOException;  
    import java.io.PrintWriter;  
    import javax.servlet.ServletException;  
    import javax.servlet.http.HttpServlet;  
    import javax.servlet.http.HttpServletRequest;  
    import javax.servlet.http.HttpServletResponse;  
    import javax.servlet.http.HttpSession;  
    public class Disconnected extends HttpServlet {  
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                          throws ServletException, IOException {  
            response.setContentType("text/html");  
            PrintWriter out=response.getWriter();  
            System.out.println("Disconnected");
             HttpSession session=request.getSession(false);  
           
            session.invalidate(); 
          
            request.getRequestDispatcher("index.html").include(request, response);
            out.close();  
		}
    }  