import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlarmSt {
	String Device;
	int Status;
	int All_Warning;
	int Critical;
	int Main;
	int NotMain;
	int Warning;
	int Tele;
	int Devi;
	int QoS;
	int Proc;
	int Envi;
	int Secu;
	String MainPart;
	String[] MainFive;
	Map Place;
	String[] MainPlaces;
	ArrayList<Alarm> MF;
	ArrayList<String> RW;
	
	
	
	
	public AlarmSt(String Device){
		this.Device=Device;
		
		
		
		
		this.All_Warning=0;
		this.Critical=0;
		this.Main=0;
		this.NotMain=0;
		this.Warning=0;
		this.Tele=0;
		this.Devi=0;
		this.QoS=0;
		this.Proc=0;
		this.Envi=0;
		this.Secu=0;
		this.Place=new HashMap<String,Integer>();
		this.MainPlaces=new String[3];
		this.MainFive=new String[5];
		this.MF=new ArrayList<Alarm>();
	}
	
	
	public void Status(){
		if(this.Critical!=0)
		{
			this.Status=1;
		}
		else if(this.Main!=0)
		{
			this.Status=2;
		}
		else if(this.NotMain!=0)
		{
			this.Status=3;
		}
		else
		{
			this.Status=0;
		}
		int size=this.MF.size();
		if(size>5)
		{
			size=5;
		}
		for(int i=0;i<size;i++)
		{
			
			this.MainFive[i]=((Alarm)this.MF.get(size-i-1)).Name;
			System.out.println(MainFive[i]);
		}
		
	}
	
	
	public void Add(Alarm W){

		this.All_Warning++;
		if(W.Type.equals("Tele"))
			this.Tele++;
		else if(W.Type.equals("Devi"))
			this.Devi++;
		else if(W.Type.equals("QoS"))
			this.QoS++;
		else if(W.Type.equals("Proc"))
			this.Proc++;
		else if(W.Type.equals("Envi"))
			this.Envi++;
		else if(W.Type.equals("Secu"))
			this.Secu++;
		if(W.Level.equals("M"))
			this.Main++;
		else if(W.Level.equals("N"))
			this.NotMain++;
		else if(W.Level.equals("C"))
			this.Critical++;
		else if(W.Level.equals("W"))
			this.Warning++;
		if(Place.containsKey(W.PlaceMaybe))
		{
			int Value=(int) Place.get(W.PlaceMaybe);
			Place.put(W.PlaceMaybe, Value+1);
		}
		else
		{
			Place.put(W.PlaceMaybe, 1);
		}
		
		
		this.MainFive(W);
	}
	
	/*
	public void Update(){
		this.All_Warning=0;
		this.Critical=0;
		this.Main=0;
		this.NotMain=0;
		this.Warning=0;
		this.Tele=0;
		this.Devi=0;
		this.QoS=0;
		this.Proc=0;
		this.Envi=0;
		this.Secu=0;
		this.MainPart=null;
		this.MainFive=new String[5];
		this.MainPlaces=new String[3];
	}
	*/
	
	public void MainFive(Alarm W){
		
		if(this.MF.size()!=0)
		{
			for(int i=0;i<this.MF.size();i++)
			{
				if(((Alarm)this.MF.get(i)).ID==W.ID)
				{
					return;
				}
			}
		}
		else
		{
			this.MF.add(W);
			return;
		}
		
		
		
		if(this.MF.size()!=0)
		{
			for(int i=0;i<this.MF.size();i++)
			{
				if(((Alarm)this.MF.get(i)).ID<W.ID)
				{
					ArrayList MF1=new ArrayList<Alarm>();
					for(int k=0;k<i;k++)
					{
						MF1.add((Alarm)this.MF.get(k));
					}
					MF1.add(W);
					for(int k=i;k<this.MF.size();k++)
					{
						MF1.add((Alarm)this.MF.get(k));
					}
					this.MF=MF1;
					return;
				}
			}
		}
		

	}
	
	public void MainPart(){
		int mapsize = Place.size();
		
		Iterator keyValuePairs1 = Place.entrySet().iterator();
		for (int i = 0; i < mapsize; i++)
		{
			Map.Entry entry = (Map.Entry) keyValuePairs1.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println(value);
			
			
			
			
			for(int j=0;j<3;j++)
			{
				if(this.MainPlaces[j]!=null)
				{
					if((int)this.Place.get(this.MainPlaces[j])<(int)value)
					{
						if(j==0)
						{
							this.MainPlaces[2]=this.MainPlaces[1];
							this.MainPlaces[1]=this.MainPlaces[0];
							this.MainPlaces[0]=(String)key;
							break;
						}
						else if(j==1)
						{
							this.MainPlaces[2]=this.MainPlaces[1];
							this.MainPlaces[1]=(String)key;
							break;
						}
						else if(j==2)
						{
							this.MainPlaces[j]=(String)key;
							break;
						}
						
						
						
					}
				}
			  	else
			  	{
			  		this.MainPlaces[j]=(String)key;
			  		break;
			  	}
			}
		}
		
		
	}
	
	
	
}
