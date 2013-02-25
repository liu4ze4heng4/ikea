package com.fec.ikea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;


public class GetProductIds {
		ArrayList<String> productlist= new ArrayList<String>();
		HashSet< String> productset=new HashSet<String>();;
		String productid = new String();
//		String strURL = "http://www.ikea.com/cn/zh/catalog/categories/departments/eating/16048/";
		String html;
		void HtmlCatch(String strURL) throws IOException{
		URL url = new URL(strURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		html = contentBuf.toString();		
		}
		
		void geT(String url){
			try {
				HtmlCatch(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int index=0;
			int x=1;
		try{
			while(true){
		int beginIx = html.indexOf("<div id=\"item_",index);
		String beginstr ="<div id=\"item_";
		int beginIxLength =beginstr.length();
		int endIx = html.indexOf("_"+x+"\" class=\"threeColumn",beginIx);
		String result = html.substring(beginIx+beginIxLength, endIx);
//		System.out.println(result);
		productlist.add(result);
		index=endIx;
		x++;
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
//		System.out.println(productlist);
		System.out.println(Thread.currentThread().getName()+"�ҵ�"+productlist.size()+"����Ʒ");
		GetExtraProductIds getExtraProductIds=new GetExtraProductIds();
		getExtraProductIds.geT(url);
		productlist.addAll(getExtraProductIds.productlist);
//		System.out.println(productlist.size()+"2");
		productset.addAll(productlist);
		productlist.clear();
		productlist.addAll(productset);
		System.out.println(Thread.currentThread().getName()+"���ҵ�"+productlist.size()+"����Ʒ");
		}
			
		public static void main(String[] args){
			GetProductIds get1=new GetProductIds();
			get1.geT("http://www.ikea.com/cn/zh/catalog/categories/departments/bathroom/20490/");
		}
	}
