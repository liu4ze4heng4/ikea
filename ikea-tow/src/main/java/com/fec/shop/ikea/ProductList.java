package com.fec.shop.ikea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import com.fec.shop.util.HtmlUtil;


public class ProductList {
	ArrayList<String> productIds = new ArrayList<String>();


	private String getCategoryName(String html) {
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
		String html;
		try {
			html = HtmlUtil.getHtmlContent(categoryUrl);
		
		String cn=getCategoryName(html);
		int index = 0;
		int x = 1;

			productIds.clear();
			while (true) {
				int beginIx = html.indexOf("<div id=\"item_", index);
				if(beginIx<=0) break;
				String beginstr = "<div id=\"item_";
				int beginIxLength = beginstr.length();
				int endIx = html.indexOf("_" + x + "\" class=\"threeColumn", beginIx);
				String result = html.substring(beginIx + beginIxLength, endIx);
				result=result+"!"+cn;
				productIds.add(result);
				index = endIx;
				x++;
			}
		
	
		productIds.addAll(getExtraProductUrls(html));
		HashSet<String> productUrlSet = new HashSet<String>();
		productUrlSet.addAll(productIds);
		productIds.clear();
		productIds.addAll(productUrlSet);
		System.out.println(Thread.currentThread().getName() + "======" + productIds.size() + "==========" + cn);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return productIds;
	}

	private ArrayList<String> getExtraProductUrls(String html) {
		
		ArrayList<String> extraProductUrls = new ArrayList<String>();
		ArrayList<String> extraProductUrlsNcate=new ArrayList<String>();
		String cn=getCategoryName(html);

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
					String element=new String();
					for(int i=0;i<extraProductUrls.size();i++)
					{element=extraProductUrls.get(i)+"!"+cn;
					extraProductUrlsNcate.add(element);}
				}
				index = endIx;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return extraProductUrlsNcate;
	}

	}