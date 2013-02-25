package com.fec.ikea;

import java.io.IOException;
import java.util.Collections;

public class GetExtraProductIds extends GetProductIds {
	void geT(String url) {
		try {
			HtmlCatch(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index = 10;
		String result;
		String[] results;
		try {

			while (true) {
				int beginIx = html.indexOf("jsonPartNumbers.push([", index);
				if (beginIx == -1)
					break;
				int beginIxLength = "jsonPartNumbers.push([".length();
				int endIx = html.indexOf("]);", beginIx);
				String tmp = html.substring(beginIx + beginIxLength, endIx);
				if (tmp.length() != 0) {
					result = tmp.replace("\"", "");
					results = result.split(",");
					Collections.addAll(productlist, results);
				}
				index = endIx;
			}
			System.out.println(Thread.currentThread().getName() + "增量找到"
					+ productlist.size() + "个产品");
			System.out.println(Thread.currentThread().getName() + ":"
					+ productlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GetExtraProductIds get1 = new GetExtraProductIds();
		get1.geT("http://www.ikea.com/cn/zh/catalog/categories/departments/bedroom/16284/");
	}
}
