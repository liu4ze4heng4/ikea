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

public void insertWholeProductInfo(Product product){
//	String sql = "insert into 1(宝贝名称,宝贝类目,店铺类目,宝贝价格,宝贝数量,运费承担,快递,放入仓库,宝贝描述," +
//			"宝贝属性,邮费模版ID,会员打折,修改时间,上传状态,图片状态,新图片,用户输入ID串,用户输入名-值对," +
//			"商家编码,数字ID,本地ID,宝贝分类,宝贝状态,新品,尺码库,库存类型,库存计数,物流体积,物流重量) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	String sql = "insert into testtt(title,seller_cids,price,description,picture,outer_id) values(?,?,?,?,?,?)";

	try {
		PreparedStatement stmt = getConnection().prepareStatement(sql);
		stmt.setString(1, product.getProductNameProdInfo() + product.getProductTypeProdInfo() + product.getProduct_dian_id());
		stmt.setString(2, product.getCategory());
		stmt.setDouble(3, product.getMinumPrice());
		stmt.setString(4, product.getDescribtion().replace("	", " ").replace("\r\n", "").replace("\"","'"));
		StringBuilder pics =new StringBuilder();
		int i=0;
		for(String e:product.getMainPics())
		{
			pics.append(e+":1:"+i+":|;");
			i++;
		}
		stmt.setString(5, pics.toString());
		stmt.setString(6,product.getProduct_id());


		stmt.execute();
	} catch (SQLException e) {
		System.out.println("=========SQLException==========" + e.getMessage());
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		System.out.println("========ClassNotFoundException===========" + e.getMessage());
		e.printStackTrace();
	}
}
	public void insertProduct(Product product) {
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
	}
/**
 * 从数据库根据商品pid获取 产品的taobaoId
 * @param code
 * @return
 */
	public String getProductTid(String pid){
		String sql = "select * from  main_table where outer_id=" + pid;
		String tid=null;
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = null;		
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				tid= rs.getString("num_id");

			}
		} catch (SQLException e) {
			System.out.println("=========SQLException==========" + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("========ClassNotFoundException===========" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println(tid);
	return tid;
	}
	/**
	 * 从数据库获得所有产品的taobaoId
	 * @param code
	 * @return
	 */
		public ArrayList<String> getProductTids(){
			String sql = "select * from  main_table ";
			ArrayList<String> tids=new ArrayList<String>();
			try {
				Statement stmt = getConnection().createStatement();
				ResultSet rs = null;		
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
//					tids.add(rs.getString("num_id")) ;
					tids.add(rs.getString("title")) ;
				}
			} catch (SQLException e) {
				System.out.println("=========SQLException==========" + e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("========ClassNotFoundException===========" + e.getMessage());
				e.printStackTrace();
			}
			System.out.println(tids);
for(String e:tids){
if (e.contains("["))
				{int beginIx = e.indexOf("[");
				int endIx = e.indexOf("]", beginIx);
				String result = e.substring(beginIx + 1, endIx);
				result = result.replace(".", "");
				sql = "update main_table set outer_id='"+result+"' where title='"+e+"'";
				try {
					PreparedStatement stmt = getConnection().prepareStatement(sql);
					stmt.execute();
					}
				catch (SQLException er) {
					System.out.println("=========SQLException==========" + er.getMessage());
					er.printStackTrace();
				} catch (ClassNotFoundException er) {
					System.out.println("========ClassNotFoundException===========" + er.getMessage());
					er.printStackTrace();
				}
				}
}
			
		return tids;
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
//				pt.setPrice(rs.getDouble("price"));
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
//		c.setName("婴锟斤拷锟�);
//		IkeaUtils.getProductList(c);
//		c.setTBCategoryFromSQL();
//		sqlhelper.insertProductList(c);
//	}
}
