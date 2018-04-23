package cn.appsys.service.appCategoryService;

import java.util.List;

import cn.appsys.pojo.appCategory;

public interface appCategoryServices {
	/*
	 * 获取1,2,3级列表的信息业务
	 */
	List<appCategory> selectAppCategoryListByParentId(Integer  pid);

}
