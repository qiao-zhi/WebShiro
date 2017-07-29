package cn.qlq.springmvc.pojo;

import java.util.List;

public class QueryVo {

	private List<Items> itemsList;
	public List<Items> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<Items> itemsList) {
		this.itemsList = itemsList;
	}

	private Items items;

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}
	
}
