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
	 * �����ߵ�¼��֤ҵ���ʵ�ַ���
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
	 * ������ע��ҵ��ʵ�ַ���
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
	 * ��ѯ�����ߵ�������
	 */
	@Override
	public int selectAppInfoCount(String devName) {
		return loginMapper.selectAppInfoCount(devName);
	}
	/**
	 * ��ѯ���п�������Ϣ
	 */
	@Override
	public List<devUser> selectAppInfoList(String devName,
			Integer currentPageNo, int pageSize) {
		currentPageNo=(currentPageNo-1)*pageSize;
		return loginMapper.selectAppInfoList(devName,currentPageNo,pageSize);
	}
	/**
	 * ɾ��APK�������˺�
	 */
	@Override
	public boolean appsysdeldevuser(int parseInt) {
		if(loginMapper.appsysdeldevuser(parseInt)>0){
			return true;
		}
		return false;
	}
	/**
	 * ��ѯ��Ӧ�Ŀ�������Ϣ
	 */
	@Override
	public devUser selectApp(int parseInt) {
		return loginMapper.selectApp(parseInt);
	}
	/**
	 * �����޸�apk�������˻�����Ϣ
	 */
	@Override
	public boolean updatedevUser(devUser devuser) {
		if(loginMapper.updatedevUser(devuser)>0){
			return true;
		}
		return false;
	}
}
