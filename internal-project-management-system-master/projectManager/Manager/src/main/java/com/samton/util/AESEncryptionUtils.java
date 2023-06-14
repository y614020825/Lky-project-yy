package com.samton.util;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


//AEC加密，用于对用户进行检验
public class AESEncryptionUtils {
	 public static byte[] iv = new byte[] { 82, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75 };  
	  
	    private static AESEncryptionUtils aes = null;  
	  
	    public static byte[] key1 = new byte[] { -42, 35, 67, -86, 19, 29, -11, 84, 94, 111, 75, -104, 71, 46, 86, -21, -119, 110, -11, -32, -28, 91, -33, -46, 99, 49, 2, 66, -101, -11, -8, 56 };  
	  
	    private AESEncryptionUtils() {  
	  
	    }  
	  
	    public static synchronized AESEncryptionUtils getInstance() {  
	        if (aes == null) {  
	            aes = new AESEncryptionUtils();  
	        }  
	        return aes;  
	    }  
	  
	    public static String encrypt(String msg) {  
	  
	        String str = "";  
	        try {  
	            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	            kgen.init(128, new SecureRandom(key1));  
	            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);  
	            SecretKey key = kgen.generateKey();  
	            Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);  
	            str = asHex(ecipher.doFinal(msg.getBytes()));  
	        } catch (BadPaddingException e) {  
	            e.printStackTrace();  
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	            e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	            e.printStackTrace();  
	        } catch (InvalidAlgorithmParameterException e) {  
	            e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	            e.printStackTrace();  
	        }  
	        return str;  
	    }  
	  
	    public static String decrypt(String value) {  
	        try {  
	            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	            kgen.init(128, new SecureRandom(key1));  
	            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);  
	            SecretKey key = kgen.generateKey();  
	            Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);  
	            return new String(dcipher.doFinal(asBin(value)));  
	        } catch (BadPaddingException e) {  
	            e.printStackTrace();  
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	            e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	            e.printStackTrace();  
	        } catch (InvalidAlgorithmParameterException e) {  
	            e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	            e.printStackTrace();  
	        }  
	        return "";  
	    }  
	  
	    private static String asHex(byte buf[]) {  
	        StringBuffer strbuf = new StringBuffer(buf.length * 2);  
	        int i;  
	  
	        for (i = 0; i < buf.length; i++) {  
	            if ((buf[i] & 0xff) < 0x10)  
	                strbuf.append("0");  
	  
	            strbuf.append(Long.toString(buf[i] & 0xff, 16));  
	        }  
	  
	        return strbuf.toString();  
	    }  
	  
	    private static byte[] asBin(String src) {  
	        if (src.length() < 1)  
	            return null;  
	        byte[] encrypted = new byte[src.length() / 2];  
	        for (int i = 0; i < src.length() / 2; i++) {  
	            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);  
	            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);  
	  
	            encrypted[i] = (byte) (high * 16 + low);  
	        }  
	        return encrypted;  
	    }  
	    
}
