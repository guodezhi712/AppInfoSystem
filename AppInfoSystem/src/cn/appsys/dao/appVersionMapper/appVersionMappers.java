package cn.appsys.dao.appVersionMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appVersion;

public interface appVersionMappers {
	/**
	 * 获取APP版本的历史记录
	 * @param parseInt
	 * @return
	 */
	List<appVersion> selectAppVersion(@Param("parseInt")int parseInt);
	/**
	 * 修改版本状态信息
	 * @param appVersion
	 */
	int modify(appVersion s);
	/**
	 * 新增APK版本信息
	 * @param appVersion
	 * @return
	 */
	int insertversion(appVersion appVersion);
	/**
	 * 获取最新版本APK信息
	 * @param parseInt
	 * @return
	 */
	appVersion getAppVersionList(@Param("parseInt")int parseInt);
	/**
	 * 删除服务器存储的物理文件(把version表上的apk文件存放路径给删除)
	 * @param parseInt
	 * @return
	 */
	int deleteApkFile(@Param("parseInt")int parseInt);
	/**
	 * 根据id获取对应的版本信息
	 * @param parseInt
	 * @return
	 */
	List<appVersion> selectversion(@Param("parseInt")int parseInt);
	/**
	 * 删除对应的版本信息
	 * @param id
	 */
	void delversion(@Param("id")Integer id);
	/**
	 * 根据下载路径获取对应的info表中的id
	 * @param images
	 * @return
	 */
	String selectid(@Param("images")String images);

}
