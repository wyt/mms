package org.wangyt.mms.util.date;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午8:56:23
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/util/date/DateUtil2.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public class DateUtil2
{

    /**
     * 将Date类型转换为字符串
     * 
     * @param date
     *            日期类型
     * @return 日期字符串
     */
    public static String format(Date date)
    {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 将Date类型转换为字符串
     * 
     * @param date
     *            日期类型
     * @param pattern
     *            字符串格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern)
    {
        if (date == null)
        {
            return "null";
        }
        if (pattern == null || pattern.equals("") || pattern.equals("null"))
        {
            pattern = "yyyy-MM-dd";
        }
        return new java.text.SimpleDateFormat(pattern).format(date);
    }

    /**
     * 将字符串转换为Date类型
     * 
     * @param date
     *            字符串类型
     * @return 日期类型
     */
    public static Date format(String date)
    {
        return format(date, null);
    }

    /**
     * 将字符串转换为Date类型
     * 
     * @param date
     *            字符串类型
     * @param pattern
     *            格式
     * @return 日期类型
     */
    public static Date format(String date, String pattern)
    {
        if (pattern == null || pattern.equals("") || pattern.equals("null"))
        {
            pattern = "yyyy-MM-dd";
        }
        if (date == null || date.equals("") || date.equals("null"))
        {
            return new Date();
        }
        Date d = null;
        try
        {
            d = new java.text.SimpleDateFormat(pattern).parse(date);
        } catch (ParseException pe)
        {
        }
        return d;
    }

}
