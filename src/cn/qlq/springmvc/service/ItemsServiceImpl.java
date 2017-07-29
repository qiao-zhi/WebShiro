package cn.qlq.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.qlq.springmvc.mapper.ItemsMapper;
import cn.qlq.springmvc.pojo.Items;

@Service
public class ItemsServiceImpl implements ItemService {

	@Autowired
	private ItemsMapper itemsMapper;
	@Override
	public List<Items> findAllItems() {
		
		List<Items> selectByExampleWithBLOBs = itemsMapper.selectByExampleWithBLOBs(null);
		return selectByExampleWithBLOBs;
	}
	@Override
	public Items findItemById(Integer id) {
		Items items = itemsMapper.selectByPrimaryKey(id);
		return items;
	}
	/**
	 * 根据ID修改items
	 *@param items  对象
	 */
	public void updateItemsById(Items items){
		itemsMapper.updateByPrimaryKeyWithBLOBs(items);	
	}

}
