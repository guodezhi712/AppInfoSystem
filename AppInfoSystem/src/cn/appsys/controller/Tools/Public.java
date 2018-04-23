package cn.appsys.controller.Tools;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.appCategory;
import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.dataDictionary;
import cn.appsys.service.appCategoryService.appCategoryServices;
import cn.appsys.service.appInfoService.appInfoServices;
import cn.appsys.service.appVersionService.appVersionServices;
import cn.appsys.service.dataDictionaryService.dataDictionaryServices;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
import cn.appsys.tools.SpringMybatisMVCtools;

@Controller
public class Public {
	@Resource 
	private  appInfoServices info;
	@Resource 
	private  appCategoryServices Category;
	@Resource 
	private  appVersionServices version;
	@Resource 
	private  dataDictionaryServices Dictionary;
	public String getAppInfoList(
			Model model,
			HttpSession session,
			String querySoftwareName,
			String _queryCategoryLevel1,
			String _queryCategoryLevel2,
			String _queryCategoryLevel3,
			String _queryFlatformId,
			String queryStatus,
			String pageIndex) {
		info=SpringMybatisMVCtools.getContext().getBean("appInfoService",appInfoServices.class);
		Category=SpringMybatisMVCtools.getContext().getBean("appCategoryService",appCategoryServices.class);
		version=SpringMybatisMVCtools.getContext().getBean("appVersionService",appVersionServices.class);
		Dictionary=SpringMybatisMVCtools.getContext().getBean("dataDictionaryServices",dataDictionaryServices.class);
		String stats="1";
		Integer devid=0;
		List<appInfo> appInfoList = null;
		List<dataDictionary> flatFormList = null;
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
		return "appinfolist";
	}
	
	//APP状态信息列表或者所属平台信息列表
	private List<dataDictionary> selectDataDictionaryList(String typeCode) {
		List<dataDictionary> list= null;
		Dictionary=SpringMybatisMVCtools.getContext().getBean("dataDictionaryServices",dataDictionaryServices.class);
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
		Category=SpringMybatisMVCtools.getContext().getBean("appCategoryService",appCategoryServices.class);
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

}
