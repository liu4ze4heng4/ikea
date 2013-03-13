package com.fec.shop.ikea;

import java.util.ArrayList;
import java.util.HashSet;

import com.fec.shop.util.HtmlUtil;

public class CategoryList {

	public static ArrayList<String> getAllCategoryUrls(String main_url) {
		ArrayList<String> categoryUrls = new ArrayList<String>();
		String html = HtmlUtil.getHtmlContent(main_url);
		int index = 20000;
		try {
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
		System.out.println("抓取了：" + categoryUrls.size() + "类别");
		return categoryUrls;
	}

	public static void main(String[] args) {
		CategoryList.getAllCategoryUrls("http://www.ikea.com/cn/zh/catalog/allproducts/");
	}
}
