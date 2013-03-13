package com.fec.shop.model;

import java.util.ArrayList;
import java.util.List;

import com.fec.shop.util.SQLHelper;

public class Category {

	public String cid;
	public String name;
	// ikea 类目 属性
	public String ikeaUrl;
	// 淘宝 类目 ：一个中文类目在淘宝上可能对应多个CID
	public List<TBCategroy> tbCategorys;
	public List<String> pidlist;
public List<String> getpidlist(){
	return pidlist;
}
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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
public void setTBCategoryFromSQL(){
	if (name==null){
		setName("0");
		System.out.println("no name category?");
	};
	
	SQLHelper sqlhelper=new SQLHelper();
	cid=(sqlhelper.getcid(name));
} 
	@Override
	public String toString() {
		return name;
	}

	public String getTBCid() {
		if (tbCategorys != null) {
			StringBuilder sb = new StringBuilder("");
			for (TBCategroy tbcat : tbCategorys) {
				sb.append(tbcat.getTb_cid()).append(",");
			}
			return sb.toString();
		}
		return "";
	}

	public void setTbCategorys(List<TBCategroy> tbCategorys) {
		this.tbCategorys = tbCategorys;
	}

	public void setIkeaUrl(String ikeaUrl) {
		this.ikeaUrl = ikeaUrl;
	}

}
