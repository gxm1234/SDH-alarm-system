//This is the class used for storing single alarm information.
import java.sql.*;

public class Alarm {
	int ID;
	String Device;
	Date OccurDate;
	Time OccurTime;
	String Name;
	String PlaceMaybe;
	String Level;
	String Type;
	

	
	/*public Alarm(int ID, String Device, Date OccurDate, Time OccurTime, String Name){
		this.ID=ID;
		this.Device=Device;
		this.OccurDate=OccurDate;
		this.OccurTime=OccurTime;
		this.Name=Name;
	}*/
	
	//Constructor
	public Alarm(String Name){
		this.Name=Name;
	}
	
	/*public void Print()
	{
		System.out.println(this.ID);
		System.out.println(this.PlaceMaybe);
		System.out.println(this.Level);
		System.out.println(this.Type);
	}*/

}
