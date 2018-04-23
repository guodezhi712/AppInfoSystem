package cn.appsys.dao.backendUserMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.dataDictionary;

public interface backendUserMappers {
	/**
	 * ��̨��¼��֤����
	 */
	public backendUser selectdevlogin(@Param("devCode") String devCode) throws Exception;
	/**
	 * ��ȡ����ƽ̨����app״̬
	 * @param typeCode
	 * @return
	 */
	public List<dataDictionary> selectDataDictionaryList(@Param("typeCode")String typeCode) throws Exception;
	/**
	 * ��֤Ψһ��
	 * @param code
	 * @return
	 */
	public backendUser selectcodeid(@Param("code")String code);
	/**
	 * ������̨�û�
	 * @param backendUser
	 * @return
	 */
	public int add(backendUser backendUser);
	/**
	 * ��ȡ��̨�˺���Ϣ�ܼ�¼��
	 * @param userCode
	 * @param userType
	 * @return
	 */
	public int selectUserCountList(@Param("userCode")String userCode,@Param("userType") String userType);
	/**
	 * ��ȡ��̨�˺���Ϣ�б�
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
	 * ����id��ѯ�˺���Ϣ
	 * @param parseInt
	 * @return
	 */
	public backendUser selectApp(@Param("parseInt")int parseInt);
	
	/**
	 * ����idɾ���û��˺�
	 * @param parseInt
	 * @return
	 */
	public int appsysdeleteAppById(@Param("parseInt")int parseInt);
	/**
	 * �޸ĺ�̨�˺���Ϣ
	 * @param backenduser
	 * @return
	 */
	public int updateuser(backendUser backenduser);
}
