package com.fec.ikea;
public class ProductFinder extends GetProductIds {
	void geT(String url) {
		HtmlCatch(url);
		int index = 20000;
		try {
			productlist.clear();
			while (true) {
				int beginIx = html.indexOf(
						" href=\"/cn/zh/catalog/categories/departments", index);
				int endIx = html.indexOf("</a>", beginIx);
				String tmp = html.substring(beginIx, endIx);
				String result = tmp.replace("	", "");
				tmp = result.replace(" href=\"", "http://www.ikea.com");
				result = tmp.replace("\">", "!");
				String[] results;
				results = result.split("!");
				productlist.add(results[0]);
				
				index = endIx;
//				 System.out.println(results[0]);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
		// System.out.println(productlist);
		productset.addAll(productlist);
		productlist.clear();
		productlist.addAll(productset);
		System.out.println(productlist.get(152));
		System.out.println("找到" + productlist.size() + "个产品目录");
	}

	public static void main(String[] args) {
		ProductFinder get1 = new ProductFinder();
		get1.geT("http://www.ikea.com/cn/catalog/allproducts/");
	}
}
