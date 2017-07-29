package cn.qlq.springmvc.service;

import java.util.List;

import cn.qlq.springmvc.pojo.Items;


public interface ItemService {

	public List<Items> findAllItems();

	/**
	 * 查找items
	 *@param id  id
	 *@return items
	 */
	Items findItemById(Integer id);
	
	public void updateItemsById(Items items);
}
