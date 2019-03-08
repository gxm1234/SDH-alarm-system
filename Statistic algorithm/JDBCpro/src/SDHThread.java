import java.util.ArrayList;

public class SDHThread implements Runnable {
	

	Thread Thr;
	Processing Proc;
	//int value;
	String name;
	
	public SDHThread(){
		
	}
		
	final long timeInterval = 10000;
	public void run() {
		while (true) {
					
			System.out.println(timeInterval);
			Proc=new Processing(name);
			if(Proc.again==false)
			{
				Proc.AddAll();
				ArrayList Wsts=new ArrayList<AlarmSt>();
				System.out.println(Proc.Alarms.size());
				for(int i=0;i<Proc.DeviceList.size();i++)
				{
					System.out.println((String) Proc.DeviceList.get(i));
					Wsts.add(new AlarmSt((String) Proc.DeviceList.get(i)));
								
				}
							
							
							
				for(int i=0;i<Proc.Alarms.size();i++)
				{
					Alarm W=Proc.Verify((Alarm)Proc.Alarms.get(i));
					for(int j=0;j<Wsts.size();j++)
					{
						if(W.Device.equals(((AlarmSt)Wsts.get(j)).Device))
						{
							((AlarmSt)Wsts.get(j)).Add(W);
						}
										
					}
								
								
				}
							
				for(int i=0;i<Wsts.size();i++)
				{
					((AlarmSt)Wsts.get(i)).Status();
					((AlarmSt)Wsts.get(i)).MainPart();
				}			
				for(int i=0;i<Wsts.size();i++)
				{
					((AlarmSt)Wsts.get(i)).RW=Proc.RuleVerify(((AlarmSt)Wsts.get(i)).Device);	
				}
				
				
				
							
				for(int i=0;i<Wsts.size();i++)
				{
					Proc.Write((AlarmSt) Wsts.get(i),i);
				}
				
			}
						
			Proc.close();
						
						
			try 
			{
				Thread.sleep(timeInterval);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
	

	
	
	

}
