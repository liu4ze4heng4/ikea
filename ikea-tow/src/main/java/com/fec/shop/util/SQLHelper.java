package com.fec.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fec.shop.model.Category;
import com.fec.shop.model.Product;

public class SQLHelper {
	public Connection getConnection() throws SQLException, java.lang.ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/ikea?characterEncoding=UTF-8";
		Class.forName("com.mysql.jdbc.Driver");
		String userName = "root";
		String password = "491272";
		Connection con = DriverManager.getConnection(url, userName, password);
		return con;
	}
	public void insertProduct(Product product) {
		String sql = "insert into tbl_product(product_dian_id,productNameProdInfo,productTypeProdInfo,price,category) values(?,?,?,?,?)";
		try {
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			stmt.setString(1, product.getProduct_dian_id());
			stmt.setString(2, product.getProductNameProdInfo());
			stmt.setString(3, product.getProductTypeProdInfo());
			stmt.setDouble(4, product.getPrice());
			stmt.setString(5, product.getCategory());

			stmt.execute();
		} catch (SQLException e) {
			System.out.println("=========SQLException==========" + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("========ClassNotFoundException===========" + e.getMessage());
			e.printStackTrace();
		}
	}

	public Product getProduct(String code) {
		String sql = "select * from  tbl_product where code=" + code;
		Product pt = null;
		ResultSet rs = null;
		try {
			Statement stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pt = new Product();
				pt.setProduct_dian_id(rs.getString("product_dian_id"));
				pt.setProductNameProdInfo(rs.getString("productNameProdInfo"));
				pt.setProductTypeProdInfo(rs.getString("productTypeProdInfo"));
				pt.setPrice(rs.getDouble("price"));
				pt.setCategory(rs.getString("category"));

			}
		} catch (SQLException e) {
			System.out.println("=========SQLException==========" + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("========ClassNotFoundException===========" + e.getMessage());
			e.printStackTrace();
		}
		return pt;
	}
	public void insertProductList(Category category) {
		String sql = "insert into tbl_productlist(pid,cid) values(?,?)";
		try {
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			String cid=category.getCid();
			for(String pid:category.getCidList())
			{stmt.setString(1, pid);
			stmt.setString(2, cid);
			stmt.execute();
			}
		} catch (SQLException e) {
			System.out.println("=========SQLException==========" + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("========ClassNotFoundException===========" + e.getMessage());
			e.printStackTrace();
		}
	}
//public void insertTBcategories(ArrayList<TBCategroy> tbcategories)
//{
//String sql = "insert into tbl_TBcategories(name,cid) values(?,?)";
//try {
//	PreparedStatement stmt = getConnection().prepareStatement(sql);
//	for(TBCategroy tbp:tbcategories){
//	String cid=tbp.getTb_cid();
//	String name=tbp.getTb_name();
//	stmt.setString(1, name);
//	stmt.setString(2, cid);
//	stmt.execute();}
//	
//} catch (SQLException e) {
//	System.out.println("=========SQLException==========" + e.getMessage());
//	e.printStackTrace();
//} catch (ClassNotFoundException e) {
//	System.out.println("========ClassNotFoundException===========" + e.getMessage());
//	e.printStackTrace();
//
//}
//}
public  String getcid(String name) {
	System.out.println(name);
	String sql = "select * from  tbl_tbcategories where name='" + name+"'";
	StringBuilder cid = null;
	ResultSet rs = null;
	try {
		Statement stmt = getConnection().createStatement();
		rs = stmt.executeQuery(sql);
		cid = new StringBuilder();
		if (rs.next()) {
			cid.append(rs.getString("cid")+",");
			
		}
	} catch (SQLException e) {
		System.out.println("=========SQLException==========" + e.getMessage());
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		System.out.println("========ClassNotFoundException===========" + e.getMessage());
		e.printStackTrace();
	}
	System.out.println(cid);
	return cid.toString();
}
//	public static void main(String[] Args){
//		ArrayList<TBCategroy>tbc=TaobaoUtils.getTBcategories();
//		SQLHelper sqlhelper=new SQLHelper();
//		sqlhelper.insertTBcategories(tbc);
//		Category c=new Category();
//		c.setIkeaUrl("http://www.ikea.com/cn/zh/catalog/categories/departments/living_room/10661/");
//		c.setName("Ӥ���");
//		IkeaUtils.getProductList(c);
//		c.setTBCategoryFromSQL();
//		sqlhelper.insertProductList(c);
//	}
}
