package com.fec.ikea;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



public class IkeaFilter {
	String title,price,productNameProdInfo,productTypeProdInfo,assembledSize,keyFeatures,designerThoughts,designer,numberOfPackages,productInformation,environment;
	String goodToKnow,careInst,lowestPrice,custMaterials, product_dian_id;
	String[] pic_id;
	String describtion;
	String product_id;
	ArrayList<String> describtions=new ArrayList<String>();
	//main gui1=new main();
	//String id="0";
	String notification=new String("");
//	public void setId(String a)
//	{
//	path = new File(".\\"+a); //生成目录
//	  path.mkdir();
//	}
	
	public void captureHtml(String id) throws Exception {
		String strURL = "http://www.ikea.com/cn/zh/catalog/products/" + id + "/";
		URL url = new URL(strURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn
				.getInputStream(), "utf-8");
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		String buf = contentBuf.toString();
		GetAnything something =new GetAnything();
title=something.geT(buf,"<meta name=\"title\" content=","- IKEA","");
//System.out.println(title);
price=something.getPrice(buf,"<div class=\"priceFamilyTextDollar\"  id=\"priceProdInfo\">","</div>","priceProdInfo");
//System.out.println(price);
productNameProdInfo=something.geT(buf,"id=\"productNameProdInfo\">","</div>","productNameProdInfo");
productTypeProdInfo=something.geT(buf,"<div class=\"prodInfoRow\"  id=\"productTypeProdInfo\">","</div>","productTypeProdInfo");
assembledSize=something.geT(buf,"<div id =\"metric\" class=\"texts\"","</div>","assembledSize");
keyFeatures =something.geT(buf,"<div id=\"custBenefit\" class=\"texts keyFeaturesmargin\">","<div id=\"dessection\" class=\"productInformation prodInfoSub\"","keyFeatures");
designerThoughts=something.geT(buf,"<div id=\"designerThoughts\" class=\"texts\">","</div>","designerThoughts");
designer=something.geT(buf,"<div id=\"designer\" class=\"texts\">","</div>","designer");
numberOfPackages=something.geT(buf,"<span id=\"numberOfPackages\">","</span>","numberOfPackages");
productInformation=something.geT(buf,"<div class=\"texts\" style=\"width: 200px;\">","</div>","productInformation");
environment=something.geT(buf,"<div id=\"environment\" class=\"texts\">","</div>","environment");
goodToKnow=something.geT(buf,"<div id=\"goodToKnow\" class=\"texts\">","</div>","goodToKnow");
 careInst=something.geT(buf,"<div id=\"careInst\" class=\"texts Wdth\">","</div>","careInst");
 lowestPrice=something.geT(buf,"<div id=\"lowestPrice\" class=\"texts\"><div class=\"prodInfoHeadline\">","</div>","lowestPrice");
 custMaterials=something.geT(buf,"<div id=\"custMaterials\" class=\"texts\">","</div>","custMaterials");
 pic_id=something.getPicUrl(buf);
 product_id=id;
// System.out.println(id);
 product_dian_id=something.geT(buf,"<div id=\"itemNumber\" class=\"floatLeft\">","</div>","product.id");

//something.geT(buf,"","</div>","");
//something.geT(buf,"","</div>","");
//something.geT(buf,"","</div>","");
//something.geT(buf,"","</div>","");
//something.geT(buf,"","</div>","");
//something.geT(buf,"","</div>","");
//something.SaveFile();	
	}
	synchronized boolean SaveFile(String id,String diypath){
		File path = new File(diypath); //生成目录
		path.mkdirs();
		try{captureHtml(id);}
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(Thread.currentThread().getName()+id+" is not exist\n");
			notification=id+" is not exist\n";
//			demo.repaint();
			return false;
		}
		try{	
			BufferedWriter writer = new BufferedWriter(new FileWriter(diypath+id+".html"));
	writer.write("<p style=\"width:120px;float:left;margin:0px 15px 0px 0px;\">" +"\n");	
	writer.write("<img src=\"http://www.ikea.com/PIAimages/"+pic_id[0]+"_S2.jpg\" /></p>" +"\n");			
	writer.write("<p style=\"width:200px;float:left;margin:30px 15px 0px 0px;\">" +"\n");
	writer.write(productNameProdInfo+"<br />"+productTypeProdInfo+"<br />RMB:"+price+"<br /></p>" +"\n");

	writer.write("<p style=\"width:750px;height:1px;margin:0px 15px 0px 0px;border-top:1px solid #ddd;float:left;\"></p>" +"\n");			
	//writer.write("<p style=\"font:12px Simsun;line-height:20px;margin:20px;background:#FFF;color:#000;width:300px;float:left;\">" +"\n");			
	//writer.write("<p style=\"font:12px Simsun;line-height:20px;margin:20px;background:#FFF;color:#000;width:300px;float:left;\">" +"\n");			
			
writer.write("<p style=\"font:12px Simsun;line-height:20px;margin:0px 0px 0px 20px;background:#FFF;color:#000;width:300px;float:left;\">" +"\n");	
	if(assembledSize.length() >=1){
	writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">安装后尺寸</span></span><br />"+"\n");
	writer.write("<span style=\"font-size:14px;\">"+assembledSize+"</span>\n");}
	if(keyFeatures.length() >=15){
	writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;line-height:40px;\">重要特征</span></span><br />"+"\n");
	writer.write("<span style=\"font-size:14px;\">"+keyFeatures+"</span>"+"\n");}
	if(designer.length() >=1||designerThoughts.length() >=1){
	writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">设计师</span></span><br />"+"\n");}
	if(designerThoughts.length() >=1)
	writer.write("<span style=\"line-height:25px;\">"+designerThoughts+"</span><br /><br />"+"\n");
	if(designer.length() >=1)
	writer.write("<span style=\"font-size:14px;\">"+designer+"</span><br /><br />"+"\n");
	writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />"+"\n");
	writer.write("<span style=\"font-size:14px;\">包装："+numberOfPackages+"</span><br />"+"\n");
	if(numberOfPackages.equals("1"))
	writer.write("<span style=\"font-size:14px;\">"+productInformation+"</span><br /><br />"+"\n");
	else
		writer.write("<span style=\"font-size:14px;\">尺寸和重量详见官网</span><br /><br />"+"\n");
	if(environment.length() >=1){
	writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">环保信息</span></span><br />"+"\n");
	writer.write("<span style=\"font-size:14px;\">"+environment+"</span><br /><br />"+"\n"+"</p>");}
writer.write("<p style=\"font:12px Simsun;line-height:20px;margin:0px 0px 0px 30px;background:#FFF;color:#000;width:250px;float:left;\">"+"\n");
	if(goodToKnow.length() >=1){
	writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">相关提示</span></span><br />"+"\n");
	writer.write("<span style=\"font-size:14px;\">"+goodToKnow+"</span><br /><br />"+"\n");}
	if(careInst.length() >=1){
	writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">保养说明</span></span><br />"+"\n");
	writer.write("<span style=\"font-size:14px;\">"+careInst+"</span><br /><br />"+"\n");}
	if(lowestPrice.length() >=1)
	{writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">低价格从哪里来</span></span><br />"+"\n");
	 writer.write("<span style=\"font-size:14px;\">"+lowestPrice+"</span><br /><br />"+"\n");}
	if(custMaterials.length() >=1){
	writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">产品描述</span></span><br />"+"\n");
	writer.write("<span style=\"font-size:14px;\">"+custMaterials+"</span><br /><br />"+"\n");}
	//writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />"+"\n");
	//writer.write("<span style=\"font-family:simhei;\"><span style=\"font-size:24px;line-height:40px;\">包装尺寸和重量</span></span><br />"+"\n");




	//for(String Info:InfoList){
			//writer.write(Info +"\n");	
			//}
			
			
			writer.close();
//			System.out.println(id+".html has been created");
			}catch(IOException ex){
				System.out.println("wrong!");
				ex.printStackTrace();
				return false;
			}
		return false; 
	}
	public synchronized boolean SavePic(String id,int p,String diypath){
		try {  
			captureHtml(id);}
		catch (Exception e){
		System.out.print(id+" is not exist[pic]\n");	
		return false;
		}
		 try{
			 for(int i=0;i<pic_id.length;i++){
			URL url = new URL("http://www.ikea.com/PIAimages/"+pic_id[i]+"_S"+p+".jpg");  
		  URLConnection urlCon = url.openConnection();  
		  InputStream is = urlCon.getInputStream();  
		  BufferedInputStream bis = new BufferedInputStream(is);  
		  FileOutputStream fos = new FileOutputStream(diypath+id+pic_id[i]+"_S"+p+".tbi");  
		  BufferedOutputStream bos = new BufferedOutputStream(fos);  
   
		  int read;  
		  while( (read = bis.read()) != -1 ){  
		    bos.write(read);  
		  }  
		  bos.close();//不关闭，输出流不刷新，有可能得到无效图片  
//		  System.out.println("picture:"+pic_id[i]+"S"+p+" is ok!");
		  }
		  } catch (MalformedURLException e) {  
		    e.printStackTrace();  
		  } catch (IOException e) {  
		    e.printStackTrace();  
		  } catch (Exception e){  
		    e.printStackTrace();  
		  }
		return false;   
		}  

 void loadFile(File a) throws IOException {
	try{
		BufferedReader reader =new BufferedReader(new FileReader(a));
		String des=null;
		describtions.clear();
		while((des=reader.readLine())!=null)
			describtions.add(des);
		reader.close();
		//return describtions;
	}catch(Exception ex){
		ex.printStackTrace();}	
		//return describtions;
	
}	
	
synchronized void saveCSV(ArrayList<String> ids,File crvfile,String diypath) throws IOException {
	
		BufferedWriter writer = new BufferedWriter(new FileWriter(crvfile));
writer.write("version 1.00" +"\n");			
writer.write("title	cid	seller_cids	stuff_status	location_state	location_city	item_type	price	auction_increment	num	valid_thru	freight_payer	post_fee	ems_fee	express_fee	has_invoice	has_warranty	approve_status	has_showcase	list_time	description	cateProps	postage_id	has_discount	modified	upload_fail_msg	picture_status	auction_point	picture	video	skuProps	inputPids	inputValues	outer_id	propAlias	auto_fill	num_id	local_cid	navigation_type	user_name	syncStatus	is_lighting_consigment	is_xinpin	foodparame	features	global_stock_type	sub_stock_type" +"\n");			
writer.write("宝贝名称	宝贝类目	店铺类目	新旧程度	省	城市	出售方式	宝贝价格	加价幅度	宝贝数量	有效期	运费承担	平邮	EMS	快递	发票	保修	放入仓库	橱窗推荐	开始时间	宝贝描述	宝贝属性	邮费模版ID	会员打折	修改时间	上传状态	图片状态	返点比例	新图片	视频	销售属性组合	用户输入ID串	用户输入名-值对	商家编码	销售属性别名	代充类型	数字ID	本地ID	宝贝分类	账户名称	宝贝状态	闪电发货	新品	食品专项	尺码库	库存类型	库存计数" +"\n");			
	for(int i=0;i<ids.size();i++){
	try{	
	captureHtml(ids.get(i));
	loadFile(new File(diypath+ids.get(i)+".html"));
writer.write("\""+productNameProdInfo+productTypeProdInfo+"["+product_dian_id+"]"+"\"	50006298	\"\"	1	\"北京\"	\"北京\"	1	"+price+"	\"\"	1	0	2	0	0	0	0	1	2	0	\"\"	\"");			
for(String cell:describtions)
{writer.write(cell+"\n");
}
writer.write("\"	\"\"	1516110	0	\"\"	\"200\"	\"\"	0	\"");
for(int j=0;j<pic_id.length;j++)
writer.write(product_id+pic_id[j]+"_S4"+":1:"+j+":|;");
writer.write("\"	\"\"	\"\"	\"\"	\"\"	\"\"	\"\"	0	0	0	1	charick	1	0	0		mysize_tp:-1	164702552	2" +"\n");
}
	catch (Exception e){
	System.out.println(Thread.currentThread().getName()+ids.get(i)+" is not exist[csv]");	
	}
	}
writer.close();
		System.out.println(diypath+"'s CRV is OK");
		
}
}


class GetAnything {
	ArrayList<String> InfoList= new ArrayList<String>();
	String Info = new String();
	String[] PicUrl;
	
	String geT(String buf,String beginstr,String endstr,String title){
	int beginIx = buf.indexOf(beginstr);
	String beginIndex =beginstr;
	int beginIxLength =beginIndex.length();
	int endIx = buf.indexOf(endstr,beginIx);
	String result = buf.substring(beginIx+beginIxLength, endIx);
	String tmp=result.replace("<div class=\"designerName\">", "<br />---");
	String tmp1=tmp.replace("style=\"display:none\">","");
		tmp=tmp1.replace("style=\"display:block\">","");
		result=tmp.replace("	", "");
		tmp=result.replace("<div>", "");
		result=tmp.replace("</div>", "<br />");
	String result_f=result.replace(" ", "");
	//Info=title+ ":" + result_f;
	Info=result_f;
	//System.out.println(Info);
	//InfoList.add(Info);
	return Info;
	
	}
	
	String[] getPicUrl(String buf){
		int beginIx = buf.indexOf("\"zoom\":[");
		String beginIndex ="\"zoom\":[";
		int beginIxLength =beginIndex.length();
		int endIx = buf.indexOf("]",beginIx);
		String result = buf.substring(beginIx+beginIxLength, endIx);
		String result1=result.replace("\"/PIAimages/", "");
		result=result1.replace("_S5.JPG\"", "");
		result1=result.replace("_S5.jpg\"","");
		PicUrl=result1.split(",");
		
		return PicUrl;
		
		}
	
		
	String getPrice(String buf,String beginstr,String endstr,String title){
		int beginIx = buf.indexOf(beginstr);
		String beginIndex =beginstr;
		int beginIxLength =beginIndex.length();
		int endIx = buf.indexOf(endstr,beginIx);
		String tmp = buf.substring(beginIx+beginIxLength+5, endIx);
		String result=tmp.replace("</b>", "");
		tmp=result.replace(",", "");
		//System.out.println(result);
		//InfoList.add(Info);
		return tmp;
		
		}

}


