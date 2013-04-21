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
import java.net.ConnectException;
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

public class ProductSimple {
	public static void main(String[] args) {
		try{
		ProductSimple p = new ProductSimple("80157916,60157917,40157918");
		IkeaUtils.initProductSimple(p);
		ProductDetail pd=new ProductDetail();
		IkeaUtils.initProductdetail(pd);}
		catch(IOException ce)
		{
			System.err.println("抓取失败IOException");
		}
	}

	String buf;
	String ProductName;
	String productTypeInfo;
	String title;

	ArrayList<String> productType;
	ArrayList<String> productIds;
	ArrayList<String> picIds;
	LinkedList<String> mainPics;
	ArrayList<Double> price;
	double changedFamilyPrice = 0;

	int num;
	String pid, dotted_pid;
	String outer_cid, seller_cid;
	String seller_cate;
	double weight = 1;
	String picpath;

	/**
	 * 构造函数
	 */
	public ProductSimple(String id) throws IOException {
		ArrayList<String> ids = new ArrayList<String>();
		Collections.addAll(ids, id.split(","));
		setProductIds(ids);
		buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + productIds.get(0) + "/");

	}

	public ProductSimple() {
	}

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
		ProductDetail pd=null;
		IkeaUtils.initProductdetail(pd);
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
			writer.append(pd.getDescribtion().replace("	", " ").replace("\n", "").replace("\"", "'"));

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
	 * 从WEB保存图片到文件
	 */
	public String getPicpathFromWeb() {
		File betterPic = new File("E:\\QuHoo\\all\\" + getPid() + "_" + getMainPics().get(0) + "_S4.jpg");
		if (betterPic.exists())
			return "E:\\QuHoo\\all\\" + getPid() + "_" + getMainPics().get(0) + "_S4.jpg";
		else if (new File("E:\\QuHoo\\tmp\\" + getPid() + "_" + getMainPics().get(0) + "_S4.jpg").exists()) {
			return "E:\\QuHoo\\tmp\\" + getPid() + "_" + getMainPics().get(0) + "_S4.jpg";

		} else {
			toPic(4, "E:\\QuHoo\\tmp\\", "jpg");
			return "E:\\QuHoo\\tmp\\" + getPid() + "_" + getMainPics().get(0) + "_S4.jpg";

		}
	}

	public double getMinumPrice() {
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.addAll(price);
		Collections.sort(temp);
		return temp.get(0);
	}

	public boolean isChangedFamilyPrice() {
		if (changedFamilyPrice == 0)
			return false;
		else
			return true;
	}

	public String getBuf() {
		return buf;
	}

	public void setBuf(String buf) {
		this.buf = buf;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getProductTypeInfo() {
		return productTypeInfo;
	}

	public void setProductTypeInfo(String productTypeInfo) {
		this.productTypeInfo = productTypeInfo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getProductType() {
		return productType;
	}

	public void setProductType(ArrayList<String> productType) {
		this.productType = productType;
	}

	public ArrayList<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(ArrayList<String> productIds) {
		this.productIds = productIds;
	}

	public ArrayList<String> getPicIds() {
		return picIds;
	}

	public void setPicIds(ArrayList<String> picIds) {
		this.picIds = picIds;
	}

	public LinkedList<String> getMainPics() {
		return mainPics;
	}

	public void setMainPics(LinkedList<String> mainPics) {
		this.mainPics = mainPics;
	}

	public ArrayList<Double> getPrice() {
		return price;
	}

	public void setPrice(ArrayList<Double> price) {
		this.price = price;
	}

	public double getChangedFamilyPrice() {
		return changedFamilyPrice;
	}

	public void setChangedFamilyPrice(double changedFamilyPrice) {
		this.changedFamilyPrice = changedFamilyPrice;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDotted_pid() {
		return dotted_pid;
	}

	public void setDotted_pid(String dotted_pid) {
		this.dotted_pid = dotted_pid;
	}

	public String getOuter_cid() {
		return outer_cid;
	}

	public void setOuter_cid(String outer_cid) {
		this.outer_cid = outer_cid;
	}

	public String getSeller_cid() {
		return seller_cid;
	}

	public void setSeller_cid(String seller_cid) {
		this.seller_cid = seller_cid;
	}

	public String getSeller_cate() {
		return seller_cate;
	}

	public void setSeller_cate(String seller_cate) {
		this.seller_cate = seller_cate;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	
}
