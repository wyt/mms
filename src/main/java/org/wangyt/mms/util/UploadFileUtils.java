package org.wangyt.mms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

/**
 * 上传文件的工具类(使用commons-io.jar)
 * 
 * @author 王永涛
 * 
 * @date 2012-9-24 下午1:49:43
 * 
 * @version $Rev: 635 $
 * 
 * @URL $URL: https://9jf20hqefy1civb:8443/svn/test/dome0.1/src/com/qingtong/framework/crm/util/UploadFileUtils.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
public class UploadFileUtils
{
    private static SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    /**
     * 保存上传的文件到指定的目录
     * 
     * @param in
     * @param basePath
     * @return 文件存储的路径
     */
    public static String saveUploadFile(InputStream in, String basePath)
    {
        FileOutputStream fos = null;
        try
        {
            String subPath = sdf.format(new Date());
            File dir = new File(basePath + subPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }

            String path = basePath + subPath + UUID.randomUUID().toString();
            fos = new FileOutputStream(path);
            IOUtils.copy(in, fos);
            return path;
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        } finally
        {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * 保存上传图片的特定方法
     * 
     * @param in
     * @param basePath
     * @return
     */
    public static String saveUploadImage(InputStream in, String basePath, String extName)
    {
        FileOutputStream fos = null;
        try
        {
            String subPath = sdf.format(new Date());
            File dir = new File(basePath + subPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }

            String path = basePath + subPath + UUID.randomUUID().toString() +"."+ extName;
            fos = new FileOutputStream(path);
            IOUtils.copy(in, fos);
            return path;
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        } finally
        {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(fos);
        }
    }
}
