package com.fec.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fec.shop.ikea.IkeaCategoryHelper;
import com.fec.shop.ikea.IkeaProductHelper;
import com.fec.shop.ikea.ProductList;
import com.fec.shop.model.Category;
import com.fec.shop.model.Product;
import com.fec.shop.taobao.TBCategoryHelper;

public class MainDriver implements Runnable {
	public static ArrayList<String> pis = new ArrayList<String>();
	public static int index = 0;

	public static Map<String, Category> cats;

	static List<String> notInTB = new ArrayList<String>();

	public static synchronized int getindex() {
		if (index < pis.size()) {
			return index++;
		} else
			return 9999;
	}

	public void run() {
		while (true) {
			int i = getindex();
			if (i != 9999) {
				String[] piNc = pis.get(i).split("!");
				Category cat = cats.get(piNc[1]);
				String tbCode = "";
				if (cat == null) {
					notInTB.add(piNc[1]);
				} else {
					tbCode = cat.getTBCode();
				}
				Product pd = new Product(piNc[0], tbCode);
				pd.toCSV("g:\\ikea\\");
				// pd.toSQL();
			} else
				break;
		}
	}

	public static void main(String[] args) {
		ProductList pl = new ProductList();
		// 获取类别信息
		cats = IkeaCategoryHelper.getCategoryMap();
		TBCategoryHelper.fillCategoryWithTBCategory(cats);

		// 获取指定类别下的产品
		Map<String, String> productMap = new HashMap<String, String>();

		for (Iterator iterator = cats.values().iterator(); iterator.hasNext();) {
			Category category = (Category) iterator.next();
			IkeaProductHelper.fillProductFromCat(productMap, category);
		}

		for (Iterator iterator = productMap.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String productUrl = productMap.get(key);
			System.out.println(key + ":" + productUrl);
			pis.add(productUrl);
		}

		System.out.println(pis.size());

		List<Thread> threads = new LinkedList<Thread>();
		for (int i = 0; i <= 15; i++) {
			MainDriver md = new MainDriver();
			Thread t1 = new Thread(md);
			threads.add(t1);
			t1.setName("t" + i + ":");
			t1.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("all thread finish!!!!");

		for (String productName : notInTB) {
			System.out.println(productName);
		}
	}
}
