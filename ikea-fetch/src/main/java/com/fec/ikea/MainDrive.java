package com.fec.ikea;

import java.io.IOException;
import java.util.ArrayList;

public class MainDrive {
	ArrayList<String> productlist = new ArrayList<String>();
	public void go(String url) throws IOException {
		GetProductIds getproductids = new GetProductIds();		
		GetMulu getmulu = new GetMulu();
		String categoryName = getmulu.getmus(url);
		productlist = getproductids.geT(url);
		int i2=productlist.size()/2;
		ArrayList<String> productlist1=new ArrayList<String>();
		ArrayList<String> productlist2=new ArrayList<String>();
		for(int i=0;i<i2;i++)
		productlist1.add(productlist.get(i));
		for(int i=i2;i<productlist.size();i++)
			productlist2.add(productlist.get(i));

		// =========================在下面方法中选择执行的操作====================
		IkeaFilter ik1=new IkeaFilter();
		Thread i1=new Thread(ik1);
		ik1.productlist=productlist1;
		ik1.categoryName=categoryName;
		i1.setName("=ik1=:");
		i1.start();
		IkeaFilter ik2=new IkeaFilter();
		Thread i21=new Thread(ik2);
		ik2.productlist=productlist2;
		ik2.categoryName=categoryName;
		i21.setName("=ik2=:");
		i21.start();
	}

	
}
