package cn.appsys.dao.dataDictionaryMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.dataDictionary;

public interface dictionaryMapper {
	/**
	 * 显示所有字典类型
	 * @param pageSize 
	 * @param currentPageNo 
	 * @return
	 */
	List<dataDictionary> dataDictionaryList(
				@Param("typeCode")String typeCode,
				@Param("currentPageNo")Integer currentPageNo,
				@Param("pageSize")int pageSize) throws Exception;
	/**
	 * 查询所有字典类型的总记录数
	 * @return
	 */
	int selectAppInfoCount(@Param("typeCode")String typeCode);
	/**
	 * 根据id查询对应的字典类型
	 * @param did
	 * @return
	 */
	dataDictionary selectOne(@Param("id")Integer did);
	/**
	 * 判断要删除的类型是否被使用
	 * @param parseInt
	 * @return
	 */
	int selectid(@Param("parseInt")int parseInt);
	/**
	 * 删除对应的字典类型
	 * @param parseInt
	 * @return
	 */
	int appsysdeldevuser(@Param("parseInt")int parseInt);
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
	int selectOnes(@Param("tCode")String tCode,
			@Param("tName")String tName, 
			@Param("vId")String vId, 
			@Param("vName")String vName);
	/**
	 * 新增字典类型
	 * @param dictionarys
	 * @return
	 */
	int insertDictionary(dataDictionary dictionarys);

}
