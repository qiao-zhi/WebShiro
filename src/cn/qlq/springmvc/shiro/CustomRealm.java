package cn.qlq.springmvc.shiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qlq.springmvc.Exception.MyException;
import cn.qlq.springmvc.pojo.ActiveUser;
import cn.qlq.springmvc.pojo.SysPermission;
import cn.qlq.springmvc.pojo.SysUser;
import cn.qlq.springmvc.service.SysService;
import cn.qlq.springmvc.serviceImpl.SysServiceImpl;

/**
 * 自定义realm。根据上面穿下来的token去数据库查信息，查到返回一个SimpleAuthenticationInfo，查不到返回null
 * 
 * @author: qlq
 * @date : 2017年7月28日下午8:45:52
 */
public class CustomRealm extends AuthorizingRealm {
	// 注入service
	@Autowired(required = true)
	private SysService sysService;

	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	// 用于认证
	/*
	 * @Override protected AuthenticationInfo doGetAuthenticationInfo(
	 * AuthenticationToken token) throws AuthenticationException {
	 * 
	 * // token是用户输入的用户名和密码 // 第一步从token中取出用户名 String userCode = (String)
	 * token.getPrincipal();
	 * 
	 * // 第二步：根据用户输入的userCode从数据库查询 // ....
	 * 
	 * 
	 * // 如果查询不到返回null //数据库中用户账号是zhangsansan
	 * if(!userCode.equals("zhangsan")){// return null; }
	 * 
	 * 
	 * // 模拟从数据库查询到密码 String password = "111111";
	 * 
	 * // 如果查询到返回认证信息AuthenticationInfo
	 * 
	 * //activeUser就是用户身份信息 ActiveUser activeUser = new ActiveUser();
	 * 
	 * activeUser.setUserid("zhangsan"); activeUser.setUsercode("zhangsan");
	 * activeUser.setUsername("张三"); //..
	 * 
	 * //根据用户id取出菜单 //通过service取出菜单 List<SysPermission> menus = null; try {
	 * menus = sysService.findMenuListByUserId("zhangsan"); } catch (Exception
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } //将用户菜单
	 * 设置到activeUser activeUser.setMenus(menus);
	 * 
	 * 
	 * //将activeUser设置simpleAuthenticationInfo SimpleAuthenticationInfo
	 * simpleAuthenticationInfo = new SimpleAuthenticationInfo( activeUser,
	 * password, this.getName());
	 * 
	 * return simpleAuthenticationInfo; }
	 */

	// realm的认证方法，从数据库查询用户信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// token是用户输入的用户名和密码
		// 第一步从token中取出用户名
		String userCode = (String) token.getPrincipal();

		// 第二步：根据用户输入的userCode从数据库查询
		SysUser sysUser = null;
		try {
			sysUser = sysService.findSysUserByUserCode(userCode);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 如果查询不到返回null
		if (sysUser == null) {//
			return null;
		}
		// 从数据库查询到密码
		String password = sysUser.getPassword();

		// 盐
		String salt = sysUser.getSalt();

		// 如果查询到返回认证信息AuthenticationInfo

		// activeUser就是用户身份信息
		ActiveUser activeUser = new ActiveUser();

		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		// ..

		// 根据用户id取出菜单
		List<SysPermission> menus = null;
		try {
			// 通过service取出菜单
			menus = sysService.findMenuListByUserId(sysUser.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将用户菜单 设置到activeUser
		activeUser.setMenus(menus);

		// 将activeUser设置simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password,
				ByteSource.Util.bytes(salt), this.getName());

		return simpleAuthenticationInfo;
	}

	/*
	 * // 用于授权不连接数据库
	 * 
	 * @Override protected AuthorizationInfo doGetAuthorizationInfo(
	 * PrincipalCollection principals) {
	 * 
	 * //从 principals获取主身份信息 //将getPrimaryPrincipal方法返回值转为真实身份类型（
	 * 在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
	 * ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
	 * 
	 * //根据身份信息获取权限信息 //从数据库获取到权限数据 List<SysPermission> permissionList = null;
	 * try { permissionList =
	 * sysService.findPermissionListByUserId(activeUser.getUserid()); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * //单独定一个集合对象 List<String> permissions = new ArrayList<String>();
	 * if(permissionList!=null){ for(SysPermission
	 * sysPermission:permissionList){ //将数据库中的权限标签 符放入集合
	 * permissions.add(sysPermission.getPercode()); } }
	 * 
	 * 
	 * List<String> permissions = new ArrayList<String>();
	 * permissions.add("user:create");//用户的创建
	 * permissions.add("item:query1");//商品查询权限
	 * permissions.add("item:add");//商品添加权限
	 * permissions.add("item:edit1");//商品修改权限 //....
	 * 
	 * //查到权限数据，返回授权信息(要包括 上边的permissions) SimpleAuthorizationInfo
	 * simpleAuthorizationInfo = new SimpleAuthorizationInfo();
	 * //将上边查询到授权信息填充到simpleAuthorizationInfo对象中
	 * simpleAuthorizationInfo.addStringPermissions(permissions);
	 * 
	 * return simpleAuthorizationInfo; }
	 */

	// 用于授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// 从 principals获取主身份信息
		// 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

		// 根据身份信息获取权限信息
		// 从数据库获取到权限数据
		List<SysPermission> permissionList = null;
		try {
			permissionList = sysService.findPermissionListByUserId(activeUser.getUserid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 单独定一个集合对象
		List<String> permissions = new ArrayList<String>();
		if (permissionList != null) {
			for (SysPermission sysPermission : permissionList) {
				// 将数据库中的权限标签 符放入集合
				permissions.add(sysPermission.getPercode());
			}
		}

		// 查到权限数据，返回授权信息(要包括 上边的permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		// 将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		simpleAuthorizationInfo.addStringPermissions(permissions);

		return simpleAuthorizationInfo;
	}

	// 清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}
