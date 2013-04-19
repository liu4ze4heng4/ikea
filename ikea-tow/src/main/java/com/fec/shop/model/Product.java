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
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.MathTool;

import com.fec.shop.constant.Constant;
import com.fec.shop.ikea.GetAnything;
import com.fec.shop.util.Errors;
import com.fec.shop.util.HtmlUtil;
import com.fec.shop.util.IkeaUtils;
import com.fec.shop.util.SQLHelper;
import com.fec.shop.util.TaobaoUtils;
import com.fec.shop.util.VelocityUtil;

public class Product {
	public static void main(String[] args) {
		Product p = new Product();
		p = IkeaUtils.initProduct("S09909105");
		IkeaUtils.initProductdescribtion(p);
		// p.toSQL();
		// p.toFile2("E:\\IKEA123\\");
		p.toCSV("E:\\IKEA临时项目\\");
		// p.toPic(4, "E:\\QuHoo\\9\\S4\\");
		// System.out.println("!");
	}

	String ProductName;
	String productTypeInfo;
	String title;
	String describtion;
	String[] ProductType;
	String[] product_ids;
	ArrayList<String> pic_id;
	LinkedList<String> mainPics;
	double[] price = new double[100];
	double changedFamilyPrice = 0;

	int num;
	String pid, dotted_pid;
	String outer_cid, seller_cid;
	String seller_cate;
	double weight=1;
	String picpath;
	public String getPicpath() {
		return picpath;
	}

	public String[] getProductType;

	
	/**
	 * 保存图片
	 * 
	 * @return
	 */
	public void toPic(int p, String diypath, String type) {
		try {
			for (int i = 0; i < mainPics.size(); i++) {
				URL url = new URL("http://www.ikea.com/PIAimages/" + mainPics.get(i) + "_S" + p + ".jpg");
				URLConnection urlCon = url.openConnection();
				InputStream is = urlCon.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				FileOutputStream fos = new FileOutputStream(diypath + "\\\\" + pid + "_" + mainPics.get(i) + "_S4." + type);
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

	/**
	 * 保存CSV
	 * 
	 * @param diypath
	 */
	public void toCSV(String diypath) {
		File path = new File(diypath + "products");
		if (path.exists() == false)
			path.mkdirs();
		toPic(4, diypath, "tbi");
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
			writer.append("\"" + ProductName + productTypeInfo + "[" + dotted_pid + "]" + "\"	50006298	\"" + seller_cid + "\"	1	\"北京\"	\"北京\"	1	" + getMinumPrice()
					+ "	\"\"	58	52	2	0	0	0	0	1	2	0	\"\"	\"");
			writer.append(getDescribtion().replace("	", " ").replace("\n", "").replace("\"", "'"));

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
	/**
	 * getter&setter
	 * @param describtion
	 */
	
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getSeller_cid() {
		return seller_cid;
	}

	public String getSeller_cate() {
		return seller_cate;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getPicpathFromWeb() {
		File betterPic = new File("E:\\QuHoo\\all\\" + getProduct_id() + "_" + getaMainPic(0) + "_S4.jpg");
		if (betterPic.exists())
			return "E:\\QuHoo\\all\\" + getProduct_id() + "_" + getaMainPic(0) + "_S4.jpg";
		else if (new File("E:\\QuHoo\\tmp\\" + getProduct_id() + "_" + getaMainPic(0) + "_S4.jpg").exists()) {
			return "E:\\QuHoo\\tmp\\" + getProduct_id() + "_" + getaMainPic(0) + "_S4.jpg";

		} else {
			toPic(4, "E:\\QuHoo\\tmp\\", "jpg");
			return "E:\\QuHoo\\tmp\\" + getProduct_id() + "_" + getaMainPic(0) + "_S4.jpg";

		}
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String[] getProduct_ids() {
		return product_ids;
	}

	public void setProduct_ids(String[] product_ids) {
		this.product_ids = product_ids;
	}

	public void setProductTypeInfo(String productTypeInfo) {
		this.productTypeInfo = productTypeInfo;
	}

	public void setDotted_pid(String dotted_pid) {
		this.dotted_pid = dotted_pid;
	}

	public void setOuter_cid(String outer_cid) {
		this.outer_cid = outer_cid;
	}

	public void setSeller_cid(String seller_cid) {
		this.seller_cid = seller_cid;
	}

	public void setSeller_cate(String seller_cate) {
		this.seller_cate = seller_cate;
	}

	public void setChangedFamilyPrice(double changedFamilyPrice) {
		this.changedFamilyPrice = changedFamilyPrice;
	}

	public String getOuter_cid() {
		return outer_cid;
	}

	public String getProductTypeInfo() {
		return productTypeInfo;
	}

	public String getDotted_pid() {
		return dotted_pid;
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

	

	public String getDescribtion() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProduct_dian_id() {
		return dotted_pid;
	}

	public void setProduct_dian_id(String product_dian_id) {
		this.dotted_pid = product_dian_id;
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

}
