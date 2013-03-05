package com.fec.ikea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ThreadMultiFilter extends IkeaFilter implements Runnable {
	public static ArrayList<String> urls = new ArrayList<String>();
	public static int index = 0;

	public void go(String url) throws IOException {
		GetProductIds getproductids = new GetProductIds();
		getproductids.geT(url);
		GetMulu getmulu = new GetMulu();
		String name = getmulu.getmus(url);
		ArrayList<String> tmps = new ArrayList<String>();
		tmps = getproductids.productlist;
		
//=========================在下面方法中选择执行的操作====================
		saveMaster(tmps, new File("E:\\IKEAss\\" + name + "\\products.csv"), "E:\\IKEAss\\" + name + "\\products\\");
//====================================================================
	}

	public void run() {

		while (true) {
			int i = getindex();
			if (i != 9999) {
				String url = urls.get(i);
				try {
					go(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				break;
		}

	}

	synchronized int getindex() {
		if (index < urls.size()) {
			return index++;
		} else
			return 9999;
	}

	public static void main(String[] args) {
		ProductFinder finder = new ProductFinder();
		finder.geT("http://www.ikea.com/cn/zh/catalog/allproducts/");
		urls = finder.productlist;

		for (int i = 0; i <= 10; i++) {
			ThreadMultiFilter a1 = new ThreadMultiFilter();
			Thread t1 = new Thread(a1);
			t1.setName("t" + i + ":");
			t1.start();
		}

	}

}
