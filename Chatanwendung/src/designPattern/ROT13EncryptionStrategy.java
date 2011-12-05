
package designPattern;

public class ROT13EncryptionStrategy implements textEncryptionStrategy {

	
	private  int getDnummber(int i){
		int dnummber=i;
		if(dnummber>=65 && dnummber<=77){//未超过循环情况
			dnummber+=13;
		}else if(dnummber>=97 && dnummber<=109 ){
			dnummber+=13;
		}else if(dnummber>=78 && dnummber<=90){//超过循环情况
			dnummber -=13;
		}else if (dnummber >=110 && dnummber <=122){
			dnummber -=13;
		}
		
		return dnummber;
	}
	
	private  char get13(char c){
		char d=(char)(getDnummber((int)c));
		//System.out.println("字符:  "+ c+ "  转变为字符: "+d);
		return d;		
		
	}
	
		/**
		 * Encrypt the Message by using ROT13
		 * @param str
		 * @return
		 */
	@Override
	public String EncryptionString(String str) {	
		
			int len=str.length();
			char[] quellString=str.toCharArray();
			String desString="";
			
			for(int i=0;i<len;i++){
				quellString[i]=get13(quellString[i]);			
			}
			desString=new String(quellString);	
			
			return desString;
		
		
	}

	@Override
	public String DecryptionString(String str) {
		return EncryptionString(str);
			
	}

	@Override
	public StringBuffer EncryptionString(StringBuffer str) {
		return new StringBuffer(EncryptionString(str.toString()));
	
		
	}

	@Override
	public StringBuffer DecryptionString(StringBuffer str) {
		return new StringBuffer(EncryptionString(str.toString()));
		// TODO Auto-generated method stub
		
	}

}
