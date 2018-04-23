package cn.appsys.service.devUserService;

import java.util.List;

import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.devUser;




public interface devUserServices {
	/**
	 * 开发者登录验证业务
	 */
	devUser selectdevlogin(String devCode,String devPassword) throws Exception;
	/**
	 * 验证开发者账号的唯一性
	 * @param code
	 * @return
	 */
	boolean selectcodeid(String code);
	/**
	 * 开发者注册业务
	 * @param devUser
	 * @return
	 */
	int insertregister(devUser devUser);
	/**
	 * 查询开发者的总人数
	 * @param devName
	 * @return
	 */
	int selectAppInfoCount(String devName);
	/**
	 * 查询当前管理员能够查看的app开发者的信息
	 * @param devName
	 * @param currentPageNo
	 * @param pageSize
	 * @param object
	 * @return
	 */
	List<cn.appsys.pojo.devUser> selectAppInfoList(String devName,
			Integer currentPageNo, int pageSize);
	/**
	 * 删除APK开发者账号
	 * @param parseInt
	 * @return
	 */
	boolean appsysdeldevuser(int parseInt);
	/**
	 * 查询对应的开发者信息
	 * @param parseInt
	 * @return
	 */
	devUser selectApp(int parseInt);
	/**
	 * 保存修改apk开发者账户的信息
	 * @param devuser
	 * @return
	 */
	boolean updatedevUser(cn.appsys.pojo.devUser devuser);
}
