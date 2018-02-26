package org.wangyt.mms.util.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * IP校验工具类.
 * 
 * @author WANG YONG TAO
 * 
 * @date 2016年7月3日 下午10:53:19
 * 
 * @version $Rev$
 * 
 * @URL $URL$
 * 
 * @Copyright (c) Copyright 2016 WANGYT, All rights reserved.
 * 
 */
public class IPvalidation {

  // private static final Logger log = Logger.getLogger(IPvalidation.class);
  private static final Log log = LogFactory.getLog(IPvalidation.class);

  private static Pattern pattern = Pattern.compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
      + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
      + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");

  /**
   * <span>处理含有通配符*的ip段,将其转换为取值范围,e.g: 192.168.0.* -> 192.168.0.0-192.168.0.255</span>
   * 
   * @param ipOriginal
   * @param ipRes
   */
  public static Set<String> process(String ipOriginal) {
    Set<String> ipRes = new HashSet<String>();
    for (String allow : ipOriginal.replaceAll("\\s", "").split(";")) {
      if (allow.indexOf("*") > -1) {
        String[] ips = allow.split("\\.");
        String[] from = new String[] {"0", "0", "0", "0"};
        String[] end = new String[] {"255", "255", "255", "255"};
        List<String> tem = new ArrayList<String>();
        for (int i = 0; i < ips.length; i++)
          if (ips[i].indexOf("*") > -1) {
            tem = complete(ips[i]);
            from[i] = null;
            end[i] = null;
          } else {
            from[i] = ips[i];
            end[i] = ips[i];
          }

        StringBuffer fromIP = new StringBuffer();
        StringBuffer endIP = new StringBuffer();
        for (int i = 0; i < 4; i++)
          if (from[i] != null) {
            fromIP.append(from[i]).append(".");
            endIP.append(end[i]).append(".");
          } else {
            fromIP.append("[*].");
            endIP.append("[*].");
          }
        fromIP.deleteCharAt(fromIP.length() - 1);
        endIP.deleteCharAt(endIP.length() - 1);

        for (String s : tem) {
          String ip =
              fromIP.toString().replace("[*]", s.split(";")[0]) + "-"
                  + endIP.toString().replace("[*]", s.split(";")[1]);
          if (validate(ip)) ipRes.add(ip);
        }
      } else {
        if (validate(allow)) ipRes.add(allow);
      }
    }
    return ipRes;
  }

  /**
   * 对单个IP节点进行范围限定.
   * 
   * @param arg
   * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
   */
  private static List<String> complete(String arg) {
    List<String> com = new ArrayList<String>();
    if (arg.length() == 1) {
      com.add("0;255");
    } else if (arg.length() == 2) {
      String s1 = complete(arg, 1);
      if (s1 != null) com.add(s1);
      String s2 = complete(arg, 2);
      if (s2 != null) com.add(s2);
    } else {
      String s1 = complete(arg, 1);
      if (s1 != null) com.add(s1);
    }
    return com;
  }

  /**
   * <span>对单个IP节点进行范围限定(方法重载).</span>
   * 
   * @param arg
   * @param length
   * @return
   */
  private static String complete(String arg, int length) {
    String from = "";
    String end = "";
    if (length == 1) {
      from = arg.replace("*", "0");
      end = arg.replace("*", "9");
    } else {
      from = arg.replace("*", "00");
      end = arg.replace("*", "99");
    }
    if (Integer.valueOf(from) > 255) return null;
    if (Integer.valueOf(end) > 255) end = "255";
    return from + ";" + end;
  }

  /**
   * <span>IP格式校验.</span>
   * 
   * @param ip
   * @return
   */
  private static boolean validate(String ip) {
    for (String s : ip.split("-"))
      if (!pattern.matcher(s).matches()) {
        log.error("The ip：" + ip + " formmat is bad!");
        return false;
      }
    return true;
  }

  /**
   * <span>IP地址校验.</span>
   * 
   * @param ip 需要校验的ip地址.
   * @param ipList 转换处理后的ip名单集合.
   * @return
   */
  public static boolean checkLoginIP(String ip, Set<String> ipList) {
    if (ipList.isEmpty() || ipList.contains(ip))
      return true;
    else {
      for (String allow : ipList) {
        if (allow.indexOf("-") > -1) {
          String[] from = allow.split("-")[0].split("\\.");
          String[] end = allow.split("-")[1].split("\\.");
          String[] tag = ip.split("\\.");

          // 对IP从左到右进行逐段匹配
          boolean check = true;
          for (int i = 0; i < 4; i++) {
            int s = Integer.valueOf(from[i]);
            int t = Integer.valueOf(tag[i]);
            int e = Integer.valueOf(end[i]);
            if (!(s <= t && t <= e)) check = false;
          }
          if (check) return true;
        }
      }
    }
    return false;
  }

  /**
   * <span>获取客户端IP.</sapn> <br />
   * 
   * @param request
   * @return
   */
  public static String getRemoteHost(HttpServletRequest request) {

    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
  }

  /**
   * Test.
   * 
   * @param args
   */
  public static void main(String[] args) {

    String testIps = "192.168.0.*;9.193.10.54;192.168.2.140-192.168.2.155;";
    Set<String> ipWhite = process(testIps);;
    for (String ip : ipWhite) {
      System.out.println(ip);
      log.info(ip);
    }
    String ip = "192.168.0.111";
    System.out.println("The validation result is: " + checkLoginIP(ip, ipWhite));
    log.info("The validation result is: " + checkLoginIP(ip, ipWhite));
  }

}
