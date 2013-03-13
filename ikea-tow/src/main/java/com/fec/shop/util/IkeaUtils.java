package com.fec.shop.util;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.fec.shop.model.Category;

public class IkeaUtils {
	public static Map<String, Category> allCategory = new HashMap<String, Category>();

	public static Map<String, Category> getCategoryMap() {

		if (allCategory.size() < 1) {
			initAllCategoryFromHtml();
		}
		return allCategory;
	}

	/*
	 * 从网页抓起一次全部 类别信息
	 */
	private static Map<String, Category> initAllCategoryFromHtml() {
		String categoryListUrl = "http://www.ikea.com/cn/zh/catalog/allproducts/";
		Map<String, Category> allCategory = new HashMap<String, Category>();
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
						System.out.println("重复的类别出现：" + results[1]);
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
		return allCategory;
	}

	public static List<String> getProductList(Category c) {
		String html = HtmlUtil.getHtmlContent(c.getIkeaUrl());
		List<String> pidlist = new ArrayList<String>();
		int index = 0;
		int x = 1;
		try {
			while (true) {
				int beginIx = html.indexOf("<div id=\"item_", index);
				if (beginIx <= 0)
					break;
				String beginstr = "<div id=\"item_";
				int beginIxLength = beginstr.length();
				int endIx = html.indexOf("_" + x + "\" class=\"threeColumn", beginIx);
				String pid = html.substring(beginIx + beginIxLength, endIx);
				pidlist.add(pid);
				index = endIx;
				x++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		index = 10;
		String result;
		String[] results;
		try {

			while (true) {
				int beginIx = html.indexOf("jsonPartNumbers.push([", index);
				if (beginIx <= 10)
					break;
				int beginIxLength = "jsonPartNumbers.push([".length();
				int endIx = html.indexOf("]);", beginIx);
				String tmp = html.substring(beginIx + beginIxLength, endIx);
				if (tmp.length() != 0) {
					result = tmp.replace("\"", "");
					results = result.split(",");
						if (pidlist.contains(results[0]))
						{	pidlist.remove(results[0]);
						pidlist.add(result);}
					}
			

				index = endIx;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.pidlist=pidlist;
		return pidlist;
	}

	public static void main(String[] args) {
		System.out.println(IkeaUtils.getCategoryMap().size());
	}

}
