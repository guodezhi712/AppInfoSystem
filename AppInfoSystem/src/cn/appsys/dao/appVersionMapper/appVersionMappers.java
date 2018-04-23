package cn.appsys.dao.appVersionMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.appVersion;

public interface appVersionMappers {
	/**
	 * ��ȡAPP�汾����ʷ��¼
	 * @param parseInt
	 * @return
	 */
	List<appVersion> selectAppVersion(@Param("parseInt")int parseInt);
	/**
	 * �޸İ汾״̬��Ϣ
	 * @param appVersion
	 */
	int modify(appVersion s);
	/**
	 * ����APK�汾��Ϣ
	 * @param appVersion
	 * @return
	 */
	int insertversion(appVersion appVersion);
	/**
	 * ��ȡ���°汾APK��Ϣ
	 * @param parseInt
	 * @return
	 */
	appVersion getAppVersionList(@Param("parseInt")int parseInt);
	/**
	 * ɾ���������洢�������ļ�(��version���ϵ�apk�ļ����·����ɾ��)
	 * @param parseInt
	 * @return
	 */
	int deleteApkFile(@Param("parseInt")int parseInt);
	/**
	 * ����id��ȡ��Ӧ�İ汾��Ϣ
	 * @param parseInt
	 * @return
	 */
	List<appVersion> selectversion(@Param("parseInt")int parseInt);
	/**
	 * ɾ����Ӧ�İ汾��Ϣ
	 * @param id
	 */
	void delversion(@Param("id")Integer id);
	/**
	 * ��������·����ȡ��Ӧ��info���е�id
	 * @param images
	 * @return
	 */
	String selectid(@Param("images")String images);

}
