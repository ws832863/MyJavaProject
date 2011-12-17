package com.ovgu.designpattern;

import javax.swing.JComboBox;

import com.ovgu.myutils.Messages;
import com.ovgu.tcpserver.AbstractConnectedClients;
import com.ovgu.tcpserver.TcpChatServer;

public interface PluginInterface {
	String getColor();
	
	String getEncryptionMsg(String strMsg);
	String getDecryptionMsg(String strMsg);
	
	void setApplication(AbstractConnectedClients acc);
	void FiltSpamMessage(Messages msg);
	
	void WriteLong(String str);
	
	public JComboBox<String> getColorJComboBox();
}
