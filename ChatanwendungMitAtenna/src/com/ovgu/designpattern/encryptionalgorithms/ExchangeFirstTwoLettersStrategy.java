package com.ovgu.designpattern.encryptionalgorithms;

import com.ovgu.designpattern.textEncryptionStrategy;

public class ExchangeFirstTwoLettersStrategy implements textEncryptionStrategy {

	@Override
	public String EncryptionString(String str) {
		// TODO Auto-generated method stub
		return new StringBuffer(str).reverse().toString();
	
	}

	@Override
	public String DecryptionString(String str) {
		// TODO Auto-generated method stub
		return new StringBuffer(str).reverse().toString();
	}

	@Override
	public StringBuffer EncryptionString(StringBuffer str) {
		// TODO Auto-generated method stub
		return new StringBuffer(str).reverse();
	}

	@Override
	public StringBuffer DecryptionString(StringBuffer str) {
		// TODO Auto-generated method stub
		return new StringBuffer(str).reverse();
	}

}
