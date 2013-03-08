package com.fec.ikeaII;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ProductList {
	ArrayList<String> categoryUrls = new ArrayList<String>();
	ArrayList<String> productIds = new ArrayList<String>();

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
		System.out.println("�ҵ�" + categoryUrls.size() + "����ƷĿ¼");
		return categoryUrls;
	}

	public String getCategoryName(String categoryUrl) {
		String html = CaptureHtml.captureHtml(categoryUrl);
		int beginIx = html.indexOf("IRWStats.subCategoryLocal\" content=\"");
		String beginstr = "IRWStats.subCategoryLocal\" content=\"";
		int beginIxLength = beginstr.length();
		int endIx = html.indexOf("\" />", beginIx);
		String mulu = html.substring(beginIx + beginIxLength, endIx);
		beginIx = html.indexOf("IRWStats.categoryLocal\" content=\"");
		beginstr = "IRWStats.categoryLocal\" content=\"";
		beginIxLength = beginstr.length();
		endIx = html.indexOf("\" />", beginIx);
//		String category = html.substring(beginIx + beginIxLength, endIx);
		// String result = category + "\\" + mulu;
		String result = mulu;
		return result;
	}

	public ArrayList<String> getProductIds(String categoryUrl) {
		String html = CaptureHtml.captureHtml(categoryUrl);
		int index = 0;
		int x = 1;
		try {
			productIds.clear();
			while (true) {
				int beginIx = html.indexOf("<div id=\"item_", index);
				if(beginIx<=0) break;
				String beginstr = "<div id=\"item_";
				int beginIxLength = beginstr.length();
				int endIx = html.indexOf("_" + x + "\" class=\"threeColumn", beginIx);
				String result = html.substring(beginIx + beginIxLength, endIx);
				// System.out.println(result);
				productIds.add(result);
				index = endIx;
				x++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productIds.addAll(getExtraProductUrls(categoryUrl));
		// System.out.println(productlist.size()+"2");
		HashSet<String> productUrlSet = new HashSet<String>();
		productUrlSet.addAll(productIds);
		productIds.clear();
		productIds.addAll(productUrlSet);
		System.out.println(Thread.currentThread().getName() + "���ҵ�" + productIds.size() + "����Ʒ@" + getCategoryName(categoryUrl));
		return productIds;
	}

	private ArrayList<String> getExtraProductUrls(String categoryUrl) {
		String html = CaptureHtml.captureHtml(categoryUrl);
		ArrayList<String> extraProductUrls = new ArrayList<String>();
		int index = 10;
		String result;
		String[] results;
		try {

			while (true) {
				int beginIx = html.indexOf("jsonPartNumbers.push([", index);
				if (beginIx <=10)
					break;
				int beginIxLength = "jsonPartNumbers.push([".length();
				int endIx = html.indexOf("]);", beginIx);
				String tmp = html.substring(beginIx + beginIxLength, endIx);
				if (tmp.length() != 0) {
					result = tmp.replace("\"", "");
					results = result.split(",");
					Collections.addAll(extraProductUrls, results);
				}
				index = endIx;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return extraProductUrls;
	}

}