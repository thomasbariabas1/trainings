

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

import javax.servlet.http.HttpSession;






public class DBConnection {
	 /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    //CONNECTION TO DB
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
    //AUTHENTICATION WITH DB
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
    
	
    //INSERT TRAINING+COUNT TRAINING+CREATING EVENTS
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
            	
            	System.out.println(s);
                query = "INSERT INTO training(userid,Start,Stop,tuxaiosarithmos) VALUES('"+id+"','"+d+"','"+s+"','"+kat+"')";
                train="User with id:" +id+" added train From:"+d+" To:"+s;
                System.out.println(query);
                Statement stmt = dbConn.createStatement();
                stmt.executeUpdate(query);
                
                query = "SELECT id FROM training WHERE userid='"+id+"' AND stop='"+s+"'";
                System.out.println(query);
                stmt = dbConn.createStatement();
                ResultSet rs =stmt.executeQuery(query);
                rs.next();
                int temp = rs.getInt("id");
                query="CREATE EVENT event"+id+temp+" ON SCHEDULE AT '"+s+"' DO UPDATE dokimastiko.training SET finishedtime='"+s+"' WHERE userid='"+id+"' AND id='"+temp+"'";
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
               rs.next();
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
	
	//GET THE PREVIOUS FROM LAST TRAINING
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
    
    //COUNTER FOR ACTIVE TRAININGS
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
    
    //GETTER FOR START AND STOP TIMES FROM TRAININGS FOR IMPUT USERS
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
    
   
    //CANCEL TRAINING AFTER INKOKING SERVLET
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
    
    
    //KEEPER FOR EACH INTERACTION USER MAKE WITH SERVICE
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
    
    
    //RETURN RANDOM WITH RANGE 1-1000
    public static HashMap<String,Integer> getRnd(int id){
    	HashMap<String,Integer> map= new HashMap<String,Integer>();
    	Connection dbConn = null;
    
			try {
				dbConn = DBConnection.createConnection();
				String query = "SELECT id,tuxaiosarithmos FROM training WHERE userid='"+id+"' AND finishedtime='0000-00-00 00:00:00'";
		         System.out.println(query);
		         Statement stmt = dbConn.createStatement();
		         ResultSet rs = stmt.executeQuery(query);
		         int i =0;
		         while(rs.next()){
		        	 System.out.println("inside while");
		        	 
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
  
    //GET THE CORRENT POSISION OF THE DELETED TRAINING
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
    //RETURN USER ID WHILE SEARCHING WITH TRAINING ID
    public static int getUserID(int trainid) throws Exception{
    	Connection dbConn = null;
    
			dbConn = DBConnection.createConnection();
			
			String query="SELECT userid FROM training WHERE id='"+trainid+"'";
			Statement stmt = dbConn.createStatement();
			ResultSet rs =stmt.executeQuery(query);
			rs.next();
			return rs.getInt("userid");
    }
    
    
    //FUNCTION FOR ALTERATION TABLE ON CANCEL INVOKED
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
    
    //SET THE TRAINING CANCELED ON DB
    public static void cancelTable(int id) throws Exception{
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    	
    	String query="UPDATE training SET finishedtime = NOW() WHERE id='"+id+"'";
		Statement stmt = dbConn.createStatement();
		stmt.executeUpdate(query);
    }
    
    //RETURN THE NUBER OF USERS
    public static int getUsers() throws Exception{
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    	
    	String query="SELECT COUNT(*) AS count FROM users";
		Statement stmt = dbConn.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		rs.next();
		int k = rs.getInt("count");
    	return k;
    }
    
    //RETURN THE NAME OF THE USER
    public static String getName(int id) throws Exception{
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    
    	String query="SELECT name  FROM users WHERE users.id='"+id+"'";
		Statement stmt = dbConn.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		rs.next();
		String s=rs.getString("name");
    	
    	return s;
    }
    //RETURN THE EMAIL OF THE USER
    public static String getEmail(int id) throws Exception{
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    
    	String query="SELECT email  FROM users WHERE users.id='"+id+"'";
		Statement stmt = dbConn.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		rs.next();
		String s=rs.getString("email");
    	
    	return s;
    }
    //RETURN THE COMPLETED TRAININGS OF THE USER
    public static int getCompleted(int id) throws Exception{
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    
    	String query="SELECT COUNT(*) As count  FROM users,training WHERE users.id='"+id+"' AND training.stop=training.finishedtime";
    	
		Statement stmt = dbConn.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		rs.next();
		int s=rs.getInt("count");
    	
    	return s;
    }
    //RETURN THE CANCELED TRANINGS OF THE USER
    public static int getNotcompleted(int id) throws Exception{
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    
    	String query="SELECT COUNT(*) As count  FROM users,training WHERE users.id='"+id+"' AND training.stop!=training.finishedtime AND training.stop!='0000-00-00 00:00:00'";
    	
		Statement stmt = dbConn.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		rs.next();
		int s=rs.getInt("count");
    	
    	return s;
    }
    //RETURN THE LAST INTERACTION OF USER WITH THE SERVICE
    public static Timestamp getLastclick(int id) throws Exception{
    	Connection dbConn = null;
    	dbConn = DBConnection.createConnection();
    	
    	String query="SELECT teletaio  FROM users WHERE users.id='"+id+"' ";
    	
		Statement stmt = dbConn.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		rs.next();
		Timestamp s=rs.getTimestamp("teletaio");
    	
    	return s;
    }
 

}

    
