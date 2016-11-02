package com.cml.springboot.framework.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.cml.springboot.framework.Configuration;
import com.cml.springboot.framework.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("exceptionResolver")
public class ExceptionHandler extends DefaultHandlerExceptionResolver {

	private static Log log = LogFactory.getLog(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		

		log.info("====exception===================================>" + ex.getMessage());

		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		mapper.setSerializationInclusion(Include.NON_NULL);
		BaseResponse responseBean = new BaseResponse(Configuration.Status.STATUS_INVALID_TOKEN, ex.getMessage());
		try {
			String message = mapper.writeValueAsString(responseBean);
			response.reset();
			response.getOutputStream().write(message.getBytes());
			response.getOutputStream().flush();
		} catch (Exception e) {
			log.error(e);
		}
		return new ModelAndView();
	}

}