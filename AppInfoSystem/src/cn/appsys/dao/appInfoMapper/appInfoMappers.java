package cn.appsys.dao.appInfoMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appInfo;

public interface appInfoMappers {
	/**
	 * ��ȡ���ݵ�������
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param devId
	 * @return
	 */
	int selectAppInfoCount(@Param("softwareName")String querySoftwareName
			,@Param("status")String queryStatus
			,@Param("categoryLevel1")String queryCategoryLevel1
			,@Param("categoryLevel2")String queryCategoryLevel2
			,@Param("categoryLevel3")String queryCategoryLevel3
			,@Param("flatformId")String queryFlatformId
			,@Param("devId")Integer devId) throws Exception;
	/**
	 * ��ȡapk�汾��Ϣ�б�
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
	List<cn.appsys.pojo.appInfo> selectAppInfoList(@Param("softwareName")String querySoftwareName
			,@Param("status")String queryStatus
			,@Param("categoryLevel1")String queryCategoryLevel1
			,@Param("categoryLevel2")String queryCategoryLevel2
			,@Param("categoryLevel3")String queryCategoryLevel3
			,@Param("flatformId")String queryFlatformId
			,@Param("devId")Integer devId
			,@Param("currentPageNo")Integer currentPageNo
			,@Param("pageSize")int pageSize) throws Exception;
	
	/**
	 * �ж�APKName�Ƿ�Ψһ
	 * @param object
	 * @param aPKName
	 * @return
	 */
	public appInfo getAppInfo(@Param(value="id")Integer id,@Param(value="APKName")String APKName)throws Exception;
	/**
	 * ����APP������Ϣ����
	 * @param appInfos
	 * @return
	 */
	int add(cn.appsys.pojo.appInfo appInfos) throws Exception;
	/**
	 * ɾ��APP������Ϣ
	 * @param parseInt
	 * @return
	 */
	int deleteAppById(@Param("parseInt") int parseInt)throws Exception;
	/**
	 * ����id��ȡ��Ӧ����Ϣ
	 */
	appInfo selectappinfo(@Param("parseInt") int parseInt) throws Exception;
	/**
	 * �¼ܲ���
	 * @param _appInfo
	 * @throws Exception
	 */
	int modify(appInfo _appInfo)throws Exception ;
	/**
	 * ������APK�汾���޸����µİ汾��Ϣ��� 
	 * @param versionId
	 * @param appId
	 * @return
	 */
	int updateVersionId(@Param("versionId")Integer versionId,@Param("appId") Integer appId)throws Exception;
	/**
	 * ɾ��apk�汾�µ�logo
	 * @param parseInt
	 * @return
	 */
	int deleteAppLogo(@Param("parseInt")int parseInt);
	
	/**
	 * ��������
	 * @param status
	 * @param id
	 * @return
	 */
	int updateSatus(@Param("status")Integer status,@Param("id")Integer id);
	/**
	 * selectid(Integer id)
	 * ��ȡҪ�޸����ش����Ķ���
	 * @param id
	 * @return
	 */
	appInfo selectid(@Param("id")Integer id);
	/**
	 * ���ش�����һ
	 * @param id
	 * @param downloads
	 */
	void insertInfo(@Param("id")Integer id,@Param("downloads")Integer downloads);

}
