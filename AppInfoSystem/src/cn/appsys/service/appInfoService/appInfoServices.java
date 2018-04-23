package cn.appsys.service.appInfoService;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appInfo;

public interface appInfoServices  {
	/**
	 * 获取数据的总条数业务
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
	 * 获取apk版本信息列表业务
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
	 * 新增信息业务
	 * @param appInfo
	 * @return
	 */
	boolean add(appInfo appInfo) throws Exception;
	
	/**
	 * 判断APKName是否唯一
	 * @param object
	 * @param aPKName
	 * @return
	 */
	cn.appsys.pojo.appInfo getAppInfo(Integer id, String aPKName) throws Exception;
	/**
	 * 删除APP基础信息业务
	 * @param parseInt
	 * @return
	 */
	boolean appsysdeleteAppById(int parseInt) throws Exception;
	/**
	 * 根据id查询对应的信息业务
	 * @param id
	 * @return
	 */
	appInfo selectApp(Integer id) throws Exception;
	/**
	 * 上下架操作业务
	 * @param appInfo
	 * @return
	 */
	boolean appsysUpdateSaleStatusByAppId(appInfo appInfo);
	/**
	 * 删除服务器存储的物理文件业务
	 * @param parseInt
	 * @return
	 */
	boolean deleteAppLogo(int parseInt);
	/**
	 * 修改apk基础信息业务
	 * @param appInfo
	 * @return
	 */
	boolean modify(appInfo appInfo);
	/**
	 * 审批操作业务
	 * @param status
	 * @param id
	 * @return
	 */
	boolean updateSatus(Integer status, Integer id);
	/**
	 * 获取对应的对象
	 * @param id
	 * @return
	 */
	appInfo selectid(Integer id);
	/**
	 * 下载次数加一
	 * @param id
	 * @param downloads
	 */
	void insertInfo(Integer id, Integer downloads);
	
}
