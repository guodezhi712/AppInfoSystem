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
	 * 跳转到apk审核页面
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
		String stats="1";//状态，默认为1：待审核状态
		Integer devid=0;//apk所属的开发者默认为0，不区分apk所属的开发者
		List<appInfo> appInfoList = null;//获取待审核apk信息列表
		List<dataDictionary> flatFormList = null;//获取所有平台信息
		List<appCategory> categoryLevel1List = null;// 列出一级分类列表，注：二级和三级分类列表通过异步ajax获取
		List<appCategory> categoryLevel2List = null;
		List<appCategory> categoryLevel3List = null;
		// 页面容量
		int pageSize = Constants.pageSize;
		// 当前页码
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

		// 总数量（表）
		int totalCount = 0;
		try {
			totalCount = info.selectAppInfoCount(querySoftwareName,stats, _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, _queryFlatformId,devid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		// 控制首页和尾页
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

		// 二级分类列表和三级分类列表---回显
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
	
	//APP状态信息列表或者所属平台信息列表
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
	 * 二,三级分类列表
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
	 * 根据parentId查询出相应的分类级别列表
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
	 * 跳转到审核页面
	 */
	@RequestMapping("/appcheck")
	public String appcheck(
			@RequestParam(value="aid",required=false)String aid,
			@RequestParam(value="vid",required=false)String vid,
			HttpServletRequest request){
		appInfo appInfo = null;
		appVersion appVersion = null;
		try {
			appInfo = info.getAppInfo(Integer.parseInt(aid),null);//获取要审核版本的基本信息
			appVersion = version.getAppVersionById(Integer.parseInt(vid));//获取最新版本信息
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("appVersion",appVersion);
		request.setAttribute("appInfo",appInfo);
		return "backend/appcheck";
	}
	/**
	 * 审批
	 * @param ainfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/checksave")
	public String checksave(appInfo ainfo,HttpServletRequest request){
		if(info.updateSatus(ainfo.getStatus(),ainfo.getId())){
			request.setAttribute("error","操作失败");
			return "forward:/backend/applist";
		}
		return "redirect:/backend/applist";
	}
	
	  @RequestMapping("/down")  
	    public void down(String images ,HttpServletRequest request,HttpServletResponse response) throws Exception{  
	        response.setCharacterEncoding("utf-8");
	        InputStream bis=null;
	        // fileName=相对路径+"/要下载的文件名称"
			String fileName = request.getSession().getServletContext()
					.getRealPath("/statics")
					+ File.separator + "uploadfiles";

			// 2.获取输入流
			File file = new File(fileName, images);
			FileInputStream stream = new FileInputStream(file);
			bis = new BufferedInputStream(stream);

			// 3.假如以中文名下载的话，但是文件名称必须与要下载文件的格式一致
			String filename = images;
			// 转码，免得文件名中文乱码
			// filename = URLEncoder.encode(filename,"UTF-8");
			filename = new String(filename.getBytes(), "iso-8859-1");
			// 4.设置文件下载头
			// 会出现一个提示框，可以另存为...
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename);
			// 会直接使用本机上的工具打开对应下载文件格式的文件
			// response.addHeader("Content-Disposition", "inline;filename=" +
			// filename);

			// 5.设置文件ContentType类型，这样设置，会自动判断下载文件类型
			// response.setContentType("multipart/form-data");

			// 通用格式，如果确定要下载文件的格式，那就可以直接指定。具体设置可以在tomcat的lib文件下的web.xml配置文件中搜索对应的后缀即可。
			response.setContentType("application/octet-stream");

			BufferedOutputStream out = new BufferedOutputStream(
					response.getOutputStream());
			int len = 0;
			/* len = bis.read(); */
			while ((len = bis.read()) != -1) {
				// 把主体内容保存到指定文件中
				out.write(len);
				// 刷新
				out.flush();
			}
			// 关闭
			out.close();
			appInfo app=info.selectid(Integer.parseInt(version.selectid(images)));//获取对应的对象
			app.setDownloads(app.getDownloads()+1);
			info.insertInfo(app.getId(),app.getDownloads());//下载次数加一
	    }
	
	/**
	 * 用户注册跳转
	 */
	@RequestMapping("/Useradd")
	public String add(HttpServletRequest request){
		List<dataDictionary> flatFormList = null;
		flatFormList=this.selectDataDictionaryList("USER_TYPE");
		request.setAttribute("tion", flatFormList);
		return "Useradd";
	}
	
	/**
	 * 后台用户管理跳转
	 */
	@RequestMapping("/userList")
	public String userList(Model model,HttpServletRequest request,
			@RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userType", required = false) String userType,
			@RequestParam(value = "pageIndex", required = false) String pageIndex){
		List<backendUser> appUserList = null;//获取用户账号信息列表
		List<dataDictionary> flatFormList = null;//获取用户类型信息
		Integer id=null;
		// 页面容量
		int pageSize = Constants.pageSize;
		// 当前页码
		Integer currentPageNo = 1;

		if (pageIndex != null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// 总数量（表）
		int totalCount = 0;
		try {
			totalCount = user.selectAppInfoCount(userCode,userType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		// 控制首页和尾页
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
	 * 删除后台账户基础信息
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
	 * 查询用户基础信息跳转页面
	 */
	@RequestMapping("/userView/{id}")
	public String userView(@PathVariable String id,Model model){
		backendUser users=user.selectApp(Integer.parseInt(id));
		model.addAttribute(users);
		return "backend/userView";
	}
	
	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/usermodify")
	public String usermodify(@RequestParam String id,
							@RequestParam String userCode,
							@RequestParam String userType,
							@RequestParam String userPassword,
							@RequestParam String userName,
							@RequestParam String error,Model model){
		List<dataDictionary> flatFormList =this.selectDataDictionaryList("USER_TYPE");//获取用户类型列表
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
	 * 修改用户基础信息
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
					+"&error=修改错误";	
	}
	
	/**
	 * app开发者审核跳转页面
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
		// 页面容量
		int pageSize = Constants.pageSize;
		// 当前页码
		Integer currentPageNo = 1;

		if (pageIndex != null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// 总数量（表）
		int totalCount = 0;
		try {
			totalCount = devUser.selectAppInfoCount(devName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		// 控制首页和尾页
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
	 * 删除APK开发者基础信息
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
	 * 查询apk开发者账户的信息
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
	 * 查询要修改apk开发者账户的信息
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
	 * 保存修改apk开发者账户的信息
	 */
	@RequestMapping(value="/devUserfomodify",method=RequestMethod.POST)
	public String devUserfomodifysave(devUser devuser,HttpServletRequest request){
		devuser.setModifyBy(((backendUser)request.getSession().getAttribute((Constants.SYS_MESSAGE))).getId());
		devuser.setModifyDate(new Date());
		if(devUser.updatedevUser(devuser)){
			return "redirect:/backend/devUserList?error=null";
		}else{
			return "redirect:/backend/devUserList?error=修改apk失败";
		}
	}
	
	/**
	 * 数据字典跳转页面方法
	 */
	@RequestMapping("/dataDictionaryList")
	public String dataDictionaryList(Model model,
			@RequestParam(value = "typeCode", required = false) String typeCode,
			@RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request){
		List<dataDictionary> list= null;
		// 页面容量
		int pageSize = Constants.pageSize;
		// 当前页码
		Integer currentPageNo = 1;

		if (pageIndex != null && !pageIndex.equals("")) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// 总数量（表）
		int totalCount = 0;
		try {
			totalCount = Dictionary.selectAppInfoCount(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		// 控制首页和尾页
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
	 * 字典类型查询页面跳转
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
	 * 根据id删除类型
	 */
	@RequestMapping("/deldevDictionary.json")
	@ResponseBody
	public String deldevDictionary(@RequestParam("id") String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(id)){
			resultMap.put("delResult", "notexist");
		}else{
			try {
				if(Dictionary.selectid(id)){//判断要删除的类型是否被引用
					if(Dictionary.appsysdeldevuser(Integer.parseInt(id)))//如果没有被引用则可以删除
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
	 * 跳转到字典类型修改页面
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
	 * 保存修改字典类型
	 */
	@RequestMapping(value="/dataDictionarymodifty",method=RequestMethod.POST)
	public String dataDictionarymodifty(dataDictionary dic,HttpServletRequest request){
		String typeCode="";
		String pageIndex="";
		dic.setModifyBy(((backendUser)request.getSession().getAttribute((Constants.SYS_MESSAGE))).getId());
		dic.setModifyDate(new Date());
		String error="";
		if(Dictionary.updateDictionary(dic)>0){
			error="修改字典类型成功";
		}else{
			error="修改字典类型失败";
		}
		return "redirect:/backend/dataDictionaryList?typeCode="+typeCode
					+"&pageIndex="+pageIndex+"&error="+error;
	}
	/**
	 * 新增字典类型跳转页面方法
	 */
	@RequestMapping("/appDictionaryadd")
	public String appDictionaryadd(){
		return "backend/dataDictionaryAdd";
	}
	/**
	 * 验证要新增的字典类型是否已经存在
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
	 * 保存新增字典类型方法
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
			error="新增字典类型失败";
			return "redirect:/backend/dataDictionaryList?typeCode="+typeCode
					+"&pageIndex="+pageIndex+"&error="+error;
		}
		
	}
	
}
