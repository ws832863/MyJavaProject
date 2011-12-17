package com.ovgu.tcpclient;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ovgu.designpattern.PluginInterface;
import com.ovgu.myutils.CommonUtils;
import com.ovgu.myutils.Messages;




public class TemplateChatConsole implements TemplateChat,ActionListener {
	
private static int defaultport=8800;
	private static String serverIp="";
	
	TextField tfName = new TextField(15);// for username input
	Button btConnect =new Button("Connect");
	Button btDisconnect =new Button("Disconnect");
	public TextArea tfChat=new TextArea(8,27);
	Button btSend=new Button("Send");
	TextField tfMessage=new TextField(30);
	
	
	Socket socket=null;
	TemplateOfClientListener listen;
	static int userNum=0;
	private String clientName="";
	
	PluginInterface pif;
	
	public TemplateChatConsole(int port,String sIp){
		defaultport=port;
		serverIp=sIp;
		//try {
		//	serverIp=InetAddress.getLocalHost().getHostAddress().toString();
		//} catch (UnknownHostException e) {
		//	e.printStackTrace();
		//}
		Frameinit();
		
		
		try {
		
			clientName="User "+ userNum;
			userNum=userNum+1;
			
			socket.getInetAddress().getHostAddress();
			
			
			com.ovgu.myutils.CommonUtils.PrintMe("The User try to connect the Server :" + serverIp +", at port:"+ defaultport);
			socket =new Socket(serverIp,defaultport);//与主机建立连接
			com.ovgu.myutils.CommonUtils.PrintMe("Socket established 接下来产生listen对象");
			
			listen=new TemplateOfClientListener(this,clientName,socket);
			CommonUtils.PrintMe("created listen object，listen。start（）");
			listen.start();		
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	
	@Override
	public void showMessage(Messages msg) {
		System.out.println("********************************************");
		System.out.println(msg.getSenderName()+": "+msg.getContent()+"  "+msg.getSendTime());
		System.out.println("********************************************");		
	}


	@Override
	public void showMessage(String str) {
		// TODO Auto-generated method stub
		/*System.out.println("**********************************************************");
		System.out.println(str);
		System.out.println("**********************************************************");
		*/
	}


	@Override
	public void sendMsgToServer(Messages msg) {
		listen.sendMsgToServer(msg);

	}


	@Override
	public void handleIncomingMsg(Messages msg) {
		// TODO Auto-generated method stub
		pif.FiltSpamMessage(msg);
		CommonUtils.PrintMe("filt Message");
	}


	@Override
	public void addNewUser(String str) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addNewUser(Messages msg) {
		// TODO Auto-generated method stub
		System.out.println("******************************");
		
		System.out.println("Online UserList:" + msg.getContent());
		System.out.println("******************************");
		
	}


	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return "nocolor";
	}
	
	public void actionPerformed(ActionEvent e){
		//Messages msg=new Messages();

		try{
			String ActionCommand=e.getActionCommand();
			
			
			if(ActionCommand.equals("Send")){
					CommonUtils.PrintMe("Konsole send Message:");
					if(socket!=null){
						Messages msg=new Messages();
						
						String msgtxt=new String(tfMessage.getText());
									
						msg.setContent(msgtxt);
						msg.setMsgType(Messages.MSG);
						sendMsgToServer(msg);
						CommonUtils.PrintMe("Sucessfully send:");
						tfMessage.setText("");
					}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			CommonUtils.PrintMe("出现异常 "+ ex.getMessage());
			
		}
	}
	
	public void Frameinit(){
		JFrame cF=new JFrame("Chat Input");
		cF.setLayout(new BorderLayout());
		Container cc=cF.getContentPane();
		
		JPanel panel3=new JPanel();
		Label label2=new Label("Chat Msg");
		
		panel3.add(label2);
		panel3.add(tfMessage);
		btSend.addActionListener(this);
		btSend.setActionCommand("Send");
		
		cc.add(btSend,BorderLayout.SOUTH);
		cc.add(panel3,BorderLayout.CENTER);
		cF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cF.setSize(350,200);
		cF.setVisible(true);			
		
	}
}
