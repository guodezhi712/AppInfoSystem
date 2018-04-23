package cn.appsys.service.backendUserService;

import java.util.List;

import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.backendUser;

public interface backendUserServices {
	/**
	 * 验证后台用户的唯一性
	 * @param code
	 * @return
	 */
	boolean selectcodeid(String code);
	/**
	 * 新增后台用户
	 * @param backendUser
	 * @return
	 */
	int insertregister(cn.appsys.pojo.backendUser backendUser);
	/**
	 * 获取后台账号信息总记录数
	 * @param userCode
	 * @param userType
	 * @return
	 */
	int selectAppInfoCount(String userCode, String userType);
	/**
	 * 获取后台账号信息列表
	 * @param userCode
	 * @param userType
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	List<backendUser> selectAppInfoList(String userCode, String userType,
			Integer currentPageNo, int pageSize,Integer id);
	/**
	 * 根据查询账号信息
	 * @param parseInt
	 * @return
	 */
	backendUser selectApp(int parseInt);
	/**
	 * 根据id删除用户账号
	 * @param parseInt
	 * @return
	 */
	boolean appsysdeleteAppById(int parseInt);
	/**
	 * 修改用户账号信息
	 * @param backenduser
	 * @return
	 */
	boolean updateuser(backendUser backenduser);

}
