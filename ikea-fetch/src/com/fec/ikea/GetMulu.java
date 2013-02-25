package com.fec.ikea;

import java.io.IOException;

public class GetMulu extends GetProductIds {
	String getmus(String url) {
		try {
			HtmlCatch(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int beginIx = html.indexOf("IRWStats.subCategoryLocal\" content=\"");
		String beginstr = "IRWStats.subCategoryLocal\" content=\"";
		int beginIxLength = beginstr.length();
		int endIx = html.indexOf("\" />", beginIx);
		String mulu = html.substring(beginIx + beginIxLength, endIx);
		beginIx = html.indexOf("IRWStats.categoryLocal\" content=\"");
		beginstr = "IRWStats.categoryLocal\" content=\"";
		beginIxLength = beginstr.length();
		endIx = html.indexOf("\" />", beginIx);
		String category = html.substring(beginIx + beginIxLength, endIx);
		String result = category + "\\" + mulu;
		System.out.println(Thread.currentThread().getName() + "´´½¨" + result);
		return result;
	}
}
