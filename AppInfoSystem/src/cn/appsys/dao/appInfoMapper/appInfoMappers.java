package cn.appsys.dao.appInfoMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appInfo;

public interface appInfoMappers {
	/**
	 * 获取数据的总条数
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
	 * 获取apk版本信息列表
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
	 * 判断APKName是否唯一
	 * @param object
	 * @param aPKName
	 * @return
	 */
	public appInfo getAppInfo(@Param(value="id")Integer id,@Param(value="APKName")String APKName)throws Exception;
	/**
	 * 新增APP基础信息方法
	 * @param appInfos
	 * @return
	 */
	int add(cn.appsys.pojo.appInfo appInfos) throws Exception;
	/**
	 * 删除APP基础信息
	 * @param parseInt
	 * @return
	 */
	int deleteAppById(@Param("parseInt") int parseInt)throws Exception;
	/**
	 * 根据id获取对应的信息
	 */
	appInfo selectappinfo(@Param("parseInt") int parseInt) throws Exception;
	/**
	 * 下架操作
	 * @param _appInfo
	 * @throws Exception
	 */
	int modify(appInfo _appInfo)throws Exception ;
	/**
	 * 在新增APK版本后，修改最新的版本信息编号 
	 * @param versionId
	 * @param appId
	 * @return
	 */
	int updateVersionId(@Param("versionId")Integer versionId,@Param("appId") Integer appId)throws Exception;
	/**
	 * 删除apk版本新的logo
	 * @param parseInt
	 * @return
	 */
	int deleteAppLogo(@Param("parseInt")int parseInt);
	
	/**
	 * 审批操作
	 * @param status
	 * @param id
	 * @return
	 */
	int updateSatus(@Param("status")Integer status,@Param("id")Integer id);
	/**
	 * selectid(Integer id)
	 * 获取要修改下载次数的对象
	 * @param id
	 * @return
	 */
	appInfo selectid(@Param("id")Integer id);
	/**
	 * 下载次数加一
	 * @param id
	 * @param downloads
	 */
	void insertInfo(@Param("id")Integer id,@Param("downloads")Integer downloads);

}
