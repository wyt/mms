package org.wangyt.mms.web.springmvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author WANG YONG TAO
 * 
 * @date 2016-7-28 10:54:32
 * 
 * @version $Rev $
 * 
 * @URL $URL $
 * 
 * @Copyright (c) Copyright 2016 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@RequestMapping("/upload")
@Controller("fileUploadController")
public class FileUploadController {
  
    @ResponseBody
    @RequestMapping(value = "/parse_file", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"})
    public String onSubmit(@RequestParam("meta-data") String metadata,
            @RequestPart("file-data") MultipartFile file) throws IOException {
        
        System.out.print("meta-data" + metadata);

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            System.out.println(file.getName() + "," + file.getContentType() + ","
                    + file.getOriginalFilename() + "," + file.getSize());
            return "1";
        } else {
            return "2";
        }
    }
  
}
