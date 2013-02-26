package com.fec.ikea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDao {
	public Connection getConnection() throws SQLException,
			java.lang.ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/ikea?characterEncoding=UTF-8";
		Class.forName("com.mysql.jdbc.Driver");
		String userName = "root";
		String password = "491272";
		Connection con = DriverManager.getConnection(url, userName, password);
		return con;
	}

	public void insert(Product product) {
		String sql = "insert into tbl_product(code,name,price) values(?,?,?)";
		try {
			PreparedStatement stmt = getConnection().prepareStatement(sql);
			stmt.setString(1, product.getCode());
			stmt.setString(2, product.getName());
			stmt.setDouble(3, product.getPrice());
			stmt.execute();
		} catch (SQLException e) {
			System.out.println("=========SQLException=========="
					+ e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("========ClassNotFoundException==========="
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public Product getProduct(String code) {
		String sql = "select * from  tbl_product where code="+code;
		Product pt = null;
		ResultSet rs = null;
		try {
			Statement stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pt = new Product();
				pt.setCode(rs.getString("code"));
				pt.setName(rs.getString("name"));
				pt.setPrice(rs.getDouble("price"));
			}
		} catch (SQLException e) {
			System.out.println("=========SQLException=========="
					+ e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("========ClassNotFoundException==========="
					+ e.getMessage());
			e.printStackTrace();
		}
		return pt;
	}

	public static void main(String[] args) {
		ProductDao pd = new ProductDao();
		Product pt = new Product();
		pt.setCode("11116");
		pt.setName("花书");
		pt.setPrice(100.5);
		pd.insert(pt);
		System.out.println("test finish!!");
		
		pt=pd.getProduct("11116");
		System.out.println("get prodect fromDB:"+pt.getName());
	}
}
