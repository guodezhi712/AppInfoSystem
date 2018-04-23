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
	 * ��ѯ������apk�б���Ϣ
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
		//��ȡ�����ߵ�¼�˺ŵ�id
		Integer devId = ((devUser)request.getSession().getAttribute(Constants.USER_SESSION)).getId();
		List<appInfo> appInfoList = null;//��ȡ��ʾ�б������
		List<dataDictionary> statusList = null;//��ȡapp��״̬��Ϣ�б�
		List<dataDictionary> flatFormList = null;//��ȡ����ƽ̨��Ϣ�б�
		List<appCategory> categoryLevel1List = null;//�г�һ�������б�ע�����������������б�ͨ���첽ajax��ȡ
		List<appCategory> categoryLevel2List = null;//��ȡ���������б�
		List<appCategory> categoryLevel3List = null;//��ȡ���������б�
		//ҳ������
		int pageSize = Constants.pageSize;//ÿҳĬ����ʾ4������
		//��ǰҳ��
		Integer currentPageNo = 1;//Ĭ�ϵĵ�ǰҳ����Ϊ1
		
		if(pageIndex != null){
			try{
				currentPageNo = Integer.valueOf(pageIndex);//��ȡ��ҳ�洫�ݹ�����ҳ����
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		Integer _queryStatus = null;
		if(queryStatus != null && !queryStatus.equals("")){
			_queryStatus = Integer.parseInt(queryStatus);//��ȡAPP״̬
		}
		Integer _queryCategoryLevel1 = null;
		if(queryCategoryLevel1 != null && !queryCategoryLevel1.equals("")){
			_queryCategoryLevel1 = Integer.parseInt(queryCategoryLevel1);//��ȡһ���б�
		}
		Integer _queryCategoryLevel2 = null;
		if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
			_queryCategoryLevel2 = Integer.parseInt(queryCategoryLevel2);//��ȡ�����б�
		}
		Integer _queryCategoryLevel3 = null;
		if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
			_queryCategoryLevel3 = Integer.parseInt(queryCategoryLevel3);//��ȡ�����б�
		}
		Integer _queryFlatformId = null;
		if(queryFlatformId != null && !queryFlatformId.equals("")){
			_queryFlatformId = Integer.parseInt(queryFlatformId);//����ƽ̨
		}
		
		//����������
		int totalCount = 0;
		try {
			totalCount = info.selectAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//��ҳ��
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();//������������ÿҳ��ʾ���������������ҳ�� 
		//������ҳ��βҳ
		if(currentPageNo < 1){
			currentPageNo = 1;//Ĭ����ʼҳ��Ϊ1
		}else if(currentPageNo > totalPageCount){
			currentPageNo = totalPageCount;//�������ҳ��Ϊ��ҳ��ֵ
		}
		try {
			//��ȡapk�汾��Ϣ�б�
			appInfoList = info.selectAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, currentPageNo, pageSize);
			//APP״̬��Ϣ�б�
			statusList = this.selectDataDictionaryList("APP_STATUS");
			//����ƽ̨��Ϣ�б�
			flatFormList = this.selectDataDictionaryList("APP_FLATFORM");
			//��ȡһ���б������ѡ����Ϣ
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
		
		//���������б�����������б�---����
		//�ж��Ƿ�ѡ����һ������,����������ʾ��������
		if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
			categoryLevel2List = getCategoryList(queryCategoryLevel1.toString());
			request.setAttribute("categoryLevel2List", categoryLevel2List);
		}
		//�ж��Ƿ�ѡ���˶�������,����������ʾ��������
		if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
			categoryLevel3List = getCategoryList(queryCategoryLevel2.toString());
			request.setAttribute("categoryLevel3List", categoryLevel3List);
		}
		return "developer/appinfolist";
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
				list = Category.selectAppCategoryListByParentId(null);//���ݸ�����id����ȡ�Ӽ��������б�
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
	 * ����app��Ϣ����ת������appinfoҳ�棩
	 * @param appInfo
	 * @return
	 */
	@RequestMapping(value="/appinfoadd",method=RequestMethod.GET)
	public String add(@ModelAttribute("appInfo") appInfo appInfo){
		return "developer/appinfoadd";
	}
	
	/**
	 * ����typeCode��ѯ����Ӧ�������ֵ��б�
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<dataDictionary> getDataDicList (@RequestParam(value="tcode",required=false) String tcode){
		return this.selectDataDictionaryList(tcode);
	}
	
	/**
	 * ��������appInfo������������
	 * @param appInfo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/appinfoaddsave",method=RequestMethod.POST)
	public String addSave(appInfo appInfo,HttpSession session,HttpServletRequest request,
					@RequestParam(value="a_logoPicPath",required= false) MultipartFile attach){		
		String logoPicPath =  null;//LOGOͼƬurl·��
		String logoLocPath =  null;//LOGOͼƬ�ķ������洢·��
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//ԭ�ļ���
			String prefix = FilenameUtils.getExtension(oldFileName);//ԭ�ļ���׺
			int filesize = 500000;//�����ϴ��ļ���С����ֵ
			if(attach.getSize() > filesize){//�ϴ���С���ó��� 50k
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);//����������ϴ���С��ֵ�����ʾ��Ӧ�Ĵ�����Ϣ
				return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//�ϴ�ͼƬ��ʽ
				 String fileName = appInfo.getAPKName() + ".jpg";//�ϴ�LOGOͼƬ����:apk����.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);//����ϴ��ļ�ʧ������ʾ��Ӧ�Ĵ�����Ϣ
					return "developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);//����ϴ��ļ��ĸ�ʽ����ָ����ʽ�����ʾ��Ӧ�Ĵ�����Ϣ
				return "developer/appinfoadd";
			}
		}
		appInfo.setCreatedBy(((devUser)session.getAttribute(Constants.USER_SESSION)).getId());
		appInfo.setCreationDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setDevId(((devUser)session.getAttribute(Constants.USER_SESSION)).getId());
		appInfo.setStatus(1);//APP״̬
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
	 * �ж�APKName�Ƿ�Ψһ
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
	 * ɾ��APP������Ϣ
	 */
	@RequestMapping("/delapp.json")
	@ResponseBody
	public Object delApp(@RequestParam("id") String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		List<appVersion> versionlist=null;
		appInfo infos=null;
		try {
			versionlist=version.selectversion(Integer.parseInt(id));//����id��ȡ��Ӧ�İ汾��Ϣ
			for (int i = 0; i < versionlist.size(); i++) {
				if(!versionlist.get(i).getApkLocPath().equals("")){
					File file=new File(versionlist.get(i).getApkLocPath());
					if(file.exists()){
						file.delete();//ɾ����Ӧ��apk�ļ�
						version.delversion(versionlist.get(i).getId());
					}
				}	
			}
			 infos=info.selectApp(Integer.parseInt(id));//��ȡҪɾ����app������Ϣ
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(!infos.getLogoLocPath().equals("")){//�жϸû�����Ϣ���Ƿ���logo����apk�ļ�û��
			new File(infos.getLogoLocPath()).delete();//ɾ����Ӧ���ļ�
		}
		if(StringUtils.isNullOrEmpty(id)){//�ж�ǰ�˴������������Ƿ�Ϊ��
			resultMap.put("delResult", "notexist");
		}else{
			try {
				if(info.appsysdeleteAppById(Integer.parseInt(id)))//ɾ��app�Ļ�����Ϣinfo.
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
	 * �鿴app��Ϣ������app������Ϣ�Ͱ汾��Ϣ�б���ת���鿴ҳ�棩ʹ��REST���
	 * @param appInfo
	 * @return
	 */
	@RequestMapping(value="/appview/{pid}")
	public String appview(@PathVariable String pid,HttpServletRequest request){
		appInfo appInfo = null;
		List<appVersion> appVersionList = null;
		try {
			//�鿴��ǰapp������Ϣ,��Ψһ��֤ʱһ����
			appInfo = info.getAppInfo(Integer.parseInt(pid),null);
			//��ȡAPP�汾����ʷ��¼
			appVersionList = version.getAppVersionList(Integer.parseInt(pid));
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("appVersionList", appVersionList);
		request.setAttribute("appInfo",appInfo);
		return "developer/appinfoview";
	}
	
	/**
	 * ����ҳ��ͨ������������apk�ļ�
	 */
	/**  
     * �ļ����ع���  
     * @param request  
     * @param response  
     * @throws Exception  
     */  
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
     * ���¼ܲ���
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
				if(info.appsysUpdateSaleStatusByAppId(appInfo)){//���¼ܲ�������
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "failed");
				}		
			} catch (Exception e) {
				//ϵͳ�쳣
				resultMap.put("errorCode", "exception000001");
			}
		}else{
			//errorCode:0Ϊ����
			//��������
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
     * ����APK�汾��Ϣ(������תҳ��)
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
			appVersionList = version.getAppVersionList(Integer.parseInt(appId));//��ȡ��ʷ�汾��Ϣ
			appVersion.setAppName((info.getAppInfo(Integer.parseInt(appId),null)).getSoftwareName());//��ȡAPK����
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute(appVersion);
		model.addAttribute("fileUploadError",fileUploadError);
		return "developer/appversionadd";
	}
	/**
	 * ��������appversion���ݣ��ӱ�-�ϴ��ð汾��apk��
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
		String downloadLink =  null;//��������
		String apkLocPath = null;//�ϴ���apk�ļ��洢�ڷ������ϵ�·��
		String apkFileName = null;//�ϴ���apk�ļ�����
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//ԭ�ļ���
			String prefix = FilenameUtils.getExtension(oldFileName);//ԭ�ļ���׺
			if(prefix.equalsIgnoreCase("apk")){//apk�ļ�������apk����+�汾��+.apk
				 String apkName = null;//��ȡAPK����
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
			if(version.appsysadd(appVersion)){//�ж�����apk�汾��Ϣ�Ƿ�ɹ�
				return "redirect:/developer/appinfolist";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/developer/appversionadd?id="+appVersion.getAppId();
	}
	
	/**
	 * �޸����µ�appVersion��Ϣ����ת���޸�appVersionҳ�棩
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
			//��ȡ���°汾apk����Ϣ
			appVersion = version.getAppVersionById(Integer.parseInt(versionId));
			//��ȡAPP�汾����ʷ��¼
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
	 * �����޸ĺ�İ汾��Ϣ���߰汾������Ϣ
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
			String oldFileName = attach.getOriginalFilename();//ԭ�ļ���
			String prefix = FilenameUtils.getExtension(oldFileName);//ԭ�ļ���׺
			if(prefix.equalsIgnoreCase("apk")){//apk�ļ�������apk����+�汾��+.apk
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
	 * �޸Ĳ���ʱ��ɾ���ļ���logoͼƬ/apk�ļ��������������ݿ⣨app_info/app_version��
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
		}else if(flag.equals("logo")){//ɾ��logoͼƬ������app_info��
			try {
				fileLocPath = (info.getAppInfo(Integer.parseInt(id), null)).getLogoLocPath();
				File file = new File(fileLocPath);
			    if(file.exists())
			     if(file.delete()){//ɾ���������洢�������ļ�
						if(info.deleteAppLogo(Integer.parseInt(id))){//���±�
							resultMap.put("result", "success");
						 }
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(flag.equals("apk")){//ɾ��apk�ļ�������app_version��
			try {
				fileLocPath = (version.getAppVersionById(Integer.parseInt(id))).getApkLocPath();
				File file = new File(fileLocPath);
			    if(file.exists())
			     if(file.delete()){//ɾ���������洢�������ļ�
						if(version.deleteApkFile(Integer.parseInt(id))){//���±�
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
	 * ��ȡ���ݵ������޸�ҳ��
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
	 * �����޸ĺ��appInfo
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
			String oldFileName = attach.getOriginalFilename();//ԭ�ļ���
			String prefix = FilenameUtils.getExtension(oldFileName);//ԭ�ļ���׺
			int filesize = 500000;
			if(attach.getSize() > filesize){//�ϴ���С���ó��� 50k 1000*5
            	 return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
						 +"&error=error4";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//�ϴ�ͼƬ��ʽ
				 String fileName = APKName + ".jpg";//�ϴ�LOGOͼƬ����:apk����.apk
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
