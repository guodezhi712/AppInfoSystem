package cn.appsys.service.appVersionService.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appInfoMapper.appInfoMappers;
import cn.appsys.dao.appVersionMapper.appVersionMappers;
import cn.appsys.pojo.appVersion;
import cn.appsys.service.appVersionService.appVersionServices;
@Service("appVersionService")
@Transactional
public class appVersionServicesImpl implements appVersionServices {
	@Resource
	private appVersionMappers version;
	@Resource
	private appInfoMappers info;
	/**
	 * 获取APP版本的历史记录业务的实现方法
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<appVersion> getAppVersionList(int parseInt) throws Exception {
		List<appVersion> list=version.selectAppVersion(parseInt);
		return list;
	}
	/**
	 * 新增APK版本信息业务的实现方法
	 */
	@Override
	public boolean appsysadd(appVersion appVersion) throws Exception {
		boolean flag = false;
		Integer versionId = null;
		if(version.insertversion(appVersion) > 0){
			versionId = appVersion.getId();
			flag = true;
		}
		if(info.updateVersionId(versionId, appVersion.getAppId()) > 0 && flag){
			flag = true;
		}
		return flag;
	}
	/**
	 * 获取最新版本APK的实现方法
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public appVersion getAppVersionById(int parseInt) throws Exception {
		appVersion versions=version.getAppVersionList(parseInt);
		return  versions;
	}
	/**
	 * 保存修改后的apk版本信息业务的实现方法
	 */
	@Override
	public boolean modify(appVersion appVersion) throws Exception {
		boolean flag = false;
		if(version.modify(appVersion) > 0){
			flag = true;
		}
		return flag;
	}
	/**
	 * 删除服务器存储的物理文件的实现方法
	 */
	@Override
	public boolean deleteApkFile(int parseInt) {
		if(version.deleteApkFile(parseInt)>0){
			return true;
		}
		return false;
	}
	/**
	 * 根据id获取对应的版本信息
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<appVersion> selectversion(int parseInt) {
		return version.selectversion(parseInt);
	}
	/**
	 * 删除对应的版本信息
	 */
	@Override
	public void delversion(Integer id){
		version.delversion(id);
	}
	/**
	 * 根据下载路径获取对应的info表中的id
	 */
	@Override
	public String selectid(String images) {
		return version.selectid(images) ;
	}

}
