package com.pactera.pacteramap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法使用SHA-256
 * D5,SHA-1,SHA-256
 * @author WMF
 * 
 */
public class PMSHA256Util {

	public static String algorithm="SHA-256";
	
	/**
	 * 对字符串加密
	 * @param strSrc	要加密的字符串
	 * @return
	 */
	public static String Encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance(algorithm);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}
	
	/**
	 * @param bts
	 * @return
	 */
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}
