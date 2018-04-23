package cn.appsys.service.appVersionService;

import java.util.List;

import cn.appsys.pojo.appVersion;

public interface appVersionServices {
	/**
	 * 获取APP版本的历史记录业务
	 * @param parseInt
	 * @return
	 */
	List<appVersion> getAppVersionList(int parseInt) throws Exception;
	/**
	 * 新增APK版本信息业务
	 * @param appVersion
	 * @return
	 */
	boolean appsysadd(appVersion appVersion) throws Exception;
	/**
	 * 获取最新版本APK业务
	 * @param parseInt
	 * @return
	 */
	cn.appsys.pojo.appVersion getAppVersionById(int parseInt) throws Exception;
	/**
	 * 保存修改后的apk版本信息业务
	 * @param appVersion
	 * @return
	 */
	boolean modify(appVersion appVersion) throws Exception;
	/**
	 * 删除服务器上的apk版本文件存放的路径
	 * @param parseInt
	 * @return
	 */
	boolean deleteApkFile(int parseInt);
	/**
	 * 根据id获取对应的版本信息
	 * @param parseInt
	 * @return
	 */
	List<appVersion> selectversion(int parseInt);
	
	/**
	 * 删除对应的版本信息
	 * @param id
	 */
	void delversion(Integer id);
	/**
	 * 根据下载路径获取对应的info表中的id
	 * @param images
	 * @return
	 */
	String selectid(String images);

}
