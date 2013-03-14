package com.fec.shop.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fec.shop.constant.Constant;

public class IkeaUtils {
	public static void main(String[] args) {
		List<Categroy> catList = getCatListFromFile();
		IkeaUtils.saveProductList2File(catList);
		// IkeaUtils.getProductListFromFile(0);
	}

	public static List<String> getProductListFromFile(int index) {
		List<String> pList = new ArrayList<String>();
		String pid;
		try {
			InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File(Constant.ikea_product_file + index)), "utf-8");
			BufferedReader bufReader = new BufferedReader(insReader);
			while ((pid = bufReader.readLine()) != null) {
				pList.add(pid);
			}
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pList;
	}

	public static List<Categroy> getCatListFromFile() {
		List<Categroy> pList = new ArrayList<Categroy>();
		String pid;
		String[] tempA;
		try {
			InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File(Constant.ikea_category_file)), "utf-8");
			BufferedReader bufReader = new BufferedReader(insReader);
			while ((pid = bufReader.readLine()) != null) {
				tempA = pid.split(Constant.split);
				pList.add(new Categroy(tempA[0], tempA[1]));
			}
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pList;
	}

	public static void saveCategory2File(List<Categroy> catList) {
		try {
			FileWriter fw = new FileWriter(new File(Constant.ikea_category_file));
			for (Categroy category : catList) {
				fw.write(category + "\n");
			}
			fw.flush();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("save ikea category to file finish ,number:" + catList.size());
	}

	public static void saveProductList2File(List<Categroy> catList) {
		List<Product> pList = getAllProdutIds(catList);
		int numPerFile = pList.size() / Constant.file_num_product;
		for (int i = 0; i < Constant.file_num_product + 1; i++) {
			try {
				FileWriter fw = new FileWriter(new File(Constant.ikea_product_file + i));
				for (int j = i * (numPerFile); j < (i + 1) * (numPerFile) && j < pList.size(); j++) {
					fw.write(pList.get(j) + "\n");
				}
				fw.flush();
				fw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.println("save ikea product id to file finish,number:" + pList.size());
	}

	public static List<Product> getAllProdutIds(List<Categroy> cats) {
		List<Product> result = new ArrayList<Product>();
		List<Product> tmp;
		for (Categroy categroy : cats) {
			tmp = getProductList(categroy);
			for (Product p : tmp) {
				if (result.contains(p)) {
					Product pInList = result.get(result.indexOf(p));
					if (!pInList.category.equals(p.category)) {
						System.out.println("该产品还属于其他目录：" + p.pid);
						pInList.category = pInList.category + "," + p.category;
					}
				} else {
					result.add(p);
				}
			}
		}
		return result;
	}

	private static List<Categroy> getCategoryFromHtml() {
		String categoryListUrl = "http://www.ikea.com/cn/zh/catalog/allproducts/";
		List<Categroy> allCategory = new ArrayList<Categroy>();
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
					Categroy c = new Categroy(results[1], results[0]);
					if (allCategory.contains(c)) {
						System.out.println("剔除重复类别：" + results[1]);
					} else {

						allCategory.add(c);
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

	public static List<Product> getProductList(Categroy c) {
		String html = HtmlUtil.getHtmlContent(c.url);
		while (html == null) {
			System.out.println("重新抓起：" + c.url);
			html = HtmlUtil.getHtmlContent(c.url);
		}
		List<Product> pidlist = new ArrayList<Product>();
		int index = 0;
		int x = 1;
		try {
			while (true) {
				Product p = new Product();
				int beginIx = html.indexOf("<div id=\"item_", index);
				if (beginIx <= 0)
					break;
				String beginstr = "<div id=\"item_";
				int beginIxLength = beginstr.length();
				int endIx = html.indexOf("_" + x + "\" class=\"threeColumn", beginIx);
				String pid = html.substring(beginIx + beginIxLength, endIx);
				p.category = c.name;
				p.pid = pid;
				pidlist.add(p);

				index = endIx;
				x++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		index = 10;
		String result;
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
					Product p1 = new Product();
					p1.category = c.name;
					p1.pid = result;
					pidlist.add(p1);
				}

				index = endIx;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("====================" + c.name + "抓取了：" + pidlist.size() + "个产品id");
		return pidlist;
	}

}

class Categroy {
	public String name;
	public String url;

	public Categroy(String name, String url) {
		this.name = name;
		this.url = url;
	}

	@Override
	public String toString() {
		return name + Constant.split + url;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Categroy) obj).name.equals(name);
	}

	@Override
	public int hashCode() {
		return 1;
	}
}

class Product {
	public String pid;
	public String category;

	@Override
	public String toString() {
		return category + Constant.split + pid;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Product) obj).pid.equals(pid);
	}

	@Override
	public int hashCode() {
		return 1;
	}
}
