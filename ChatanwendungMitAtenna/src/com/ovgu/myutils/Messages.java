package com.ovgu.myutils;
import java.io.Serializable;

import com.ovgu.designpattern.PluginInterface;
import com.ovgu.designpattern.textEncryptionStrategy;

public class Messages implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4463027764976155007L;
	private String msgText="";
	private int msgType=0;
	private String senderIp;
	private String senderName;
	private String sendTime;
	private String color;
	private PluginInterface pif;
	
	public static int NEWUSER=0;
	public static int MSG=1;
	public static int INFO=2;
	public static int QUIT=3;
	
	public boolean Encrypted=false;
	

	
	public Messages(){msgText="";sendTime=CommonUtils.getNowDateInstance();}
	public Messages(String strMsg,int msgType){
		msgText=(strMsg);
		this.msgType=msgType;
		sendTime=CommonUtils.getNowDateInstance();
	}
	public Messages(String strMsg){
		msgText=strMsg;
		sendTime=CommonUtils.getNowDateInstance();
	}
	public Messages(StringBuffer strMsg){
		msgText=strMsg.toString();
		sendTime=CommonUtils.getNowDateInstance();
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
		//#if MsgEncryption
		msgText=te.EncryptionString(msgText);		
		this.Encrypted=true;
		//#endif
		
	}
	public void DecryptedMe(textEncryptionStrategy te){
		//#if MsgEncryption
		msgText=te.DecryptionString(msgText);
		this.Encrypted=false;		
		//#endif
		
		
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
	public String getSendTime(){
		return this.sendTime;
	}
	public String getTextColor(){
		String FontColor="no";
		//#if FontColor
		FontColor= this.color;	
		//#endif
		return FontColor;
		
	}
	public void setTextColor(String color){
		//#if FontColor
		this.color=color;
		//#endif
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("Content:"+this.getContent());
		sb.append("  ");
		sb.append("Messagetype:"+this.getMsgType());
		sb.append("  ");
		sb.append("EncryptStatus:"+this.getEncryptedStatus());
		sb.append("  ");
		sb.append("SenderIp:"+this.getSenderIp());
		sb.append("  ");
		sb.append("SenderName:"+this.getSenderName());
		sb.append("  ");
		sb.append("SenderTime:"+this.getSendTime());
		sb.append("  ");
		sb.append("Color:"+this.getTextColor());
		return sb.toString();
		
	}
	public void printMsg(){
		System.out.println("Messagetype:"+this.getMsgType());
		System.out.println("Content:"+this.getContent());
		System.out.println("EncryptStatus:"+this.getEncryptedStatus());
		System.out.println("SenderIp:"+this.getSenderIp());
		System.out.println("SenderName:"+this.getSenderName());
		System.out.println("SenderTime:"+this.getSendTime());
		System.out.println("SendColor:"+this.getTextColor());
		
	}
	
	 
}
