package cn.appsys.service.appCategoryService.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appCategoryMapper.appCategoryMappers;
import cn.appsys.pojo.appCategory;
import cn.appsys.service.appCategoryService.appCategoryServices;
@Service("appCategoryService")
@Transactional
public class appCategoryServicesImpl implements appCategoryServices {
	@Resource
	private appCategoryMappers appCategoryMappers;
	
	/**
	 * ��ȡ1,2,3���б����Ϣҵ��
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<appCategory> selectAppCategoryListByParentId(Integer pid) {
		List<appCategory> list=null;
		list=appCategoryMappers.selectAppCategoryListByParentId(pid);
		return list;
	}

}
