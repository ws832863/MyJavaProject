package ServerandClient;

import designPattern.ROT13EncryptionStrategy;
import designPattern.textEncryptionStrategy;

public abstract class  AbstractConnectedClients extends Thread{
	

	textEncryptionStrategy ROT13=new ROT13EncryptionStrategy();	
	
	public abstract void sendMsg(Messages msg);
	public abstract void cleanUp();
	public abstract void run();
	public abstract String getClientName();
	public abstract String getClientIp();
	public abstract void removeSocket();

}
