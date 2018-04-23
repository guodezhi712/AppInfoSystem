package cn.appsys.service.dataDictionaryService;

import java.util.List;

import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.dataDictionary;

public interface dataDictionaryServices {
	/**
	 * 后台登录验证业务
	 */
	backendUser selectdevlogin(String devCode,String devPassword) throws Exception;
	/**
	 * 获取所属平台或者app状态
	 * @param typeCode
	 * @return
	 */
	List<dataDictionary> selectDataDictionaryList(String typeCode) throws Exception;
	/**
	 * 显示所有字典类型
	 * @param typeCode 
	 * @param pageSize 
	 * @param currentPageNo 
	 * @return
	 */
	List<dataDictionary> dataDictionaryList(String typeCode, Integer currentPageNo, int pageSize);
	/**
	 * 查询所有字典类型的总记录数
	 * @param typeCode 
	 * @return
	 */
	int selectAppInfoCount(String typeCode);
	/**
	 * 字典类型查询页面跳转
	 * @param id
	 * @return
	 */
	dataDictionary selectOne(String id) throws Exception;
	/**
	 * 判断要删除的类型是否被使用
	 * @param id
	 * @return
	 */
	boolean selectid(String id);
	/**
	 * 删除对应的字典类型
	 * @param parseInt
	 * @return
	 */
	boolean appsysdeldevuser(int parseInt);
	/**
	 * 保存修改字典类型
	 * @param dic
	 * @return
	 */
	int updateDictionary(dataDictionary dic);
	/**
	 * 验证要新增的字典类型是否已经存在
	 * @param tCode
	 * @param tName
	 * @param vId
	 * @param vName
	 * @return
	 */
	int selectOnes(String tCode, String tName, String vId, String vName);
	/**
	 * 新增字典类型
	 * @param dictionarys
	 * @return
	 */
	int insertDictionary(dataDictionary dictionarys);
}
