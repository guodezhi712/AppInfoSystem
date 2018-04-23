package cn.appsys.service.appInfoService.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sound.midi.MidiDevice.Info;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appInfoMapper.appInfoMappers;
import cn.appsys.dao.appVersionMapper.appVersionMappers;
import cn.appsys.pojo.appInfo;
import cn.appsys.pojo.appVersion;
import cn.appsys.service.appInfoService.appInfoServices;

@Service("appInfoService")
@Transactional
public class appInfoServicesImpl implements appInfoServices {
	@Resource
	private appInfoMappers info;
	@Resource
	private appVersionMappers appVersionMapper;
	/**
	 * 获取数据的总条数业务的实现方法
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public int selectAppInfoCount(String querySoftwareName, String queryStatus,
			String queryCategoryLevel1, String queryCategoryLevel2,
			String queryCategoryLevel3, String queryFlatformId, Integer devId)
			throws Exception {
		int count=info.selectAppInfoCount(querySoftwareName,queryStatus,queryCategoryLevel1
								,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId
								,devId);
		return count;
	}
	/**
	 * 获取apk版本信息列表业务的实现方法
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<cn.appsys.pojo.appInfo> selectAppInfoList(
			String querySoftwareName, String queryStatus,
			String queryCategoryLevel1, String queryCategoryLevel2,
			String queryCategoryLevel3, String queryFlatformId, Integer devId,
			Integer currentPageNo, int pageSize) {
		List<cn.appsys.pojo.appInfo> list=null;
		try {
			currentPageNo=(currentPageNo-1)*pageSize;
			list = info.selectAppInfoList(
					querySoftwareName,queryStatus,queryCategoryLevel1,queryCategoryLevel2
					,queryCategoryLevel3,queryFlatformId,devId
					,currentPageNo,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 新增信息业务的实现方法
	 */
	@Override
	public boolean add(cn.appsys.pojo.appInfo appInfos) {
		int count=0;
		try {
			count = info.add(appInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean flag=false;
		if(count==1){
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 判断APKName是否唯一
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public cn.appsys.pojo.appInfo getAppInfo(Integer id, String aPKName)
			throws Exception {
		cn.appsys.pojo.appInfo appInfos=info.getAppInfo(id,aPKName);
		return appInfos;
	}
	/**
	 * 删除APP基础信息的实现方法
	 */
	@Override
	public boolean appsysdeleteAppById(int parseInt) throws Exception {
		int count=info.deleteAppById(parseInt);
		boolean flag=false;
		if(count==1){
			flag=true;
		}
		return flag;
	}
	/**
	 * 根据id查询对应的信息
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public cn.appsys.pojo.appInfo selectApp(Integer id) {
		cn.appsys.pojo.appInfo smAppInfo=null;
		try {
			smAppInfo = info.selectappinfo(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smAppInfo;
	}
	/**
	 * 上下架操作业务的实现方法
	 */
	@Override
	public boolean appsysUpdateSaleStatusByAppId(cn.appsys.pojo.appInfo appInfos) {
		Integer id=appInfos.getId();
		appInfo appInfo=null;
		try {
			appInfo = info.getAppInfo(id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer operator = appInfos.getModifyBy();
		if(null ==appInfo){
			return false;
		}else{
			switch (appInfo.getStatus()) {
				case 2: //当状态为审核通过时，可以进行上架操作
				try {
					onSale(appInfo,operator,4,2);
				} catch (Exception e) {
					e.printStackTrace();
				}
					break;
				case 5://当状态为下架时，可以进行上架操作
				try {
					onSale(appInfo,operator,4,2);
				} catch (Exception e) {
					e.printStackTrace();
				}
					break;
				case 4://当状态为上架时，可以进行下架操作
				try {
					offSale(appInfo,operator,5);
				} catch (Exception e) {
					e.printStackTrace();
				}
					break;

			default:
				return false;
			}
		}
		return true;
	}

	/**
	 * on Sale
	 * @param appInfo
	 * @param operator
	 * @param appInfStatus
	 * @param versionStatus
	 * @throws Exception
	 */
	private void onSale(appInfo appInfo,Integer operator,Integer appInfStatus,Integer versionStatus) throws Exception{
		offSale(appInfo,operator,appInfStatus);
		setSaleSwitchToAppVersion(appInfo,operator,versionStatus);
	}
	
	
	/**
	 * offSale上架
	 * @param appInfo
	 * @param operator
	 * @param appInfStatus
	 * @return
	 * @throws Exception
	 */
	private boolean offSale(appInfo appInfo,Integer operator,Integer appInfStatus) throws Exception{
		appInfo _appInfo = new appInfo();
		_appInfo.setId(appInfo.getId());
		_appInfo.setStatus(appInfStatus);
		_appInfo.setModifyBy(operator);
		_appInfo.setOffSaleDate(new Date(System.currentTimeMillis()));
		int count=info.modify(_appInfo);
		if(count!=1){
			return false;
		}
		return true;
	}
	
	/**
	 * 下架
	 * set sale method to on or off
	 * @param appInfo
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	private boolean setSaleSwitchToAppVersion(appInfo appInfo,Integer operator,Integer saleStatus) throws Exception{
		appVersion appVersion = new appVersion();
		appVersion.setId(appInfo.getVersionId());
		appVersion.setPublishStatus(saleStatus);
		appVersion.setModifyBy(operator);
		appVersion.setModifyDate(new Date());
		appVersionMapper.modify(appVersion);
		return false;
	}
	/**
	 * 删除服务器存储的物理文件的实现方法
	 */
	@Override
	public boolean deleteAppLogo(int parseInt) {
		try {
			if(info.deleteAppLogo(parseInt)>0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 修改apk基础信息的实现方法
	 */
	@Override
	public boolean modify(appInfo appInfo) {
		try {
			if(info.modify(appInfo)>0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 *  审批操作业务实现方法
	 */
	@Override
	public boolean updateSatus(Integer status, Integer id) {
		if(info.updateSatus(status,id)>0){
			return true;
		}
		return false;
	}
	/**
	 * 获取对应的对象
	 */
	@Override
	public appInfo selectid(Integer id) {
		return info.selectid(id);
	}
	/**
	 * 下载次数加一
	 */
	@Override
	public void insertInfo(Integer id, Integer downloads) {
		info.insertInfo( id,  downloads);
	}
	
}
