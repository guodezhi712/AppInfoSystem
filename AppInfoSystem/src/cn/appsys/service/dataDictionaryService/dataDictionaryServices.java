package cn.appsys.service.dataDictionaryService;

import java.util.List;

import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.dataDictionary;

public interface dataDictionaryServices {
	/**
	 * ��̨��¼��֤ҵ��
	 */
	backendUser selectdevlogin(String devCode,String devPassword) throws Exception;
	/**
	 * ��ȡ����ƽ̨����app״̬
	 * @param typeCode
	 * @return
	 */
	List<dataDictionary> selectDataDictionaryList(String typeCode) throws Exception;
	/**
	 * ��ʾ�����ֵ�����
	 * @param typeCode 
	 * @param pageSize 
	 * @param currentPageNo 
	 * @return
	 */
	List<dataDictionary> dataDictionaryList(String typeCode, Integer currentPageNo, int pageSize);
	/**
	 * ��ѯ�����ֵ����͵��ܼ�¼��
	 * @param typeCode 
	 * @return
	 */
	int selectAppInfoCount(String typeCode);
	/**
	 * �ֵ����Ͳ�ѯҳ����ת
	 * @param id
	 * @return
	 */
	dataDictionary selectOne(String id) throws Exception;
	/**
	 * �ж�Ҫɾ���������Ƿ�ʹ��
	 * @param id
	 * @return
	 */
	boolean selectid(String id);
	/**
	 * ɾ����Ӧ���ֵ�����
	 * @param parseInt
	 * @return
	 */
	boolean appsysdeldevuser(int parseInt);
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
	int selectOnes(String tCode, String tName, String vId, String vName);
	/**
	 * �����ֵ�����
	 * @param dictionarys
	 * @return
	 */
	int insertDictionary(dataDictionary dictionarys);
}
