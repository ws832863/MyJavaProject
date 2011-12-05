package ServerandClient;
import java.io.Serializable;

import designPattern.*;

public class Messages implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4463027764976155007L;
	private String msgText="";
	private int msgType=0;
	private String senderIp;
	private String senderName;
	public static int NEWUSER=0;
	public static int MSG=1;
	public static int INFO=2;
	public static int QUIT=3;
	
	public boolean Encrypted=false;
	

	
	public Messages(){msgText="";}
	public Messages(String strMsg,int msgType){
		msgText=(strMsg);
		this.msgType=msgType;
	}
	public Messages(String strMsg){
		msgText=strMsg;
	}
	public Messages(StringBuffer strMsg){
		msgText=strMsg.toString();
	}
	public  String getContent(){
		return msgText;
	}
	public  void setContent(String str){
		msgText=str;
	}
	
	public int getMsgType(){
		
		return msgType;
	}
	public void setMsgType(int type){
		msgType=type;
	}
	
	public void EncryptedMe(textEncryptionStrategy te){
	
		msgText=te.EncryptionString(msgText);
		this.Encrypted=true;
	}
	public void DecryptedMe(textEncryptionStrategy te){
		
		msgText=te.DecryptionString(msgText);
		this.Encrypted=false;		
	}
	
	public boolean getEncryptedStatus(){
		return Encrypted;
	}
	public String getSenderIp(){
		return senderIp;
	}
	public String getSenderName(){
		return senderName;
	}
	public void setSenderIp(String ip){
		senderIp=ip;
	}
	public void setSenderName(String name){
		senderName=name;
	}
	public String toString(){
		
		return msgText;
		
	}
	public void printMsg(){
		System.out.println("Messagetype:"+this.getMsgType());
		System.out.println("Content:"+this.getContent());
		System.out.println("EncryptStatus:"+this.getEncryptedStatus());
		System.out.println("SenderIp:"+this.senderIp);
		System.out.println("SenderName:"+this.senderName);
		
	}
}
