package com.fec.shop.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

import com.fec.shop.ikea.GetAnything;
import com.fec.shop.util.HtmlUtil;

public class Product {
	String buf;
	double[] price = { 200000, 200000, 200000, 200000, 200000, 200000, 200000, 200000, 200000, 200000 };
	String ProductName;
	String[] ProductType;
	String productNameProdInfo, productTypeProdInfo, assembledSize, keyFeatures, designerThoughts, designer, numberOfPackages, productInformation, environment, goodToKnow, careInst, lowestPrice,
			custMaterials, product_dian_id, category;

	String[] title = new String[10];
	ArrayList<String> pic_id;
	LinkedList<String> mainPics;
	String describtion;
	String product_id;
	String path;

	public String getProduct_dian_id() {
		return product_dian_id;
	}

	public void setProduct_dian_id(String product_dian_id) {
		this.product_dian_id = product_dian_id;
	}

	public String getProductNameProdInfo() {
		return productNameProdInfo;
	}

	public void setProductNameProdInfo(String productNameProdInfo) {
		this.productNameProdInfo = productNameProdInfo;
	}

	public String getProductTypeProdInfo() {
		return productTypeProdInfo;
	}

	public void setProductTypeProdInfo(String productTypeProdInfo) {
		this.productTypeProdInfo = productTypeProdInfo;
	}

	// public double getPrice() {
	// return price;
	// }
	//
	// public void setPrice(double price) {
	// this.price = price;
	// }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	// public void getPicIds() {
	// GetAnything something = new GetAnything();
	// pic_id = something.getPicUrl(buf, product_id);
	//
	// }
	public void toFile2(String diypath) {
		File path = new File(diypath + "products");
		if (path.exists() == false)
			path.mkdirs();
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(diypath + "products\\" + product_id + ".html"), "utf-8"));

			writer.write("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" background=\"e:\\bg.png\" text-align=\"left\" font-size=\"12.0px\" line-height=\"1.5\" color=\"#6a6a6a\"><tr><td style=\"font-size: 0;\"><img src=\"e:\\top.png\"></td></tr>");
			writer.write("<tr><td style=\"padding: 10.0px 0 0;\"><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" ><tr><td width=\"8\"></td><td width=\"120\" valign=\"top\" ><table width=\"120\"><tr><td><img src=\"http://www.ikea.com/PIAimages/"
					+ pic_id.get(0)
					+ "_S2.jpg\" /></td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td align=\"center\" width=\"120\";><div style=\"width: 120.0px;word-wrap:break-word;;\">"
					+ productNameProdInfo + "<br />" + productTypeProdInfo + "<br />RMB:" + price[0] + "<br />" + "<div></td></tr></table></td>");
			writer.write("<td width=\"600\"valign=\"top\" background=\"\"><table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");

			if (custMaterials.length() >= 1) {
				writer.write("<tr><td width=\"580\" valign=\"top\" colspan=\"4\"><table>");
				writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��Ʒ����</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
						+ custMaterials + "</td></tr><tr><td width=\"300\"><p style=\"width:560px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr> ");
				writer.write("</table> </td></tr>");
			}

			if (keyFeatures.length() >= 15) {
				writer.write("<tr><td width=\"580\" valign=\"top\" colspan=\"4\"><table>");
				writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��Ҫ����</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
						+ keyFeatures + "</td></tr><tr><td width=\"300\"><p style=\"width:580px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr> ");
				System.out.println(keyFeatures + "!!!");
				writer.write("</table> </td></tr>");
			}

			writer.write("<tr><td width=\"300\"valign=\"top\"><table>");
			if (assembledSize.length() >= 1) {
				writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��װ��ߴ�</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
						+ assembledSize + "</td></tr> ");
			}

			if (designer.length() >= 1 || designerThoughts.length() >= 1) {
				writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>���ʦ</td></tr>");
			}
			if (designerThoughts.length() >= 1)
				writer.write("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>" + designerThoughts + "</td></tr> ");
			if (designer.length() >= 1)
				writer.write("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>" + designer + "</td></tr> ");
			if (environment.length() >= 1) {
				writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>������Ϣ</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
						+ environment + "</td></tr> ");
			}

			writer.write("</table> </td><td width=\"300\"valign=\"top\"><table>");
			writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��װ�ߴ������</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>��װ��"
					+ numberOfPackages + "</td></tr> ");
			if (numberOfPackages.equals("1")) {
				writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
						+ productInformation + "</td></tr> ");
			} else {
				writer.write("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>�ߴ���������IKEA����<br/>" + "<a href=\"http://www.ikea.com/cn/zh/catalog/products/"
						+ product_id + "\">www.ikea.com/cn/zh/catalog/products" + product_id + "</a>" + "</td></tr> ");
			}
			if (goodToKnow.length() >= 1) {
				writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>�����ʾ</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
						+ goodToKnow + "</td></tr> ");
			}

			if (careInst.length() >= 1) {

				writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>����˵��</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
						+ careInst + "</td></tr> ");
			}
			if (lowestPrice.length() >= 1) {
				writer.write("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>�ͼ۸��������</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
						+ lowestPrice + "</td></tr> ");
			}
			// if (custMaterials.length() >= 1) {
			// writer.write("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��Ʒ����</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"+custMaterials+"</td></tr> ");}

			writer.write("</table> </td></tr></table></td><td width=\"2\"></td></tr>");
			if (mainPics.size() > 1) {
				writer.write(" <tr><td width=\"10\"></td><td colspan=\"4\" height=\"1\" align=\"left\"><p style=\"width:700px;height:1px;margin:0px 0px 0px 0px;border-top:1px solid #ddd;\"></p></td></tr></table><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  ><tr ><td width=\"2\"></td><td  width=\"120\" align=\"left\">");
				for (int i = 1; i < mainPics.size(); i++)

					writer.write("<table width=\"120\"  align=\"left\"><tr><td align=\"left\"><img src=\"http://www.ikea.com/PIAimages/"
							+ mainPics.get(i)
							+ "_S2.jpg\" /></td></tr><tr><td align=\"center\" width=\"120\";><div style=\"width: 120.0px;word-wrap:break-word;text-align: center;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
							+ ProductType[i] + "<br />RMB:" + price[i] + "<br />" + "</div></td></tr></table>");

				writer.write("</td></tr></table><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >");
			}
			writer.write("<tr><td width=\"10\"></td><td colspan=\"4\" height=\"1\" align=\"left\"><p style=\"width:700px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;\"></p></td></tr>");
			for (int i = 0; i < pic_id.size(); i++)
				writer.write(" <tr><td width=\"10\"></td><td colspan=\"4\" ><img src=\"http://www.ikea.com/PIAimages/" + pic_id.get(i) + "_S4.jpg\" /> <img src=\"e:\\BAMBER1.png\"/></td></tr>");
			writer.write("</table></td></tr><tr><td align=\"right\" style=\"padding: 0 20.0px 5.0px 0;color: #333;\">COPYRIGHT 2013 BESIDE IKEA</td></tr></table>");
			writer.close();
		} catch (IOException exp) {
			System.out.println("wrong!");
			exp.printStackTrace();
		}
	}

	// public void toFile(String diypath) {
	// File path = new File(diypath + "products");
	// if (path.exists() == false)
	// path.mkdirs();
	// try {
	// Writer writer = new BufferedWriter(new OutputStreamWriter(new
	// FileOutputStream(diypath + "products\\" + product_id + ".html"),
	// "utf-8"));
	//
	// writer.write("<p style=\"width:120px;float:left;margin:0px 15px 0px 0px;\">");
	// writer.write("<img src=\"http://www.ikea.com/PIAimages/" + pic_id[0] +
	// "_S2.jpg\" /></p>");
	// writer.write("<p style=\"width:120px;float:left;margin:30px 15px 0px 0px;\">");
	// writer.write(productNameProdInfo + "<br />" + productTypeProdInfo +
	// "<br />RMB:" + price + "<br /></p>");
	// writer.write("<p style=\"width:120px;float:left;margin:0px 15px 0px 0px;\">");
	// writer.write("<img src=\"http://www.ikea.com/PIAimages/" + pic_id[0] +
	// "_S2.jpg\" /></p>");
	// writer.write("<p style=\"width:120px;float:left;margin:30px 15px 0px 0px;\">");
	// writer.write(productNameProdInfo + "<br />" + productTypeProdInfo +
	// "<br />RMB:" + price + "<br /></p>");
	//
	// writer.write("<p style=\"width:750px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p>");
	// writer.write("<p style=\"font:12px Simsun;line-height:20px;margin:0px 0px 0px 20px;background:#FFF;color:#000;width:300px;float:left;\">");
	// if (assembledSize.length() >= 1) {
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">��װ��ߴ�</span></span><br />");
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + assembledSize + "</span>");
	// }
	// if (keyFeatures.length() >= 15) {
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;line-height:40px;\">��Ҫ����</span></span><br />");
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + keyFeatures + "</span>");
	// }
	// if (designer.length() >= 1 || designerThoughts.length() >= 1) {
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">���ʦ</span></span><br />");
	// }
	// if (designerThoughts.length() >= 1)
	// writer.write("<span style=\"line-height:25px;\">" + designerThoughts +
	// "</span><br /><br />");
	// if (designer.length() >= 1)
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + designer + "</span><br /><br />");
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">��װ�ߴ������</span></span><br />");
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">��װ��"
	// + numberOfPackages + "</span><br />");
	// if (numberOfPackages.equals("1"))
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + productInformation + "</span><br /><br />");
	// else
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">�ߴ�������������</span><br /><br />");
	// if (environment.length() >= 1) {
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">������Ϣ</span></span><br />");
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + environment + "</span><br /><br />" + "</p>");
	// }
	// writer.write("<p style=\"font:12px Simsun;line-height:20px;margin:0px 0px 0px 30px;background:#FFF;color:#000;width:250px;float:left;\">");
	// if (goodToKnow.length() >= 1) {
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">�����ʾ</span></span><br />");
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + goodToKnow + "</span><br /><br />");
	// }
	// if (careInst.length() >= 1) {
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">����˵��</span></span><br />");
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + careInst + "</span><br /><br />");
	// }
	// if (lowestPrice.length() >= 1) {
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">�ͼ۸��������</span></span><br />");
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + lowestPrice + "</span><br /><br />");
	// }
	// if (custMaterials.length() >= 1) {
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">��Ʒ����</span></span><br />");
	// writer.write("<span style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
	// + custMaterials + "</span><br /><br />");
	// }
	// //
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">��װ�ߴ������</span></span><br />"+"\n");
	// //
	// writer.write("<span style=\"font-family:Microsoft YaHei,simhei;\"><span style=\"font-size:24px;line-height:40px;\">��װ�ߴ������</span></span><br />"+"\n");
	//
	// // for(String Info:InfoList){
	// // writer.write(Info +"\n");
	// // }
	//
	// writer.close();
	// // System.out.println(id+".html has been created");
	// } catch (IOException exp) {
	// System.out.println("wrong!");
	// exp.printStackTrace();
	// }
	// }

	public StringBuilder getDescribtion() {
		StringBuilder describtion = new StringBuilder();
		describtion
				.append("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" background=\"e:\\bg.png\" text-align=\"left\" font-size=\"12.0px\" line-height=\"1.5\" color=\"#6a6a6a\"><tr><td style=\"font-size: 0;\"><img src=\"e:\\top.png\"></td></tr>");
		describtion
				.append("<tr><td style=\"padding: 10.0px 0 0;\"><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" ><tr><td width=\"8\"></td><td width=\"120\" valign=\"top\" ><table width=\"120\"><tr><td><img src=\"http://www.ikea.com/PIAimages/"
						+ pic_id.get(0)
						+ "_S2.jpg\" /></td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td align=\"center\" width=\"120\";><div style=\"width: 120.0px;word-wrap:break-word;;\">"
						+ productNameProdInfo + "<br />" + productTypeProdInfo + "<br />RMB:" + price[0] + "<br />" + "<div></td></tr></table></td>");
		describtion.append("<td width=\"600\"valign=\"top\" background=\"\"><table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");

		if (custMaterials.length() >= 1) {
			describtion.append("<tr><td width=\"580\" valign=\"top\" colspan=\"4\"><table>");
			describtion
					.append("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��Ʒ����</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
							+ custMaterials + "</td></tr><tr><td width=\"300\"><p style=\"width:560px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr> ");
			describtion.append("</table> </td></tr>");
		}

		if (keyFeatures.length() >= 15) {
			describtion.append("<tr><td width=\"580\" valign=\"top\" colspan=\"4\"><table>");
			describtion
					.append("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��Ҫ����</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
							+ keyFeatures + "</td></tr><tr><td width=\"300\"><p style=\"width:580px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr> ");
			System.out.println(keyFeatures + "!!!");
			describtion.append("</table> </td></tr>");
		}

		describtion.append("<tr><td width=\"300\"valign=\"top\"><table>");
		if (assembledSize.length() >= 1) {
			describtion
					.append("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��װ��ߴ�</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
							+ assembledSize + "</td></tr> ");
		}

		if (designer.length() >= 1 || designerThoughts.length() >= 1) {
			describtion
					.append("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>���ʦ</td></tr>");
		}
		if (designerThoughts.length() >= 1)
			describtion.append("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>" + designerThoughts + "</td></tr> ");
		if (designer.length() >= 1)
			describtion.append("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>" + designer + "</td></tr> ");
		if (environment.length() >= 1) {
			describtion
					.append("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>������Ϣ</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
							+ environment + "</td></tr> ");
		}

		describtion.append("</table> </td><td width=\"300\"valign=\"top\"><table>");
		describtion
				.append("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��װ�ߴ������</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>��װ��"
						+ numberOfPackages + "</td></tr> ");
		if (numberOfPackages.equals("1")) {
			describtion
					.append("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
							+ productInformation + "</td></tr> ");
		} else {
			describtion.append("<tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>�ߴ���������IKEA����<br/>" + "<a href=\"http://www.ikea.com/cn/zh/catalog/products/"
					+ product_id + "\">www.ikea.com/cn/zh/catalog/products" + product_id + "</a>" + "</td></tr> ");
		}
		if (goodToKnow.length() >= 1) {
			describtion
					.append("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>�����ʾ</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
							+ goodToKnow + "</td></tr> ");
		}

		if (careInst.length() >= 1) {

			describtion
					.append("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>����˵��</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
							+ careInst + "</td></tr> ");
		}
		if (lowestPrice.length() >= 1) {
			describtion
					.append("<tr><td width=\"300\"><p style=\"width:280px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p></td></tr><tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>�ͼ۸��������</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"
							+ lowestPrice + "</td></tr> ");
		}
		// if (custMaterials.length() >= 1) {
		// describtion.append("<tr style=\"font-family:Microsoft YaHei,simhei;font-size: 14px;line-height: 18px;color: #333;margin-bottom: 0.20em;;\"><td>��Ʒ����</td></tr><tr style=\"text-align: left;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\"><td>"+custMaterials+"</td></tr> ");}

		describtion.append("</table> </td></tr></table></td><td width=\"2\"></td></tr>");
		if (mainPics.size() > 1) {
			describtion
					.append(" <tr><td width=\"10\"></td><td colspan=\"4\" height=\"1\" align=\"left\"><p style=\"width:700px;height:1px;margin:0px 0px 0px 0px;border-top:1px solid #ddd;\"></p></td></tr></table><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  ><tr ><td width=\"2\"></td><td  width=\"120\" align=\"left\">");
			for (int i = 1; i < mainPics.size(); i++)

				describtion
						.append("<table width=\"120\"  align=\"left\"><tr><td align=\"left\"><img src=\"http://www.ikea.com/PIAimages/"
								+ mainPics.get(i)
								+ "_S2.jpg\" /></td></tr><tr><td align=\"center\" width=\"120\";><div style=\"width: 120.0px;word-wrap:break-word;text-align: center;font-size: 12.0px;line-height: 1.5;color: #6a6a6a;\">"
								+ ProductType[i] + "<br />RMB:" + price[i] + "<br />" + "</div></td></tr></table>");

			describtion.append("</td></tr></table><table width=\"750\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >");
		}
		describtion
				.append("<tr><td width=\"10\"></td><td colspan=\"4\" height=\"1\" align=\"left\"><p style=\"width:700px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;\"></p></td></tr>");
		for (int i = 0; i < pic_id.size(); i++)
			describtion.append(" <tr><td width=\"10\"></td><td colspan=\"4\" ><img src=\"http://www.ikea.com/PIAimages/" + pic_id.get(i) + "_S4.jpg\" /> <img src=\"e:\\BAMBER1.png\"/></td></tr>");
		describtion.append("</table></td></tr><tr><td align=\"right\" style=\"padding: 0 20.0px 5.0px 0;color: #333;\">COPYRIGHT 2013 BESIDE IKEA</td></tr></table>");

		return describtion;
	}

	public void toPic(int p, String diypath) {
		System.out.println(Thread.currentThread().getName() + "is Saveing Pic" + product_id);
		// try {
		// captureHtml(id);
		// // pic_id = something.getPicUrl(buf,id);
		// } catch (Excepton e) {
		// System.out.print(id + " is not exist[pic]\n");
		// return false;
		// }
		try {
			for (int i = 0; i < mainPics.size(); i++) {
				URL url = new URL("http://www.ikea.com/PIAimages/" + mainPics.get(i) + "_S" + p + ".jpg");
				URLConnection urlCon = url.openConnection();
				InputStream is = urlCon.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				FileOutputStream fos = new FileOutputStream(diypath + "\\products\\" + product_id + "_" + mainPics.get(i) + "_S" + p + ".tbi");
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				int read;
				while ((read = bis.read()) != -1) {
					bos.write(read);
				}
				bos.close();// ���رգ��������ˢ�£��п��ܵõ���ЧͼƬ
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// public void toSQL() {
	// ProductDao pd = new ProductDao();
	// setProduct_dian_id(product_dian_id);
	// setProductNameProdInfo(productNameProdInfo);
	// setProductTypeProdInfo(productTypeProdInfo);
	// setPrice(new Double(price));
	// System.out.println("===============" + productNameProdInfo + "  " +
	// productTypeProdInfo + "===============");
	// setCategory(category);
	// pd.insert(this);
	// }

	public void toCSV(String diypath) {
		File path = new File(diypath + "products");
		if (path.exists() == false)
			path.mkdirs();
		toPic(4, diypath);
		File csvfile = new File(diypath + "//" + "products.csv");
		if (csvfile.exists() == false) {
			try {
				Writer initWriter = new BufferedWriter(new FileWriter(csvfile, true));

				initWriter.write("version 1.00" + "\n");
				initWriter
						.write("title	cid	seller_cids	stuff_status	location_state	location_city	item_type	price	auction_increment	num	valid_thru	freight_payer	post_fee	ems_fee	express_fee	has_invoice	has_warranty	approve_status	has_showcase	list_time	description	cateProps	postage_id	has_discount	modified	upload_fail_msg	picture_status	auction_point	picture	video	skuProps	inputPids	inputValues	outer_id	propAlias	auto_fill	num_id	local_cid	navigation_type	user_name	syncStatus	is_lighting_consigment	is_xinpin	foodparame	features	global_stock_type	sub_stock_type"
								+ "\n");
				initWriter
						.write("��������	������Ŀ	������Ŀ	�¾ɳ̶�	ʡ	����	���۷�ʽ	�����۸�	�Ӽ۷���	��������	��Ч��	�˷ѳе�	ƽ��	EMS	���	��Ʊ	����	����ֿ�	�����Ƽ�	��ʼʱ��	��������	��������	�ʷ�ģ��ID	��Ա����	�޸�ʱ��	�ϴ�״̬	ͼƬ״̬	�������	��ͼƬ	��Ƶ	�����������	�û�����ID��	�û�������-ֵ��	�̼ұ���	�������Ա���	��������	����ID	����ID	��������	�˻�����	����״̬	���緢��	��Ʒ	ʳƷר��	�����	�������	������"
								+ "\n");
				initWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Writer writer = new BufferedWriter(new FileWriter(csvfile, true));

			writer.append("\"" + productNameProdInfo + productTypeProdInfo + "[" + product_dian_id + "]" + "\"	50006298	\"" + category + "\"	1	\"����\"	\"����\"	1	" + getMinumPrice()
					+ "	\"\"	58	52	2	0	0	0	0	1	2	0	\"\"	\"");
			writer.append(getDescribtion().toString().replaceAll("\"", "'"));

			writer.append("\"	\"\"	1516110	0	\"\"	\"200\"	\"\"	0	\"");
			for (int j = 0; j < mainPics.size(); j++)
				writer.append(product_id+"_"+ mainPics.get(j) + "_S4" + ":1:" + j + ":|;");
			writer.append("\"	\"\"	\"\"	\"\"	\"\"	\""+product_idh+"\"	\"\"	0	0	0	1	charick	1	0	0		mysize_tp:-1	164702552	2" + "\n");
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + product_id + " is not exist[csv]");
		}
		System.out.println(Thread.currentThread().getName() + diypath + "'s CRV is OK");

	}

	private double getMinumPrice() {
		Arrays.sort(price);
		return price[0];
	}

	public Product(String id, String cate) {
		System.out.println(id);
		String[] ids = id.split(",");
		buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + ids[0] + "/");
		GetAnything something = new GetAnything();
		title[0] = something.geT(buf, "<meta name=\"title\" content=", "- IKEA", "");
		price[0] = something.getPrice(buf, "<div class=\"priceFamilyTextDollar\"  id=\"priceProdInfo\">", "</div>", "priceProdInfo");
		productNameProdInfo = something.geT(buf, "id=\"productNameProdInfo\">", "</div>", "productNameProdInfo");
		productTypeProdInfo = something.geT(buf, "<div class=\"prodInfoRow\"  id=\"productTypeProdInfo\">", "</div>", "productTypeProdInfo");
		assembledSize = something.geT(buf, "<div id =\"metric\" class=\"texts\"", "</div>", "assembledSize");
		keyFeatures = something.geT(buf, "<div id=\"custBenefit\" class=\"texts keyFeaturesmargin\">", "<div id=\"dessection\" class=\"productInformation prodInfoSub\"", "keyFeatures");
		designerThoughts = something.geT(buf, "<div id=\"designerThoughts\" class=\"texts\">", "</div>", "designerThoughts");
		designer = something.getDesigner(buf);
		numberOfPackages = something.geT(buf, "<span id=\"numberOfPackages\">", "</span>", "numberOfPackages");
		productInformation = something.geT(buf, "<div class=\"texts\" style=\"width: 200px;\">", "</div>", "productInformation");
		environment = something.geT(buf, "<div id=\"environment\" class=\"texts\">", "</div>", "environment");
		goodToKnow = something.geT(buf, "<div id=\"goodToKnow\" class=\"texts\">", "</div>", "goodToKnow");
		careInst = something.geT(buf, "<div id=\"careInst\" class=\"texts Wdth\">", "</div>", "careInst");
		lowestPrice = something.geT(buf, "<div id=\"lowestPrice\" class=\"texts\"><div class=\"prodInfoHeadline\">", "</div>", "lowestPrice");
		custMaterials = something.geT(buf, "<div id=\"custMaterials\" class=\"texts\">", "</div>", "custMaterials");
		product_id = ids[0];
		product_dian_id = something.geT(buf, "<div id=\"itemNumber\" class=\"floatLeft\">", "</div>", "product.id");
		String[] picurl = new String[10];
		picurl = something.getPicUrl(buf, product_id);
		pic_id = new ArrayList<String>();
		Collections.addAll(pic_id, picurl);
		mainPics = new LinkedList<String>();
		mainPics.add(pic_id.get(0));
		category = cate;
		ProductName = something.getProductName(buf);
		ProductType = new String[10];
		ProductType[0] = something.getProductType(buf);

		for (int i = 1; i < ids.length; i++) {
			System.out.println(ids[i]);
			buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + ids[i] + "/");
			title[i] = something.geT(buf, "<meta name=\"title\" content=", "- IKEA", "");
			price[i] = something.getPrice(buf, "<div class=\"priceFamilyTextDollar\"  id=\"priceProdInfo\">", "</div>", "priceProdInfo");
			ProductType[i] = something.getProductType(buf);
			System.out.println(something.getPicUrl(buf, product_id)[0]);
			Collections.addAll(pic_id, something.getPicUrl(buf, ids[i]));
			mainPics.add(something.getPicUrl(buf, ids[i])[0]);
		}
		System.out.println(pic_id);
		System.out.println(ProductType[1] + ProductType[0]);

	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Product p = new Product("20208532,60174219,S69875838,S39875401,S69897251,S29897253", "");
		// p.toSQL();
		// p.toFile2("E:\\IKEA123\\");
		p.toCSV("E:\\IKEA123\\");
		// p.toPic(4, "E:\\IKEA123\\");
		// System.out.println("!");
	}
}
