package designPattern;

import ServerandClient.Messages;


public interface TemplateChat {

public  void sendMsgToServer(Messages msg);
public  void handleIncomingMsg(Messages msg);
public  void showMessage(Messages msg);
public  void showMessage(String str);
public  void addNewUser(String str);
public 	void addNewUser(Messages msg);
public String getColor();

	
}
