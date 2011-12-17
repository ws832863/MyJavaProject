package com.ovgu.tcpserver;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ServerFrame implements ActionListener,Runnable{

	/**
	 * @param args
	 */
	JPanel jpTop=null;
	JPanel jpMid=null;
	JPanel jpDown=null;
	JLabel jlInfo=null;
	JButton jbtnStart=null;
	JButton jbtnStop=null;
	JTextField jfport=null;
	String Serverport=null;
	JLabel jl=null;
	TcpChatServer cServer;
	public ServerFrame() {
		jpTop=new JPanel();
		jpMid=new JPanel();
		jpDown=new JPanel();
		jfport=new JTextField("8800");
		jfport.setColumns(5);
		
		jlInfo=new JLabel("Please input the Port Nummer");
		jbtnStart=new JButton("Start Server");
		jbtnStop=new JButton("Stop Server");
		
		
		// TODO Auto-generated constructor stub
	}

	public void setEnable(){
		jbtnStart.setEnabled(!jbtnStart.isEnabled());
		jbtnStop.setEnabled(!jbtnStop.isEnabled());
		
		jfport.setEnabled(!jfport.isEnabled());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new ServerFrame().init();
	}//end of method main
	
	public void init()
	{	
		jl=new JLabel();
		jbtnStart.setEnabled(true);
		jbtnStop.setEnabled(false);
		jlInfo.setEnabled(true);
		JFrame sf=new JFrame("TcpServer for Chatroom");
		Container c=sf.getContentPane();
		
		jpTop.add(jlInfo);
		c.add("North",jpTop);
		
		jpMid.add(jfport);
		jpMid.add(jl);
		c.add("Center",jpMid);
		
		
		jpDown.add(jbtnStart);
		jpDown.add(jbtnStop);
		c.add("South",jpDown);
		
		
		RegistAction();//regist all the Action
		com.ovgu.myutils.CommonUtils.setFrameCenter(sf, false);
		sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sf.setSize(300,200);
		
		sf.setVisible(true);
	}//end of method init
	public void RegistAction(){
		jbtnStart.setActionCommand("StartServer");
		jbtnStart.addActionListener(this);
		
		jbtnStop.setActionCommand("StopServer");
		jbtnStop.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command=e.getActionCommand();
		com.ovgu.myutils.CommonUtils.PrintMe("actionperfermed:"+ command);
		if(command.equals("StartServer")){
			
			String port=jfport.getText().trim();
			if(com.ovgu.myutils.CommonUtils.checkIsNum(port)){
			//int p=Integer.parseInt(port);
			com.ovgu.myutils.CommonUtils.PrintMe("The server runs at port :"+port);
			
				setEnable();
				jlInfo.setText("Server is running");
				jl.setText("please run the client on same Port:" + port);
				Serverport=port;
				new Thread(new ServerFrame()).start();
				//Thread tt=new Thread();
		
			}else
			{
				
				JOptionPane.showMessageDialog(null, "Please Input the valid port nummber!");
				return;
			}
			
			
		}else if (command.equals("StopServer")){
			setEnable();
			cServer.stopServer();
			jlInfo.setText("Server is Stopped,Please Input a valied port nummber");
		}
	}//end of actionedperformed
	public void run(){
		cServer=new TcpChatServer(Serverport);
	}

}
