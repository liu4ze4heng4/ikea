package com.fec.ikea;

import java.util.ArrayList;

class GetAnything {
	ArrayList<String> InfoList = new ArrayList<String>();
	String Info = new String();
	String[] PicUrl;

	String geT(String buf, String beginstr, String endstr, String title) {
		int beginIx = buf.indexOf(beginstr);
		String beginIndex = beginstr;
		int beginIxLength = beginIndex.length();
		int endIx = buf.indexOf(endstr, beginIx);
		String result = buf.substring(beginIx + beginIxLength, endIx);
		String tmp = result.replace("<div class=\"designerName\">", "<br />---");
		String tmp1 = tmp.replace("style=\"display:none\">", "");
		tmp = tmp1.replace("style=\"display:block\">", "");
		result = tmp.replace("	", "");
		tmp = result.replace("<div>", "");
		result = tmp.replace("</div>", "<br />");
		String result_f = result.replace(" ", "");
		// Info=title+ ":" + result_f;
		Info = result_f;
		// System.out.println(Info);
		// InfoList.add(Info);
		return Info;

	}

	String[] getPicUrl(String buf, String id) {

		int beginIx = buf.indexOf("\"zoom\":[", buf.indexOf("availabilityUrl\":\"/cn/zh/catalog/availability/" + id) - 1500);
		int endIx = buf.indexOf("]", beginIx);
		String result = buf.substring(beginIx + "\"zoom\":[".length(), endIx);
		String result1 = result.replace("\"/PIAimages/", "");
		result = result1.replace("_S5.JPG\"", "");
		result1 = result.replace("_S5.jpg\"", "");
		PicUrl = result1.split(",");

		return PicUrl;

	}

	String getPrice(String buf, String beginstr, String endstr, String title) {
		int beginIx = buf.indexOf(beginstr);
		String beginIndex = beginstr;
		int beginIxLength = beginIndex.length();
		int endIx = buf.indexOf(endstr, beginIx);
		String tmp = buf.substring(beginIx + beginIxLength + 5, endIx);
		String result = tmp.replace("</b>", "");
		tmp = result.replace(",", "");
		// System.out.println(result);
		// InfoList.add(Info);
		return tmp;

	}
	String getCategory(String buf){
		int beginIx = buf.indexOf("<meta name=\"category_name\" content=\"");
		int endIx = buf.indexOf("\" />", beginIx);
		String tmp = buf.substring(beginIx + "<meta name=\"category_name\" content=\"".length(), endIx);
		return tmp;
	}

}