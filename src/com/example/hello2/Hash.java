package com.example.hello2;


import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

	public Hash(){
		
	}
	
	public String getHash(String s){
	    try {
	       
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();
	        
	        
	        StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<messageDigest.length; i++)
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	        return hexString.toString();
	        
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
		
		 
	
	private byte[] stringToByteArray (String s) {
	    byte[] byteArray = new byte[s.length()];
	    for (int i = 0; i < s.length(); i++) {
	        byteArray[i] = (byte) s.charAt(i);
	    }
	    return byteArray;
	}
}
