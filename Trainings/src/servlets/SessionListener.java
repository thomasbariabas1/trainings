package servlets;
   
    import java.util.HashMap;   
    import javax.servlet.http.HttpSession;
    import javax.servlet.http.HttpSessionEvent;
    import javax.servlet.http.HttpSessionListener;  
  
    
    public class SessionListener  implements HttpSessionListener{  
    	
    	private static  HashMap<String, HttpSession>  sessions = new HashMap<String, HttpSession> ();
    	
            
            @Override
            public void sessionCreated(HttpSessionEvent event) {
            	System.out.println("Created");
                HttpSession session = event.getSession();            
                sessions.put(LoginServlet.i, session);
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
            	System.out.println("Destroyed");
                sessions.remove(LoginServlet.i);
            }

            public static HttpSession find(String sessionId) {
                return sessions.get(sessionId);
            }
        
    }  