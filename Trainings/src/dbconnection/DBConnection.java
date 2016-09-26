package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;








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
    
	
    
	public static void addtrain(int id,int counter) throws Exception {
    	 Connection dbConn = null;
    	 Timestamp d,s,current;
    	
    	 Date date = new Date();
    	 String query;
    	 int kat;
    	 Random random = new Random();
    	 kat =random.nextInt(1000 - 1 + 1) + 1;
    	 try {
             
             dbConn = DBConnection.createConnection();
             d=getPrevious(id);
             s=getPrevious(id);
            
             current= new Timestamp(date.getTime());  
            
            if(current.before(d)){
            	String k = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(s);
                query = "INSERT INTO training(userid,Start,Stop,tuxaiosarithmos) VALUES('"+id+"','"+d+"','"+k+"'+ INTERVAL 30 MINUTE,'"+kat+"')";              
                Statement stmt = dbConn.createStatement();
                stmt.executeUpdate(query);
                query = "SELECT id FROM training WHERE userid='"+id+"' AND stop='"+k+"'";
                stmt = dbConn.createStatement();
                ResultSet rs =stmt.executeQuery(query);
                rs.next();
                int temp = rs.getInt("id");
                query="CREATE EVENT event"+id+temp+" ON SCHEDULE AT '"+k+"' DO UPDATE dokimastiko.training SET finishedtime='"+k+"' WHERE userid='"+id+"' AND id='"+temp+"'";
                stmt = dbConn.createStatement();
                stmt.executeUpdate(query);
            }else{
            	query = "INSERT INTO training(userid,Start,Stop) VALUES('"+id+"',NOW(), NOW()+INTERVAL 30 MINUTE)";
                Statement stmt = dbConn.createStatement();
                stmt.executeUpdate(query);
                query = "SELECT id FROM training WHERE userid='"+id+"' AND stop=NOW()+INTERVAL 30 MINUTE";
                stmt = dbConn.createStatement();
                ResultSet rs =stmt.executeQuery(query);
               
               int temp = rs.getInt("id");
               query="CREATE EVENT event"+id+temp+"ON SCHEDULE AT NOW()+INTERVAL 30 MINUTE DO UPDATE dokimastiko.training SET finishedtime=NOW() WHERE userid='"+id+"' AND id='"+temp;
             
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
    
    }
    public static Timestamp getPrevious(int id){
    	Connection dbConn = null;
    	Timestamp d = null ;
    	java.util.Date date= new java.util.Date();
            try {
				dbConn = DBConnection.createConnection();
				 String query = "SELECT Stop FROM training WHERE userid='"+id+"' AND finishedtime='0000-00-00 00:00:00' ORDER BY stop DESC LIMIT 1";
		       
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
	       
	         Statement stmt = dbConn.createStatement();
	         ResultSet rs = stmt.executeQuery(query);
	         int i =0;
	         while(rs.next()){
	        	
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
       
    public static void cancelTrain(int userid,int trainid){
    	ArrayList<Integer> id=new ArrayList<Integer>();
    	ArrayList<Timestamp> start = new ArrayList<Timestamp>();
    	ArrayList<Timestamp> stop = new ArrayList<Timestamp>();
    	Connection dbConn = null;
    	try {
			dbConn = DBConnection.createConnection();
			
			String query="SELECT start,stop,id FROM training WHERE userid='"+userid+"' AND finishedtime='0000-00-00 00:00:00' ORDER BY stop ASC";
			Statement stmt = dbConn.createStatement();
			ResultSet rs =stmt.executeQuery(query);
			
			 while(rs.next()){
				 
				 id.add(rs.getInt("id"));
				 start.add(rs.getTimestamp("start"));
				 stop.add(rs.getTimestamp("stop"));
			 }
			 int k=getPosition(id,trainid);
			    cancelTable(trainid);
				alterTable(k,start,stop,id);       
	       
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    }
    public static int getPosition(ArrayList<Integer> id,int trainid){
    	int k = id.size();
    	 System.out.println("inside GET POSITION"+k);
    	 for(int i=0;i<k;i++){
    		 System.out.println("inside GET POSITION FOR"+i);
			 if(trainid==id.get(i)){
				 System.out.println("inside GET POSITION IF"+i);
				return i;
				}
		 }
    	return 0;
    }
    public static int getUserID(int trainid) throws Exception{
    	Connection dbConn = null;
    
			dbConn = DBConnection.createConnection();
			
			String query="SELECT userid FROM training WHERE id='"+trainid+"'";
			Statement stmt = dbConn.createStatement();
			ResultSet rs =stmt.executeQuery(query);
			rs.next();
			return rs.getInt("userid");
    }
    public static void alterTable(int pos,ArrayList<Timestamp> start,ArrayList<Timestamp> stop , ArrayList<Integer> id) throws Exception{
    	int temp = id.size();
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    	System.out.println("Inside AlterTable pos:"+pos);
    	String query;
          for(int i=pos; i<temp-1;i++){
    		System.out.println("Inside AlterTable:"+i);
    		if(pos==0){
    			if(i==1){    				
    			query="UPDATE training SET start=NOW(),stop= NOW()+INTERVAL 30 MINUTE WHERE id='"+id.get(i+1)+"'";
    			System.out.println(query);
    		
    			}else{
    				
    				query="UPDATE training SET start='"+getPrevious(id.get(i))+"',stop= '"+getPrevious(id.get(i))+"'+INTERVAL 30 MINUTE WHERE id='"+id.get(i+1)+"'";
    				System.out.println(query);
    				
    			}
    		}else{    		
    	    query="UPDATE training SET start='"+start.get(i)+"',stop='"+stop.get(i)+"' WHERE id='"+id.get(i+1)+"'";
    		System.out.println(query);
			
			}
    	System.out.println(query);
		Statement stmt = dbConn.createStatement();
		stmt.executeUpdate(query);
			
    	  }
      
    	
    }
    public static void cancelTable(int id) throws Exception{
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    	String query="UPDATE training SET finishedtime = NOW() WHERE id='"+id+"'";
		Statement stmt = dbConn.createStatement();
		stmt.executeUpdate(query);
    }
    public static void lasttime(int id){
    	Connection dbConn = null;
    
			try {
				dbConn = DBConnection.createConnection();
				String query = "UPDATE users SET teletaio=NOW() WHERE id='"+id+"'";
		      
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
				String query = "SELECT id,tuxaiosarithmos FROM training WHERE userid='"+id+"' AND finishedtime='0000-00-00 00:00:00'";
		        
		         Statement stmt = dbConn.createStatement();
		         ResultSet rs = stmt.executeQuery(query);
		         int i =0;
		         while(rs.next()){
		        	
		        	 
		        	 map.put("tuxaiosarithmos"+i, rs.getInt("tuxaiosarithmos"));
		             map.put("id"+i, rs.getInt("id"));
		        	 i++;
		         }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
			
    
  
    }
}
