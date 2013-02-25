package com.fec.ikea;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ThreadMultiFilter extends IkeaFilter implements Runnable {
	ArrayList<String> urls=new ArrayList<String>(); 
	int index=0;
	public void go(String url) throws IOException{
		GetProductIds getproductids=new GetProductIds();	
		getproductids.geT(url);
		GetMulu getmulu=new GetMulu();
		String name= getmulu.getmus(url);
		ArrayList<String> tmps=new ArrayList<String>();
		tmps=getproductids.productlist;
	for(int i=0;i<tmps.size();i++){
		SaveFile(tmps.get(i),".\\"+name+"\\products\\");
		SavePic(tmps.get(i),4,".\\"+name+"\\products\\");
		}
		
	saveCSV(tmps,new File(".\\"+name+"\\products.csv"),".\\"+name+"\\products\\");
}
	public void  run(){	
		System.out.println(Thread.currentThread().getName()+"is runing");
		while(true){
		int i=getindex();
		if(i!=9999)
		{String url=urls.get(i);
		try {
			go(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}	
		else
			break;
		}
		
	}
	synchronized int getindex(){
		if(index<=urls.size())
		{return index++;
		}
		else return 9999;
	}
	public static void main(String[] args){
		ProductFinder finder=new ProductFinder();
		finder.geT("http://www.ikea.com/cn/zh/catalog/allproducts/");
		ThreadMultiFilter a1=new ThreadMultiFilter();
		a1.urls=finder.productlist;
		
	Thread t1= new Thread(a1);
	Thread t2=new Thread(a1);
	Thread t3=new Thread(a1);
	Thread t4=new Thread(a1);
	t1.setName("t1:");
	t2.setName("t2:");
	t3.setName("t3:");
	t4.setName("t4:");
	t1.start();
	t2.start();
	t3.start();
	t4.start();
	}
	

}


