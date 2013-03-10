package com.fec.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
	public List<TBCategroy> getTbCategorys() {
		return tbCategorys;
	}

	public void setTbCategorys(List<TBCategroy> tbCategorys) {
		this.tbCategorys = tbCategorys;
	}

	public void setIkeaUrl(String ikeaUrl) {
		this.ikeaUrl = ikeaUrl;
	}

	public int id;
	public String name;
	// ikea 类目 属性
	public String ikeaUrl;
	// 淘宝 类目 ：一个类目在淘宝上可能对应多个
	public List<TBCategroy> tbCategorys;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIkeaUrl() {
		return ikeaUrl;
	}

	public void addTBCategory(TBCategroy tb) {
		if (tbCategorys == null) {
			tbCategorys = new ArrayList<TBCategroy>(2);
		}
		tbCategorys.add(tb);
	}

	@Override
	public String toString() {
		return name;
	}

	public String getTBCode() {
		if (tbCategorys != null) {
			StringBuilder sb = new StringBuilder("");
			for (TBCategroy tbcat : tbCategorys) {
				sb.append(tbcat.getTb_cid()).append(",");
			}
			return sb.toString();
		}
		return "";
	}

}
