package cn.appsys.service.backendUserService.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.backendUserMapper.backendUserMappers;
import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.backendUser;
import cn.appsys.service.backendUserService.backendUserServices;
@Service("backendUserService")
@Transactional
public class backendUserServicesImpl implements backendUserServices {
	@Resource
	private backendUserMappers ma;
	/*
	 * 验证后台账户的唯一性
	 * (non-Javadoc)
	 * @see cn.appsys.service.backendUserService.backendUserServices#selectcodeid(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public boolean selectcodeid(String code) {
		backendUser user=ma.selectcodeid(code);
		if(user==null){
			return true;
		}
		return false;
	}
	/**
	 * 新增后台用户
	 */
	@Override
	public int insertregister(backendUser backendUser) {
		return ma.add(backendUser);
	}
	/**
	 * 获取后台账号信息总记录数
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public int selectAppInfoCount(String userCode, String userType) {
		return ma.selectUserCountList(userCode,userType);
	}
	/**
	 * 获取后台账号信息列表
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<backendUser> selectAppInfoList(String userCode, String userType,
			Integer currentPageNo, int pageSize,Integer id) {
		return ma.selectAppInfoList( userCode,  userType,currentPageNo,  pageSize,id);
	}
	/**
	 * 
	 *根据id查询账号信息
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public backendUser selectApp(int parseInt) {
		return ma.selectApp(parseInt);
	}
	
	/**
	 * 根据id删除用户账号
	 */
	@Override
	public boolean appsysdeleteAppById(int parseInt) {
		if(ma.appsysdeleteAppById( parseInt)>0){
			return true;
		}
		return false;
	}
	/**
	 * 修改用户账号信息
	 */
	@Override
	public boolean updateuser(backendUser backenduser) {
		if(ma.updateuser(backenduser)>0){
			return true;
		}
		return false;
	}

}
