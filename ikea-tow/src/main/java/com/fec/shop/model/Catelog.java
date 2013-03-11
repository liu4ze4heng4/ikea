package com.fec.shop.model;

import java.util.ArrayList;

public class Catelog {
public ArrayList<Product> productlist;
String name;
String url;
int cid;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}
public Integer getCid() {
	return cid;
}

public void setCid(int cid) {
	this.cid = cid;
}

}
