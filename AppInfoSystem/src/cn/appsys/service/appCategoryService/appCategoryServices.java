package cn.appsys.service.appCategoryService;

import java.util.List;

import cn.appsys.pojo.appCategory;

public interface appCategoryServices {
	/*
	 * ��ȡ1,2,3���б����Ϣҵ��
	 */
	List<appCategory> selectAppCategoryListByParentId(Integer  pid);

}
