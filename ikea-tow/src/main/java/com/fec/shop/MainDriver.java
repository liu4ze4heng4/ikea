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
				System.out.println(Thread.currentThread().getName() + "抓取：第" + i + "个产品：" + piNc[0]);
				Product pd = new Product(piNc[0], piNc[1]);
				pd.toCSV("g:\\ikea\\");
				// pd.toSQL();
			} else
				break;
		}
	}

	public static void main(String[] args) {
		ProductList pl = new ProductList();
		// [类别名字-类别对象]
		Map<String, Category> cats = IkeaCategoryHelper.getCategoryMap();
		TBCategoryHelper.fillCategoryWithTBCategory(cats);

		// [产品编号--产品类别]
		Map<String, String> productMap = new HashMap<String, String>();

		for (Iterator iterator = cats.values().iterator(); iterator.hasNext();) {
			Category category = (Category) iterator.next();
			IkeaProductHelper.fillProductFromCat(productMap, category);
		}

		for (Iterator iterator = productMap.keySet().iterator(); iterator.hasNext();) {
			String productId = (String) iterator.next();
			String productcat = productMap.get(productId);
			Category cat = cats.get(productcat);
			String productTBcat = cat == null ? " " : cat.getTBCode();
			System.out.println(productId + "!" + productTBcat);
			pis.add(productId + "!" + productTBcat);
		}

		System.out.println("一共" + pis.size() + "产品");

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
