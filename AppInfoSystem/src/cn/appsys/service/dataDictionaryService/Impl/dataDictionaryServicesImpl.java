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
	 * 获取所属平台或者app状态的实现方法
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
	 * 显示所有字典类型
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
	 * 查询所有字典类型的总记录数
	 */
	@Override
	public int selectAppInfoCount(String typeCode) {
		return dictionaryMapper.selectAppInfoCount(typeCode);
	}
	/**
	 * 根据id查询对应的字典类型
	 */
	@Override
	public dataDictionary selectOne(String id) throws Exception {
		Integer did=Integer.parseInt(id);
		return dictionaryMapper.selectOne(did);
	}
	/**
	 * 判断要删除的类型是否被使用
	 */
	@Override
	public boolean selectid(String id) {
		if(dictionaryMapper.selectid(Integer.parseInt(id))>0){
			return false;
		}
		return true;
	}
	/**
	 * 删除对应的字典类型
	 */
	@Override
	public boolean appsysdeldevuser(int parseInt) {
		if(dictionaryMapper.appsysdeldevuser(parseInt)>0){
			return true;
		}
		return false;
	}
	/**
	 * 保存修改字典类型
	 */
	@Override
	public int updateDictionary(dataDictionary dic) {
		return dictionaryMapper.updateDictionary(dic);
	}
	/**
	 * 验证要新增的字典类型是否已经存在
	 */
	@Override
	public int selectOnes(String tCode, String tName, String vId, String vName) {
		return dictionaryMapper.selectOnes( tCode,  tName,  vId,  vName);
	}
	/**
	 * 新增字典类型
	 */
	@Override
	public int insertDictionary(dataDictionary dictionarys) {
		return dictionaryMapper.insertDictionary(dictionarys);
	}

}
