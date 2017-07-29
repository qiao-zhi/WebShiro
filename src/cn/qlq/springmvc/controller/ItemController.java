package cn.qlq.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.qlq.springmvc.Exception.MyException;
import cn.qlq.springmvc.pojo.Items;
import cn.qlq.springmvc.pojo.QueryVo;
import cn.qlq.springmvc.service.ItemService;

/**
 * 商品管理
 * 
 * @author lx
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	// 入门程序 第一 包类 + 类包 + 方法名
	@RequestMapping(value = "/itemlist.action")
	public String itemList(Model model) throws MyException {
		// 从Mysql中查询
		List<Items> list = itemService.findAllItems();

		// 抛出未知异常
		// int i3 = 1 / 0;
		// 抛出一个自定义异常
		/*
		 * if (1 == 1) { throw new MyException("自定义异常"); }
		 */

		ModelAndView mav = new ModelAndView();
		// 数据
		model.addAttribute("itemsList", list);
		return "itemList";
	}

	// 去修改页面 入参 id
	@RequestMapping(value = "/itemEdit.action")
	@RequiresPermissions("item:edit")
	public ModelAndView toEdit(Integer id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {

		// Servlet时代开发
		// String id = request.getParameter("id");

		// 查询一个商品
		// Items items = itemService.selectItemsById(Integer.parseInt(id));
		Items items = itemService.findItemById(id);
		ModelAndView mav = new ModelAndView();
		// 数据
		mav.addObject("item", items);
		mav.setViewName("refuse");
		return mav;

	}

	// public ModelAndView updateItemById(Items items){
	@RequestMapping(value = "/updateitem.action")
	public String updateItem(QueryVo vo, MultipartFile pictureFile) throws Exception {
		// 产生32位随机数并去掉-
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		// jpg 获取文件拓展名
		String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
		// 保存文件,用UUID产生的唯一名字保存
		pictureFile.transferTo(new File("F:\\upload\\" + name + "." + ext));
		vo.getItems().setPic(name + "." + ext);
		// 修改
		vo.getItems().setCreatetime(new Date());
		itemService.updateItemsById(vo.getItems());

		// ModelAndView mav = new ModelAndView();
		// mav.setViewName("success");
		return "redirect:/itemEdit.action?id=" + vo.getItems().getId();
	}

	// JSON数据交互
	@RequestMapping(value = "/json.action")
	public @ResponseBody Items json(@RequestBody Items items) {
		// 设置@RequestBody后子发动将ajax请求封装为对象
		System.out.println(items);

		// 设置@ResponseBody后自动将返回的数据封装为ajax
		return items;
	}

	// RestFul风格测试
	@RequestMapping(value = "/itemEdit/{id}.action")
	public ModelAndView toEdit2(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {
		Items items = itemService.findItemById(id);
		ModelAndView mav = new ModelAndView();
		// 数据
		mav.addObject("item", items);
		mav.setViewName("editItem");
		return mav;

	}

/*	// 去登陆的页面,提交方式不一样可以映射到相同路径
	@RequestMapping(value = "/login.action", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login.action", method = RequestMethod.POST)
	public String login(String username, HttpSession httpSession) {
		httpSession.setAttribute("user", username);
		return "redirect:/itemlist.action";
	}
*/
}
