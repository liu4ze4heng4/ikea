package com.fec.ikea;
import java.io.IOException;


public class ProductFinder extends GetProductIds {
	void geT(String url){
		try {
			HtmlCatch(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index=20000;
	try{
		while(true){
	int beginIx = html.indexOf(" href=\"/cn/zh/catalog/categories/",index);
	int endIx = html.indexOf("</a>",beginIx);
	String tmp = html.substring(beginIx, endIx);
	String result=tmp.replace("	", "");
	tmp=result.replace(" href=\"", "http://www.ikea.com");
	result=tmp.replace("\">", "!");
	String[] results;
	results=result.split("!");
	productlist.add(results[0]);
	index=endIx;
//	System.out.println(results[0]);

	}
	} catch (Exception e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
	}
//	System.out.println(productlist);
	System.out.println("找到"+productlist.size()+"个产品目录");
	}
	public static void main(String[] args){
		ProductFinder get1=new ProductFinder();
		get1.geT("http://www.ikea.com/cn/zh/catalog/allproducts/");
	}
}
