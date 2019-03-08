import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Rule {
	
	ArrayList RawRelation;
	ArrayList Rules;
	Processing Proc;
	Date Date1;
	Time Time1;
	int interval=10;
	float support;
	String name;
	
	public Rule(int value1,float value2, String name){
		interval=value1;
		support=value2;
		this.RawRelation=new ArrayList();
		Proc=new Processing(name);
		
	}
	
	public void AddAll(){
		Proc.RuleGet();
		int j=0;
		String add=null;
		Date1=((Alarm)Proc.Alarms.get(0)).OccurDate;
		Time1=((Alarm)Proc.Alarms.get(0)).OccurTime;
		
		for(int i=0;i<Proc.Alarms.size();i++)
		{
			if(Date1.equals(((Alarm)Proc.Alarms.get(i)).OccurDate))
			{
				if(comp(Time1,((Alarm)Proc.Alarms.get(i)).OccurTime,interval))
				{
					if(add==null)
					{
						add=((Alarm)Proc.Alarms.get(i)).Name;
					}
					else
					{
						add=add+" "+((Alarm)Proc.Alarms.get(i)).Name;
					}
				}
				else
				{
					Time1=((Alarm)Proc.Alarms.get(i)).OccurTime;
					this.RawRelation.add(add);
					add=((Alarm)Proc.Alarms.get(i)).Name;

				}
			}
			else
			{
				Date1=((Alarm)Proc.Alarms.get(i)).OccurDate;
				Time1=((Alarm)Proc.Alarms.get(i)).OccurTime;
				
				this.RawRelation.add(add);
				add=((Alarm)Proc.Alarms.get(i)).Name;
			}
		}
		
		if(add!=null)
		{
			this.RawRelation.add(add);
		}
	}
	
	public boolean comp(Time time1, Time time2, int val)
	{
	
		if((time2.getHours())-(time1.getHours())>1)
		{
			return false;
		}
		
		else if((time2.getHours())-(time1.getHours())==1)
		{
			if((time2.getMinutes()+60)-(time1.getMinutes())<=val)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		else if((time2.getHours())-(time1.getHours())==0)
		{
			if((time2.getMinutes())-(time1.getMinutes())<=val)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		return false;
	}

	

	
	public void mine(){
		String[] input =new String[RawRelation.size()];
		for(int i=0;i<RawRelation.size();i++)
		{
			input[i]=(String)RawRelation.get(i);
			System.out.println((String)RawRelation.get(i));
		}
		this.Rules=new Apriori(input, support).mine();
	}
	
	
}
