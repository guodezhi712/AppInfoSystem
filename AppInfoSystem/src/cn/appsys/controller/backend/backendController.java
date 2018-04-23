package cn.appsys.controller.backend;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.appCategory;
import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.appVersion;
import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.dataDictionary;
import cn.appsys.pojo.devUser;
import cn.appsys.service.appCategoryService.appCategoryServices;
import cn.appsys.service.appInfoService.appInfoServices;
import cn.appsys.service.appVersionService.appVersionServices;
import cn.appsys.service.backendUserService.backendUserServices;
import cn.appsys.service.dataDictionaryService.dataDictionaryServices;
import cn.appsys.service.devUserService.devUserServices;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/backend")
public class backendController {
	@Resource 
	private  appInfoServices info;
	@Resource 
	private  appCategoryServices Category;
	@Resource 
	private  appVersionServices version;
	@Resource 
	private  dataDictionaryServices Dictionary;
	@Resource 
	private  backendUserServices user;
	@Resource
	private devUserServices devUser;
	
	/**
	 * ��ת��apk���ҳ��
	 * @param model
	 * @param session
	 * @param querySoftwareName
	 * @param _queryCategoryLevel1
	 * @param _queryCategoryLevel2
	 * @param _queryCategoryLevel3
	 * @param _queryFlatformId
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/applist")
	public String getAppInfoList(
			Model model,
			HttpSession session,
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryCategoryLevel1", required = false) String _queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String _queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String _queryCategoryLevel3,
			@RequestParam(value = "queryFlatformId", required = false) String _queryFlatformId,
			@RequestParam(value = "pageIndex", required = false) String pageIndex) {
		String stats="1";//״̬��Ĭ��Ϊ1�������״̬
		Integer devid=0;//apk�����Ŀ�����Ĭ��Ϊ0��������apk�����Ŀ�����
		List<appInfo> appInfoList = null;//��ȡ�����apk��Ϣ�б�
		List<dataDictionary> flatFormList = null;//��ȡ����ƽ̨��Ϣ
		List<appCategory> categoryLevel1List = null;// �г�һ�������б�ע�����������������б�ͨ���첽ajax��ȡ
		List<appCategory> categoryLevel2List = null;
		List<appCategory> categoryLevel3List = null;
		// ҳ������
		int pageSize = Constants.pageSize;
		// ��ǰҳ��
		Integer currentPageNo = 1;

		if (pageIndex != null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		Integer queryCategoryLevel1 = null;
		if (_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")) {
			queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
		}
		Integer queryCategoryLevel2 = null;
		if (_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")) {
			queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
		}
		Integer queryCategoryLevel3 = null;
		if (_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")) {
			queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
		}
		Integer queryFlatformId = null;
		if (_queryFlatformId != null && !_queryFlatformId.equals("")) {
			queryFlatformId = Integer.parseInt(_queryFlatformId);
		}

		// ����������
		int totalCount = 0;
		try {
			totalCount = info.selectAppInfoCount(querySoftwareName,stats, _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, _queryFlatformId,devid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ��ҳ��
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		// ������ҳ��βҳ
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		try {
			appInfoList = info.selectAppInfoList(querySoftwareName,stats, _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, _queryFlatformId,devid, currentPageNo,pageSize);
			flatFormList = this.selectDataDictionaryList("APP_FLATFORM");
			categoryLevel1List = Category.selectAppCategoryListByParentId(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("pages", pages);
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		model.addAttribute("queryFlatformId", queryFlatformId);

		// ���������б�����������б�---����
		if (queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")) {
			categoryLevel2List = getCategoryList(queryCategoryLevel1.toString());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if (queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")) {
			categoryLevel3List = getCategoryList(queryCategoryLevel2.toString());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		return "backend/applist";
	}
	
	//APP״̬��Ϣ�б��������ƽ̨��Ϣ�б�
	private List<dataDictionary> selectDataDictionaryList(String typeCode) {
		List<dataDictionary> list= null;
		try {
			list = Dictionary.selectDataDictionaryList(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * ��,���������б�
	 * @param string
	 * @return
	 */
	private List<appCategory> getCategoryList(String pid) {
		List<appCategory> list = null;
		try {
			if(pid==null){
				list = Category.selectAppCategoryListByParentId(null);
			}else{
				list = Category.selectAppCategoryListByParentId(Integer.parseInt(pid));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ����parentId��ѯ����Ӧ�ķ��༶���б�
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<appCategory> getAppCategoryList (@RequestParam String pid){
		if(pid.equals("")) pid = null;
		return getCategoryList(pid);
	}
	/**
	 * ��ת�����ҳ��
	 */
	@RequestMapping("/appcheck")
	public String appcheck(
			@RequestParam(value="aid",required=false)String aid,
			@RequestParam(value="vid",required=false)String vid,
			HttpServletRequest request){
		appInfo appInfo = null;
		appVersion appVersion = null;
		try {
			appInfo = info.getAppInfo(Integer.parseInt(aid),null);//��ȡҪ��˰汾�Ļ�����Ϣ
			appVersion = version.getAppVersionById(Integer.parseInt(vid));//��ȡ���°汾��Ϣ
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("appVersion",appVersion);
		request.setAttribute("appInfo",appInfo);
		return "backend/appcheck";
	}
	/**
	 * ����
	 * @param ainfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/checksave")
	public String checksave(appInfo ainfo,HttpServletRequest request){
		if(info.updateSatus(ainfo.getStatus(),ainfo.getId())){
			request.setAttribute("error","����ʧ��");
			return "forward:/backend/applist";
		}
		return "redirect:/backend/applist";
	}
	
	  @RequestMapping("/down")  
	    public void down(String images ,HttpServletRequest request,HttpServletResponse response) throws Exception{  
	        response.setCharacterEncoding("utf-8");
	        InputStream bis=null;
	        // fileName=���·��+"/Ҫ���ص��ļ�����"
			String fileName = request.getSession().getServletContext()
					.getRealPath("/statics")
					+ File.separator + "uploadfiles";

			// 2.��ȡ������
			File file = new File(fileName, images);
			FileInputStream stream = new FileInputStream(file);
			bis = new BufferedInputStream(stream);

			// 3.���������������صĻ��������ļ����Ʊ�����Ҫ�����ļ��ĸ�ʽһ��
			String filename = images;
			// ת�룬����ļ�����������
			// filename = URLEncoder.encode(filename,"UTF-8");
			filename = new String(filename.getBytes(), "iso-8859-1");
			// 4.�����ļ�����ͷ
			// �����һ����ʾ�򣬿������Ϊ...
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename);
			// ��ֱ��ʹ�ñ����ϵĹ��ߴ򿪶�Ӧ�����ļ���ʽ���ļ�
			// response.addHeader("Content-Disposition", "inline;filename=" +
			// filename);

			// 5.�����ļ�ContentType���ͣ��������ã����Զ��ж������ļ�����
			// response.setContentType("multipart/form-data");

			// ͨ�ø�ʽ�����ȷ��Ҫ�����ļ��ĸ�ʽ���ǾͿ���ֱ��ָ�����������ÿ�����tomcat��lib�ļ��µ�web.xml�����ļ���������Ӧ�ĺ�׺���ɡ�
			response.setContentType("application/octet-stream");

			BufferedOutputStream out = new BufferedOutputStream(
					response.getOutputStream());
			int len = 0;
			/* len = bis.read(); */
			while ((len = bis.read()) != -1) {
				// ���������ݱ��浽ָ���ļ���
				out.write(len);
				// ˢ��
				out.flush();
			}
			// �ر�
			out.close();
			appInfo app=info.selectid(Integer.parseInt(version.selectid(images)));//��ȡ��Ӧ�Ķ���
			app.setDownloads(app.getDownloads()+1);
			info.insertInfo(app.getId(),app.getDownloads());//���ش�����һ
	    }
	
	/**
	 * �û�ע����ת
	 */
	@RequestMapping("/Useradd")
	public String add(HttpServletRequest request){
		List<dataDictionary> flatFormList = null;
		flatFormList=this.selectDataDictionaryList("USER_TYPE");
		request.setAttribute("tion", flatFormList);
		return "Useradd";
	}
	
	/**
	 * ��̨�û�������ת
	 */
	@RequestMapping("/userList")
	public String userList(Model model,HttpServletRequest request,
			@RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userType", required = false) String userType,
			@RequestParam(value = "pageIndex", required = false) String pageIndex){
		List<backendUser> appUserList = null;//��ȡ�û��˺���Ϣ�б�
		List<dataDictionary> flatFormList = null;//��ȡ�û�������Ϣ
		Integer id=null;
		// ҳ������
		int pageSize = Constants.pageSize;
		// ��ǰҳ��
		Integer currentPageNo = 1;

		if (pageIndex != null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// ����������
		int totalCount = 0;
		try {
			totalCount = user.selectAppInfoCount(userCode,userType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ��ҳ��
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		// ������ҳ��βҳ
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		try {
			
			id=((backendUser)request.getSession().getAttribute((Constants.SYS_MESSAGE))).getUserType();
			Integer uid=((backendUser)request.getSession().getAttribute((Constants.SYS_MESSAGE))).getId();
			if(id==1){
				appUserList = user.selectAppInfoList(userCode,userType, currentPageNo,pageSize,null);
			}else{
				appUserList = user.selectAppInfoList(userCode,userType, currentPageNo,pageSize,uid);
			}
			flatFormList = this.selectDataDictionaryList("USER_TYPE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("appInfoList", appUserList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("pages", pages);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("userType",userType);
		model.addAttribute("userCode",userCode);
		model.addAttribute("currentPageNo",currentPageNo);
		model.addAttribute("id",id);
		return "backend/userList";
	}
	/**
	 * ɾ����̨�˻�������Ϣ
	 */
	@RequestMapping("/delapp.json")
	@ResponseBody
	public Object delApp(@RequestParam("id") String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(id)){
			resultMap.put("delResult", "notexist");
		}else{
			try {
				if(user.appsysdeleteAppById(Integer.parseInt(id)))
					resultMap.put("delResult", "true");
				else
					resultMap.put("delResult", "false");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	/**
	 * ��ѯ�û�������Ϣ��תҳ��
	 */
	@RequestMapping("/userView/{id}")
	public String userView(@PathVariable String id,Model model){
		backendUser users=user.selectApp(Integer.parseInt(id));
		model.addAttribute(users);
		return "backend/userView";
	}
	
	/**
	 * ��ת���޸�ҳ��
	 */
	@RequestMapping("/usermodify")
	public String usermodify(@RequestParam String id,
							@RequestParam String userCode,
							@RequestParam String userType,
							@RequestParam String userPassword,
							@RequestParam String userName,
							@RequestParam String error,Model model){
		List<dataDictionary> flatFormList =this.selectDataDictionaryList("USER_TYPE");//��ȡ�û������б�
		model.addAttribute("flatFormList",flatFormList);
		model.addAttribute("id",id);
		model.addAttribute("userCode",userCode);
		model.addAttribute("userType",userType);
		model.addAttribute("userPassword",userPassword);
		model.addAttribute("userName",userName);
		model.addAttribute("error",error);
		return "/backend/usermodify";
	}
	/**
	 * �޸��û�������Ϣ
	 */
	@RequestMapping(value="/userfomodifysave",method=RequestMethod.POST)
	public String userfomodifysave(backendUser backenduser,HttpServletRequest request){
		backenduser.setModifyBy(((backendUser)request.getSession().getAttribute(Constants.SYS_MESSAGE)).getId());
		backenduser.setModifyDate(new Date());
		if(user.updateuser(backenduser))
			return "redirect:/backend/userList";
		else
			return "redirect:/backend/usermodify?id="+backenduser.getId()
					+"&userCode="+backenduser.getUserCode()
					+"&userType="+backenduser.getUserType()
					+"&userPassword="+backenduser.getUserPassword()
					+"&userName="+backenduser.getUserName()
					+"&error=�޸Ĵ���";	
	}
	
	/**
	 * app�����������תҳ��
	 * @param request
	 * @param model
	 * @param devName
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/devUserList")
	public String devUserList(HttpServletRequest request,Model model,
			@RequestParam(value = "devName", required = false) String devName,
			@RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "error", required = false) String error){
		List<devUser> appUserList = null;
		// ҳ������
		int pageSize = Constants.pageSize;
		// ��ǰҳ��
		Integer currentPageNo = 1;

		if (pageIndex != null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// ����������
		int totalCount = 0;
		try {
			totalCount = devUser.selectAppInfoCount(devName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ��ҳ��
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		// ������ҳ��βҳ
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		try {
			appUserList = devUser.selectAppInfoList(devName, currentPageNo,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("appUserList",appUserList);
		model.addAttribute("pages",pages);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("error",error);
		model.addAttribute("devName",devName);
		return "backend/devUserList";
	}
	
	/**
	 * ɾ��APK�����߻�����Ϣ
	 */
	@RequestMapping("/deldevuser.json")
	@ResponseBody
	public Object deldevuser(@RequestParam("id") String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(id)){
			resultMap.put("delResult", "notexist");
		}else{
			try {
				if(devUser.appsysdeldevuser(Integer.parseInt(id)))
					resultMap.put("delResult", "true");
				else
					resultMap.put("delResult", "false");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	/**
	 * ��ѯapk�������˻�����Ϣ
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/devuserView/{id}")
	public String devuserView(@PathVariable String id,HttpServletRequest request){
		devUser users=devUser.selectApp(Integer.parseInt(id));
		request.setAttribute("users", users);
		return "backend/devUserView";
	}
	
	/**
	 * ��ѯҪ�޸�apk�������˻�����Ϣ
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/devuserViews/{id}")
	public String devuserViews(@PathVariable String id,HttpServletRequest request){
		devUser users=devUser.selectApp(Integer.parseInt(id));
		request.setAttribute("users", users);
		return "backend/devUsermodify";
	}
	
	/**
	 * �����޸�apk�������˻�����Ϣ
	 */
	@RequestMapping(value="/devUserfomodify",method=RequestMethod.POST)
	public String devUserfomodifysave(devUser devuser,HttpServletRequest request){
		devuser.setModifyBy(((backendUser)request.getSession().getAttribute((Constants.SYS_MESSAGE))).getId());
		devuser.setModifyDate(new Date());
		if(devUser.updatedevUser(devuser)){
			return "redirect:/backend/devUserList?error=null";
		}else{
			return "redirect:/backend/devUserList?error=�޸�apkʧ��";
		}
	}
	
	/**
	 * �����ֵ���תҳ�淽��
	 */
	@RequestMapping("/dataDictionaryList")
	public String dataDictionaryList(Model model,
			@RequestParam(value = "typeCode", required = false) String typeCode,
			@RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request){
		List<dataDictionary> list= null;
		// ҳ������
		int pageSize = Constants.pageSize;
		// ��ǰҳ��
		Integer currentPageNo = 1;

		if (pageIndex != null && !pageIndex.equals("")) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// ����������
		int totalCount = 0;
		try {
			totalCount = Dictionary.selectAppInfoCount(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ��ҳ��
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		// ������ҳ��βҳ
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		try {
			list = Dictionary.dataDictionaryList(typeCode,currentPageNo,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer devid=((backendUser)request.getSession().getAttribute((Constants.SYS_MESSAGE))).getId();
		model.addAttribute("pages",pages);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("dataDictionary",list);
		model.addAttribute("typeCode",typeCode);
		model.addAttribute("devid",devid);
		model.addAttribute("error",error);
		return "backend/dataDictionaryList";
	}
	/**
	 * �ֵ����Ͳ�ѯҳ����ת
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/dataDictionaryView/{id}")
	public String dataDictionaryView(@PathVariable String id,Model model){
		dataDictionary dictionary=null;
		try {
			 dictionary=Dictionary.selectOne(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("dictionary", dictionary);
		return "backend/dataDictionaryView";
	}
	
	/**
	 * ����idɾ������
	 */
	@RequestMapping("/deldevDictionary.json")
	@ResponseBody
	public String deldevDictionary(@RequestParam("id") String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(id)){
			resultMap.put("delResult", "notexist");
		}else{
			try {
				if(Dictionary.selectid(id)){//�ж�Ҫɾ���������Ƿ�����
					if(Dictionary.appsysdeldevuser(Integer.parseInt(id)))//���û�б����������ɾ��
						resultMap.put("delResult", "true");
					else
						resultMap.put("delResult", "false");
				}else{
					resultMap.put("delResult", "notdel");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	/**
	 * ��ת���ֵ������޸�ҳ��
	 */
	@RequestMapping("/dataDictionaryupd/{id}")
	public String dataDictionaryupd(@PathVariable String id,Model model){
		dataDictionary dictionary=null;
		try {
			 dictionary=Dictionary.selectOne(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("dictionary", dictionary);
		return "backend/dataDictionarymodifty";
	}
	/**
	 * �����޸��ֵ�����
	 */
	@RequestMapping(value="/dataDictionarymodifty",method=RequestMethod.POST)
	public String dataDictionarymodifty(dataDictionary dic,HttpServletRequest request){
		String typeCode="";
		String pageIndex="";
		dic.setModifyBy(((backendUser)request.getSession().getAttribute((Constants.SYS_MESSAGE))).getId());
		dic.setModifyDate(new Date());
		String error="";
		if(Dictionary.updateDictionary(dic)>0){
			error="�޸��ֵ����ͳɹ�";
		}else{
			error="�޸��ֵ�����ʧ��";
		}
		return "redirect:/backend/dataDictionaryList?typeCode="+typeCode
					+"&pageIndex="+pageIndex+"&error="+error;
	}
	/**
	 * �����ֵ�������תҳ�淽��
	 */
	@RequestMapping("/appDictionaryadd")
	public String appDictionaryadd(){
		return "backend/dataDictionaryAdd";
	}
	/**
	 * ��֤Ҫ�������ֵ������Ƿ��Ѿ�����
	 */
	@RequestMapping("/Dictionarys.json")
	@ResponseBody
	public Object Dictionarys(@RequestParam(value="tCode",required=false) String tCode,
					@RequestParam(value="tName",required=false) String tName,
					@RequestParam(value="vId",required=false) String vId,
					@RequestParam(value="vName",required=false) String vName){
		Map<String,Object> hashmap=new HashMap<String,Object>();
		if(StringUtils.isNullOrEmpty(vId)||StringUtils.isNullOrEmpty(tName)
					||StringUtils.isNullOrEmpty(vName) ||
					StringUtils.isNullOrEmpty(tCode)){
			hashmap.put("map", "notexis");
		}
		if(Dictionary.selectOnes(tCode,tName,vId,vName)>0){
			hashmap.put("map", "false");
		}else{
			hashmap.put("map", "true");
		}
		return JSON.toJSONString(hashmap);
	}
	/**
	 * ���������ֵ����ͷ���
	 */
	@RequestMapping(value="/appDictionaryaddsave",method=RequestMethod.POST)
	public String appDictionaryaddsave(dataDictionary Dictionarys,HttpServletRequest request){
		Dictionarys.setCreatedBy(((backendUser)request.getSession().getAttribute((Constants.SYS_MESSAGE))).getId());
		Dictionarys.setCreationDate(new Date());
		String typeCode="";
		String pageIndex="";
		String error="";
		if(Dictionary.insertDictionary(Dictionarys)>0){
			return "redirect:/backend/dataDictionaryList?typeCode="+typeCode
					+"&pageIndex="+pageIndex+"&error="+error;
		}else{
			error="�����ֵ�����ʧ��";
			return "redirect:/backend/dataDictionaryList?typeCode="+typeCode
					+"&pageIndex="+pageIndex+"&error="+error;
		}
		
	}
	
}
