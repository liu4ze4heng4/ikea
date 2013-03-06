package com.fec.ikea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MultiFilter extends IkeaFilter {
	ArrayList<String> tmps = new ArrayList<String>();

	public void go(String url) throws IOException {
		GetProductIds getproductids = new GetProductIds();
		getproductids.geT(url);
		GetMulu getmulu = new GetMulu();
		String name = getmulu.getmus(url);
		tmps = getproductids.productlist;
		categoryName=name;
		saveMaster(tmps, new File("E:\\IKEAaa\\" + categoryName + "\\products.csv"), "E:\\IKEAaa\\" + categoryName + "\\products\\");
	}

	public static void main(String[] args) {
		MultiFilter a1 = new MultiFilter();
		ProductFinder finder = new ProductFinder();
		finder.geT("http://www.ikea.com/cn/zh/catalog/allproducts/");
		ArrayList<String> urls = finder.productlist;
		for (int i = 0; i < 5; i++) {
			String url = urls.get(i);
			try {
				a1.go(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
