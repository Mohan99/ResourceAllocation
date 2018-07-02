package com.gcs.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class SaltedMD5Example 
{
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException 
	{
		String passwordToHash = "password";
		
		String salt = getSalt();
		String salt1 = getSalt();
		String securePassword = getSecurePassword(passwordToHash, salt);
		String check=getSecurePassword("password1",salt);
		System.out.println(securePassword);
		
		String regeneratedPassowrdToVerify = getSecurePassword(passwordToHash, salt1);
		
		System.out.println(regeneratedPassowrdToVerify);
		System.out.println(securePassword.equals(regeneratedPassowrdToVerify));
	}
	
	public String encryptPassword(String pwd) {
		String salt;
		String securePassword=null;
		try {
			salt = getSalt();
			securePassword = getSecurePassword(pwd, salt);
			System.out.println(securePassword);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return securePassword;
		
	}
	
	private static String getSecurePassword(String passwordToHash, String salt)
	{
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(salt.getBytes());
			//Get the hash's bytes 
			byte[] bytes = md.digest(passwordToHash.getBytes());
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	//Add salt
	private static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
	{
		//Always use a SecureRandom generator
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		//Create array for salt
		byte[] salt = new byte[16];
		//Get a random salt
		sr.nextBytes(salt);
		//return salt
		return salt.toString();
	}
}
