package com.fec.ikea;

public class Product {
	public String product_dian_id;
	public String productNameProdInfo;
	public String productTypeProdInfo;
	public String category;
	public double price;
	

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
	
}
