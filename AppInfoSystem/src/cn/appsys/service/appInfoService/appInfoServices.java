package cn.appsys.service.appInfoService;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appInfo;

public interface appInfoServices  {
	/**
	 * ��ȡ���ݵ�������ҵ��
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param devId
	 * @return
	 */
	int selectAppInfoCount(String querySoftwareName, String queryStatus,
			String queryCategoryLevel1, String queryCategoryLevel2,
			String queryCategoryLevel3, String queryFlatformId, Integer devId)throws Exception;
	/**
	 * ��ȡapk�汾��Ϣ�б�ҵ��
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param devId
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	List<appInfo> selectAppInfoList(String querySoftwareName,
			String queryStatus, String queryCategoryLevel1,
			String queryCategoryLevel2, String queryCategoryLevel3,
			String queryFlatformId, Integer devId, Integer currentPageNo,
			int pageSize) throws Exception;
	/**
	 * ������Ϣҵ��
	 * @param appInfo
	 * @return
	 */
	boolean add(appInfo appInfo) throws Exception;
	
	/**
	 * �ж�APKName�Ƿ�Ψһ
	 * @param object
	 * @param aPKName
	 * @return
	 */
	cn.appsys.pojo.appInfo getAppInfo(Integer id, String aPKName) throws Exception;
	/**
	 * ɾ��APP������Ϣҵ��
	 * @param parseInt
	 * @return
	 */
	boolean appsysdeleteAppById(int parseInt) throws Exception;
	/**
	 * ����id��ѯ��Ӧ����Ϣҵ��
	 * @param id
	 * @return
	 */
	appInfo selectApp(Integer id) throws Exception;
	/**
	 * ���¼ܲ���ҵ��
	 * @param appInfo
	 * @return
	 */
	boolean appsysUpdateSaleStatusByAppId(appInfo appInfo);
	/**
	 * ɾ���������洢�������ļ�ҵ��
	 * @param parseInt
	 * @return
	 */
	boolean deleteAppLogo(int parseInt);
	/**
	 * �޸�apk������Ϣҵ��
	 * @param appInfo
	 * @return
	 */
	boolean modify(appInfo appInfo);
	/**
	 * ��������ҵ��
	 * @param status
	 * @param id
	 * @return
	 */
	boolean updateSatus(Integer status, Integer id);
	/**
	 * ��ȡ��Ӧ�Ķ���
	 * @param id
	 * @return
	 */
	appInfo selectid(Integer id);
	/**
	 * ���ش�����һ
	 * @param id
	 * @param downloads
	 */
	void insertInfo(Integer id, Integer downloads);
	
}
