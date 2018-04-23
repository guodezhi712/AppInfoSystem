package cn.appsys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/endlogin")
public class endlogin {
	private Logger logger=Logger.getLogger(endlogin.class);
	/**
	 * ÍË³öÏµÍ³
	 */
	@RequestMapping("/login.html")
	public String exists(HttpServletRequest request){
		request.getSession().invalidate();
		logger.debug("session-->>>>>>>>>>>>>"+request.getSession(false));
		return "redirect:/login/devlogin.html";
	}
	
}
