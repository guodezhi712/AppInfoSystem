package cn.appsys.dao.appCategoryMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appCategory;

public interface appCategoryMappers {
	/**
	 * ��ȡ1,2,3���б����Ϣ
	 * @param pid
	 * @return
	 */
	List<appCategory> selectAppCategoryListByParentId(@Param("parentId")Integer pid);

}
