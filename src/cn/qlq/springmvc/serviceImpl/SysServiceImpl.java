package cn.qlq.springmvc.serviceImpl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qlq.springmvc.Exception.MyException;
import cn.qlq.springmvc.mapper.SysPermissionMapperCustom;
import cn.qlq.springmvc.mapper.SysUserMapper;
import cn.qlq.springmvc.pojo.ActiveUser;
import cn.qlq.springmvc.pojo.SysPermission;
import cn.qlq.springmvc.pojo.SysUser;
import cn.qlq.springmvc.pojo.SysUserExample;
import cn.qlq.springmvc.service.SysService;
import cn.qlq.springmvc.utils.MD5;

/**
 * 认证和授权
 * 
 * @author: qlq
 * @date : 2017年7月28日上午10:45:52
 */
public class SysServiceImpl implements SysService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	

	public SysUserMapper getSysUserMapper() {
		return sysUserMapper;
	}

	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	public SysPermissionMapperCustom getSysPermissionMapperCustom() {
		return sysPermissionMapperCustom;
	}

	public void setSysPermissionMapperCustom(SysPermissionMapperCustom sysPermissionMapperCustom) {
		this.sysPermissionMapperCustom = sysPermissionMapperCustom;
	}

	@Override
	public ActiveUser authenticat(String userCode, String password) throws Exception {
		/**
		 * 认证过程： 根据用户身份（账号）查询数据库，如果查询不到用户不存在 对输入的密码 和数据库密码 进行比对，如果一致，认证通过
		 */
		// 根据用户账号查询数据库
		SysUser sysUser = this.findSysUserByUserCode(userCode);

		if (sysUser == null) {
			// 抛出异常
			throw new MyException("用户账号不存在");
		}

		// 数据库密码 (md5密码 )
		String password_db = sysUser.getPassword();

		// 对输入的密码 和数据库密码 进行比对，如果一致，认证通过
		// 对页面输入的密码 进行md5加密
		String password_input_md5 = new MD5().getMD5ofStr(password);
		if (!password_input_md5.equalsIgnoreCase(password_db)) {
			// 抛出异常
			throw new MyException("用户名或密码 错误");
		}
		// 得到用户id
		String userid = sysUser.getId();
		// 根据用户id查询菜单
		List<SysPermission> menus = this.findMenuListByUserId(userid);

		// 根据用户id查询权限url
		List<SysPermission> permissions = this.findPermissionListByUserId(userid);

		// 认证通过，返回用户身份信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(userCode);
		activeUser.setUsername(sysUser.getUsername());// 用户名称

		// 放入权限范围的菜单和url
		activeUser.setMenus(menus);
		activeUser.setPermissions(permissions);

		return activeUser;
	}

	// 根据用户账号查询用户信息
	public SysUser findSysUserByUserCode(String userCode) throws Exception {
		// 根据用户名查询用户信息
		SysUserExample sysUserExample = new SysUserExample();
		SysUserExample.Criteria criteria = sysUserExample.createCriteria();
		criteria.andUsercodeEqualTo(userCode);

		List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);

		if (list != null && list.size() == 1) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public List<SysPermission> findMenuListByUserId(String userid) throws Exception {

		return sysPermissionMapperCustom.findMenuListByUserId(userid);
	}
	
	@Override
	public List<SysPermission> findPermissionListByUserId(String userid) throws Exception {

		return sysPermissionMapperCustom.findPermissionListByUserId(userid);
	}
}
