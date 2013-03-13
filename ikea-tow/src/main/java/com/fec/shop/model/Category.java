package com.fec.shop.model;

import java.util.List;

public class Category {

	public String name;
	// ikea 类目 属性
	public String ikeaUrl;
	// 淘宝 类目 ：一个中文类目在淘宝上可能对应多个CID
	public List<String> cidList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIkeaUrl() {
		return ikeaUrl;
	}

	@Override
	public String toString() {
		return name;
	}

	public void setIkeaUrl(String ikeaUrl) {
		this.ikeaUrl = ikeaUrl;
	}

	public List<String> getCidList() {
		return cidList;
	}

	public void setCidList(List<String> cidList) {
		this.cidList = cidList;
	}

}
