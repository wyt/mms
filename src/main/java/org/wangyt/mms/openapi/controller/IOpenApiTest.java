package org.wangyt.mms.openapi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IOpenApiTest {

	void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException;

	void captchaBase64(HttpServletRequest request, HttpServletResponse response) throws Exception;

	void getSystemProperties(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
