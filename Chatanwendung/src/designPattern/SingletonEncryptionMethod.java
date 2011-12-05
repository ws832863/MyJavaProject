package designPattern;


public class SingletonEncryptionMethod {
	private static textEncryptionStrategy instance=null;
	
	protected SingletonEncryptionMethod(){
		
		
	}
	public static textEncryptionStrategy getInstance(){
		if(instance==null){
			instance=new ROT13EncryptionStrategy();
					
		}
		return instance;
	}
	
	
}
