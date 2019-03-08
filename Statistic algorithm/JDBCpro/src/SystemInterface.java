import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Font;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class SystemInterface {
	
	private JFrame f;
	private JButton start;
	private JButton stop;
	private JButton report;
	private JButton mine;
	private JButton option;
	private JLabel state;
	private JLabel title;
	private SDHThread SDHT;
	String Starttime;
	String Stoptime;
	String LatestDate;
	ChangeInterface C;
	Rule R;
	Thread T;
	int work;
	int stw;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		SDHThread SDHT = new SDHThread();
		SystemInterface I = new SystemInterface(SDHT);
		
		
	}
	
	public SystemInterface(SDHThread SDHT){
		
		
		int val2=0;
		float val3=0;
		String name=null;
		
		File file = new File("value.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		BufferedReader bfreader = null;
		String content = null;
		try {
			FileReader reader = new FileReader(file);

			bfreader = new BufferedReader(reader);

			
			val2=Integer.parseInt(bfreader.readLine());
			val3=Float.parseFloat(bfreader.readLine());
			name=bfreader.readLine();

		} catch (IOException h) {
			// TODO Auto-generated catch block
			h.printStackTrace();
		} finally {
			try {
				bfreader.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		C = new ChangeInterface(val2,val3,name);
		
		init();
		stw=0;
	}

	private void init(){
		work=0;
		f=new JFrame("SDH statistics");
		f.setBounds(300,100,800,600);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start=new JButton("Start!");
		stop=new JButton("Stop!");
		report=new JButton("Report!");
		mine=new JButton("Mine!");
		option=new JButton("Option");
		state=new JLabel("System is stopped.");
		title=new JLabel("SDH statistic system");
		start.setBounds(100,500,100,50);
		stop.setBounds(600,500,100,50);
		report.setBounds(250,500,100,50);
		mine.setBounds(450,500,100,50);
		option.setBounds(350,430,100,50);
		state.setBounds(300,200,200,100);
		title.setBounds(200,50,400,100);
		title.setFont(new Font("Dialog",1,40));
		state.setFont(new Font("Dialog",0,20));
		start.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(C.status==0)
				{
					if(work==0)
					{
						SDHT=new SDHThread();
						SDHT.name=C.name;
						T=new Thread(SDHT);
						
						T.start();
						Starttime=df.format(new Date());
						state.setText("System is working!");
						work=1;
						stw=1;
					}
				}
				else
				{
					Object[] options = {"OK"}; 
					JOptionPane.showOptionDialog(null, "Option window haven't been closed!", "Warning", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
					null, options, options[0]);  
				}

			}
		});
		
		
		stop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(C.status==0)
				{
					if(work==1)
					{
						if(SDHT.Proc.working==false)
						{
							LatestDate=SDHT.Proc.DateString;
							T.stop();
							Stoptime=df.format(new Date());
							state.setText("System is stopped.");
							work=0;
						}
						else
						{
							Object[] options = {"OK"}; 
							JOptionPane.showOptionDialog(null, "The system is still running!", "Warning", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
							null, options, options[0]); 
						}
					}
				}
				else
				{
					Object[] options = {"OK"}; 
					JOptionPane.showOptionDialog(null, "Option window haven't been closed!", "Warning", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
					null, options, options[0]); 
				}

			}
		});
		
		report.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(C.status==0)
				{
					if(stw==1)
					{
						if(work==0)
						{
							Object[] options = {"OK"}; 
							JOptionPane.showOptionDialog(null, "Start time:"+Starttime+
									"\nStop time:"+Stoptime+
									"\n Latest Date of Data:"+LatestDate, "Warning", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
							null, options, options[0]);  
						}
						else
						{
							Object[] options = {"OK"}; 
							JOptionPane.showOptionDialog(null, "The system is working!", "Warning", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
							null, options, options[0]);  
						}
					}
					else
					{
						Object[] options = {"OK"}; 
						JOptionPane.showOptionDialog(null, "The system have just opened!", "Warning", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
						null, options, options[0]);  
					}
				}
				else
				{
					Object[] options = {"OK"}; 
					JOptionPane.showOptionDialog(null, "Option window haven't been closed!", "Warning", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
					null, options, options[0]); 
				}

			}
		});
		
		mine.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(C.status==0)
				{
					if(work==1)
					{
						Object[] options = {"OK"}; 
						JOptionPane.showOptionDialog(null, "The system is working!", "Warning", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
						null, options, options[0]);  
					}
					else
					{
						int i=0;
						R=new Rule(C.value2,C.value3,C.name);
						R.name=C.name;
						R.AddAll();
						R.mine();
						System.out.println(R.Rules.size());
						for(i=0;i<R.Rules.size();i++)
						{
							System.out.println("Rules!");
							R.Proc.WriteRule((String[])R.Rules.get(i));
						}
						R.Proc.close();
						
						Object[] options = {"OK"}; 
						JOptionPane.showOptionDialog(null, "The rule table has been updated!\n"+i+" rules have been added into the table.", "Warning", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
						null, options, options[0]);  
					}
				}
				else
				{
					Object[] options = {"OK"}; 
					JOptionPane.showOptionDialog(null, "Option window haven't been closed!", "Warning", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
					null, options, options[0]); 
				}

			}
		});
		
		
		
		option.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(C.status==0)
				{
					
					if(work==0)
					{
						C.init();
					}
					else
					{
						Object[] options = {"OK"}; 
						JOptionPane.showOptionDialog(null, "The system is working!", "Warning", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
						null, options, options[0]);  
					}
					
				}
				else
				{
					Object[] options = {"OK"}; 
					JOptionPane.showOptionDialog(null, "Option window haven't been closed!", "Warning", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
					null, options, options[0]); 
				}

			}
		});
		
		
		
		f.add(start);
		f.add(stop);
		f.add(report);
		f.add(option);
		f.add(state);
		f.add(title);
		f.add(mine);
	}  
	
}
