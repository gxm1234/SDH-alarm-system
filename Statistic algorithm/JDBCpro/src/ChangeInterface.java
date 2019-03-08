import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.awt.Font;


public class ChangeInterface {
	private JFrame f1;
	
	private JTextField val2;
	private JTextField val3;
	private JTextField namefield;
	private JButton OK;
	private JButton Cancel;
	private JLabel title;
	
	private JLabel v2;
	private JLabel v3;
	private JLabel namelabel;
	int status;
	int value2;
	float value3;
	String name;
	
	
	
	public ChangeInterface(int value2, float value3, String name){
		this.value2=value2;
		this.value3=value3;
		this.name=name;
		status=0;
	}
	

	public void init(){
		
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

			value2=Integer.parseInt(bfreader.readLine());
			value3=Float.parseFloat(bfreader.readLine());
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
		
		
		
		
		
		f1=new JFrame("Option");
		val2=new JTextField(String.valueOf(value2));
		val3=new JTextField(String.valueOf(value3));
		namefield=new JTextField(name);
		title=new JLabel("Option");
		v2=new JLabel("time interval of mine:(in minute)");
		v3=new JLabel("minimum support of mine:(less than 1)");
		namelabel=new JLabel("source table:");
		status=1;
		OK=new JButton("OK!");
		Cancel=new JButton("Cancel");
		f1.setLayout(null);
		f1.setBounds(300,100,400,400);
		
		val2.setBounds(300,100,50,20);
		val3.setBounds(300,130,50,20);
		namefield.setBounds(300,160,50,20);
		OK.setBounds(100,300,100,50);
		Cancel.setBounds(200,300,100,50);
		title.setBounds(150,10,200,100);
		title.setFont(new Font("Dialog",1,30));
		
		v2.setBounds(50,100,230,20);
		v3.setBounds(50,130,230,20);
		namelabel.setBounds(50,160,230,20);
		
		f1.add(val2);
		f1.add(val3);
		f1.add(namefield);
		f1.add(OK);
		f1.add(Cancel);
		f1.add(title);
		f1.add(v2);
		f1.add(v3);
		f1.add(namelabel);
		f1.addWindowListener(new WindowAdapter() 
		{  
			  
			  
			public void windowClosing(WindowEvent e) 
			{  

				status=0;
				super.windowClosing(e); 
				
			}  
			  
		});
		
		Cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				status=0;
				f1.dispose();
			}


		});
		
		OK.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				String s2=val2.getText().toString();
				String s3=val3.getText().toString();
				String getname=namefield.getText().toString();
				value2=Integer.parseInt(s2);
				value3=Float.parseFloat(s3);
				name=getname;
				
				status=0;
				
				File file = new File("value.txt");
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				BufferedWriter bfwriter = null;
				try 
				{
					FileWriter writer1 = new FileWriter(file,false);
					writer1.write("");
					writer1.close();
					FileWriter writer2 = new FileWriter(file,true);
	
					bfwriter = new BufferedWriter(writer2);
					int p = 0;
					bfwriter.write(s2);
					bfwriter.newLine();
					bfwriter.write(s3);
					bfwriter.newLine();
					bfwriter.write(getname);

				} 
				catch (IOException h) 
				{
					// TODO Auto-generated catch block
					h.printStackTrace();
				} 
				finally
				{
					try 
					{
						bfwriter.close();
					} 
					catch (IOException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				
				f1.dispose();
			}


		});
		
		f1.setVisible(true);
	}
	
}
