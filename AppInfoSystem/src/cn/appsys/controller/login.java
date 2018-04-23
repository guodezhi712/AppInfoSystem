package cn.appsys.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.devUser;
import cn.appsys.service.backendUserService.backendUserServices;
import cn.appsys.service.dataDictionaryService.dataDictionaryServices;
import cn.appsys.service.devUserService.devUserServices;
import cn.appsys.tools.Constants;
import cn.appsys.tools.SpringMybatisMVCtools;


@RequestMapping("/login")
@Controller
public class login{
	@Resource
	private devUserServices devUserServices;
	@Resource
	private dataDictionaryServices dataDictionaryServices;
	@Resource
	private backendUserServices user;
	/**
	 * ��¼����
	 * @return
	 */
	@RequestMapping("/backendLogin.html")
	public String backendLogin(String uid,Model model){
		model.addAttribute("uid", uid);
		return "backendLogin";
	}
	/**
	 * ���ʿ�
	 * @return
	 */
	@RequestMapping("/devlogin.html")
	public String devlogin(){
		return "devlogin";
	}
	/**
	 * ��������֤����
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	@RequestMapping("/selectdevlogin.html")
	public String selectdevlogin(String devCode,String devPassword,HttpServletRequest request){
		devUser devUser=null;
		try {
			devUser = devUserServices.selectdevlogin(devCode, devPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(devUser==null){
			throw new RuntimeException("�������û�������");
		}else{
			request.getSession().setAttribute(Constants.USER_SESSION,devUser);
			request.getSession().setAttribute("devUserName",devUser.getDevCode());
			request.getSession().setAttribute("devUserNames",devUser.getDevName());
		}
		if(!devUser.getDevPassword().equals(devPassword)){
			throw new RuntimeException("�������");
		}
		return "developer/main";
	}
	
	/**
	 * ��֤��̨�˻���¼
	 * backendUser.html
	 */
	@RequestMapping("/backendUser.html")
	public String backendUser(String devCode,String devPassword,HttpServletRequest request){
		backendUser user=null;
		try {
			user = dataDictionaryServices.selectdevlogin(devCode, devPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user==null){
			throw new RuntimeException("��̨��¼�û�������");
		}else{
			if(!user.getUserPassword().equals(devPassword)){
				throw new RuntimeException("��̨�˻���¼�������");
			}
			request.getSession().setAttribute(Constants.SYS_MESSAGE,user);
			request.getSession().setAttribute("userSession",user);
		}
		return "backend/main";
	}
	
	/**
	 * ע�Ὺ�����˺�
	 */
	@RequestMapping("/add.html")
	public String add(){
		return "add";
	}
	/**
	 * �ֲ��쳣������
	 */
	@ExceptionHandler(value={RuntimeException.class})
	public String exception(RuntimeException e,HttpServletRequest request){
		request.setAttribute("e",e);
		if(e.getMessage().equals("��̨�˻���¼�������") || e.getMessage().equals("��̨��¼�û�������")){
			request.setAttribute("uid",2);
		}else{
			request.setAttribute("uid",1);
		}
		return "backendLogin";
	}
	
	/**
	 * ��֤�������˺ŵ�Ψһ�Է���
	 */
	@RequestMapping("/sole")
	@ResponseBody
	public Object sole(String code){
		Map<String,Object> haMap=new HashMap<String, Object>();
		boolean flag=devUserServices.selectcodeid(code);
		if(code.equals("") ||code==null){
			haMap.put("flag","noexists");
		}else{
			if(flag){
				haMap.put("flag","true");
			}else{
				haMap.put("flag","false");
			}
		}
		return haMap;
	}
	/**
	 * ��֤��̨�˺ŵ�Ψһ�Է���
	 */
	@RequestMapping("/sole2")
	@ResponseBody
	public Object sole2(String code){
		Map<String,Object> haMap=new HashMap<String, Object>();
		boolean flag=user.selectcodeid(code);
		if(code.equals("") ||code==null){
			haMap.put("flag","noexists");
		}else{
			if(flag){
				haMap.put("flag","true");
			}else{
				haMap.put("flag","false");
			}
		}
		return haMap;
	}
	
	/**
	 * ���п�����ע�᷽��
	 */
	@RequestMapping("/register.html")
	public String register(devUser devUser,HttpServletRequest request){
		devUser.setCreationDate(new Date());
		Integer id=1;
		if(request.getSession().getAttribute(Constants.USER_SESSION)!=null){
			id=((devUser)request.getSession().getAttribute(Constants.USER_SESSION)).getId();
		}
		devUser.setCreatedBy(1);//������ע��Ķ���Ĭ�϶���һ
		int count=devUserServices.insertregister(devUser);
		if(count==1){
			return "redirect:/login/devlogin.html";
		}else{
			return "redirect:/login/add.html";
		}
	}
	
	/**
	 * ���к�̨�˺�ע�᷽��
	 */
	@RequestMapping("/register2.html")
	public String register2(backendUser backendUser,HttpServletRequest request){
		backendUser.setCreationDate(new Date());
		Integer devid=((backendUser)request.getSession().getAttribute(Constants.SYS_MESSAGE)).getId();
		backendUser.setCreatedBy(devid);
		int count=user.insertregister(backendUser);
		if(count==1){
			return "redirect:/backend/applist";
		}else{
			return "redirect:/login/add.html";
		}
	}
	
}
