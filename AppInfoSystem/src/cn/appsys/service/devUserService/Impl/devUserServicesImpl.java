package cn.appsys.service.devUserService.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.devUserMapper.loginMapper;
import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.devUser;
import cn.appsys.service.devUserService.devUserServices;

@Transactional
@Service("devUserServices")
public class devUserServicesImpl implements devUserServices {
	@Resource
	private loginMapper loginMapper;
	
	/**
	 * 开发者登录验证业务的实现方法
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public devUser selectdevlogin(String devCode, String devPassword)
			throws Exception {
		devUser devUser=loginMapper.selectdevlogin(devCode);
		return devUser;
	}

	@Override
	public boolean selectcodeid(String code) {
		devUser devUser=null;
		try {
			devUser = loginMapper.selectdevlogin(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean flag=false;
		if(devUser==null){
			flag=true;
		}
		return flag;
	}
	/**
	 * 开发者注册业务实现方法
	 */
	@Override
	public int insertregister(devUser devUser) {
		int count=0;
		try {
			count = loginMapper.insertregister(devUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 查询开发者的总人数
	 */
	@Override
	public int selectAppInfoCount(String devName) {
		return loginMapper.selectAppInfoCount(devName);
	}
	/**
	 * 查询所有开发者信息
	 */
	@Override
	public List<devUser> selectAppInfoList(String devName,
			Integer currentPageNo, int pageSize) {
		currentPageNo=(currentPageNo-1)*pageSize;
		return loginMapper.selectAppInfoList(devName,currentPageNo,pageSize);
	}
	/**
	 * 删除APK开发者账号
	 */
	@Override
	public boolean appsysdeldevuser(int parseInt) {
		if(loginMapper.appsysdeldevuser(parseInt)>0){
			return true;
		}
		return false;
	}
	/**
	 * 查询对应的开发者信息
	 */
	@Override
	public devUser selectApp(int parseInt) {
		return loginMapper.selectApp(parseInt);
	}
	/**
	 * 保存修改apk开发者账户的信息
	 */
	@Override
	public boolean updatedevUser(devUser devuser) {
		if(loginMapper.updatedevUser(devuser)>0){
			return true;
		}
		return false;
	}
}
