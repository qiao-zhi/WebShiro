package cn.qlq.springmvc.Exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, 
			Object object,
			Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		if(e instanceof MyException){
			MyException myException= (MyException)e;
			modelAndView.addObject("error", myException.getMsg());
		}
		else{
		modelAndView.addObject("error", "未知异常！！！！！！！！");
		}
		modelAndView.setViewName("error");
		return modelAndView;
	}

}
