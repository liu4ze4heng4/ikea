package com.fec.shop.ikea;

import java.util.HashMap;
import java.util.Map;

import com.fec.shop.model.Category;
import com.fec.shop.util.HtmlUtil;

public class IkeaCategoryHelper {
	private static final String categoryListUrl = "http://www.ikea.com/cn/zh/catalog/allproducts/";

	private static Map<String, Category> allCategory = new HashMap<String, Category>();

	public static Map<String, Category> getCategoryMap() {
		if (allCategory.size() < 1) {
			initAllCategoryFromHtml();
		}
		return allCategory;
	}

	public static void initAllCategoryFromHtml() {
		String html = HtmlUtil.getHtmlContent(categoryListUrl);
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

					if (allCategory.get(results[1]) != null) {
						System.out.println("" + results[1]);
					} else {
						Category c = new Category();
						c.setIkeaUrl(results[0]);
						c.setName(results[1]);
						allCategory.put(results[1], c);
					}
					index = endIx;
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(IkeaCategoryHelper.getCategoryMap().size());
	}
}
