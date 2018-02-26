package org.wangyt.mms.util.security;

import java.security.MessageDigest;
import java.util.UUID;

public class OAuth2EncryptAlgorithm {

  /***
   * MD5加密 生成32位md5码
   * 
   * @param 待加密字符串
   * @return 返回32位md5码
   */
  public static String md5Encode(String inStr) throws Exception {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
      return "";
    }

    byte[] byteArray = inStr.getBytes("UTF-8");
    byte[] md5Bytes = md5.digest(byteArray);
    StringBuffer hexValue = new StringBuffer();
    for (int i = 0; i < md5Bytes.length; i++) {
      int val = ((int) md5Bytes[i]) & 0xff;
      if (val < 16) {
        hexValue.append("0");
      }
      hexValue.append(Integer.toHexString(val));
    }
    return hexValue.toString();
  }

  /***
   * SHA加密 生成40位SHA码
   * 
   * @param 待加密字符串
   * @return 返回40位SHA码
   */
  public static String shaEncode(String inStr) throws Exception {
    MessageDigest sha = null;
    try {
      sha = MessageDigest.getInstance("SHA");
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
      return "";
    }

    byte[] byteArray = inStr.getBytes("UTF-8");
    byte[] md5Bytes = sha.digest(byteArray);
    StringBuffer hexValue = new StringBuffer();
    for (int i = 0; i < md5Bytes.length; i++) {
      int val = ((int) md5Bytes[i]) & 0xff;
      if (val < 16) {
        hexValue.append("0");
      }
      hexValue.append(Integer.toHexString(val));
    }
    return hexValue.toString();
  }

  public static void main(String[] args) throws Exception {

    String uuid = UUID.randomUUID().toString();
    String APP_KEY = md5Encode(uuid).toUpperCase();
    String APP_SECRET = shaEncode(uuid).toUpperCase();

    System.out.println("APP_KEY: " + APP_KEY);
    System.out.println("APP_SECRET: " + APP_SECRET);
  }

}
