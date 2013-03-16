package com.fec.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fec.shop.model.Product;

public class ProductDao {
	public Connection getConnection() throws SQLException, java.lang.ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/ikea?characterEncoding=UTF-8";
		Class.forName("com.mysql.jdbc.Driver");
		String userName = "root";
		String password = "491272";
		Connection con = DriverManager.getConnection(url, userName, password);
		return con;
	}

//	public void insert(Product product) {
//		String sql = "insert into tbl_product(product_dian_id,productNameProdInfo,productTypeProdInfo,price,category) values(?,?,?,?,?)";
//		try {
//			PreparedStatement stmt = getConnection().prepareStatement(sql);
//			stmt.setString(1, product.getProduct_dian_id());
//			stmt.setString(2, product.getProductNameProdInfo());
//			stmt.setString(3, product.getProductTypeProdInfo());
//			stmt.setDouble(4, product.getPrice());
//			stmt.setString(5, product.getCategory());
//
//			stmt.execute();
//		} catch (SQLException e) {
//			System.out.println("=========SQLException==========" + e.getMessage());
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			System.out.println("========ClassNotFoundException===========" + e.getMessage());
//			e.printStackTrace();
//		}
//	}

//	public Product getProduct(String code) {
//		String sql = "select * from  tbl_product where code=" + code;
//		Product pt = null;
//		ResultSet rs = null;
//		try {
//			Statement stmt = getConnection().createStatement();
//			rs = stmt.executeQuery(sql);
//			if (rs.next()) {
//				pt = new Product();
//				pt.setProduct_dian_id(rs.getString("product_dian_id"));
//				pt.setProductNameProdInfo(rs.getString("productNameProdInfo"));
//				pt.setProductTypeProdInfo(rs.getString("productTypeProdInfo"));
//				pt.setPrice(rs.getDouble("price"));
//				pt.setCategory(rs.getString("category"));
//
//			}
//		} catch (SQLException e) {
//			System.out.println("=========SQLException==========" + e.getMessage());
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			System.out.println("========ClassNotFoundException===========" + e.getMessage());
//			e.printStackTrace();
//		}
//		return pt;
//	}

	public static void main(String[] args) {
//		ProductDao pd = new ProductDao();
//	Product pt = new Product();
//	pt.setProduct_dian_id("1234");
//	pt.setProductNameProdInfo("222");
//	pt.setProductTypeProdInfo("1234");
//	pt.setPrice(12.3);
//	pt.setCategory("123");
//	pd.insert(pt);
//		System.out.println("test finish!!");
//
//		pt = pd.getProduct("11116");
//		System.out.println("get prodect fromDB:" + pt.getName());
	}
}
