package com.ovgu.tcpserver;

import com.ovgu.myutils.Messages;

public interface IConnectedClient {
	public void setApp(AbstractConnectedClients acc);
	public void FiltSpamMsg(Messages msg);
	public  void sendMsg(Messages msg);
	public  void cleanUp();
	public  void run();
	public  String getClientName();
	public  String getClientIp();
	public  void removeSocket();

}
