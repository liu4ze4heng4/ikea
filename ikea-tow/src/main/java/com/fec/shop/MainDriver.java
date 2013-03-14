package com.fec.shop;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fec.shop.constant.Constant;
import com.fec.shop.model.Product;
import com.fec.shop.util.IkeaUtils;
import com.fec.shop.util.TaobaoUtils;

public class MainDriver implements Runnable {
	public static List<String> pis = IkeaUtils.getProductListFromFile(111111);
	
	public static Map<String, String> taobaocid = TaobaoUtils.getCCMapFromFile();

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
				String[] tmp=pis.get(i).split(Constant.split);
				
				//生产 该系列产品  对应的 淘宝类别
				String[] catlogs=tmp[0].split(",");
				StringBuilder taobaoCatLogs=new StringBuilder();
				for (String cat : catlogs) {
					taobaoCatLogs.append(taobaocid.get(cat)+",");
				}
				
				Product pd = new Product(tmp[1],taobaoCatLogs.toString() );
				pd.toCSV("g:\\ikea\\");
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
