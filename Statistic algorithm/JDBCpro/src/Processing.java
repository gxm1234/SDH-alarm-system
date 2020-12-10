import java.sql.*;
import java.util.ArrayList;

public class Processing {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/sdh_warning";

	
	static final String USER = "root";
	static final String PASS = "123456";
	
	Connection con;
	ArrayList Alarms;
	ArrayList DeviceList;
	Date DateNow;
	Time TimeNow;
	String DateString;
	boolean working;
	String rawname;
	boolean again;
	
	
	public Processing(String name){
		again=false;
		rawname=name;
        try {  
            Class.forName(JDBC_DRIVER);  
            System.out.println("Driver success!");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
            System.out.println("Driver failed!");  
        }  
          
        this.con = null;  
        try {  
            this.con = DriverManager.getConnection(DB_URL,USER,PASS);  
            System.out.println("Connection success!");    
        } catch (SQLException e) {  
            e.printStackTrace();  
        } 
        
        
		Statement stmt = null;
		Date DNow=null;
		Time TNow=null;
		try
		{
			System.out.println("Creating statement...");
			stmt = this.con.createStatement();
	
			System.out.println("Fetching records with condition...");
			String sql = "select * from "+rawname;
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			this.DateNow= rs.getDate("Date");
			this.TimeNow= rs.getTime("Time");
			

			DateString=DateNow.toString()+" "+TimeNow.toString();
				
			
			rs.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
		
		try
		{
			System.out.println("Creating statement...");
			stmt = this.con.createStatement();
	
			System.out.println("Fetching records with condition...");
			String sql = "select * from statistics";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				rs.last();
				DNow= rs.getDate("Date");
				TNow= rs.getTime("Time");
			}




			
			
				
			
			rs.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
        
		if(DNow!=null&&TNow!=null)
		{
	        if(DNow.equals(this.DateNow))
	        {
	        	if(TNow.equals(this.TimeNow))
	        	{
	        		again=true;
	        	}
	        }
		}

		
		
        this.Alarms=new ArrayList<Alarm>();
        this.DeviceList=new ArrayList<String>();
        this.working=true;
	}
	
	
	
	
	
	public void close(){
        try {  
            this.con.close();  
            System.out.println("ÒÑ¾­ÊÍ·Å");  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        
        this.working=false;
	}
	

	
	
	public Alarm Verify(Alarm W){
		Statement stmt = null;
		try
		{
			//System.out.println("Creating statement...");
			stmt = this.con.createStatement();
	
			//System.out.println("Fetching records with condition...");
			String sql = "SELECT ID, AlarmName, PlaceMaybe, Type, Level FROM alarminf" +
			                   " WHERE AlarmName='"+W.Name+"' ";
			ResultSet rs = stmt.executeQuery(sql);
	
			while(rs.next()){
	
				W.ID  = rs.getInt("ID");
				W.PlaceMaybe = rs.getString("PlaceMaybe");
				W.Type = rs.getString("Type");
				W.Level=rs.getString("Level");
				
			}
		rs.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
		//System.out.println("Goodbye!");
		return W;
	}
	
	
	public void RuleGet(){
		Statement stmt = null;
		int k=0;
		try
		{
			System.out.println("Creating statement...");
			stmt = this.con.createStatement();
	
			System.out.println("Fetching records with condition...");
			
			String sql = "SELECT Device, Date, Time, AlarmName FROM "+rawname;
			

			ResultSet rs = stmt.executeQuery(sql);
			

			while(rs.next()){
				
				Alarm W = new Alarm(rs.getString("AlarmName"));
				W.Device=rs.getString("Device");
				W.OccurDate = rs.getDate("Date");
				W.OccurTime = rs.getTime("Time");
				this.Alarms.add(W);
			}
		System.out.println("FInish!");
		rs.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
	}
	
	public void AddAll(){
		Statement stmt = null;
		try
		{
			System.out.println("Creating statement...");
			stmt = this.con.createStatement();
	
			System.out.println("Fetching records with condition...");
			
			String sql = "SELECT Device, Date, Time, AlarmName FROM "+rawname+
	                   " WHERE Date='"+this.DateNow+"' and Time='"+this.TimeNow+"' ";
			

			ResultSet rs = stmt.executeQuery(sql);
			

			while(rs.next()){
				
				Alarm W = new Alarm(rs.getString("AlarmName"));
				W.Device=rs.getString("Device");
				W.OccurDate = rs.getDate("Date");
				W.OccurTime = rs.getTime("Time");
				this.Alarms.add(W);
				if(this.DeviceList.size()==0)
				{
					this.DeviceList.add(W.Device);
				}
				else if(!(this.DeviceList.contains(W.Device)))
				{
					this.DeviceList.add(W.Device);
					
				}
					
			}
		System.out.println("FInish!");
		rs.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
	}
	
	/*public void Delete(){
		Statement stmt = null;
		try
		{
			stmt = this.con.createStatement();
			String sql = "truncate table statistics";
			stmt.executeUpdate(sql);
			
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
		
	}*/
	
	public void Write(AlarmSt Wst, int number){
		Statement stmt = null;
		try
		{
			stmt = this.con.createStatement();
			
			String sql = "INSERT INTO statistics " +
	                   "VALUES ('"+DateNow+"', '"+TimeNow+"', '"+Wst.Device+"', "+Wst.Status+", "+Wst.All_Warning+
	                   ", "+Wst.Critical+", "+Wst.Main+", "+Wst.NotMain+", "+Wst.Warning+
	                   ", "+Wst.Tele+", "+Wst.Devi+", "+Wst.QoS+", "+Wst.Proc+", "+Wst.Envi+
	                   ", "+Wst.Secu+", '"+Wst.MainPlaces[0]+"', '"+Wst.MainPlaces[1]+"', '"+
	                   Wst.MainPlaces[2]+"', '"+Wst.MainFive[0]+"', '"+Wst.MainFive[1]+"', '"+
	                   Wst.MainFive[2]+"', '"+Wst.MainFive[3]+"', '"+Wst.MainFive[4]+"', null"
		                   		+ ", null, null, null, null)";
			
			
			
			
			
			stmt.executeUpdate(sql);
			
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
		
		UpdateRule(Wst);
	}
	
	
	
	
	
	public void UpdateRule(AlarmSt Wst){
		Statement stmt = null;
		
		int length;
		if(Wst.RW.size()>5)
		{
			length=5;
		}
		else
		{
			length=Wst.RW.size();
		}
		
		for(int i=0;i<length;i++)
		{
			try
			{
				stmt = this.con.createStatement();
				
				String sql = "UPDATE statistics SET Relative"+(i+1)+"= '"+(String)Wst.RW.get(i)+"' "
						+ "WHERE Device='"+Wst.Device+"'";
				
				
				stmt.executeUpdate(sql);
				
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
			catch(Exception e){

				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	public ArrayList find(String Name, ArrayList<String> RV){
		Statement stmt = null;
		try
		{

			stmt = this.con.createStatement();
	

			
		
			
			String sql = "SELECT Alarm1, Alarm2, Alarm3 FROM rule " +
						"WHERE Alarm1='"+Name+"' OR Alarm2='"+Name+"' OR Alarm3='"+Name+"' ";
			

			ResultSet rs = stmt.executeQuery(sql);
			

			while(rs.next()){
				
				String Alarm1 =rs.getString("Alarm1");
				String Alarm2 =rs.getString("Alarm2");
				String Alarm3 =rs.getString("Alarm3");
				
				if(!(Alarm1.equals(Name)))
				{
					RV.add(Alarm1);
				}
				
				if(!(Alarm2.equals(Name)))
				{
					RV.add(Alarm2);
				}
				
				if(!(Alarm3.equals(Name)))
				{
					
					if(!(Alarm3.isEmpty()))
					{
						RV.add(Alarm3);
					}
				}
	
			}
			
		rs.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
		
		return RV;
	}
	
	public void WriteRule(String[] Result){
		Statement stmt = null;
		try
		{
			stmt = this.con.createStatement();
			
			String sql=null;
			System.out.println("Once");
			if(Result.length==2)
			{
				sql = "INSERT INTO rule " +
		                   "VALUES ('"+Result[0]+"', '"+Result[1]+"', 'null')";
			}

			if(Result.length==3)
			{
				sql = "INSERT INTO rule " +
		                   "VALUES ('"+Result[0]+"', '"+Result[1]+"', '"+Result[2]+"')";
			}

			
			stmt.executeUpdate(sql);
			
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e){

			e.printStackTrace();
		}
	}	
	
	public ArrayList RuleVerify(String Device){
		ArrayList<String> RV = new ArrayList<String>();
		ArrayList<String> Origin = new ArrayList<String>();
		for(int i=0;i<Alarms.size();i++)
		{
			Alarm a = (Alarm)Alarms.get(i);
			if(a.Device.equals(Device))
			{
				if(!(Origin.contains(a.Name)))
				{
					RV=find(a.Name,RV);
					Origin.add(a.Name);
				}
			}
		}
		
		for(int i=0;i<RV.size();i++)
		{
			System.out.println((String)RV.get(i));
		}
		return RV;
	}
	
}
