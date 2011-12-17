package com.ovgu.designpattern;

import com.ovgu.designpattern.encryptionalgorithms.ExchangeFirstTwoLettersStrategy;
import com.ovgu.designpattern.encryptionalgorithms.ROT13EncryptionStrategy;



public class SingletonEncryptionMethod {
	private static textEncryptionStrategy instance=null;
	
	protected SingletonEncryptionMethod(){
		
		
	}
	public static textEncryptionStrategy getInstance(){
		if(instance==null){

			//#if ROT13
			instance=new ROT13EncryptionStrategy();					
			//#endif
			//#if Reverse
//@			instance=new ExchangeFirstTwoLettersStrategy();					
			//#endif
			
	
		}
		return instance;
	}
	
	
}
