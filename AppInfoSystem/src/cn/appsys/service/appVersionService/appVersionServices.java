package cn.appsys.service.appVersionService;

import java.util.List;

import cn.appsys.pojo.appVersion;

public interface appVersionServices {
	/**
	 * ��ȡAPP�汾����ʷ��¼ҵ��
	 * @param parseInt
	 * @return
	 */
	List<appVersion> getAppVersionList(int parseInt) throws Exception;
	/**
	 * ����APK�汾��Ϣҵ��
	 * @param appVersion
	 * @return
	 */
	boolean appsysadd(appVersion appVersion) throws Exception;
	/**
	 * ��ȡ���°汾APKҵ��
	 * @param parseInt
	 * @return
	 */
	cn.appsys.pojo.appVersion getAppVersionById(int parseInt) throws Exception;
	/**
	 * �����޸ĺ��apk�汾��Ϣҵ��
	 * @param appVersion
	 * @return
	 */
	boolean modify(appVersion appVersion) throws Exception;
	/**
	 * ɾ���������ϵ�apk�汾�ļ���ŵ�·��
	 * @param parseInt
	 * @return
	 */
	boolean deleteApkFile(int parseInt);
	/**
	 * ����id��ȡ��Ӧ�İ汾��Ϣ
	 * @param parseInt
	 * @return
	 */
	List<appVersion> selectversion(int parseInt);
	
	/**
	 * ɾ����Ӧ�İ汾��Ϣ
	 * @param id
	 */
	void delversion(Integer id);
	/**
	 * ��������·����ȡ��Ӧ��info���е�id
	 * @param images
	 * @return
	 */
	String selectid(String images);

}
