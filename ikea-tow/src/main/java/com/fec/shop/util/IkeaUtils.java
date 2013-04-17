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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fec.shop.constant.Constant;

public class IkeaUtils {
	public static void main(String[] args) {
		// IkeaUtils.saveProductList2File(getAllProdutIdsFromHtml(getCatListFromFile(),
		// TaobaoUtils.getCCMapFromFile()),Constant.ikea_product_file);
		IkeaUtils.getStockInfo("60169835",true,true,true);
		// SQLHelper sh=new SQLHelper();
		// sh.getProductTids();
	}
	static String quantity;
	static String info = "无发货时效信息";
	static int isheavy=-1;
	static double wholeweight=0;
	static double wholesize=0;
	public static String getQuantity() {
		return quantity;
	}

	public static String getInfo() {
		return info;
	}

	public static int getIsheavy() {
		return isheavy;
	}

	public static double getWholeweight() {
		return wholeweight;
	}

	public static double getWholesize() {
		return wholesize;
	}


	/**
	 * 获取宜家库存信息
	 */
	public static void getStockInfo(String id,boolean b_stockinfo,boolean b_weight,boolean b_size) {
		String buf = new String();
		String buf2 = new String();
		isheavy=-1;
		wholeweight=0;
		wholesize=0;
		ArrayList<Double> weight=new ArrayList<Double>();
		

		ArrayList<Double> sizer=new ArrayList<Double>();
		id = id.replace(".", "").replace("S", "");
if(b_stockinfo==true)
		{buf = HtmlUtil.getHtmlContent("http://m.ikea.com/cn/zh/store/availability/?storeCode=802&itemType=art&itemNo=" + id + "&change=true&_=1");
		if (buf == null)
			buf = HtmlUtil.getHtmlContent("http://m.ikea.com/cn/zh/store/availability/?storeCode=802&itemType=spr&itemNo=" + id + "&change=true&_=1");


		if (buf.contains("北京商场当前有库存")) {
			int beginIx = buf.indexOf("北京商场当前有库存");
			int endIx = buf.indexOf("</b>", beginIx);
			quantity = buf.substring(beginIx+"北京商场当前有库存：".length(), endIx);
			quantity = quantity.replace("<b>", "").replace(" ", "");

					if (buf.contains("联系工作人员")) {
						info = "本产品在外仓，不能及时发货";
					} else {
						info = "可以及时发货";
					}

				
		} else if (buf.contains("北京商场当前无库存")) {
			quantity = "无库存";
		} else if (buf.contains("北京商场不出售该产品")) {
			quantity = "商场不出售该产品";
		} else

			quantity = "未知错误2，请重试";
		}
if(b_weight==true||b_size==true)
{
		buf2 = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/packagepopup/" + id);
		if (buf2 == null)
		buf2 = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/packagepopup/S" + id);
		String regexStr = "<div class=\"rowContainerPackage\">[\\s\\S]*?<div class=\"clear\"></div>";
		Pattern productCell = Pattern.compile(regexStr);
		Matcher m = productCell.matcher(buf2);
		while (m.find()) {
			if (!"".equals(m.group())) {
				String content=m.group();
				int beginIx = content.indexOf("<div class=\"colPack\">");
				int endIx = content.indexOf("</div>", beginIx);
				int count=new Integer(content.substring(beginIx+"<div class=\"colPack\">".length(), endIx).replace("	", "").replace("千克","").replace("&nbsp;", ""));
				if(b_size==true)
				{
				beginIx = content.indexOf("<div class=\"colWidth\">");
				endIx = content.indexOf("</div>", beginIx);
				ArrayList<Double> size=new ArrayList<Double>();
				size.add(new Double(content.substring(beginIx+"<div class=\"colWidth\">".length(), endIx).replace("	", "").replace("厘米","").replace("&nbsp;", "0")));

				 beginIx = content.indexOf("<div class=\"colHeight\">");
				 endIx = content.indexOf("</div>", beginIx);
				size.add(new Double(content.substring(beginIx+"<div class=\"colHeight\">".length(), endIx).replace("	", "").replace("厘米","").replace("&nbsp;", "0")));
				 beginIx = content.indexOf("<div class=\"colLength\">");
				 endIx = content.indexOf("</div>", beginIx);
				size.add(new Double(content.substring(beginIx+"<div class=\"colLength\">".length(), endIx).replace("	", "").replace("厘米","").replace("&nbsp;", "0")));
				if(!size.contains((double)0))
				sizer.add(count*size.get(0)*size.get(1)*size.get(2)/1000000);
				else
					sizer.add((double) 9999);
				}
				if(b_weight==true)
				{
				beginIx = content.indexOf("<div class=\"colWeight\">");
				 endIx = content.indexOf("</div>", beginIx);
				weight.add(count*new Double(content.substring(beginIx+"<div class=\"colWeight\">".length(), endIx).replace("	", "").replace("千克","").replace("&nbsp;", "0")));
				}
				} else {
				info = "未知错误1";
			}
		}
		for(int i=0;i<sizer.size();i++)
		wholesize=wholesize+sizer.get(i);
		for(int i=0;i<weight.size();i++)
			wholeweight=wholeweight+weight.get(i);
		if(wholesize<9999){
			if(b_weight==true&&b_size==true)
			{
			if(wholesize*210<wholeweight)
		isheavy=1;
		else
			isheavy=0;}}
}	
	}

	/**
	 * 生成新增加的产品列表
	 */
	public void generateNewProductList() {
		List<Product> listInFile = new ArrayList<Product>();
		for (int i = 0; new File(Constant.ikea_product_file + i).exists(); i++) {
			listInFile.addAll(getProductListFromFile(i));
		}
		List<Product> listInHtml = getAllProdutIdsFromHtml(getCatListFromFile(), TaobaoUtils.getCCMapFromFile());
		List<Product> newAddProduct = new ArrayList<Product>();
		for (Product product : listInHtml) {
			if (!listInFile.contains(product)) {
				System.out.println("新增加产品：" + product);
				newAddProduct.add(product);
			}
		}
		if (newAddProduct.size() > 0) {
			saveProductList2File(listInHtml, Constant.ikea_product_file);
			saveProductList2File(newAddProduct, Constant.ikea_product_file_new_add);
		}

	}

	/**
	 * 从文件获取类别
	 * 
	 * @return
	 */
	public static List<Categroy> getCatListFromFile() {
		List<Categroy> pList = new ArrayList<Categroy>();
		String pid;
		String[] tempA;
		try {
			InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File(Constant.ikea_category_file)), "utf-8");
			BufferedReader bufReader = new BufferedReader(insReader);
			while (StringUtils.isNotBlank(pid = bufReader.readLine())) {
				tempA = pid.split(Constant.split);
				pList.add(new Categroy(tempA[0], tempA[1]));
			}
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pList;
	}

	/**
	 * 从网页获取类别列表
	 * 
	 * @return
	 */
	public static List<Categroy> getCategoryFromHtml() {
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

	/**
	 * 保存类别到文件
	 * 
	 * @param catList
	 */
	private static void saveCategory2File(List<Categroy> catList, String filePath) {
		try {
			FileWriter fw = new FileWriter(new File(filePath));
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

	public static List<String> getProductStrListFromFile(int index) {
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

	/**
	 * 从指定文件获取产品列表
	 * 
	 * @param index
	 * @return
	 */
	private static List<Product> getProductListFromFile(int index) {
		List<Product> pList = new ArrayList<Product>();
		String pid;
		try {
			InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File(Constant.ikea_product_file + index)), "utf-8");
			BufferedReader bufReader = new BufferedReader(insReader);
			String temp[];
			while ((pid = bufReader.readLine()) != null) {
				temp = pid.split(Constant.split);
				Product p = new Product(temp[0], temp[1], temp[2]);
				pList.add(p);
			}
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pList;
	}

	/**
	 * 保存产品到文件
	 * 
	 * @param pList
	 * @param filePath
	 */
	public static void saveProductList2File(List<Product> pList, String filePath) {
		for (int i = 0; i * (Constant.num_product_per_file) < pList.size() + 1; i++) {
			try {
				FileWriter fw = new FileWriter(new File(filePath + i));
				for (int j = i * (Constant.num_product_per_file); j < (i + 1) * (Constant.num_product_per_file) && j < pList.size(); j++) {
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

	/**
	 * 从网页获取产品列表
	 * 
	 * @param ikeacatList
	 *            宜家类目
	 * @param taobaocidMap
	 *            淘宝sellerCid
	 * @return
	 */
	public static List<Product> getAllProdutIdsFromHtml(List<Categroy> ikeacatList, Map<String, String> taobaocidMap) {
		List<Product> result = new ArrayList<Product>();
		List<Product> tmp;
		for (Categroy categroy : ikeacatList) {
			tmp = getProductListFromHtml(categroy);
			for (Product p : tmp) {
				if (result.contains(p)) {
					Product pInList = result.get(result.indexOf(p));
					if (!pInList.category.equals(p.category)) {
						Constant.baseLoger.info("该产品还属于其他目录：" + p.pid);
						pInList.category = pInList.category + "," + p.category;
						pInList.cid = pInList.cid + "," + taobaocidMap.get(p.category);
					}
				} else {
					p.cid = taobaocidMap.get(p.category);
					result.add(p);
				}
			}
		}
		return result;
	}

	/**
	 * 从网页获取单个类别下的全部产品
	 * 
	 * @param c
	 * @return
	 */
	private static List<Product> getProductListFromHtml(Categroy c) {
		String html = HtmlUtil.getHtmlContent(c.url);
		int count = 0;
		while (html == null) {
			if (count++ > 3) {
				Constant.baseLoger.info("[error]: 从类目[" + c.url + "]获取产品信息为空，尝试重新抓取了3次仍然失败");
				break;
			}
			html = HtmlUtil.getHtmlContent(c.url);
		}
		List<Product> pidlist = new ArrayList<Product>();

		if (html != null) {

			String regexStr = "<div class=\"productDetails\">[\\s\\S]*?</a>";
			Pattern productCell = Pattern.compile(regexStr);
			Matcher m = productCell.matcher(html);
			while (m.find()) {
				if (!"".equals(m.group())) {
					String date = m.group();
					int beginIx = date.indexOf("/cn/zh/catalog/products/");
					if (beginIx <= 0)
						continue;
					int endIx = date.indexOf("/\"", beginIx);
					String pid = date.substring(beginIx + 24, endIx);
					Product p = new Product();
					p.category = c.name;
					p.pid = pid;
					p.containPreviousPrice = date.contains("previousPrice") ? 1 : 0;
					if (p.containPreviousPrice == 1) {
						Constant.dailyCheepLoger.info("优惠产品：pid: " + p.pid + ",category" + p.category);
					}
					if (!pidlist.contains(p)) {
						pidlist.add(p);
					}
				}
			}

			// 再抓取json里面的
			int index = 11000;
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
		}
		Constant.baseLoger.info("====================" + c.name + "抓取了：" + pidlist.size() + "个产品id");
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
	public String cid;
	public int containPreviousPrice;

	public Product() {
	}

	public Product(String cat, String cd, String pd) {
		this.category = cat;
		this.cid = cd;
		this.pid = pd;
	}

	@Override
	public String toString() {
		return category + Constant.split + cid + Constant.split + pid + Constant.split + containPreviousPrice;
	}

	@Override
	public boolean equals(Object obj) {
		String otherPid = ((Product) obj).pid;
		return otherPid.equals(pid) || otherPid.contains(pid) || pid.contains(otherPid);
	}

	@Override
	public int hashCode() {
		return 1;
	}
}
