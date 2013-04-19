package com.fec.shop.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.MathTool;

import com.fec.shop.constant.Constant;
import com.fec.shop.ikea.GetAnything;
import com.fec.shop.model.Product;
import com.taobao.api.ApiException;

public class IkeaUtils {
	public static void main(String[] args) {
		// IkeaUtils.saveProductList2File(getAllProdutIdsFromHtml(getCatListFromFile(),
		// TaobaoUtils.getCCMapFromFile()),Constant.ikea_product_file);
		IkeaUtils.setStockInfo("60169835",true,true,true);
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
public static Product initProduct(String id)
{Product p=new Product();
String[] ids = id.split(",");
String buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + ids[0] + "/");
Map<String, String> cmap=TaobaoUtils.getCCMapFromFile();
double[] price = new double[100];
Arrays.fill(price, 200000);
	

try{
	GetAnything something = new GetAnything();
	double ChangedFamilyPrice=something.getPrice(buf, "<meta name=\"changed_family_price\" conten", "\" />", "changedFamilyPrice"); 
	price[0] = something.getPrice(buf, "<div class=\"priceFamilyTextDollar\"  id=\"priceProdInfo\">", "</div>", "priceProdInfo");
	String dotted_pid=something.geT(buf, "<div id=\"itemNumber\" class=\"floatLeft\">", "</div>", "product.id");
	String[] picurl = new String[100];
	picurl = something.getPicUrl(buf, ids[0]);
	ArrayList<String> pic_id = new ArrayList<String>();
	Collections.addAll(pic_id, picurl);
	LinkedList<String> mainPics = new LinkedList<String>();
	mainPics.add(pic_id.get(0));
	String ProductName = something.getProductName(buf);
	String[] ProductType = new String[100];
	ProductType[0] = something.getProductType(buf);
	String productTypeInfo = something.getProductTypeInfo(buf);
	String title=ProductName+productTypeInfo + "[" + dotted_pid + "]";
	for (int i = 1; i < ids.length; i++) {
		buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + ids[i] + "/");

		price[i] = something.getPrice(buf, "<div class=\"priceFamilyTextDollar\"  id=\"priceProdInfo\">", "</div>", "priceProdInfo");
		ProductType[i] = something.getProductType(buf);
		Collections.addAll(pic_id, something.getPicUrl(buf, ids[i]));
		mainPics.add(something.getPicUrl(buf, ids[i])[0]);
	}
	String seller_cid=null;
	String seller_cate=null;
	if (buf.contains("IRWStats.subCategoryLocal\" content=\"")) {
		seller_cid = something.getSeller_cids(buf, cmap);
		seller_cate = something.getSeller_cates(buf, cmap);
	}
	p.setPrice(price);
	p.setChangedFamilyPrice(ChangedFamilyPrice);
	p.setPid(ids[0]);
	p.setProduct_ids(ids);
	p.setDotted_pid(dotted_pid);
	p.setPic_id(pic_id);
	p.setMainPics(mainPics);
	p.setProductName(ProductName);
	p.setProductType(ProductType);
	p.setProductTypeInfo(productTypeInfo);
	p.setTitle(title);
	p.setSeller_cid(seller_cid);
p.setSeller_cate(seller_cate);
}
catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return p;
}

public static void initProductdescribtion(Product p) {
	String buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + p.getPid() + "/");
	GetAnything something = new GetAnything();
	String assembledSize = something.geT(buf, "<div id =\"metric\" class=\"texts\"", "</div>", "assembledSize");
	String designerThoughts = something.geT(buf, "<div id=\"designerThoughts\" class=\"texts\">", "</div>", "designerThoughts");
	String designer = something.getDesigner(buf);
	// String numberOfPackages = something.geT(buf,
	// "<span id=\"numberOfPackages\">", "</span>", "numberOfPackages");
	String productInformation = something.geT(buf, "<div class=\"texts\" style=\"width: 200px;\">", "</div>", "productInformation");
	String environment = something.geT(buf, "<div id=\"environment\" class=\"texts\">", "</div>", "environment");
	String goodToKnow = something.geT(buf, "<div id=\"goodToKnow\" class=\"texts\">", "</div>", "goodToKnow");
	String careInst = something.geT(buf, "<div id=\"careInst\" class=\"texts Wdth\">", "</div>", "careInst");
	// String lowestPrice = something.geT(buf,
	// "<div id=\"lowestPrice\" class=\"texts\"><div class=\"prodInfoHeadline\">",
	// "</div>", "lowestPrice");
	String custMaterials = something.geT(buf, "<div id=\"custMaterials\" class=\"texts\">", "</div>", "custMaterials");
	String keyFeatures = something.getKeyFeatures(buf);

	VelocityContext context = new VelocityContext();
	context.put("ProductName", p.getProductName());
	context.put("ProductName", p.getProductType[0]);
	context.put("assembledSize", assembledSize);
	context.put("designerThoughts", designerThoughts);
	context.put("designer", designer);
	context.put("productInformation", productInformation);
	context.put("environment", environment);
	context.put("goodToKnow", goodToKnow);
	context.put("careInst", careInst);
	context.put("custMaterials", custMaterials);
	context.put("keyFeatures", keyFeatures);
String[] productId=p.getProduct_ids();
context.put("productId", productId);
	String[] productType=p.getProductType();
	context.put("productType", productType);
	ArrayList<String> typecolorlist=new ArrayList<String>(); 
	for(String pt:productType)
		{String typecolor;
		if (pt.contains("黄"))
			typecolor= "#e5cd00";
		else if (pt.contains("红"))
			typecolor= "#cc0000";
		else if (pt.contains("绿"))
			typecolor= "#22cc00";
		else if (pt.contains("蓝"))
			typecolor= "#1759a8";
		else if (pt.contains("橙"))
			typecolor= "#f27405";
		else
			typecolor= "#000000";
		typecolorlist.add(typecolor);
	}
	context.put("typecolorlist", typecolorlist);
	double[] price=p.getPrice();
	context.put("price", price);
	ArrayList<String> pic_id=p.getPic_id();
	context.put("pic_id", pic_id);
	String productName=p.getProductName();
	context.put("productName", productName);
	context.put("product_id", p.getPid());
	context.put("mainPics", p.getMainPics());
	
	context.put("product", p);
	context.put("math", new MathTool());
	String result = VelocityUtil.filterVM("productDetail.vm", context);
	p.setDescribtion(result);
}

	/**
	 * 获取宜家库存信息
	 */
	public static void setStockInfo(String id,boolean b_stockinfo,boolean b_weight,boolean b_size) {
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
		List<SmallProduct> listInFile = new ArrayList<SmallProduct>();
		for (int i = 0; new File(Constant.ikea_product_file + i).exists(); i++) {
			listInFile.addAll(getProductListFromFile(i));
		}
		List<SmallProduct> listInHtml = getAllProdutIdsFromHtml(getCatListFromFile(), TaobaoUtils.getCCMapFromFile());
		List<SmallProduct> newAddProduct = new ArrayList<SmallProduct>();
		for (SmallProduct product : listInHtml) {
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
	private static List<SmallProduct> getProductListFromFile(int index) {
		List<SmallProduct> pList = new ArrayList<SmallProduct>();
		String pid;
		try {
			InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File(Constant.ikea_product_file + index)), "utf-8");
			BufferedReader bufReader = new BufferedReader(insReader);
			String temp[];
			while ((pid = bufReader.readLine()) != null) {
				temp = pid.split(Constant.split);
				SmallProduct p = new SmallProduct(temp[0], temp[1], temp[2]);
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
	public static void saveProductList2File(List<SmallProduct> pList, String filePath) {
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
	public static List<SmallProduct> getAllProdutIdsFromHtml(List<Categroy> ikeacatList, Map<String, String> taobaocidMap) {
		List<SmallProduct> result = new ArrayList<SmallProduct>();
		List<SmallProduct> tmp;
		for (Categroy categroy : ikeacatList) {
			tmp = getProductListFromHtml(categroy);
			for (SmallProduct p : tmp) {
				if (result.contains(p)) {
					SmallProduct pInList = result.get(result.indexOf(p));
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
	private static List<SmallProduct> getProductListFromHtml(Categroy c) {
		String html = HtmlUtil.getHtmlContent(c.url);
		int count = 0;
		while (html == null) {
			if (count++ > 3) {
				Constant.baseLoger.info("[error]: 从类目[" + c.url + "]获取产品信息为空，尝试重新抓取了3次仍然失败");
				break;
			}
			html = HtmlUtil.getHtmlContent(c.url);
		}
		List<SmallProduct> pidlist = new ArrayList<SmallProduct>();

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
					SmallProduct p = new SmallProduct();
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
						SmallProduct p1 = new SmallProduct();
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

class SmallProduct {

	public String pid;
	public String category;
	public String cid;
	public int containPreviousPrice;

	public SmallProduct() {
	}



	public SmallProduct(String cat, String cd, String pd) {
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
		String otherPid = ((SmallProduct) obj).pid;
		return otherPid.equals(pid) || otherPid.contains(pid) || pid.contains(otherPid);
	}

	@Override
	public int hashCode() {
		return 1;
	}
}
