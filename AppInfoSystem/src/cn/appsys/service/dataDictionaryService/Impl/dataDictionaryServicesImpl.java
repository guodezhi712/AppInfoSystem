package cn.appsys.service.dataDictionaryService.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.backendUserMapper.backendUserMappers;
import cn.appsys.dao.dataDictionaryMapper.dictionaryMapper;
import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.dataDictionary;
import cn.appsys.service.dataDictionaryService.dataDictionaryServices;
@Service("dataDictionaryServices")
@Transactional
public class dataDictionaryServicesImpl implements dataDictionaryServices {
	@Resource
	private backendUserMappers backendUserMappers;
	@Resource
	private dictionaryMapper dictionaryMapper;
	
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public backendUser selectdevlogin(String devCode, String devPassword)
			throws Exception {
		backendUser user=backendUserMappers.selectdevlogin(devCode);
		return user;
	}
	/**
	 * ��ȡ����ƽ̨����app״̬��ʵ�ַ���
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<dataDictionary> selectDataDictionaryList(String typeCode) {
		List<dataDictionary> list=null;
		try {
			list=backendUserMappers.selectDataDictionaryList(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * ��ʾ�����ֵ�����
	 */
	@Override
	public List<dataDictionary> dataDictionaryList(String typeCode,Integer currentPageNo,
			int pageSize) {
		currentPageNo=(currentPageNo-1)*pageSize;
		try {
			return dictionaryMapper.dataDictionaryList(typeCode,currentPageNo,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ѯ�����ֵ����͵��ܼ�¼��
	 */
	@Override
	public int selectAppInfoCount(String typeCode) {
		return dictionaryMapper.selectAppInfoCount(typeCode);
	}
	/**
	 * ����id��ѯ��Ӧ���ֵ�����
	 */
	@Override
	public dataDictionary selectOne(String id) throws Exception {
		Integer did=Integer.parseInt(id);
		return dictionaryMapper.selectOne(did);
	}
	/**
	 * �ж�Ҫɾ���������Ƿ�ʹ��
	 */
	@Override
	public boolean selectid(String id) {
		if(dictionaryMapper.selectid(Integer.parseInt(id))>0){
			return false;
		}
		return true;
	}
	/**
	 * ɾ����Ӧ���ֵ�����
	 */
	@Override
	public boolean appsysdeldevuser(int parseInt) {
		if(dictionaryMapper.appsysdeldevuser(parseInt)>0){
			return true;
		}
		return false;
	}
	/**
	 * �����޸��ֵ�����
	 */
	@Override
	public int updateDictionary(dataDictionary dic) {
		return dictionaryMapper.updateDictionary(dic);
	}
	/**
	 * ��֤Ҫ�������ֵ������Ƿ��Ѿ�����
	 */
	@Override
	public int selectOnes(String tCode, String tName, String vId, String vName) {
		return dictionaryMapper.selectOnes( tCode,  tName,  vId,  vName);
	}
	/**
	 * �����ֵ�����
	 */
	@Override
	public int insertDictionary(dataDictionary dictionarys) {
		return dictionaryMapper.insertDictionary(dictionarys);
	}

}
