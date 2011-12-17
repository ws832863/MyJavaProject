package com.ovgu.tcpclient;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ovgu.designpattern.PluginInterface;
import com.ovgu.myutils.CommonUtils;
import com.ovgu.myutils.Messages;



public class TemplateChatClient extends JFrame implements ActionListener,TemplateChat {
	

	private static final long serialVersionUID = -3756993045529952043L;
	private static int defaultport=8800;
	private static String serverIp="";

	
	TextField tfName = new TextField(15);// for username input
	Button btConnect =new Button("Connect");
	Button btDisconnect =new Button("Disconnect");
	public TextArea tfChat=new TextArea(8,27);
	Button btSend=new Button("Send");
	TextField tfMessage=new TextField(30);
	public java.awt.List list1=new java.awt.List(9);//show the online user

	
	Socket socket=null; //socket for the Client
	TemplateOfClientListener listen=null;
	String usrName="";
	String usrIp="";
	
	PluginInterface pif;
	JComboBox<String> jColor;
	public void actionPerformed(ActionEvent e){
		
		try{
			String ActionCommand=e.getActionCommand();
			
			if(ActionCommand.equals("Connect")){
				CommonUtils.PrintMe("btnConnect");
				usrName=tfName.getText();
				
				if(usrName.trim().equals("")) {
					JOptionPane.showMessageDialog(null,"Username can not be Null");
					return;
				}
				if(socket==null){
				
					CommonUtils.PrintMe("InetAddress.getLocalHost()="+InetAddress.getLocalHost().toString());
					CommonUtils.PrintMe("InetAddress.getLocalHost().getHostAddress()="+InetAddress.getLocalHost().getHostAddress().toString());
					CommonUtils.PrintMe("InetAddress.getLocalHost().getHostName()="+InetAddress.getLocalHost().getHostName().toString());
					//CommonUtils.PrintMe("InetAddress.getLocalHost()="+InetAddress.getLocalHost()..toString());
					
					com.ovgu.myutils.CommonUtils.PrintMe("the user connected with the server:" + serverIp);
					socket =new Socket(serverIp,defaultport);//与主机建立连接
					com.ovgu.myutils.CommonUtils.PrintMe("socket established，listener created");
					
					listen=new TemplateOfClientListener(this,usrName,socket);
					CommonUtils.PrintMe("a listener object is established ，listen.start()");
					
					listen.start();
					
					usrIp=InetAddress.getLocalHost().toString();
					
					tfName.setEnabled(false);
				}else{
					JOptionPane.showMessageDialog(this,"This User has benn Connected");
				}
			}else if(ActionCommand.equals("Disconnect")){
					CommonUtils.PrintMe("i am disconnected");
					tfName.setEnabled(true);
					disconnect();
			}else if(ActionCommand.equals("Send")){
					CommonUtils.PrintMe("click send button,send msg to server");
					if(socket!=null){
						
						StringBuffer msgBuffer=new StringBuffer();
						String msgtxt=new String(tfMessage.getText());
						msgBuffer.append(msgtxt);
						
						Messages msg=new Messages();
						msg.setContent(msgBuffer.toString());
						msg.setSenderIp(usrIp);
						msg.setSenderName(usrName);
						msg.setMsgType(Messages.MSG);
						
						sendMsgToServer(msg);
						
						CommonUtils.PrintMe("msg sucessfully send:");
						tfMessage.setText("");
					}else{
						JOptionPane.showMessageDialog(this,"please connect first");//.showMessageDialog(this,"Please connect first","message");
					}
			}
		CommonUtils.PrintMe("button action over！");
			
		}catch(Exception ex){
			ex.printStackTrace();
			CommonUtils.PrintMe("exception "+ ex.getMessage());
			
		}
	}
	public void disconnect(){
	
		if(socket!=null){
			
			tfName.setText("");
			listen.clearUp();
			socket=null;
		}	
		listen.clearUp();
	}
	public TemplateChatClient(int port,String sIp,PluginInterface pi){
		defaultport=port;
		serverIp=sIp;
		pif=pi;
		
		Frameinit();
	}
	
	public void Frameinit(){
		
		
		this.setLayout(new BorderLayout());
		JPanel panel1=new JPanel();
		Label label=new Label("name");
		panel1.setBackground(Color.blue);
		panel1.add(label);
		panel1.add(tfName);
		panel1.add(btConnect);
		panel1.add(btDisconnect);
		this.add(panel1,BorderLayout.NORTH);
		
		JPanel panel2=new JPanel();
		panel2.add(tfChat);
		//#if OnlineUserList
		panel2.add(list1);
		//#endif

		//#if FontColor
		jColor=pif.getColorJComboBox();
		panel2.add(jColor);
		//#endif
		
		this.add(panel2,BorderLayout.CENTER);
		
		JPanel panel3=new JPanel();
		Label label2=new Label("Chat Msg");
		panel3.add(label2);
		panel3.add(tfMessage);
		panel3.add(btSend);
		this.add(panel3,BorderLayout.SOUTH);

		this.setBounds(50,50,400,350);
		this.setVisible(true);
		
		btConnect.addActionListener(this);
		btConnect.setActionCommand("Connect");
		btDisconnect.addActionListener(this);
		btDisconnect.setActionCommand("Disconnect");
		btSend.addActionListener(this);
		btSend.setActionCommand("Send");
		
	}
	
	public String getColor(){
		String color="";
		color=jColor.getSelectedItem().toString();
		return color;		
				
	}
	@Override
	public void sendMsgToServer(Messages msg) {
		//#if FontColor
		msg.setTextColor(getColor());
		CommonUtils.PrintMe("****************************************************");
		CommonUtils.PrintMe(getColor());
		
		CommonUtils.PrintMe("****************************************************");
		
		//#endif
		listen.sendMsgToServer(msg);
		msg.printMsg();
	}
	
	@Override
	public void handleIncomingMsg(Messages msg) {
		// TODO Auto-generated method stub
		//#if Spamfilter
		pif.FiltSpamMessage(msg);
		CommonUtils.PrintMe("The Message has been filted");
		//#endif
	}
	@Override
	public void showMessage(Messages msg) {
		StringBuffer sbFormatMsg=new StringBuffer(msg.getSenderName());
		sbFormatMsg.append(" says:<");
		sbFormatMsg.append(msg.getTextColor());
		sbFormatMsg.append("> ");
		sbFormatMsg.append(msg.getContent());
		sbFormatMsg.append(" </");
		sbFormatMsg.append(msg.getTextColor());
		sbFormatMsg.append(">");
		sbFormatMsg.append(msg.getSendTime());
		tfChat.append(sbFormatMsg.toString()+"\n");
	}
	@Override
	public void showMessage(String str) {
		
			try{
				//tfChat.append(str +"\n");
			}catch(Exception ex){
				ex.printStackTrace();
			}
		
	}
	@Override
	public void addNewUser(String str) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addNewUser(Messages msg) {
		
		list1.removeAll();//clear();
		list1.add("-----Online User List-----",0);
		int i=1;
		String line=msg.getContent();
		StringTokenizer stinfo=new StringTokenizer(line,":");
	
		while(stinfo.hasMoreTokens()){
			list1.add(stinfo.nextToken(),i++);
		}
		
	}


}
