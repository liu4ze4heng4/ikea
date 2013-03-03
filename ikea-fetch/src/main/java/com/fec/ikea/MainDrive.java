package com.fec.ikea;

import java.io.IOException;
import java.util.ArrayList;

public class MainDrive {

	public static void main(String[] args) {
		MultiFilter a1 = new MultiFilter();
		ProductFinder finder = new ProductFinder();
		finder.geT("http://www.ikea.com/cn/zh/catalog/allproducts/");
		ArrayList<String> urls = finder.productlist;
		for (int i = 0; i < 1; i++) {
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
