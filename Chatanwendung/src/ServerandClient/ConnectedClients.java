package ServerandClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import MyUtils.CommonUtils;
import designPattern.SingletonEncryptionMethod;

public class ConnectedClients extends AbstractConnectedClients {

	Socket clientSocket;
	String clientName;
	String clientIp;
	ObjectOutputStream os;
	ObjectInputStream in;

	public ConnectedClients(Socket inputSocket){
		clientSocket=inputSocket;//initialization of connections.		
		Object msg = null;
		
			try {
				os=new ObjectOutputStream(clientSocket.getOutputStream());
				in=new ObjectInputStream(clientSocket.getInputStream());
				CommonUtils.PrintMe("invoke themethod to create a instance of connected client");
				msg = in.readObject();
				
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}		

		Messages firstMsg=(Messages)msg;
		CommonUtils.PrintMe("the server get the frist  message from the new client："+firstMsg.getContent()+"是否加密："+firstMsg.getEncryptedStatus() +"消息类型："+firstMsg.getMsgType());
		firstMsg.DecryptedMe(SingletonEncryptionMethod.getInstance());//use ROT13 decrypt the incomming Msg
		CommonUtils.PrintMe("the decrypted msg is :"+firstMsg.getContent());
	
		clientName=firstMsg.getSenderName();
		clientIp=firstMsg.getSenderIp();
		
		firstMsg.printMsg();
		
		CommonUtils.PrintMe("the instance of client send a  message:---"+ firstMsg.getContent() +"---");

		
	}
	@Override
	public void sendMsg(Messages msg) {
		try {
			if(!msg.getEncryptedStatus()){
				msg.EncryptedMe(SingletonEncryptionMethod.getInstance());
			}
			os.writeObject(msg);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public String getClientName(){
		return clientName;
	}
	public String getClientIp(){
		return clientIp;
	}
	@Override
	public void run() {
		while(clientSocket!=null){
			//String StringMessage=null;
			Object objectMsg;
			Messages msg;
			try{
				CommonUtils.PrintMe("The server receive a Message from client");
				objectMsg=in.readObject();
				msg=(Messages)objectMsg;				
				CommonUtils.PrintMe("the thread read a message from client" + msg.getContent());
				msg=this.decryptedMsg(msg);
				CommonUtils.PrintMe("the message decrypted is:" + msg.getContent());
				//StringMessage=msg.getContent();
			}catch(IOException | ClassNotFoundException ex){
				ex.printStackTrace();
				TcpChatServer.disconnect(this);
				TcpChatServer.notifyChatRoom();
				return;
			}
			if(objectMsg==null){ // the client leaved
				CommonUtils.PrintMe("waring-----------objectMsg对象为空");
				TcpChatServer.disconnect(this);
				TcpChatServer.notifyChatRoom();
				return;
			}
			//StringTokenizer st=new StringTokenizer(StringMessage,":");

			CommonUtils.PrintMe("The message type is :"+ msg.getMsgType() );
			if(msg.getMsgType()==Messages.MSG){
				CommonUtils.PrintMe("the user send a message");
				
				//这里转发信息，信息的处理要在这里进行
				
				Messages sendMsg=new Messages(msg.getContent());
				sendMsg.setMsgType(Messages.MSG);
				sendMsg.setSenderIp(msg.getSenderIp());
				sendMsg.setSenderName(msg.getSenderName());
				TcpChatServer.sendClients(sendMsg);//send message to every clients
				
				CommonUtils.PrintMe("the message sended to all clients:" );
				sendMsg.printMsg();
			}else if(msg.getMsgType()==Messages.QUIT){
					CommonUtils.PrintMe("the user want to disconnect");
					TcpChatServer.disconnect(this);
					TcpChatServer.notifyChatRoom();
			}
		}//end while
		
	}
	private Messages decryptedMsg(Messages msg){
		msg.DecryptedMe(SingletonEncryptionMethod.getInstance());
		return msg;
	}
	@Override
	public void removeSocket() {
		clientSocket=null;
		cleanUp();		
	}
	
	@Override
	public void cleanUp() {
		try {
			in.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
