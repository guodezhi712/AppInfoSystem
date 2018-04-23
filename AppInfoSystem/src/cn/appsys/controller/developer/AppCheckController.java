package cn.appsys.controller.developer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.appsys.pojo.appCategory;
import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.appVersion;
import cn.appsys.pojo.dataDictionary;
import cn.appsys.pojo.devUser;
import cn.appsys.service.appCategoryService.appCategoryServices;
import cn.appsys.service.appInfoService.appInfoServices;
import cn.appsys.service.appVersionService.appVersionServices;
import cn.appsys.service.dataDictionaryService.dataDictionaryServices;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
import cn.appsys.tools.SpringMybatisMVCtools;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/developer")
public class AppCheckController {
	@Resource 
	private  appInfoServices info;
	@Resource 
	private  appCategoryServices Category;
	@Resource 
	private  appVersionServices version;
	@Resource 
	private  dataDictionaryServices Dictionary;
	
	/**
	 * 查询开发者apk列表信息
	 * @return
	 */
	@RequestMapping("/appinfolist")
	public String appinfolist(@RequestParam(value="querySoftwareName",required=false) String querySoftwareName
			,@RequestParam(value="queryFlatformId",required=false) String queryFlatformId
			,@RequestParam(value="queryCategoryLevel1",required=false) String queryCategoryLevel1
			,@RequestParam(value="queryCategoryLevel2",required=false) String queryCategoryLevel2
			,@RequestParam(value="queryCategoryLevel3",required=false) String queryCategoryLevel3
			,@RequestParam(value="queryStatus",required=false) String queryStatus
			,@RequestParam(value="pageIndex",required=false) String pageIndex
			,HttpServletRequest request){
		//获取开发者登录账号的id
		Integer devId = ((devUser)request.getSession().getAttribute(Constants.USER_SESSION)).getId();
		List<appInfo> appInfoList = null;//获取显示列表的数据
		List<dataDictionary> statusList = null;//获取app的状态信息列表
		List<dataDictionary> flatFormList = null;//获取所属平台信息列表
		List<appCategory> categoryLevel1List = null;//列出一级分类列表，注：二级和三级分类列表通过异步ajax获取
		List<appCategory> categoryLevel2List = null;//获取二级分类列表
		List<appCategory> categoryLevel3List = null;//获取三级分类列表
		//页面容量
		int pageSize = Constants.pageSize;//每页默认显示4条数据
		//当前页码
		Integer currentPageNo = 1;//默认的当前页码数为1
		
		if(pageIndex != null){
			try{
				currentPageNo = Integer.valueOf(pageIndex);//获取从页面传递过来的页码数
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		Integer _queryStatus = null;
		if(queryStatus != null && !queryStatus.equals("")){
			_queryStatus = Integer.parseInt(queryStatus);//获取APP状态
		}
		Integer _queryCategoryLevel1 = null;
		if(queryCategoryLevel1 != null && !queryCategoryLevel1.equals("")){
			_queryCategoryLevel1 = Integer.parseInt(queryCategoryLevel1);//获取一级列表
		}
		Integer _queryCategoryLevel2 = null;
		if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
			_queryCategoryLevel2 = Integer.parseInt(queryCategoryLevel2);//获取二级列表
		}
		Integer _queryCategoryLevel3 = null;
		if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
			_queryCategoryLevel3 = Integer.parseInt(queryCategoryLevel3);//获取三级列表
		}
		Integer _queryFlatformId = null;
		if(queryFlatformId != null && !queryFlatformId.equals("")){
			_queryFlatformId = Integer.parseInt(queryFlatformId);//所属平台
		}
		
		//总数量（表）
		int totalCount = 0;
		try {
			totalCount = info.selectAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();//根据总数量与每页显示的条数来计算出总页数 
		//控制首页和尾页
		if(currentPageNo < 1){
			currentPageNo = 1;//默认起始页码为1
		}else if(currentPageNo > totalPageCount){
			currentPageNo = totalPageCount;//最大允许页码为总页数值
		}
		try {
			//获取apk版本信息列表
			appInfoList = info.selectAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, currentPageNo, pageSize);
			//APP状态信息列表
			statusList = this.selectDataDictionaryList("APP_STATUS");
			//所属平台信息列表
			flatFormList = this.selectDataDictionaryList("APP_FLATFORM");
			//获取一级列表的下拉选项信息
			categoryLevel1List = Category.selectAppCategoryListByParentId(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("appInfoList", appInfoList);
		request.setAttribute("statusList", statusList);
		request.setAttribute("flatFormList", flatFormList);
		request.setAttribute("categoryLevel1List", categoryLevel1List);
		request.setAttribute("pages", pages);
		request.setAttribute("queryStatus", queryStatus);
		request.setAttribute("querySoftwareName", querySoftwareName);
		request.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
		request.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
		request.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
		request.setAttribute("queryFlatformId", queryFlatformId);
		
		//二级分类列表和三级分类列表---回显
		//判断是否选择了一级分类,来联动的显示二级分类
		if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
			categoryLevel2List = getCategoryList(queryCategoryLevel1.toString());
			request.setAttribute("categoryLevel2List", categoryLevel2List);
		}
		//判断是否选择了二级分类,来联动的显示三级分类
		if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
			categoryLevel3List = getCategoryList(queryCategoryLevel2.toString());
			request.setAttribute("categoryLevel3List", categoryLevel3List);
		}
		return "developer/appinfolist";
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
				list = Category.selectAppCategoryListByParentId(null);//根据父级的id来获取子集的类型列表
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
	 * 增加app信息（跳转到新增appinfo页面）
	 * @param appInfo
	 * @return
	 */
	@RequestMapping(value="/appinfoadd",method=RequestMethod.GET)
	public String add(@ModelAttribute("appInfo") appInfo appInfo){
		return "developer/appinfoadd";
	}
	
	/**
	 * 根据typeCode查询出相应的数据字典列表
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<dataDictionary> getDataDicList (@RequestParam(value="tcode",required=false) String tcode){
		return this.selectDataDictionaryList(tcode);
	}
	
	/**
	 * 保存新增appInfo（主表）的数据
	 * @param appInfo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/appinfoaddsave",method=RequestMethod.POST)
	public String addSave(appInfo appInfo,HttpSession session,HttpServletRequest request,
					@RequestParam(value="a_logoPicPath",required= false) MultipartFile attach){		
		String logoPicPath =  null;//LOGO图片url路径
		String logoLocPath =  null;//LOGO图片的服务器存储路径
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize = 500000;//允许上传文件大小的总值
			if(attach.getSize() > filesize){//上传大小不得超过 50k
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);//如果超过了上传大小的值则会显示对应的错误信息
				return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
				 String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);//如果上传文件失败则显示对应的错误信息
					return "developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);//如果上传文件的格式不是指定格式则会显示对应的错误信息
				return "developer/appinfoadd";
			}
		}
		appInfo.setCreatedBy(((devUser)session.getAttribute(Constants.USER_SESSION)).getId());
		appInfo.setCreationDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setDevId(((devUser)session.getAttribute(Constants.USER_SESSION)).getId());
		appInfo.setStatus(1);//APP状态
		try {
			if(info.add(appInfo)){
				return "redirect:/developer/appinfolist";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "developer/appinfoadd";
	}
	
	/**
	 * 判断APKName是否唯一
	 * @param apkName
	 * @return
	 */
	@RequestMapping(value="/apkexist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object apkNameIsExit(@RequestParam String APKName){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(APKName)){
			resultMap.put("APKName", "empty");
		}else{
			appInfo appInfo = null;
			try {
				appInfo = info.getAppInfo(null, APKName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null != appInfo)
				resultMap.put("APKName", "exist");
			else
				resultMap.put("APKName", "noexist");
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	/**
	 * 删除APP基础信息
	 */
	@RequestMapping("/delapp.json")
	@ResponseBody
	public Object delApp(@RequestParam("id") String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		List<appVersion> versionlist=null;
		appInfo infos=null;
		try {
			versionlist=version.selectversion(Integer.parseInt(id));//根据id获取对应的版本信息
			for (int i = 0; i < versionlist.size(); i++) {
				if(!versionlist.get(i).getApkLocPath().equals("")){
					File file=new File(versionlist.get(i).getApkLocPath());
					if(file.exists()){
						file.delete();//删除对应的apk文件
						version.delversion(versionlist.get(i).getId());
					}
				}	
			}
			 infos=info.selectApp(Integer.parseInt(id));//获取要删除的app基础信息
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(!infos.getLogoLocPath().equals("")){//判断该基础信息中是否有logo或者apk文件没有
			new File(infos.getLogoLocPath()).delete();//删除对应的文件
		}
		if(StringUtils.isNullOrEmpty(id)){//判断前端传过来的数据是否为空
			resultMap.put("delResult", "notexist");
		}else{
			try {
				if(info.appsysdeleteAppById(Integer.parseInt(id)))//删除app的基础信息info.
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
	 * 查看app信息，包括app基本信息和版本信息列表（跳转到查看页面）使用REST风格
	 * @param appInfo
	 * @return
	 */
	@RequestMapping(value="/appview/{pid}")
	public String appview(@PathVariable String pid,HttpServletRequest request){
		appInfo appInfo = null;
		List<appVersion> appVersionList = null;
		try {
			//查看当前app基本信息,跟唯一验证时一样的
			appInfo = info.getAppInfo(Integer.parseInt(pid),null);
			//获取APP版本的历史记录
			appVersionList = version.getAppVersionList(Integer.parseInt(pid));
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("appVersionList", appVersionList);
		request.setAttribute("appInfo",appInfo);
		return "developer/appinfoview";
	}
	
	/**
	 * 详情页面通过链接来下载apk文件
	 */
	/**  
     * 文件下载功能  
     * @param request  
     * @param response  
     * @throws Exception  
     */  
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
     * 上下架操作
     */
    @RequestMapping(value="/{appid}/sale",method=RequestMethod.PUT)
	@ResponseBody
	public Object sale(@PathVariable String appid,HttpSession session){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Integer appIdInteger = 0;
		try{
			appIdInteger = Integer.parseInt(appid);
		}catch(Exception e){
			appIdInteger = 0;
		}
		resultMap.put("errorCode", "0");
		resultMap.put("appId", appid);
		if(appIdInteger>0){
			try {
				devUser devUser = (devUser)session.getAttribute(Constants.USER_SESSION);
				appInfo appInfo = new appInfo();
				appInfo.setId(appIdInteger);
				appInfo.setModifyBy(devUser.getId());
				if(info.appsysUpdateSaleStatusByAppId(appInfo)){//上下架操作方法
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "failed");
				}		
			} catch (Exception e) {
				//系统异常
				resultMap.put("errorCode", "exception000001");
			}
		}else{
			//errorCode:0为正常
			//参数错误
			resultMap.put("errorCode", "param000001");
		}
		
		/*
		 * resultMsg:success/failed
		 * errorCode:exception000001
		 * appId:appId
		 * errorCode:param000001
		 */
		return resultMap;
	}
    
    /**
     * 新增APK版本信息(包含跳转页面)
     */
    @RequestMapping(value="/appversionadd",method=RequestMethod.GET)
	public String addVersion(@RequestParam(value="id")String appId,
							 @RequestParam(value="error",required= false)String fileUploadError,
							 appVersion appVersion,Model model){
    	if(null != fileUploadError && fileUploadError.equals("error1")){
			fileUploadError = Constants.FILEUPLOAD_ERROR_1;
		}else if(null != fileUploadError && fileUploadError.equals("error2")){
			fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
		}else if(null != fileUploadError && fileUploadError.equals("error3")){
			fileUploadError = Constants.FILEUPLOAD_ERROR_3;
		}
		appVersion.setAppId(Integer.parseInt(appId));
		List<appVersion> appVersionList = null;
		try {
			appVersionList = version.getAppVersionList(Integer.parseInt(appId));//获取历史版本信息
			appVersion.setAppName((info.getAppInfo(Integer.parseInt(appId),null)).getSoftwareName());//获取APK名称
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute(appVersion);
		model.addAttribute("fileUploadError",fileUploadError);
		return "developer/appversionadd";
	}
	/**
	 * 保存新增appversion数据（子表）-上传该版本的apk包
	 * @param appInfo
	 * @param appVersion
	 * @param session
	 * @param request
	 * @param attach
	 * @return
	 */
	@RequestMapping(value="/addversionsave",method=RequestMethod.POST)
	public String addVersionSave(appVersion appVersion,HttpSession session
									,HttpServletRequest request,
						@RequestParam(value="a_downloadLink",required= false) MultipartFile attach ){		
		String downloadLink =  null;//下载链接
		String apkLocPath = null;//上传的apk文件存储在服务器上的路径
		String apkFileName = null;//上传的apk文件名称
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			if(prefix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
				 String apkName = null;//获取APK名称
				 try {
					apkName = info.getAppInfo(appVersion.getAppId(),null).getAPKName();
				 } catch (Exception e1) {
					e1.printStackTrace();
				 }
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/developer/appversionadd?id="+appVersion.getAppId()
							 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/developer/appversionadd?id="+appVersion.getAppId()
							 +"&error=error2";
				} 
				downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/developer/appversionadd?id="+appVersion.getAppId()
						 +"&error=error3";
			}
		}
		appVersion.setCreatedBy(((devUser)session.getAttribute(Constants.USER_SESSION)).getId());
		appVersion.setCreationDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		try {
			if(version.appsysadd(appVersion)){//判断新增apk版本信息是否成功
				return "redirect:/developer/appinfolist";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/developer/appversionadd?id="+appVersion.getAppId();
	}
	
	/**
	 * 修改最新的appVersion信息（跳转到修改appVersion页面）
	 * @param versionId
	 * @param appId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/appversionmodify",method=RequestMethod.GET)
	public String modifyAppVersion(@RequestParam("vid") String versionId,
									@RequestParam("aid") String appId,
									@RequestParam(value="error",required= false)String fileUploadError,
									Model model){
		appVersion appVersion = null;
		List<appVersion> appVersionList = null;
		if(null != fileUploadError && fileUploadError.equals("error1")){
			fileUploadError = Constants.FILEUPLOAD_ERROR_1;
		}else if(null != fileUploadError && fileUploadError.equals("error2")){
			fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
		}else if(null != fileUploadError && fileUploadError.equals("error3")){
			fileUploadError = Constants.FILEUPLOAD_ERROR_3;
		}
		try {
			//获取最新版本apk的信息
			appVersion = version.getAppVersionById(Integer.parseInt(versionId));
			//获取APP版本的历史记录
			appVersionList = version.getAppVersionList(Integer.parseInt(appId));
		}catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute(appVersion);
		model.addAttribute("appVersionList",appVersionList);
		model.addAttribute("fileUploadError",fileUploadError);
		return "developer/appversionmodify";
	}
	
	
	/**
	 * 保存修改后的版本信息或者版本基础信息
	 * @param appVersion
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/appversionmodifysave",method=RequestMethod.POST)
	public String modifyAppVersionSave(appVersion appVersion,HttpSession session,HttpServletRequest request,
					@RequestParam(value="attach",required= false) MultipartFile attach){	
		String downloadLink =  null;
		String apkLocPath = null;
		String apkFileName = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			if(prefix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
				 String apkName = null;
				 try {
					apkName = info.getAppInfo(appVersion.getAppId(),null).getAPKName();
				 } catch (Exception e1) {
					e1.printStackTrace();
				 }
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/developer/appversionmodify?vid="+appVersion.getId()
							 +"&aid="+appVersion.getAppId()
							 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/developer/appversionmodify?vid="+appVersion.getId()
							 +"&aid="+appVersion.getAppId()
							 +"&error=error2";
				} 
				downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/developer/appversionmodify?vid="+appVersion.getId()
						 +"&aid="+appVersion.getAppId()
						 +"&error=error3";
			}
		}
		appVersion.setModifyBy(((devUser)session.getAttribute(Constants.USER_SESSION)).getId());
		appVersion.setModifyDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		try {
			if(version.modify(appVersion)){
				return "redirect:/developer/appinfolist";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "developer/appversionmodify";
	}
	
	/**
	 * 修改操作时，删除文件（logo图片/apk文件），并更新数据库（app_info/app_version）
	 * @param fileUrlPath
	 * @param fileLocPath
	 * @param flag
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delversionmodifyFile",method=RequestMethod.GET)
	@ResponseBody
	public Object delFile(@RequestParam(value="flag",required=false) String flag,
						 @RequestParam(value="id",required=false) String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String fileLocPath = null;
		if(flag == null || flag.equals("") ||
			id == null || id.equals("")){
			resultMap.put("result", "failed");
		}else if(flag.equals("logo")){//删除logo图片（操作app_info）
			try {
				fileLocPath = (info.getAppInfo(Integer.parseInt(id), null)).getLogoLocPath();
				File file = new File(fileLocPath);
			    if(file.exists())
			     if(file.delete()){//删除服务器存储的物理文件
						if(info.deleteAppLogo(Integer.parseInt(id))){//更新表
							resultMap.put("result", "success");
						 }
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(flag.equals("apk")){//删除apk文件（操作app_version）
			try {
				fileLocPath = (version.getAppVersionById(Integer.parseInt(id))).getApkLocPath();
				File file = new File(fileLocPath);
			    if(file.exists())
			     if(file.delete()){//删除服务器存储的物理文件
						if(version.deleteApkFile(Integer.parseInt(id))){//更新表
							resultMap.put("result", "success");
						 }
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	/**
	 * 获取数据调整到修改页面
	 * @param id
	 * @param fileUploadError
	 * @param model
	 * @return
	 */
	@RequestMapping("appinfomodify")
	public String modifyAppInfo(
			@RequestParam("id") String id,
			@RequestParam(value = "error", required = false) String fileUploadError,
			Model model) {
		appInfo appInfo = null;
		if (null != fileUploadError && fileUploadError.equals("error1")) {
			fileUploadError = Constants.FILEUPLOAD_ERROR_1;
		} else if (null != fileUploadError && fileUploadError.equals("error2")) {
			fileUploadError = Constants.FILEUPLOAD_ERROR_2;
		} else if (null != fileUploadError && fileUploadError.equals("error3")) {
			fileUploadError = Constants.FILEUPLOAD_ERROR_3;
		} else if (null != fileUploadError && fileUploadError.equals("error4")) {
			fileUploadError = Constants.FILEUPLOAD_ERROR_4;
		}
		try {
			appInfo = info.getAppInfo(Integer.parseInt(id), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute(appInfo);
		model.addAttribute("fileUploadError", fileUploadError);
		return "developer/appinfomodify";
	}
	
	/**
	 * 保存修改后的appInfo
	 * @param appInfo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/appinfomodifysave",method=RequestMethod.POST)
	public String modifySave(appInfo appInfo,HttpSession session,HttpServletRequest request,
							@RequestParam(value="attach",required= false) MultipartFile attach){		
		String logoPicPath =  null;
		String logoLocPath =  null;
		String APKName = appInfo.getAPKName();
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize = 500000;
			if(attach.getSize() > filesize){//上传大小不得超过 50k 1000*5
            	 return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
						 +"&error=error4";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
				 String fileName = APKName + ".jpg";//上传LOGO图片命名:apk名称.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/developer/appinfomodify?id="+appInfo.getId()
							+"&error=error2";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
            }else{
            	return "redirect:/developer/appinfomodify?id="+appInfo.getId()
						 +"&error=error3";
            }
		}
		appInfo.setModifyBy(((devUser)session.getAttribute(Constants.USER_SESSION)).getId());
		appInfo.setModifyDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		try {
			if(info.modify(appInfo)){
				return "redirect:/developer/appinfolist";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/developer/appinfomodify";
	}
	

}
