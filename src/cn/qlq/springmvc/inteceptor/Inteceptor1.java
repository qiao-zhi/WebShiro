package cn.qlq.springmvc.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * SpringMVC的拦截器
* @author: qlq
* @date :  2017年7月22日下午12:20:52
 */
public class Inteceptor1 implements HandlerInterceptor{


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
//		判断是否是登陆
		String requestURI = request.getRequestURI();  //springmvc-mybatis/itemlist.action
		StringBuffer requestURL = request.getRequestURL(); //http://localhost:8080/springmvc-mybatis/itemlist.action
//		判断用户是否登陆  如果没有登陆  重定向到登陆页面   不放行   如果登陆了  就放行了
		if(!requestURI.contains("login")){
			Object attribute = request.getSession().getAttribute("user");
			if(attribute==null){
				response.sendRedirect(request.getContextPath()+"/login.action");
				return false;
			}
		}
		//不放行的话返回false
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("这是方法后1");		
	}
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("这是页面渲染后1");
	}



	
}
