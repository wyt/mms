package org.wangyt.mms.util.date;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

/**
 * 将返回的JSON格式中，日期类型转化为字符串类型，用于前台显示
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午8:56:01
 * 
 * @version $Rev: 54 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/util/date/JsonDateSerializer.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date>
{
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeString(DateUtil.format(date));
    }

}