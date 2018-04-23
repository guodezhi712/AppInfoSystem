package cn.appsys.dao.devUserMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.devUser;

public interface loginMapper {
	/**
	 * 开发者登录验证方法
	 */
	public devUser selectdevlogin(@Param("devCode") String devCode) throws Exception;
	/**
	 * 开发者注册方法
	 * @param devUser
	 * @return
	 * @throws Exception
	 */
	public int insertregister(devUser devUser)throws Exception;
	/**
	 * 查询开发者的总人数
	 * @param devName
	 * @return
	 */
	public int selectAppInfoCount(@Param("devName")String devName);
	/**
	 * 查询所有开发者信息
	 * @param devName
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public List<devUser> selectAppInfoList(@Param("devName")String devName,
			@Param("currentPageNo")Integer currentPageNo,@Param("pageSize") int pageSize);
	/**
	 * 删除APK开发者账号
	 * @param parseInt
	 * @return
	 */
	public int appsysdeldevuser(@Param("parseInt")int parseInt);
	/**
	 * 查询对应的开发者信息
	 * @param parseInt
	 * @return
	 */
	public devUser selectApp(@Param("id")int parseInt);
	/**
	 * 保存修改apk开发者账户的信息
	 * @param devuser
	 * @return
	 */
	public int updatedevUser(devUser devuser);
	
}
