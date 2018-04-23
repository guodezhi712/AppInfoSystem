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
	 * ��ȡAPP�汾����ʷ��¼ҵ���ʵ�ַ���
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<appVersion> getAppVersionList(int parseInt) throws Exception {
		List<appVersion> list=version.selectAppVersion(parseInt);
		return list;
	}
	/**
	 * ����APK�汾��Ϣҵ���ʵ�ַ���
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
	 * ��ȡ���°汾APK��ʵ�ַ���
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public appVersion getAppVersionById(int parseInt) throws Exception {
		appVersion versions=version.getAppVersionList(parseInt);
		return  versions;
	}
	/**
	 * �����޸ĺ��apk�汾��Ϣҵ���ʵ�ַ���
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
	 * ɾ���������洢�������ļ���ʵ�ַ���
	 */
	@Override
	public boolean deleteApkFile(int parseInt) {
		if(version.deleteApkFile(parseInt)>0){
			return true;
		}
		return false;
	}
	/**
	 * ����id��ȡ��Ӧ�İ汾��Ϣ
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<appVersion> selectversion(int parseInt) {
		return version.selectversion(parseInt);
	}
	/**
	 * ɾ����Ӧ�İ汾��Ϣ
	 */
	@Override
	public void delversion(Integer id){
		version.delversion(id);
	}
	/**
	 * ��������·����ȡ��Ӧ��info���е�id
	 */
	@Override
	public String selectid(String images) {
		return version.selectid(images) ;
	}

}
