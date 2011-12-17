package ServerandClient;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import MyUtils.CommonUtils;
import MyUtils.WriteLog;
import designPattern.ROT13EncryptionStrategy;
import designPattern.SingletonEncryptionMethod;
import designPattern.TemplateChat;
public class TemplateOfClientListener extends Thread{
	
		
	ObjectInputStream ins;
	ObjectOutputStream outs;

	String cname;
	Socket clientSocket;
	TemplateChat tClient;
	boolean Threadrun=true;
	ROT13EncryptionStrategy ROT13;
	public void sendMsgToServer(Messages msg){
		try {
			MyUtils.CommonUtils.PrintMe("a Message been send to server(TemplateOfClient):"+ msg.getEncryptedStatus());
			
			
			if(!msg.Encrypted){
				MyUtils.CommonUtils.PrintMe("not encrypted msg:"+ msg.getContent());
				msg.EncryptedMe(SingletonEncryptionMethod.getInstance());
				MyUtils.CommonUtils.PrintMe("the msg has been encrypt，内容:"+ msg.getContent());
			}else{
				MyUtils.CommonUtils.PrintMe("the msg has ben already encrypted:"+ msg.getContent());
			}
			outs.writeObject(msg);
			outs.flush();
			MyUtils.CommonUtils.PrintMe("TemplateOfClientListener发送成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TemplateOfClientListener(TemplateChat tc,String name,Socket socket){
		try{
			CommonUtils.PrintMe("TemplateOfClientListener constructor has been invoked" );
						
			this.tClient=tc;			
			this.clientSocket=socket;
			this.cname=name;
			
			Threadrun=true;
			ins=new ObjectInputStream(clientSocket.getInputStream());
			outs=new ObjectOutputStream(clientSocket.getOutputStream());
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public void run(){
		while(clientSocket!=null){
			String line=null;
			Object objMsg;
			Messages msg;
			try{
				CommonUtils.PrintMe("the client listening the server,ready for receive message.");
				objMsg=ins.readObject();
				msg=(Messages)objMsg;
				CommonUtils.PrintMe("the client read a message:"+msg.getContent());
			}catch(IOException | ClassNotFoundException ex){
				//ex.printStackTrace();
				
				System.out.println(cname+ "  problem occured, quit, the Server not answered");
				Messages quit=new Messages("quit",Messages.QUIT);
				quit.setSenderName(cname);
				
				sendMsgToServer(quit);//ps.println("quit");				
				return;
			}
			
			msg.DecryptedMe(SingletonEncryptionMethod.getInstance());
			CommonUtils.PrintMe("The message has been decrypted:"+msg.getContent());
			line =msg.getContent();
			
			StringTokenizer stinfo=new StringTokenizer(line,":");

			if(msg.getMsgType()==Messages.MSG){//get a message from server
				String username=stinfo.nextToken();//get the sender's name
				String color=tClient.getColor();//get the selected color
				CommonUtils.PrintMe("此处判定keyword是 MSG类型，将再用户桌面显示：");
				String MsgText=msg.getContent();//get the context of msg without keyword.
				//MsgText=MsgText.substring(username.length());//只得到信息正文，去掉用户名
				CommonUtils.PrintMe(MsgText+"$$$$$$$$$$$$$$$$$");
				String Logmsgtype=username+ " says:" + MsgText+"--" + CommonUtils.getNowDateInstance();//for log format
				StringBuffer sbFormatMsg=new StringBuffer(username);
				sbFormatMsg.append(" says:<");
				sbFormatMsg.append(color);
				sbFormatMsg.append("> ");
				sbFormatMsg.append(MsgText);
				sbFormatMsg.append(" </");
				sbFormatMsg.append(color);
				sbFormatMsg.append(">");
				sbFormatMsg.append(CommonUtils.getNowDateInstance());
				
				tClient.showMessage(sbFormatMsg.toString());
			/*	try{
				chatClient.tfChat.append(sbFormatMsg.toString() +"\n");
				}catch(Exception ex){
					WriteLog.write(ex.getMessage());
				}*/
				WriteLog.writeChatLog(Logmsgtype);// write the msg to Log
				
				
				
			}else if(msg.getMsgType()==Messages.NEWUSER){
				CommonUtils.PrintMe("new user comming");
				msg.printMsg();
				tClient.addNewUser(msg);

			}
		}//end true();
	}
	public void clearUp(){
	
		try {
			if(clientSocket!=null){
				clientSocket=null;
				if(!clientSocket.isClosed())
				clientSocket.close();
			}
			
		
			ins.close();
			outs.close();
			
		} catch (IOException e) {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("@@@@@@@@@cloes because of exception@@@@@");
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
			e.printStackTrace();
		}
		
		
		
	}//end of clearUp;
}