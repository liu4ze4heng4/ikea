package com.fec.shop.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.MathTool;

import com.fec.shop.constant.Constant;
import com.fec.shop.ikea.GetAnything;
import com.fec.shop.util.Errors;
import com.fec.shop.util.HtmlUtil;
import com.fec.shop.util.SQLHelper;
import com.fec.shop.util.VelocityUtil;

public class Product {
	String buf;
	String ProductName;
	String[] ProductType;
	String pid,dotted_pid;
	String outer_cid;
	public String getOuter_cid() {
		return outer_cid;
	}

	String[] product_ids;
//	String[] title = new String[100];
	double[] price = new double[100];
	double changedFamilyPrice = 0;
	ArrayList<String> pic_id;
	LinkedList<String> mainPics;
	
	
	/**
	 * 获取和生成宝贝描述
	 * 
	 * @return
	 */
	public String getDescribtion() {
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
		context.put("ProductName", ProductName);
		context.put("ProductName", ProductType[0]);
		context.put("assembledSize", assembledSize);
		context.put("designerThoughts", designerThoughts);
		context.put("designer", designer);
		context.put("productInformation", productInformation);
		context.put("environment", environment);
		context.put("goodToKnow", goodToKnow);
		context.put("careInst", careInst);
		context.put("custMaterials", custMaterials);
		context.put("keyFeatures", keyFeatures);
		context.put("product", this);
		context.put("math", new MathTool());
		String result = VelocityUtil.filterVM("productDetail.vm", context);
		return result;
	}

	public void toPic(int p, String diypath,String type) {
		try {
			for (int i = 0; i < mainPics.size(); i++) {
				URL url = new URL("http://www.ikea.com/PIAimages/" + mainPics.get(i) + "_S" + p + ".jpg");
				URLConnection urlCon = url.openConnection();
				InputStream is = urlCon.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				FileOutputStream fos = new FileOutputStream(diypath + "\\products\\" + pid + "_" + mainPics.get(i) + "_S4." + type);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				int read;
				while ((read = bis.read()) != -1) {
					bos.write(read);
				}
				bos.close();// 不关闭，输出流不刷新，有可能得到无效图片
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Errors.addtoPEL(pid);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void toSQL() {
		SQLHelper sh = new SQLHelper();

		sh.insertWholeProductInfo(this);
	}

	public void toCSV(String diypath) {
		File path = new File(diypath + "products");
		if (path.exists() == false)
			path.mkdirs();
		toPic(4, diypath,"tbi");
		File csvfile = new File(diypath + "//" + "products.csv");
		if (csvfile.exists() == false) {
			try {
				OutputStreamWriter initWriter = new OutputStreamWriter(new FileOutputStream(csvfile, true), "GBK");
				initWriter.append(Constant.CSV_l1);
				initWriter.append(Constant.CSV_l2);
				initWriter.append(Constant.CSV_l3);
				initWriter.flush();
				initWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(csvfile, true), "GBK");
			writer.append("\"" + ProductName + ProductType[0] + "[" + dotted_pid + "]" + "\"	50006298	\"" + outer_cid + "\"	1	\"北京\"	\"北京\"	1	" + getMinumPrice()
					+ "	\"\"	58	52	2	0	0	0	0	1	2	0	\"\"	\"");
			 writer.append(getDescribtion().replace("	", " ").replace("\n","").replace("\"","'"));

			writer.append("\"	\"\"	1516110	0	\"\"	\"200\"	\"\"	0	\"");
			for (int j = 0; j < mainPics.size() && j < 5; j++)
				writer.append(pid + "_" + mainPics.get(j) + "_S4" + ":1:" + j + ":|;");
			writer.append("\"	\"\"	\"\"	\"\"	\"\"	\"" + pid + "\"	\"\"	0	0	0	1	charick	1	0	0		mysize_tp:-1	164702552	2" + "\n");
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + pid + " is not exist[csv]");
		}
		// System.out.println(Thread.currentThread().getName() + diypath +
		// "'s CRV is OK");

	}

	public double getMinumPrice() {
		double[] temp = Arrays.copyOf(price, price.length);
		Arrays.sort(temp);
		return temp[0];
	}

	public boolean isChangedFamilyPrice() {
		if (changedFamilyPrice == 0)
			return false;
		else
			return true;
	}

	public double getChangedFamilyPrice() {
		return changedFamilyPrice;
	}

	public Product(String id, String cate) {
		Arrays.fill(price, 200000);
		String[] ids = id.split(",");
		buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + ids[0] + "/");
		GetAnything something = new GetAnything();
		changedFamilyPrice = something.getPrice(buf, "<meta name=\"changed_family_price\" conten", "\" />", "changedFamilyPrice");
//		title[0] = something.geT(buf, "<meta name=\"title\" content=", "- IKEA", "");
		price[0] = something.getPrice(buf, "<div class=\"priceFamilyTextDollar\"  id=\"priceProdInfo\">", "</div>", "priceProdInfo");
		pid = ids[0];
		product_ids = ids;
		dotted_pid = something.geT(buf, "<div id=\"itemNumber\" class=\"floatLeft\">", "</div>", "product.id");
		String[] picurl = new String[100];
		picurl = something.getPicUrl(buf, pid);
		pic_id = new ArrayList<String>();
		Collections.addAll(pic_id, picurl);
		mainPics = new LinkedList<String>();
		mainPics.add(pic_id.get(0));
		outer_cid = cate;
		ProductName = something.getProductName(buf);
		ProductType = new String[100];
		ProductType[0] = something.getProductType(buf);

		for (int i = 1; i < ids.length; i++) {
			// System.out.println(ids[i]);
			buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + ids[i] + "/");
//			title[i] = something.geT(buf, "<meta name=\"title\" content=", "- IKEA", "");
			price[i] = something.getPrice(buf, "<div class=\"priceFamilyTextDollar\"  id=\"priceProdInfo\">", "</div>", "priceProdInfo");
			ProductType[i] = something.getProductType(buf);
			// System.out.println(something.getPicUrl(buf, product_id)[0]);
			Collections.addAll(pic_id, something.getPicUrl(buf, ids[i]));
			mainPics.add(something.getPicUrl(buf, ids[i])[0]);
		}
		// System.out.println(pic_id);
		// System.out.println(ProductType[1] + ProductType[0]);

	}

	public Product() {
	}

	public String getProduct_dian_id() {
		return dotted_pid;
	}

	public void setProduct_dian_id(String product_dian_id) {
		this.dotted_pid = product_dian_id;
	}

	public String getBuf() {
		return buf;
	}

	public void setBuf(String buf) {
		this.buf = buf;
	}

	public double[] getPrice() {
		return price;
	}

	public double getaPrice(int i) {
		return price[i];
	}

	public void setPrice(double[] price) {
		this.price = price;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String[] getProductType() {
		return ProductType;
	}

	public String getaProductType(int i) {
		return ProductType[i];
	}

	public void setProductType(String[] productType) {
		ProductType = productType;
	}

	
	public ArrayList<String> getPic_id() {
		return pic_id;
	}

	public void setPic_id(ArrayList<String> pic_id) {
		this.pic_id = pic_id;
	}

	public LinkedList<String> getMainPics() {
		return mainPics;
	}
	public String getaMainPic(int i) {
		return mainPics.get(i);
	}

	public void setMainPics(LinkedList<String> mainPics) {
		this.mainPics = mainPics;
	}

	public String getProduct_id() {
		return pid;
	}

	public void setProduct_id(String product_id) {
		this.pid = product_id;
	}



	
	public String getaProductId(int i) {
		return product_ids[i];
	}

	public String getColor(int i) {
		String pt = getaProductType(i);
		if (pt.contains("黄"))
			return "#e5cd00";
		if (pt.contains("红"))
			return "#cc0000";
		if (pt.contains("绿"))
			return "#22cc00";
		if (pt.contains("蓝"))
			return "#1759a8";
		if (pt.contains("橙"))
			return "#f27405";
		else
			return "#000000";
	}

	public static void main(String[] args) {
		Product p = new Product("S89869544", "680631254,680632361,680631260");
		System.out.println(p.getDescribtion().replace("\n", " ").replace("\"", "'"));
		// p.toSQL();
		// p.toFile2("E:\\IKEA123\\");
		p.toCSV("E:\\IKEA临时项目\\");
		// p.toPic(4, "E:\\QuHoo\\9\\S4\\");
		// System.out.println("!");
	}
}
/**
 * 
 */

// public void toFile2(String diypath) {
// File path = new File(diypath + "products");
// if (path.exists() == false)
// path.mkdirs();
// try {
// Writer writer = new BufferedWriter(new OutputStreamWriter(new
// FileOutputStream(diypath + "products\\" + product_id + ".html"), "gbk"));
//
// writer.write("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" background=\"http://img02.taobaocdn.com/imgextra/i2/42635718/T2c5zwXi4XXXXXXXXX_!!42635718.png\" text-align=\"left\" font-size=\"12.0px\" line-height=\"1.5\" color=\"#6a6a6a\"><tr><td style=\"font-size: 0;\"><img src=\"http://img02.taobaocdn.com/imgextra/i2/42635718/T2SBnwXgJXXXXXXXXX_!!42635718.png\"></td></tr>");
// writer.write("<tr><td style=\"padding: 10.0px 0 0;\"><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" ><tr><td width=\"8\"></td><td width=\"120\" valign=\"top\" ><table width=\"120\"><tr><td><img src=\"http://www.ikea.com/PIAimages/"
// + pic_id.get(0)
// +
// "_S2.jpg\" /></td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td align=\"center\" width=\"120\";><div style=\"width: 120.0px;word-wrap:break-word;;\">"
// + productNameProdInfo + "<br />" + productTypeProdInfo + "<br />RMB:" +
// price[0] + "<br />" + "<div></td></tr></table></td>");
// writer.write("<td width=\"600\"valign=\"top\" background=\"\"><table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
//
// if (custMaterials.length() >= 1) {
// writer.write("<tr><td width=\"580\" valign=\"top\" colspan=\"4\"><table>");
// writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>产品描述</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + custMaterials +
// "</td></tr><tr><td width=\"580\"><p style=\"width:580px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr> ");
// writer.write("</table> </td></tr>");
// }
//
// if (keyFeatures.length() >= 15) {
// writer.write("<tr><td width=\"580\" valign=\"top\" colspan=\"4\"><table>");
// writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>重要特征</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + keyFeatures +
// "</td></tr><tr><td width=\"580\"><p style=\"width:580px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr> ");
// // System.out.println(keyFeatures + "!!!");
// writer.write("</table> </td></tr>");
// }
//
// writer.write("<tr><td width=\"300\"valign=\"top\"><table>");
// if (assembledSize.length() >= 1) {
// writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>安装后尺寸</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + assembledSize + "</td></tr> ");
// }
//
// if (designer.length() > 1 || designerThoughts.length() > 1) {
// writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>设计师</td></tr>");
// }
// if (designerThoughts.length() >= 1)
// writer.write("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + designerThoughts + "</td></tr> ");
// if (designer.length() >= 1)
// writer.write("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + designer + "</td></tr> ");
// if (goodToKnow.length() >= 1) {
// writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>相关提示</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + goodToKnow + "</td></tr> ");
// }
// if (environment.length() >= 1) {
// writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>环保信息</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + environment + "</td></tr> ");
// }
//
// writer.write("</table> </td><td width=\"300\"valign=\"top\"><table>");
// writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>包装尺寸和重量</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>包装："
// + numberOfPackages + "</td></tr> ");
// if (numberOfPackages.equals("1")) {
// writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + productInformation + "</td></tr> ");
// } else {
// writer.write("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>尺寸和重量详见IKEA官网<br/>"
// + "<a href=\"http://www.ikea.com/cn/zh/catalog/products/"
// + product_id + "\">www.ikea.com/cn/zh/catalog/products" + product_id + "</a>"
// + "</td></tr> ");
// }
//
// if (careInst.length() >= 1) {
//
// writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>保养说明</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + careInst + "</td></tr> ");
// }
// if (lowestPrice.length() >= 1) {
// writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>低价格从哪里来</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
// + lowestPrice + "</td></tr> ");
// }
// // if (custMaterials.length() >= 1) {
// //
// writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>产品描述</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"+custMaterials+"</td></tr> ");}
//
// writer.write("</table> </td></tr></table></td><td width=\"2\"></td></tr>");
// if (mainPics.size() > 1) {
// writer.write(" <tr><td width=\"10\"></td><td colspan=\"4\" height=\"1\" align=\"left\"><p style=\"width:700px;height:1px;margin:0px 0px 0px 0px;border-top:1px solid #ddd;\"></p></td></tr></table><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  ><tr ><td width=\"2\"></td><td  width=\"120\" align=\"left\">");
// for (int i = 1; i < mainPics.size(); i++)
//
// writer.write("<table width=\"120\"  align=\"left\"><tr><td align=\"left\"><img src=\"http://www.ikea.com/PIAimages/"
// + mainPics.get(i)
// +
// "_S2.jpg\" /></td></tr><tr><td align=\"center\" width=\"120\";><div style=\"width: 120.0px;word-wrap:break-word;text-align: center;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
// + ProductType[i] + "<br />RMB:" + price[i] + "<br />" +
// "</div></td></tr></table>");
//
// writer.write("</td></tr></table><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >");
// }
// writer.write("<tr><td width=\"10\"></td><td colspan=\"4\" height=\"1\" align=\"left\"><p style=\"width:700px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;\"></p></td></tr>");
// for (int i = 0; i < pic_id.size(); i++)
// writer.write(" <tr><td width=\"10\"></td><td colspan=\"4\" ><img src=\"http://www.ikea.com/PIAimages/"
// + pic_id.get(i)
// +
// "_S4.jpg\" /> <img src=\"http://img02.taobaocdn.com/imgextra/i2/42635718/T2ukzvXepaXXXXXXXX_!!42635718.png\"/></td></tr>");
// writer.write("</table></td></tr><tr><td align=\"right\" style=\"padding: 0 20.0px 5.0px 0;color: #333;\">COPYRIGHT 2013 BESIDE IKEA</td></tr></table>");
// writer.close();
// } catch (IOException exp) {
// System.out.println("wrong!");
// exp.printStackTrace();
// }
// }
