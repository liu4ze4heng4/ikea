package com.fec.shop;

import java.util.LinkedList;
import java.util.List;

import com.fec.shop.constant.Constant;
import com.fec.shop.model.Product;
import com.fec.shop.util.IkeaUtils;

public class MainDriver implements Runnable {
	public static List<String> pis = IkeaUtils.getProductStrListFromFile(5);

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
				System.out.println(Thread.currentThread().getName() + "：抓取第" + i + "个产品：" + pis.get(i));
				String[] tmp = pis.get(i).split(Constant.split);

				Product pd = new Product(tmp[2], tmp[1]);
				pd.toCSV(Constant.product_cvs_file);
				// pd.toSQL();
			} else
				break;
		}
	}

	public static void main(String[] args) {

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

	}
}
