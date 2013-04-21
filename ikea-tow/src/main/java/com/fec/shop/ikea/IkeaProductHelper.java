package com.fec.shop.ikea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.fec.shop.model.Category;
import com.fec.shop.util.HtmlUtil;

public class IkeaProductHelper {

	public static void fillProductFromCat(Map<String, String> productMap, Category cat) {
		String categoryUrl = cat.getIkeaUrl();
		if (categoryUrl == null) {
			System.out.println("category url is null!");
			return;
		}
		String html;
		try {
			html = HtmlUtil.getHtmlContent(categoryUrl);
		
		String cn = getCategoryName(html);
		int index = 0;
		int x = 1;

			while (true) {
				int beginIx = html.indexOf("<div id=\"item_", index);
				if (beginIx <= 0)
					break;
				String beginstr = "<div id=\"item_";
				int beginIxLength = beginstr.length();
				int endIx = html.indexOf("_" + x + "\" class=\"threeColumn", beginIx);
				String productName = html.substring(beginIx + beginIxLength, endIx);
				productMap.put(productName, cn);
				index = endIx;
				x++;
			}
		
		
		getExtraProductUrls(productMap, html);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String getCategoryName(String html) {
		int beginIx = html.indexOf("IRWStats.subCategoryLocal\" content=\"");
		String beginstr = "IRWStats.subCategoryLocal\" content=\"";
		int beginIxLength = beginstr.length();
		int endIx = html.indexOf("\" />", beginIx);
		String mulu = html.substring(beginIx + beginIxLength, endIx);
		beginIx = html.indexOf("IRWStats.categoryLocal\" content=\"");
		beginstr = "IRWStats.categoryLocal\" content=\"";
		beginIxLength = beginstr.length();
		endIx = html.indexOf("\" />", beginIx);
		// String category = html.substring(beginIx + beginIxLength, endIx);
		// String result = category + "\\" + mulu;
		String result = mulu;
		return result;
	}

	private static void getExtraProductUrls(Map<String, String> productMap, String html) {

		ArrayList<String> extraProductUrls = new ArrayList<String>();
		String cn = getCategoryName(html);

		int index = 10;
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

					Collections.addAll(extraProductUrls, results);
					@SuppressWarnings("unused")
					String element = null;
					for (int i = 0; i < extraProductUrls.size(); i++) {
						element = extraProductUrls.get(i) + "!" + cn;
						productMap.put(extraProductUrls.get(i), extraProductUrls.get(i) + "!" + cn);
					}
				}
				index = endIx;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
/*
 * http://m.ikea.com/cn/zh/store/availability/?storeCode=802&itemType=art&itemNo=70176642&change=true&_=1
 */
