package org.platform.manager.web.base;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class BaseAction {
	public Logger LOG = LoggerFactory.getLogger(BaseAction.class);
	/**
	 * 设置参数数据
	 * @param request
	 * @param mv
	 */
	public void params(HttpServletRequest request,ModelAndView mv){
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enumeration =  request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String key = enumeration.nextElement();
			map.put(key, request.getParameter(key));
		}
		mv.addObject("param", map);
	}
	
}
