package com.fec.ikeaII;

import java.util.ArrayList;
import java.util.HashSet;

public class MainDriver implements Runnable {
	public static ArrayList<String> pis = new ArrayList<String>();
	public static int index = 0;

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
				String[] piNc=pis.get(i).split("!");
					Product pd = new Product(piNc[0],piNc[1]);
					pd.toCSV("e:\\ikea234\\91\\");
					pd.toSQL();
			}
			else break;
		}
	}

	public static void main(String[] args) {
		ProductList pl = new ProductList();
		ArrayList<String> cu = pl.getAllCategoryUrls("http://www.ikea.com/cn/zh/catalog/allproducts/");
		for(int j=0;j<cu.size();j++)
		{pis.addAll(pl.getProductIds(cu.get(j)));
//		System.out.println(pis.get(0));
		}
		HashSet<String> piSet= new HashSet<String>();
		piSet.addAll(pis);
		pis.clear();
		pis.addAll(piSet);
		
		System.out.println(pis.size());

		for (int i = 0; i <= 10; i++) {
			MainDriver md = new MainDriver();
			Thread t1 = new Thread(md);
			t1.setName("t" + i + ":");
			t1.start();
		}
	}
}
