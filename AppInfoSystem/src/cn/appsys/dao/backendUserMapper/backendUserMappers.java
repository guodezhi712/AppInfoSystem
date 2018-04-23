package cn.appsys.dao.backendUserMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.dataDictionary;

public interface backendUserMappers {
	/**
	 * 后台登录验证方法
	 */
	public backendUser selectdevlogin(@Param("devCode") String devCode) throws Exception;
	/**
	 * 获取所属平台或者app状态
	 * @param typeCode
	 * @return
	 */
	public List<dataDictionary> selectDataDictionaryList(@Param("typeCode")String typeCode) throws Exception;
	/**
	 * 验证唯一性
	 * @param code
	 * @return
	 */
	public backendUser selectcodeid(@Param("code")String code);
	/**
	 * 新增后台用户
	 * @param backendUser
	 * @return
	 */
	public int add(backendUser backendUser);
	/**
	 * 获取后台账号信息总记录数
	 * @param userCode
	 * @param userType
	 * @return
	 */
	public int selectUserCountList(@Param("userCode")String userCode,@Param("userType") String userType);
	/**
	 * 获取后台账号信息列表
	 * @param userCode
	 * @param userType
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public List<backendUser> selectAppInfoList(@Param("userCode")String userCode,
							@Param("userType")String userType,
							@Param("currentPageNo")Integer currentPageNo, 
							@Param("pageSize")int pageSize,
							@Param("id")Integer id);
	/**
	 * 根据id查询账号信息
	 * @param parseInt
	 * @return
	 */
	public backendUser selectApp(@Param("parseInt")int parseInt);
	
	/**
	 * 根据id删除用户账号
	 * @param parseInt
	 * @return
	 */
	public int appsysdeleteAppById(@Param("parseInt")int parseInt);
	/**
	 * 修改后台账号信息
	 * @param backenduser
	 * @return
	 */
	public int updateuser(backendUser backenduser);
}
