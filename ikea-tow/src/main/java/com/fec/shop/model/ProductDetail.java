package com.fec.shop.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.MathTool;

import com.fec.shop.util.HtmlUtil;
import com.fec.shop.util.VelocityUtil;

public class ProductDetail extends ProductSimple {
String assembledSize;
String designerThoughts;
String designer;
String environment;
String goodToKnow;
String careInst;
String custMaterials;
String keyFeatures;

String saleInfo;
String describtion;
/**
 * 构造函数
 */
public ProductDetail(String id) throws IOException {
	ArrayList<String> ids = new ArrayList<String>();
	Collections.addAll(ids, id.split(","));
	setProductIds(ids);
	buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + productIds.get(0) + "/");
}

public ProductDetail() {
}
/**
 * 生成宝贝描述
 * @return
 */
public void creatDescribtion(){
	VelocityContext context = new VelocityContext();
	context.put("assembledSize", assembledSize);
	context.put("designerThoughts", designerThoughts);
	context.put("designer", designer);
	context.put("environment", environment);
	context.put("goodToKnow", goodToKnow);
	context.put("careInst", careInst);
	context.put("custMaterials", custMaterials);
	context.put("keyFeatures", keyFeatures);
	context.put("mainPics", getMainPics());
	context.put("productIds", getProductIds());
	context.put("productType", getProductType());
	context.put("price",getPrice());
	context.put("pic_ids", getPicIds());
	context.put("productName", getProductName());
	context.put("product_id", getPid());
	ArrayList<String> typecolorlist = new ArrayList<String>();
	ArrayList<String> productType = getProductType();
	for (String pt : productType) {
		String typecolor;
		if (pt.contains("黄"))
			typecolor = "#e5cd00";
		else if (pt.contains("红"))
			typecolor = "#cc0000";
		else if (pt.contains("绿"))
			typecolor = "#22cc00";
		else if (pt.contains("蓝"))
			typecolor = "#1759a8";
		else if (pt.contains("橙"))
			typecolor = "#f27405";
		else
			typecolor = "#000000";
		typecolorlist.add(typecolor);
	}
	context.put("typecolorlist", typecolorlist);
	// context.put("product", p);
	context.put("math", new MathTool());
	String result = VelocityUtil.filterVM("productDetail.vm", context);
	setDescribtion(result);
}
//==========================getter&setter====================


public String getAssembledSize() {
	return assembledSize;
}
public void setAssembledSize(String assembledSize) {
	this.assembledSize = assembledSize;
}
public String getDesignerThoughts() {
	return designerThoughts;
}
public void setDesignerThoughts(String designerThoughts) {
	this.designerThoughts = designerThoughts;
}
public String getDesigner() {
	return designer;
}
public void setDesigner(String designer) {
	this.designer = designer;
}

public String getEnvironment() {
	return environment;
}
public void setEnvironment(String environment) {
	this.environment = environment;
}
public String getGoodToKnow() {
	return goodToKnow;
}
public void setGoodToKnow(String goodToKnow) {
	this.goodToKnow = goodToKnow;
}
public String getCareInst() {
	return careInst;
}
public void setCareInst(String careInst) {
	this.careInst = careInst;
}
public String getCustMaterials() {
	return custMaterials;
}
public void setCustMaterials(String custMaterials) {
	this.custMaterials = custMaterials;
}
public String getKeyFeatures() {
	return keyFeatures;
}
public void setKeyFeatures(String keyFeatures) {
	this.keyFeatures = keyFeatures;
}
public String getSaleInfo() {
	return saleInfo;
}
public void setSaleInfo(String saleInfo) {
	this.saleInfo = saleInfo;
}
public String getDescribtion() {
	return describtion;
}
public void setDescribtion(String describtion) {
	this.describtion = describtion;
}


}
