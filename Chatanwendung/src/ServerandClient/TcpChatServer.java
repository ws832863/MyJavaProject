package ServerandClient;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import MyUtils.CommonUtils;
/**
 * Chatanwendung Servercode
 * @author wangshuo
 *
 */
public class TcpChatServer {
	static int defaultport=8800;//default port
	//static String defaultIp="";
	static Vector<AbstractConnectedClients> clients=new Vector<AbstractConnectedClients>(10);//Client capacity max 10.
	static ServerSocket server=null; //set server socket
	static Socket socket =null;
	
		
	/**
	 * Constructor
	 */
	public TcpChatServer(String port){
		try{
			
			System.out.print("Server start....");
			if(port!=null){
				defaultport=Integer.parseInt(port);
				System.out.println("Server run at port:" + defaultport);
			}else
			{
				System.out.println("no specified port, using the default port 8800");
			}
			server =new ServerSocket(defaultport);
			while(true){
				CommonUtils.PrintMe("the server accept() method blocking..waiting for client connect");
				socket = server.accept();//make a connection with client
				CommonUtils.PrintMe(socket.getInetAddress().toString()+"joined the server\n");
				
				AbstractConnectedClients client=new ConnectedClients(socket);
				clients.add(client);
				client.start();//start a client thread
				TcpChatServer.notifyChatRoom();
				CommonUtils.PrintMe("test,the TcpChatServer has been sucessfully initializated,and get a socket from client");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void notifyChatRoom(){//notify the chatroom,there are user left or join in
		
		Messages msg=new Messages();
		msg.setMsgType(Messages.NEWUSER);
		
		StringBuffer newUser=new StringBuffer();
		for(int i=0;i<clients.size();i++){
			AbstractConnectedClients c=(AbstractConnectedClients)clients.elementAt(i);
			newUser.append(":"+ c.getClientName());//join all the user name together with token :
		}
		
		msg.setContent(newUser.toString());//////////////////
		CommonUtils.PrintMe("the msg content of notifyChatRoom "+msg.getContent()+"  msg type is:"+msg.getMsgType()+msg.getEncryptedStatus());
		sendClients(msg);
	}
	
	/**
	 * send the Message to the connected Client
	 * @param msg
	 */
	public static void sendClients(Messages msg){
		for(int i=0;i<clients.size();i++){
			AbstractConnectedClients client=(AbstractConnectedClients)clients.elementAt(i);
			client.sendMsg(msg);
		}
	}
	
	/**
	 * close all the connected Clients
	 */
	public void closeAll(){ //close all of the connections 
		while(clients.size()>0){
			AbstractConnectedClients client=(AbstractConnectedClients)clients.firstElement();
			clients.removeElement(client); //remove client
		}
	}
	
	/**
	 * Disconnect a Connected Client
	 * @param c
	 */
	public static void disconnect(AbstractConnectedClients c){
		try{
			System.err.println(c.getClientIp()+ "lost connection\n");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		clients.removeElement(c);
		c.removeSocket();
	}
	/**
	 * stop the server running
	 * @throws  
	 */
	public void stopServer(){
		closeAll();	
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server=null;
		
	}

}//end ClassChatserver



