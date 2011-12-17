package com.ovgu.tcpclient;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.ovgu.designpattern.SingletonEncryptionMethod;
import com.ovgu.myutils.CommonUtils;
import com.ovgu.myutils.Messages;
import com.ovgu.myutils.WriteLog;

/**
 * 
 * @author wangshuo
 * 此部分作为客户端监听器的模板类
 * 由于senderMsgToServer包含组装MSG信息，所以在发送信息的时候不用填写client 和senderIP
 */
public class TemplateOfClientListener extends Thread{
	
		
	ObjectInputStream ins;
	ObjectOutputStream outs;

	String cname;
	Socket clientSocket;
	TemplateChat tClient;
	private String senderIp;
	private
	
	boolean Threadrun=true;

	public void sendMsgToServer(Messages msg){
		try {
			com.ovgu.myutils.CommonUtils.PrintMe("a Message been send to server(TemplateOfClient):"+ msg.getEncryptedStatus());
			//module Msg Encryption. if not selected, will be deactive
			
			//#if MsgEncryption
			if(!msg.Encrypted){
				com.ovgu.myutils.CommonUtils.PrintMe("Message not be Encrypted:"+ msg.getContent());
				msg.EncryptedMe(SingletonEncryptionMethod.getInstance());
				com.ovgu.myutils.CommonUtils.PrintMe("Message be Encryped:"+ msg.getContent());
			}else{
				com.ovgu.myutils.CommonUtils.PrintMe("decrypted Msg, the content:"+ msg.getContent());
			}
			//#endif
			msg.setSenderIp(senderIp);
			msg.setSenderName(cname);
			
			outs.writeObject(msg);
			outs.flush();
			com.ovgu.myutils.CommonUtils.PrintMe("TemplateOfClientListener SendMsg Sucessfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TemplateOfClientListener(TemplateChat tc,String name,Socket socket){
		try{
			CommonUtils.PrintMe("TemplateOfClientListener constructor has been invoked" );
						
			this.tClient=tc;			
			this.clientSocket=socket;
			this.cname=name;
			this.senderIp=socket.getInetAddress().getHostAddress();
			Threadrun=true;
			ins=new ObjectInputStream(clientSocket.getInputStream());
			outs=new ObjectOutputStream(clientSocket.getOutputStream());
			
			//notify the server that a new user coming
			Messages msg=new Messages();			
			
			msg.setMsgType(Messages.INFO);
			sendMsgToServer(msg);
			
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public void run(){
		while(Threadrun){
		
			Object objMsg;
			Messages msg;
			try{
				CommonUtils.PrintMe("the client listening the server,ready for receive message.");
				objMsg=ins.readObject();
				msg=(Messages)objMsg;
				CommonUtils.PrintMe("the client read a message:"+msg.getContent());
				
			}catch(IOException | ClassNotFoundException ex){
				//ex.printStackTrace();
				
				System.out.println(cname+ "  problem occured, quit, the Server not answered");
				Messages quit=new Messages("quit",Messages.QUIT);
				quit.setSenderName(cname);
				
				sendMsgToServer(quit);//ps.println("quit");				
				return;
			}
			
//#if MsgEncryption
			msg.DecryptedMe(SingletonEncryptionMethod.getInstance());
//#endif
			
			CommonUtils.PrintMe("The message has been decrypted:"+msg.getContent());


			if(msg.getMsgType()==Messages.MSG){//get a message from server
				tClient.handleIncomingMsg(msg);//here handle the message, filter and so on
				tClient.showMessage(msg);
//#if Logging
				WriteLog.writeChatLog(msg.toString());// write the Log in Server
//#endif				
				
			}
//#if OnlineUserList 
				else if(msg.getMsgType()==Messages.NEWUSER){
				CommonUtils.PrintMe("new user comming");
				msg.printMsg();
				tClient.addNewUser(msg);

			}
//#endif
			else
			{
				System.out.println("can not recognized msg type");
			}
		}//end true();
	}
	public void clearUp(){
	
		try {
			Threadrun=false;
			clientSocket.close();
			clientSocket=null;
		
			ins.close();
			outs.close();
			
		} catch (IOException e) {
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%关闭出现异常%%%%%%%%%%");
			
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
			e.printStackTrace();
		}
		
		
		
	}//end of clearUp;
}
