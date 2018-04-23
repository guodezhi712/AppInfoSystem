package cn.appsys.dao.dataDictionaryMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.dataDictionary;

public interface dictionaryMapper {
	/**
	 * ��ʾ�����ֵ�����
	 * @param pageSize 
	 * @param currentPageNo 
	 * @return
	 */
	List<dataDictionary> dataDictionaryList(
				@Param("typeCode")String typeCode,
				@Param("currentPageNo")Integer currentPageNo,
				@Param("pageSize")int pageSize) throws Exception;
	/**
	 * ��ѯ�����ֵ����͵��ܼ�¼��
	 * @return
	 */
	int selectAppInfoCount(@Param("typeCode")String typeCode);
	/**
	 * ����id��ѯ��Ӧ���ֵ�����
	 * @param did
	 * @return
	 */
	dataDictionary selectOne(@Param("id")Integer did);
	/**
	 * �ж�Ҫɾ���������Ƿ�ʹ��
	 * @param parseInt
	 * @return
	 */
	int selectid(@Param("parseInt")int parseInt);
	/**
	 * ɾ����Ӧ���ֵ�����
	 * @param parseInt
	 * @return
	 */
	int appsysdeldevuser(@Param("parseInt")int parseInt);
	/**
	 * �����޸��ֵ�����
	 * @param dic
	 * @return
	 */
	int updateDictionary(dataDictionary dic);
	/**
	 * ��֤Ҫ�������ֵ������Ƿ��Ѿ�����
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
	 * �����ֵ�����
	 * @param dictionarys
	 * @return
	 */
	int insertDictionary(dataDictionary dictionarys);

}
