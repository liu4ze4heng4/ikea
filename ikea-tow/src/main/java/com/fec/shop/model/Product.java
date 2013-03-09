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
import java.util.List;
import java.util.Map;

import com.fec.shop.dao.ProductDao;
import com.fec.shop.ikea.CaptureHtml;
import com.fec.shop.ikea.GetAnything;

public class Product {
	String buf;
	double price;
	String title, productNameProdInfo, productTypeProdInfo, assembledSize, keyFeatures, designerThoughts, designer, numberOfPackages,
			productInformation, environment, goodToKnow, careInst, lowestPrice, custMaterials, product_dian_id, category;
	String[] pic_id;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void getPicIds() {
		GetAnything something = new GetAnything();
		pic_id = something.getPicUrl(buf, product_id);

	}

	public void toFile(String diypath) {
		File path = new File(diypath + "products");
		if (path.exists() == false)
			path.mkdirs();
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(diypath + "products\\" + product_id + ".html"),
					"utf-8"));

			writer.write("<p style=\"width:120px;float:left;margin:0px 15px 0px 0px;\">");
			writer.write("<img src=\"http://www.ikea.com/PIAimages/" + pic_id[0] + "_S2.jpg\" /></p>");
			writer.write("<p style=\"width:200px;float:left;margin:30px 15px 0px 0px;\">");
			writer.write(productNameProdInfo + "<br />" + productTypeProdInfo + "<br />RMB:" + price + "<br /></p>");

			writer.write("<p style=\"width:750px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p>");
			writer.write("<p style=\"font:12px Simsun;line-height:20px;margin:0px 0px 0px 20px;background:#FFF;color:#000;width:300px;float:left;\">");
			if (assembledSize.length() >= 1) {
				writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">安装后尺寸</span></span><br />");
				writer.write("<span style=\"font-size:14px;\">" + assembledSize + "</span>");
			}
			if (keyFeatures.length() >= 15) {
				writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;line-height:40px;\">重要特征</span></span><br />");
				writer.write("<span style=\"font-size:14px;\">" + keyFeatures + "</span>");
			}
			if (designer.length() >= 1 || designerThoughts.length() >= 1) {
				writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">设计师</span></span><br />");
			}
			if (designerThoughts.length() >= 1)
				writer.write("<span style=\"line-height:25px;\">" + designerThoughts + "</span><br /><br />");
			if (designer.length() >= 1)
				writer.write("<span style=\"font-size:14px;\">" + designer + "</span><br /><br />");
			writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />");
			writer.write("<span style=\"font-size:14px;\">包装：" + numberOfPackages + "</span><br />");
			if (numberOfPackages.equals("1"))
				writer.write("<span style=\"font-size:14px;\">" + productInformation + "</span><br /><br />");
			else
				writer.write("<span style=\"font-size:14px;\">尺寸和重量详见官网</span><br /><br />");
			if (environment.length() >= 1) {
				writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">环保信息</span></span><br />");
				writer.write("<span style=\"font-size:14px;\">" + environment + "</span><br /><br />" + "</p>");
			}
			writer.write("<p style=\"font:12px Simsun;line-height:20px;margin:0px 0px 0px 30px;background:#FFF;color:#000;width:250px;float:left;\">");
			if (goodToKnow.length() >= 1) {
				writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">相关提示</span></span><br />");
				writer.write("<span style=\"font-size:14px;\">" + goodToKnow + "</span><br /><br />");
			}
			if (careInst.length() >= 1) {
				writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">保养说明</span></span><br />");
				writer.write("<span style=\"font-size:14px;\">" + careInst + "</span><br /><br />");
			}
			if (lowestPrice.length() >= 1) {
				writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">低价格从哪里来</span></span><br />");
				writer.write("<span style=\"font-size:14px;\">" + lowestPrice + "</span><br /><br />");
			}
			if (custMaterials.length() >= 1) {
				writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">产品描述</span></span><br />");
				writer.write("<span style=\"font-size:14px;\">" + custMaterials + "</span><br /><br />");
			}
			// writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />"+"\n");
			// writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />"+"\n");

			// for(String Info:InfoList){
			// writer.write(Info +"\n");
			// }

			writer.close();
			// System.out.println(id+".html has been created");
		} catch (IOException exp) {
			System.out.println("wrong!");
			exp.printStackTrace();
		}
	}

	public StringBuilder getDescribtion() {
		StringBuilder describtion = new StringBuilder();
		describtion.append("<p style=\"width:120px;float:left;margin:0px 15px 0px 0px;\">");
		describtion.append("<img src=\"http://www.ikea.com/PIAimages/" + pic_id[0] + "_S2.jpg\" /></p>");
		describtion.append("<p style=\"width:200px;float:left;margin:30px 15px 0px 0px;\">");
		describtion.append(productNameProdInfo + "<br />" + productTypeProdInfo + "<br />RMB:" + price + "<br /></p>");

		describtion.append("<p style=\"width:750px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p>");
		describtion
				.append("<p style=\"font:12px Simsun;line-height:20px;margin:0px 0px 0px 20px;background:#FFF;color:#000;width:300px;float:left;\">");
		if (assembledSize.length() >= 1) {
			describtion
					.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">安装后尺寸</span></span><br />");
			describtion.append("<span style=\"font-size:14px;\">" + assembledSize + "</span>");
		}
		if (keyFeatures.length() >= 15) {
			describtion
					.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;line-height:40px;\">重要特征</span></span><br />");
			describtion.append("<span style=\"font-size:14px;\">" + keyFeatures + "</span>");
		}
		if (designer.length() >= 1 || designerThoughts.length() >= 1) {
			describtion
					.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">设计师</span></span><br />");
		}
		if (designerThoughts.length() >= 1)
			describtion.append("<span style=\"line-height:25px;\">" + designerThoughts + "</span><br /><br />");
		if (designer.length() >= 1)
			describtion.append("<span style=\"font-size:14px;\">" + designer + "</span><br /><br />");
		describtion
				.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />");
		describtion.append("<span style=\"font-size:14px;\">包装：" + numberOfPackages + "</span><br />");
		if (numberOfPackages.equals("1"))
			describtion.append("<span style=\"font-size:14px;\">" + productInformation + "</span><br /><br />");
		else
			describtion.append("<span style=\"font-size:14px;\">尺寸和重量详见官网</span><br /><br />");
		if (environment.length() >= 1) {
			describtion
					.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">环保信息</span></span><br />");
			describtion.append("<span style=\"font-size:14px;\">" + environment + "</span><br /><br />" + "</p>");
		}
		describtion
				.append("<p style=\"font:12px Simsun;line-height:20px;margin:0px 0px 0px 30px;background:#FFF;color:#000;width:250px;float:left;\">");
		if (goodToKnow.length() >= 1) {
			describtion
					.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">相关提示</span></span><br />");
			describtion.append("<span style=\"font-size:14px;\">" + goodToKnow + "</span><br /><br />");
		}
		if (careInst.length() >= 1) {
			describtion
					.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">保养说明</span></span><br />");
			describtion.append("<span style=\"font-size:14px;\">" + careInst + "</span><br /><br />");
		}
		if (lowestPrice.length() >= 1) {
			describtion
					.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">低价格从哪里来</span></span><br />");
			describtion.append("<span style=\"font-size:14px;\">" + lowestPrice + "</span><br /><br />");
		}
		if (custMaterials.length() >= 1) {
			describtion
					.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">产品描述</span></span><br />");
			describtion.append("<span style=\"font-size:14px;\">" + custMaterials + "</span><br /><br />");
		}
		// describtion.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />"+"\n");
		// describtion.append("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />"+"\n");

		// for(String Info:InfoList){
		// describtion.append(Info +"\n");
		// }
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
			for (int i = 0; i < pic_id.length; i++) {
				URL url = new URL("http://www.ikea.com/PIAimages/" + pic_id[i] + "_S" + p + ".jpg");
				URLConnection urlCon = url.openConnection();
				InputStream is = urlCon.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				FileOutputStream fos = new FileOutputStream(diypath + "\\products\\" + product_id + pic_id[i] + "_S" + p + ".tbi");
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
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void toSQL() {
		ProductDao pd = new ProductDao();
		setProduct_dian_id(product_dian_id);
		setProductNameProdInfo(productNameProdInfo);
		setProductTypeProdInfo(productTypeProdInfo);
		setPrice(new Double(price));
		System.out.println("===============" + productNameProdInfo + "  " + productTypeProdInfo + "===============");
		setCategory(category);
		pd.insert(this);
	}

	public void toCSV(String diypath, Map<String, List<TaobaoProduct>> allTBPt) {
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
						.write("宝贝名称	宝贝类目	店铺类目	新旧程度	省	城市	出售方式	宝贝价格	加价幅度	宝贝数量	有效期	运费承担	平邮	EMS	快递	发票	保修	放入仓库	橱窗推荐	开始时间	宝贝描述	宝贝属性	邮费模版ID	会员打折	修改时间	上传状态	图片状态	返点比例	新图片	视频	销售属性组合	用户输入ID串	用户输入名-值对	商家编码	销售属性别名	代充类型	数字ID	本地ID	宝贝分类	账户名称	宝贝状态	闪电发货	新品	食品专项	尺码库	库存类型	库存计数"
								+ "\n");
				initWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Writer writer = new BufferedWriter(new FileWriter(csvfile, true));

			writer.append("\"" + productNameProdInfo + productTypeProdInfo + "[" + product_dian_id + "]" + "\"	50006298	\"" + category
					+ "\"	1	\"北京\"	\"北京\"	1	" + price + "	\"\"	1	0	2	0	0	0	0	1	2	0	\"\"	\"");
			writer.append(getDescribtion().toString());

			writer.append("\"	\"\"	1516110	0	\"\"	\"200\"	\"\"	0	\"");
			for (int j = 0; j < pic_id.length; j++)
				writer.append(product_id + pic_id[j] + "_S4" + ":1:" + j + ":|;");
			writer.append("\"	\"\"	\"\"	\"\"	\"\"	\"\"	\"\"	0	0	0	1	charick	1	0	0		mysize_tp:-1	164702552	2" + "\n");
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + product_id + " is not exist[csv]");
		}
		System.out.println(Thread.currentThread().getName() + diypath + "'s CRV is OK");

	}

	public Product(String id, String cate) {
		buf = CaptureHtml.captureHtml("http://www.ikea.com/cn/zh/catalog/products/" + id + "/");
		GetAnything something = new GetAnything();
		title = something.geT(buf, "<meta name=\"title\" content=", "- IKEA", "");
		price = something.getPrice(buf, "<div class=\"priceFamilyTextDollar\"  id=\"priceProdInfo\">", "</div>", "priceProdInfo");
		productNameProdInfo = something.geT(buf, "id=\"productNameProdInfo\">", "</div>", "productNameProdInfo");
		productTypeProdInfo = something
				.geT(buf, "<div class=\"prodInfoRow\"  id=\"productTypeProdInfo\">", "</div>", "productTypeProdInfo");
		assembledSize = something.geT(buf, "<div id =\"metric\" class=\"texts\"", "</div>", "assembledSize");
		keyFeatures = something.geT(buf, "<div id=\"custBenefit\" class=\"texts keyFeaturesmargin\">",
				"<div id=\"dessection\" class=\"productInformation prodInfoSub\"", "keyFeatures");
		designerThoughts = something.geT(buf, "<div id=\"designerThoughts\" class=\"texts\">", "</div>", "designerThoughts");
		designer = something.geT(buf, "<div id=\"designer\" class=\"texts\">", "</div>", "designer");
		numberOfPackages = something.geT(buf, "<span id=\"numberOfPackages\">", "</span>", "numberOfPackages");
		productInformation = something.geT(buf, "<div class=\"texts\" style=\"width: 200px;\">", "</div>", "productInformation");
		environment = something.geT(buf, "<div id=\"environment\" class=\"texts\">", "</div>", "environment");
		goodToKnow = something.geT(buf, "<div id=\"goodToKnow\" class=\"texts\">", "</div>", "goodToKnow");
		careInst = something.geT(buf, "<div id=\"careInst\" class=\"texts Wdth\">", "</div>", "careInst");
		lowestPrice = something.geT(buf, "<div id=\"lowestPrice\" class=\"texts\"><div class=\"prodInfoHeadline\">", "</div>",
				"lowestPrice");
		custMaterials = something.geT(buf, "<div id=\"custMaterials\" class=\"texts\">", "</div>", "custMaterials");
		product_id = id;
		product_dian_id = something.geT(buf, "<div id=\"itemNumber\" class=\"floatLeft\">", "</div>", "product.id");
		pic_id = something.getPicUrl(buf, product_id);
		category = cate;

	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Product p = new Product("90222474", "");
		p.toSQL();
		p.toFile("E:\\IKEA123\\");
//		p.toCSV("E:\\IKEA123\\");
		p.toPic(4, "E:\\IKEA123\\");
//		System.out.println("!");
	}
}
