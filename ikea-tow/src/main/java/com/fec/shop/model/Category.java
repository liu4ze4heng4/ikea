package com.fec.shop.model;

import java.util.List;

public class Category {

	public String name;
	// ikea ��Ŀ ����
	public String ikeaUrl;
	// �Ա� ��Ŀ ��һ��������Ŀ���Ա��Ͽ��ܶ�Ӧ���CID
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
