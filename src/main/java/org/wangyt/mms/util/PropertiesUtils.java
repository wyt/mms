package org.wangyt.mms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesUtils {

    private static final Log log = LogFactory.getLog(PropertiesUtils.class);
    private static final Map<String, Properties> map = new HashMap<String, Properties>();

    private static Properties loadProperties(String classpath) {
        Properties props = new Properties();
        InputStream is = null;
        try {
            is = PropertiesUtils.class.getResourceAsStream(classpath);
            props.load(is);
            log.info(classpath + " is already loaded.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

    public static Properties getProperties(String classpath) {
        Properties props = map.get(classpath);
        if (props == null) {
            props = loadProperties(classpath);
            map.put(classpath, props);
        }
        return props;
    }

    public static String getProperty(String classpath, String key) {
        return getProperties(classpath).getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(getProperty("/config/db.config.properties", "hibernate.dialect"));
    }

}
