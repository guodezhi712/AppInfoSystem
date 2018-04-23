package cn.appsys.service.devUserService;

import java.util.List;

import cn.appsys.pojo.backendUser;
import cn.appsys.pojo.devUser;




public interface devUserServices {
	/**
	 * �����ߵ�¼��֤ҵ��
	 */
	devUser selectdevlogin(String devCode,String devPassword) throws Exception;
	/**
	 * ��֤�������˺ŵ�Ψһ��
	 * @param code
	 * @return
	 */
	boolean selectcodeid(String code);
	/**
	 * ������ע��ҵ��
	 * @param devUser
	 * @return
	 */
	int insertregister(devUser devUser);
	/**
	 * ��ѯ�����ߵ�������
	 * @param devName
	 * @return
	 */
	int selectAppInfoCount(String devName);
	/**
	 * ��ѯ��ǰ����Ա�ܹ��鿴��app�����ߵ���Ϣ
	 * @param devName
	 * @param currentPageNo
	 * @param pageSize
	 * @param object
	 * @return
	 */
	List<cn.appsys.pojo.devUser> selectAppInfoList(String devName,
			Integer currentPageNo, int pageSize);
	/**
	 * ɾ��APK�������˺�
	 * @param parseInt
	 * @return
	 */
	boolean appsysdeldevuser(int parseInt);
	/**
	 * ��ѯ��Ӧ�Ŀ�������Ϣ
	 * @param parseInt
	 * @return
	 */
	devUser selectApp(int parseInt);
	/**
	 * �����޸�apk�������˻�����Ϣ
	 * @param devuser
	 * @return
	 */
	boolean updatedevUser(cn.appsys.pojo.devUser devuser);
}
