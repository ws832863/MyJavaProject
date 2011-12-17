package com.ovgu.plugins;

import javax.swing.JComboBox;

import com.ovgu.designpattern.PluginInterface;
import com.ovgu.myutils.Messages;
import com.ovgu.tcpserver.AbstractConnectedClients;


public class hotSpotPlugin implements PluginInterface {

	
	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEncryptionMsg(String strMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDecryptionMsg(String strMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void FiltSpamMessage(Messages msg) {
		//#if Spamfilter
				//pif.FiltSpamMessage(msg);
				System.out.println("here filt the Message");
		//#endif
		
		
	}

	@Override
	public void WriteLong(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JComboBox<String> getColorJComboBox() {
		// TODO Auto-generated method stub
		return new JComboBox<String>(new String[]{"Red","Black","Blue"});
	}

	@Override
	public void setApplication(AbstractConnectedClients acc) {
		// TODO Auto-generated method stub
		
	}

	
}
