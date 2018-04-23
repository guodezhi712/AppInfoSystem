package cn.appsys.dao.devUserMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.devUser;

public interface loginMapper {
	/**
	 * �����ߵ�¼��֤����
	 */
	public devUser selectdevlogin(@Param("devCode") String devCode) throws Exception;
	/**
	 * ������ע�᷽��
	 * @param devUser
	 * @return
	 * @throws Exception
	 */
	public int insertregister(devUser devUser)throws Exception;
	/**
	 * ��ѯ�����ߵ�������
	 * @param devName
	 * @return
	 */
	public int selectAppInfoCount(@Param("devName")String devName);
	/**
	 * ��ѯ���п�������Ϣ
	 * @param devName
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public List<devUser> selectAppInfoList(@Param("devName")String devName,
			@Param("currentPageNo")Integer currentPageNo,@Param("pageSize") int pageSize);
	/**
	 * ɾ��APK�������˺�
	 * @param parseInt
	 * @return
	 */
	public int appsysdeldevuser(@Param("parseInt")int parseInt);
	/**
	 * ��ѯ��Ӧ�Ŀ�������Ϣ
	 * @param parseInt
	 * @return
	 */
	public devUser selectApp(@Param("id")int parseInt);
	/**
	 * �����޸�apk�������˻�����Ϣ
	 * @param devuser
	 * @return
	 */
	public int updatedevUser(devUser devuser);
	
}
