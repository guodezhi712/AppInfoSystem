package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.devUser;
import cn.appsys.tools.Constants;



public class SysInterceptor2 extends HandlerInterceptorAdapter {

		private Logger logger=Logger.getLogger(SysInterceptor2.class);
		public boolean preHandle(HttpServletRequest request,HttpServletResponse response,
				Object handler) throws Exception{
			logger.debug("SysInterceptor preHandle");
			HttpSession session=request.getSession();
			backendUser devUser=(backendUser) session.getAttribute(Constants.SYS_MESSAGE);
			if(devUser==null){
				//getContextPath():返回请求URI中表示请求上下文的路径，上下文路径是请求URI的开始部分,即http://localhost:8080/SM
				response.sendRedirect(request.getContextPath()+"/403.jsp");
				return false;
			}
			return true;
		}
}
