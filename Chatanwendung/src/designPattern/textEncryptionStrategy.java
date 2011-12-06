package designPattern;
//a strategy interface for text encryption 
public interface textEncryptionStrategy {
	public String EncryptionString(String str);
	public String DecryptionString(String str);
	public StringBuffer EncryptionString(StringBuffer str);
	public StringBuffer DecryptionString(StringBuffer str);
}
