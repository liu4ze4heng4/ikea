package com.fec.shop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fec.shop.ikea.CategoryList;
import com.fec.shop.ikea.ProductList;
import com.fec.shop.model.Product;
import com.fec.shop.model.TaobaoProduct;
import com.fec.shop.taobao.OurCats;

public class MainDriver implements Runnable {
	public static ArrayList<String> pis = new ArrayList<String>();
	public static int index = 0;
	static Map<String, List<TaobaoProduct>> allTBPt;
	
	static List<String>  notInTB=new ArrayList<String>();

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
				List<TaobaoProduct> products = allTBPt.get(piNc[1]);
				StringBuilder TBcode = new StringBuilder();
				if(products==null){
					notInTB.add(piNc[1]);
				}else{
					for (TaobaoProduct taobaoProduct : products) {
						TBcode.append(taobaoProduct.getCid() + ",");
					}
				}
				Product pd = new Product(piNc[0], TBcode.toString());
				pd.toCSV("e:\\ikea234\\912\\");
//				 pd.toSQL();
			} else
				break;
		}
	}

	public static void main(String[] args) {
		ProductList pl = new ProductList();
		//获取类别信息
		ArrayList<String> cu = CategoryList.getAllCategoryUrls("http://www.ikea.com/cn/zh/catalog/allproducts/");
		//获取淘宝类别信息
		allTBPt = OurCats.getTaobaoCats();
		//
		for (int j = 0; j < cu.size(); j++) {
			pis.addAll(pl.getProductIds(cu.get(j)));
			// System.out.println(pis.get(0));
		}
		HashSet<String> piSet = new HashSet<String>();
		piSet.addAll(pis);
		pis.clear();
		pis.addAll(piSet);

		System.out.println(pis.size());

		List<Thread> threads=new LinkedList<Thread>();
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
