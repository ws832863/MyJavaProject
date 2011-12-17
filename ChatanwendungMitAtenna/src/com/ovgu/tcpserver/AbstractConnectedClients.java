package com.ovgu.tcpserver;

import com.ovgu.designpattern.PluginInterface;
import com.ovgu.designpattern.textEncryptionStrategy;
import com.ovgu.designpattern.encryptionalgorithms.ROT13EncryptionStrategy;
import com.ovgu.myutils.Messages;
import com.ovgu.plugins.hotSpotPlugin;


public abstract class  AbstractConnectedClients extends Thread{

	
	private 
	textEncryptionStrategy ROT13=new ROT13EncryptionStrategy();	
	private PluginInterface pif=new hotSpotPlugin();
	public void FiltspamMsg(Messages msg){
		pif.FiltSpamMessage(msg);
	}
	public abstract void sendMsg(Messages msg);
	public abstract void cleanUp();
	public abstract void run();
	public abstract String getClientName();
	public abstract String getClientIp();
	public abstract void removeSocket();
	public abstract Messages enctyptMsg(Messages msg);
	public abstract Messages decryptMsg(Messages msg);
	public void sendMsgToClients(Messages msg){
		
		
		//#if Spamfilter
			this.FiltspamMsg(msg);
		//#endif
		//#if MsgEncryption
			
			if(!msg.getEncryptedStatus()){
				msg=this.enctyptMsg(msg);
			}
		//#endif
		//这里可以加入所有的借口信息。。
		
		System.out.println("%%%%%%%%%%%filter msg%%%%%%%%%%%%");
		this.sendMsg(msg);
		System.out.println("%%%%%%%%%%%filter msg%%%%%%%%%%%%");
		
	}
}
