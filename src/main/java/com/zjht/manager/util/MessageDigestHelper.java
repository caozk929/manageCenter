package com.zjht.manager.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * MessageDigest(SHA-1,SHA-256,MD5加密)
 *
 * @outhor caozk
 * @create 2017-09-05 20:41
 *
 **/
public class MessageDigestHelper {

	private static final String ALGORITHM = "MD5";
	 
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
 
    /**
     * 先使用algorithm加密，后使用Base64编码返回字符串
     * 
     * 加密字符串默认使用编码utf-8国际编码
     *
     * @param algorithm 加密类型 （例如：SHA-1，MD5）
     * @param str 需要加密的字符串
     * @param charsetName 需要加密的字符串的编码（例如：utf-8）
     * 
     * @return String 
     */
    public static String encode(String algorithm, String str, String charsetName) throws RuntimeException {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(charsetName)) {
			charsetName="utf-8";
		}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes(charsetName));
            return Base64Helper.encodeToString(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * 使用MessageDigest指定的algorithm类型进行摘要
     * 
     * @param algorithm 加密类型 （例如：SHA-1，MD5）
     * @param str 需要加密的字符串
     * @param charsetName 需要加密的字符串的编码（例如：utf-8）
     * @return MessageDigest.digest()
     * @throws RuntimeException
     */
    public static byte[] digest(String algorithm, String str, String charsetName) throws RuntimeException {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(charsetName)) {
			charsetName="utf-8";
		}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes(charsetName));
            return messageDigest.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * MD5加密
     *
     * @param str 需要加密字符串
     * @return String 返回MD5加密后的字符串
     */
    public static String encodeByMD5(String str, String charsetName) throws RuntimeException {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(charsetName)) {
			charsetName="utf-8";
		}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(str.getBytes(charsetName));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * encode by SHA1
     * @param str 加密字符串
     * @param charsetName 编码，如果为null，默认为utf-8编码
     * @return 返回SHA1加密字符串
     */
    public static String encodeBySHA1(String str, String charsetName){
    	if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(charsetName)) {
			charsetName="utf-8";
		}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes(charsetName));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encode by SHA256
     * @param str 加密字符串
     * @param charsetName 编码，如果为null，默认为utf-8编码
     * @return 返回SHA256加密字符串
     */
    public static String encodeBySHA256(String str, String charsetName){
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(charsetName)) {
            charsetName="utf-8";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(charsetName));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 返回MD5密文的十六进制的字符串
     *
     * @param bytes
     *            the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
        	buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

	/**
	 * base64加密
	 * 
	 * @param b
	 * @return
	 */
	public static String base64Encode(byte[] b) {
		return new String(Base64.encodeBase64(b));
	}

	/**
	 * base64解密
	 * 
	 * @param b
	 * @return
	 */
	public static String base64Decode(byte[] b) {
		return new String(Base64.decodeBase64(b));
	}

}
