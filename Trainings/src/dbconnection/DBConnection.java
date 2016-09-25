package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;






public class DBConnection {
	 /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    
    @SuppressWarnings("finally")
	public static Connection createConnection() throws Exception {
        Connection con = null;
       
        try {
       
            Class.forName(Constants.dbClass);
           

            con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
            
        } catch (Exception e) {
            throw e;
        } finally {
            return con;
        }
    }
/**
    
     * @return
     * @throws Exception
     */
    public static int CheckInfo(String name, String password) throws Exception {
       
        Connection dbConn = null;
       
        try {
        	
                dbConn = createConnection();
                int id;
              
                String query = "SELECT * FROM users WHERE (name='"+name+"' OR email='"+name+"') AND password='"+password+"'";
               
                Statement stmt = dbConn.createStatement();
                
                ResultSet rs = stmt.executeQuery(query);
                System.out.println(query);
                if(!rs.next())
                	return -1;
                else{
                	id=rs.getInt("id");
                	
                	
                	return id;
                	}
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        
    }
    
	
    
	public static String addtrain(int id,int counter) throws Exception {
    	 Connection dbConn = null;
    	 Timestamp d;
    	 Timestamp s;
    	 Timestamp current;
    	 Timestamp current1;
    	 String train;
    	 Date date = new Date();
    	 String query;
    	 try {
             
             dbConn = DBConnection.createConnection();
             d=getPrevious(id);
             s=getPrevious(id);
             s.setMinutes(s.getMinutes()+30);
             current= new Timestamp(date.getTime());  
             System.out.println(current.before(d));
            if(current.before(d)){
                query = "INSERT INTO training(userid,Start,Stop) VALUES('"+id+"','"+d+"','"+s+"')";
                train="User with id:" +id+" added train From:"+d+" To:"+s;
                System.out.println(query);
            }else{
            	query = "INSERT INTO training(userid,Start,Stop) VALUES('"+id+"',NOW(), NOW()+INTERVAL 30 MINUTE)";
            	 current1 = new Timestamp(date.getTime());
                 current1.setMinutes(current.getMinutes()+30);
            	train="User with id:" +id+" added train From:"+current+" To:"+current1;
                System.out.println(query);
            }
         
         Statement stmt = dbConn.createStatement();
         stmt.executeUpdate(query);
                   
     } catch (SQLException sqle) {
         throw sqle;
     } catch (Exception e) {
         // TODO Auto-generated catch block
         if (dbConn != null) {
             dbConn.close();
         }
         throw e;
     } finally {
         if (dbConn != null) {
             dbConn.close();
         }
     }
    	 return train;
    }
    public static Timestamp getPrevious(int id){
    	Connection dbConn = null;
    	Timestamp d = null ;
    	java.util.Date date= new java.util.Date();
            try {
				dbConn = DBConnection.createConnection();
				 String query = "SELECT Stop FROM training WHERE userid='"+id+"' ORDER BY stop DESC LIMIT 1";
		         System.out.println(query);
		         Statement stmt = dbConn.createStatement();
		         ResultSet rs = stmt.executeQuery(query);
		         if(rs.next()){
		         d=rs.getTimestamp("Stop") ;
		         }else{
		         d= new Timestamp(date.getTime());
		         }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
    	return d;
    }
    public static int getTrainings(int id){
    	Connection dbConn = null;
    	
    	int i=0;
            
				try {
					dbConn = DBConnection.createConnection();
					String query = "SELECT COUNT(*) AS count FROM training WHERE id='"+id+"' AND (time_to_sec(timediff(NOW(),(SELECT stop FROM training WHERE id='"+id+"' ORDER BY stop DESC LIMIT 4) )) / 3600)>1.5 ";
			         System.out.println(query);
			         Statement stmt = dbConn.createStatement();
			         ResultSet rs = stmt.executeQuery(query);
			         if(rs.next()){
			        	 i=rs.getInt("count");
			        	}
			         
			         
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
    	return i;
    }
    public static void Session(String name, HttpSession session){
    	Connection dbConn = null;
    	try {
			dbConn = DBConnection.createConnection();
			String query = "INSERT INTO sesmana(name,sessionid) VALUES('"+name+"','"+session+"') ";
	         System.out.println(query);
	         Statement stmt = dbConn.createStatement();
	          stmt.executeUpdate(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
