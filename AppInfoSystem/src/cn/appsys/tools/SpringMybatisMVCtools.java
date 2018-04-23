package cn.appsys.tools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMybatisMVCtools {
	private static ApplicationContext context;
	static{
		context=new ClassPathXmlApplicationContext("applicationContext-*.xml");
	}
	public static ApplicationContext getContext() {
		return context;
	}
	public static void setContext(ApplicationContext context) {
		SpringMybatisMVCtools.context = context;
	}
}
