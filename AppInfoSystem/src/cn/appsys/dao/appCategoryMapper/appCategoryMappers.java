package cn.appsys.dao.appCategoryMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appCategory;

public interface appCategoryMappers {
	/**
	 * 获取1,2,3级列表的信息
	 * @param pid
	 * @return
	 */
	List<appCategory> selectAppCategoryListByParentId(@Param("parentId")Integer pid);

}
