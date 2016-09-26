package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

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
    	 int kat;
    	 Random random = new Random();
    	 kat =random.nextInt(1000 - 1 + 1) + 1;
    	 try {
             
             dbConn = DBConnection.createConnection();
             d=getPrevious(id);
             s=getPrevious(id);
             s.setMinutes(s.getMinutes()+30);
             current= new Timestamp(date.getTime());  
             System.out.println(current.before(d));
            if(current.before(d)){
            	String k = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(s);
                query = "INSERT INTO training(userid,Start,Stop,tuxaiosarithmos) VALUES('"+id+"','"+d+"','"+k+"','"+kat+"')";
                train="User with id:" +id+" added train From:"+d+" To:"+s;
                System.out.println(query);
                Statement stmt = dbConn.createStatement();
                stmt.executeUpdate(query);
                
                query = "SELECT id FROM training WHERE userid='"+id+"' AND stop='"+k+"'";
                System.out.println(query);
                stmt = dbConn.createStatement();
                ResultSet rs =stmt.executeQuery(query);
                rs.next();
                int temp = rs.getInt("id");
                query="CREATE EVENT event"+id+temp+" ON SCHEDULE AT '"+k+"' DO UPDATE dokimastiko.training SET finishedtime='"+k+"' WHERE userid='"+id+"' AND id='"+temp+"'";
                System.out.println(query);
                stmt = dbConn.createStatement();
                stmt.executeUpdate(query);
            }else{
            	query = "INSERT INTO training(userid,Start,Stop) VALUES('"+id+"',NOW(), NOW()+INTERVAL 30 MINUTE)";
            	 current1 = new Timestamp(date.getTime());
                 current1.setMinutes(current.getMinutes()+30);
            	 train="User with id:" +id+" added train From:"+current+" To:"+current1;
                System.out.println(query);
                Statement stmt = dbConn.createStatement();
                stmt.executeUpdate(query);
                query = "SELECT id FROM training WHERE userid='"+id+"' AND stop=NOW()+INTERVAL 30 MINUTE";
                System.out.println(query);
                stmt = dbConn.createStatement();
               ResultSet rs =stmt.executeQuery(query);
               
               int temp = rs.getInt("id");
               query="CREATE EVENT event"+id+temp+"ON SCHEDULE AT NOW()+INTERVAL 30 MINUTE DO UPDATE dokimastiko.training SET finishedtime=NOW() WHERE userid='"+id+"' AND id='"+temp;
               System.out.println(query);
               stmt = dbConn.createStatement();
               stmt.executeUpdate(query);
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
    	 return train;
    }
    public static Timestamp getPrevious(int id){
    	Connection dbConn = null;
    	Timestamp d = null ;
    	java.util.Date date= new java.util.Date();
            try {
				dbConn = DBConnection.createConnection();
				 String query = "SELECT Stop FROM training WHERE userid='"+id+"' AND finishedtime='0000-00-00 00:00:00' ORDER BY stop DESC LIMIT 1";
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
					String query = "SELECT COUNT(*) AS count FROM training WHERE userid='"+id+"' AND finishedtime='0000-00-00 00:00:00'";
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
    
    public static HashMap<String,Timestamp> getStartStop(int id){
    	
    	HashMap<String,Timestamp> map= new HashMap<String,Timestamp>();
    	Connection dbConn = null;
    	try {
			dbConn = DBConnection.createConnection();
			String query = "SELECT start,stop FROM training WHERE userid='"+id+"' AND finishedtime='0000-00-00 00:00:00'";
	         System.out.println(query);
	         Statement stmt = dbConn.createStatement();
	         ResultSet rs = stmt.executeQuery(query);
	         int i =0;
	         while(rs.next()){
	        	 System.out.println("inside while");
	        	 map.put("start"+i, rs.getTimestamp("start"));
	        	 map.put("stop"+i, rs.getTimestamp("stop"));
	        
	             
	        	 i++;
	         }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return map;
		
    }
    public void cancelTrain(int id){
    	Connection dbConn = null;
    	try {
			dbConn = DBConnection.createConnection();
			String query = "UPDATE trainings SET finishedtime=NOW() WHERE userid='"+id+"'";
	         System.out.println(query);
	         Statement stmt = dbConn.createStatement();
	         ResultSet rs = stmt.executeQuery(query);
	        
	         while(rs.next()){
	        	 System.out.println("inside while");
	        	
	           
	         }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    }
    public static void lasttime(int id){
    	Connection dbConn = null;
    
			try {
				dbConn = DBConnection.createConnection();
				String query = "UPDATE users SET teletaio=NOW() WHERE id='"+id+"'";
		         System.out.println(query);
		         Statement stmt = dbConn.createStatement();
		         stmt.executeUpdate(query);
		        
		      
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    public static HashMap<String,Integer> getRnd(int id){
    	HashMap<String,Integer> map= new HashMap<String,Integer>();
    	Connection dbConn = null;
    
			try {
				dbConn = DBConnection.createConnection();
				String query = "SELECT tuxaiosarithmos FROM training WHERE userid='"+id+"' AND finishedtime='0000-00-00 00:00:00'";
		         System.out.println(query);
		         Statement stmt = dbConn.createStatement();
		         ResultSet rs = stmt.executeQuery(query);
		         int i =0;
		         while(rs.next()){
		        	 System.out.println("inside while");
		        	 
		        	 map.put("tuxaiosarithmos"+i, rs.getInt("tuxaiosarithmos"));
		             
		        	 i++;
		         }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
			
    
  
    }
}
