package com.fec.ikeaII;

import java.util.ArrayList;
import java.util.HashSet;

public class CategoryList {
	ArrayList<String> categoryUrls = new ArrayList<String>();

	public ArrayList<String> getAllCategoryUrls(String main_url) {
		String html = CaptureHtml.captureHtml(main_url);
		int index = 20000;
		try {
			categoryUrls.clear();
			while (true) {
				int beginIx = html.indexOf(" href=\"/cn/zh/catalog/categories/departments", index);
				if (beginIx > 1) {
					int endIx = html.indexOf("</a>", beginIx);
					String tmp = html.substring(beginIx, endIx);
					String result = tmp.replace("	", "");
					tmp = result.replace(" href=\"", "http://www.ikea.com");
					result = tmp.replace("\">", "!");
					String[] results;
					results = result.split("!");
					categoryUrls.add(results[0]);

					index = endIx;
				} else
					break;
				// System.out.println(results[0]);
			}
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashSet<String> categoryUrlSet = new HashSet<String>();
		categoryUrlSet.addAll(categoryUrls);
		categoryUrls.clear();
		categoryUrls.addAll(categoryUrlSet);
		// System.out.println(categoryUrls.get(152));
		System.out.println("找到" + categoryUrls.size() + "个产品目录");
		return categoryUrls;
	}
}
