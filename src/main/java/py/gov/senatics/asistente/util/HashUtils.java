package py.gov.senatics.asistente.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

	public static String md5(String pwd) {
		
		try {
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(pwd.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			
			while (hashtext.length() < 32) {
				
				hashtext = "0" + hashtext;
			
			}
		  
		  return hashtext;
	    
		}
	    catch (NoSuchAlgorithmException e) {
	    
	    	throw new RuntimeException(e);
	    	
	    }
	}	
}
