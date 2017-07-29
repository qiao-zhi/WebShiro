package cn.qlq.springmvc.mapper;

import java.util.List;

import cn.qlq.springmvc.pojo.SysPermission;


public interface SysPermissionMapperCustom {
	
	//根据用户id查询菜单
	public List<SysPermission> findMenuListByUserId(String userid)throws Exception;
	//根据用户id查询权限url
	public List<SysPermission> findPermissionListByUserId(String userid)throws Exception;

}
