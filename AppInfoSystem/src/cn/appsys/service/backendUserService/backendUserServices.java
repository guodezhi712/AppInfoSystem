package cn.appsys.service.backendUserService;

import java.util.List;

import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.backendUser;

public interface backendUserServices {
	/**
	 * ��֤��̨�û���Ψһ��
	 * @param code
	 * @return
	 */
	boolean selectcodeid(String code);
	/**
	 * ������̨�û�
	 * @param backendUser
	 * @return
	 */
	int insertregister(cn.appsys.pojo.backendUser backendUser);
	/**
	 * ��ȡ��̨�˺���Ϣ�ܼ�¼��
	 * @param userCode
	 * @param userType
	 * @return
	 */
	int selectAppInfoCount(String userCode, String userType);
	/**
	 * ��ȡ��̨�˺���Ϣ�б�
	 * @param userCode
	 * @param userType
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	List<backendUser> selectAppInfoList(String userCode, String userType,
			Integer currentPageNo, int pageSize,Integer id);
	/**
	 * ���ݲ�ѯ�˺���Ϣ
	 * @param parseInt
	 * @return
	 */
	backendUser selectApp(int parseInt);
	/**
	 * ����idɾ���û��˺�
	 * @param parseInt
	 * @return
	 */
	boolean appsysdeleteAppById(int parseInt);
	/**
	 * �޸��û��˺���Ϣ
	 * @param backenduser
	 * @return
	 */
	boolean updateuser(backendUser backenduser);

}
