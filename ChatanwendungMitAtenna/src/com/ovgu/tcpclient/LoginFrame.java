package com.ovgu.tcpclient;
import java.awt.*;

import javax.swing.*;

import com.ovgu.myutils.CommonUtils;
import com.ovgu.plugins.hotSpotPlugin;



import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LoginFrame extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4105816710640063452L;
	/**
	 * Method main
	 *
	 *
	 * @param args
	 *
	 */
	 private JFrame fLogin;
	 private JLabel jlName;
	 private JLabel jlPwd;
	 private JTextField jfName;
	 private JTextField jfPwd;
     private JLabel jlTips;
     private static int defaultport=8800;
     private String correctName="User001";
	 private String correctPwd="user001";
	 private JTextField jfIp;
	 JComboBox<String> Jclass;
	 private static String serverIp="";
	 
     public static void main(String[] args) {
    	 boolean auth=true;
		
    	 if(args.length==1){
			defaultport=Integer.parseInt(args[0].toString());
		}else if(args.length==2)
		{
			defaultport=Integer.parseInt(args[0].toString());
			serverIp=args[1].toString();
		}
		//#if authentication
			new LoginFrame().init();
			auth=false;
		//#endif
		if(auth){
			new LoginFrame().LoginSelect();
		}
		
     }

	public void init(){
		
		fLogin=new JFrame("Login");
		Container fc=fLogin.getContentPane();
		
		JPanel jpBtn=new JPanel();//add jpanel, contains 2 buttons
		JButton btnLogin=new JButton("Login");
		JButton btnQuit=new JButton("Quit");
		btnLogin.setActionCommand("Login");
		btnQuit.setActionCommand("Quit");
		
		jpBtn.add(btnLogin);
		jpBtn.add(btnQuit);
		
		
		JPanel jpTextField=new JPanel();//add jpanel contains 2label and 2 textfield
		
		
		jlName=new JLabel("User Name:");
		jlPwd=new JLabel ("Password :");
		jlName.setSize(20,10);
		jlPwd.setSize(20,10);
		
		jfName=new JTextField("User001",15);
		jfPwd=new JTextField("user001",15);
		jlTips=new JLabel("");
		jlTips.setForeground(Color.red);
		jlTips.setSize(15,15);
		jpTextField.add(jlName);
		jpTextField.add(jfName);
		jpTextField.add(jlPwd);
		jpTextField.add(jfPwd);
		jpTextField.add(jlTips);
		//f
		
		
		
		try {
			jfIp=new JTextField(InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jfIp.setText("127.0.0.1");
		}
		jfIp.setSize(20,10);
		
		String output[]={				
		//#if GUI
		"GUI"
		//#endif
		//#if Console
//@		"Konsole"
		//#endif
		};
		Jclass=new JComboBox<String>(output);
		Jclass.setSize(150, 80);
		JPanel jcontainer =new JPanel();
		
		JLabel jblServer=new JLabel("Server Ip");
		JPanel jServer=new JPanel();
		jServer.add(jblServer);
		jServer.add(jfIp);
		
		jcontainer.setLayout(new BorderLayout());
		jcontainer.add(jpTextField,"Center");
		
		jcontainer.add(Jclass,"South");
		jcontainer.add(jServer,"North");
		
		
		//fc.add(jpTextField,"North");
		fc.add(jpBtn,"South");
		fc.add(jcontainer,"Center");
		//Event for window closing
		fLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*fLogin.addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent e){
			//	fLogin.dispose();
				System.exit(0);
			}
			
		});*/
		
		btnQuit.addActionListener(this);
		btnLogin.addActionListener(this);
		fLogin.setSize(300,200);
		setFrameCenter(fLogin,false);
		fLogin.setVisible(true);
	}
	public void LoginSelect(){
		try{
			//#if GUI

				CommonUtils.PrintMe("the user select gui");
				new TemplateChatClient(defaultport,serverIp,new hotSpotPlugin());					

			//#endif
			
			//#if Console
//@				
//@					new TemplateChatConsole(defaultport,serverIp);
//@					
			//#endif
		//#if OnlineUserList
		System.out.println("with onlineuserlise");
		//#endif
		}catch(Exception ex){
			System.out.println("Initialization failed!!");
			return;
		}
	}
	public void actionPerformed(ActionEvent e){
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals("Login")){
			serverIp=jfIp.getText();
			if(!CommonUtils.checkIsCorrectIp(serverIp)){
				JOptionPane.showMessageDialog(null,"Ip Format is incorrect");
				return;
			}
			
			if(isPasswordCorrect(jfName.getText().trim(),jfPwd.getText().trim())){
		
				jlTips.setText("input Correct");
				System.out.println("Correct");
				
				//new ChatClient(defaultport);
				
				//two class plugins. gui and konsole..
				//gui ---TemplateChatClient
				//konsole---ChatConsole
				LoginSelect();
			
				
				//
				
				//cf.setVisible(true);
				fLogin.setVisible(false);
				fLogin.dispose();
				
			}
			else{
				jlTips.setText("Username or Password is not correct.");
				System.out.println("False Retry!");
				jfPwd.selectAll();
			}
				
		}else
		{
			System.exit(0);
		}
	}
	
	
	//check the username and password
	private  boolean isPasswordCorrect(String strName,String strPwd){
		boolean isCorrect=true;

		if(strName.length()!=correctName.length()||correctPwd.length()!=strPwd.length()){
			isCorrect=false;
		}else{
			isCorrect=correctName.equals(strName)&&correctPwd.equals(strPwd);
		}
		return isCorrect;
	}
	// set the frame on the center of bildschirm
	private static void setFrameCenter(Frame f,boolean flag){
		//f.getToolkit();
		//	Dimension screen=f.getToolkit().getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕尺寸对象
		Dimension myframe=f.getSize();//获取当前窗体的尺寸对象		
		int w=(screen.width-myframe.width)/2;//水平位置
		int h=(screen.height-myframe.width)/2;//垂直位置
		f.setLocation(w,h);
		f.setResizable(flag);
	}
		
}
