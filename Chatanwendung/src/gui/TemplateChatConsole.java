package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import MyUtils.CommonUtils;

import ServerandClient.Messages;
import ServerandClient.TemplateOfClientListener;
import designPattern.TemplateChat;

public class TemplateChatConsole implements TemplateChat,ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5148150169194595621L;
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
	;

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
			Messages InfoMsg=new Messages();
			String tfName="User "+userNum;
			
			MyUtils.CommonUtils.PrintMe("The User try to connect the Server :" + serverIp +", at port:"+ defaultport);
			socket =new Socket(serverIp,defaultport);//与主机建立连接
			MyUtils.CommonUtils.PrintMe("Socket established 接下来产生listen对象");
			
			listen=new TemplateOfClientListener(this,tfName,socket);
			CommonUtils.PrintMe("产生了Listen对象，listen。start（）");
			listen.start();
		
			StringBuffer info=new StringBuffer("info:");
			InfoMsg.setMsgType(Messages.INFO);
			info.append(tfName);// add the userinputname
			info.append(":");//add the Troken
			info.append(InetAddress.getLocalHost().toString());//add userlocal address

			InfoMsg.setContent(info.toString());

			listen.sendMsgToServer(InfoMsg);	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	
	private void PrintMsg(String str){
		System.out.println(str);
	}





	@Override
	public void showMessage(Messages msg) {
		System.out.println("********************************************");
		System.out.println(msg.getContent());
		System.out.println("********************************************");
		
		
	}


	@Override
	public void showMessage(String str) {
		// TODO Auto-generated method stub
		System.out.println("**********************************************************");
		System.out.println(str);
		System.out.println("**********************************************************");
		
	}


	@Override
	public void sendMsgToServer(Messages msg) {
		// TODO Auto-generated method stub

	}


	@Override
	public void handleIncomingMsg(Messages msg) {
		// TODO Auto-generated method stub
		
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
					CommonUtils.PrintMe("在Console端调用send的方法，发送到服务器端，发送的信息为");
					if(socket!=null){
						Messages msg=new Messages();
						StringBuffer msgBuffer=new StringBuffer("MSG:");
						String msgtxt=new String(tfMessage.getText());
						msgBuffer.append(msgtxt);
						
						
						msg.setContent(msgBuffer.toString());
						CommonUtils.PrintMe("未加密的信息将被包装成Messages送往服务器  :"+msgBuffer.toString());			

						msg.setMsgType(Messages.MSG);
						listen.sendMsgToServer(msg);
						CommonUtils.PrintMe("信息发送成功:");
						tfMessage.setText("");
					}else{
					//	JOptionPane.showMessageDialog(this,"please connect first");//.showMessageDialog(this,"Please connect first","message");
					}
			}
		CommonUtils.PrintMe("Konsole按钮事件结束！");
			
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
