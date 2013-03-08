package com.fec.ikea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class GetProductIds {
	ArrayList<String> productlist = new ArrayList<String>();
	HashSet<String> productset = new HashSet<String>();;
	String productid = new String();
	// String strURL =
	// "http://www.ikea.com/cn/zh/catalog/categories/departments/eating/16048/";
	String html;

	void HtmlCatch(String strURL) {
		URL url;
		try {
			url = new URL(strURL);

			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
			BufferedReader bufReader = new BufferedReader(input);
			String line = "";
			StringBuilder contentBuf = new StringBuilder();
			while ((line = bufReader.readLine()) != null) {
				contentBuf.append(line);
			}

			html = contentBuf.toString();

		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + strURL + " 超时错误");
			HtmlCatch(strURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// captureHtml(id);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + strURL + " IO错误");
			// captureHtml(id);
		}
	}

	ArrayList<String> geT(String url) {
		HtmlCatch(url);
		int index = 0;
		int x = 1;
		try {
			while (true) {
				int beginIx = html.indexOf("<div id=\"item_", index);
				String beginstr = "<div id=\"item_";
				int beginIxLength = beginstr.length();
				int endIx = html.indexOf("_" + x + "\" class=\"threeColumn", beginIx);
				String result = html.substring(beginIx + beginIxLength, endIx);
				// System.out.println(result);
				productlist.add(result);
				index = endIx;
				x++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(productlist);
		System.out.println(Thread.currentThread().getName() + "找到" + productlist.size() + "个产品");
		GetExtraProductIds getExtraProductIds = new GetExtraProductIds();

		productlist.addAll(getExtraProductIds.geT(url));
		// System.out.println(productlist.size()+"2");
		productset.addAll(productlist);
		productlist.clear();
		productlist.addAll(productset);
		System.out.println(Thread.currentThread().getName() + "共找到" + productlist.size() + "个产品@" + url);
		return productlist;

	}

	public static void main(String[] args) {
		GetProductIds get1 = new GetProductIds();
		get1.geT("http://www.ikea.com/cn/zh/catalog/categories/departments/childrens_ikea/18723/");
	}
}
