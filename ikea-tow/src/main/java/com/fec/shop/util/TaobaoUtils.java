package com.fec.shop.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fec.shop.constant.Constant;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.request.SellercatsListAddRequest;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.response.ItemAddResponse;
import com.taobao.api.response.SellercatsListAddResponse;
import com.taobao.api.response.SellercatsListGetResponse;

public class TaobaoUtils {
	public static void main(String[] args) {
		try {
			TaobaoUtils.addTaobaoItem();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 TaobaoUtils.getCCMapFromFile();
//		SellercatsListAddRequest req=new SellercatsListAddRequest();
//		req.setParentCid(0l);
//		req.setName("testsets======");
//		req.setPictUrl("http://www.ikea.com/cn/zh/images/products/fu-li-suo-yang-san__0103516_PE249675_S4.JPG");
//		TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
//		try {
//			SellercatsListAddResponse response=client.execute(req);
//			response.getParams();
//			response.getBody();
//			response.getSellerCat();
//			response.getMsg();
//		} catch (ApiException e) {
//			e.printStackTrace();
//		}
		System.out.println("ok!");
	}

	public static Map<String, String> getCCMapFromFile() {
		Map<String, String> result = new HashMap<String, String>();
		String temp;
		String[] tempA;
		try {
			InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File(Constant.tb_category_file)),"utf-8");
			BufferedReader bufReader = new BufferedReader(insReader);
			while ((temp = bufReader.readLine()) != null) {
				tempA = temp.split(Constant.split);
				result.put(tempA[0], tempA[1]);
			}
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void saveTBcategory2File() {
		try {
			ArrayList<SellerCid> tbclist = getSellerCid();
			FileWriter fw = new FileWriter(new File(Constant.tb_category_file));
			for (SellerCid tbCategory : tbclist) {
				fw.write(tbCategory + "\n");
			}
			fw.flush();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("save taobao category to file finish!");
	}
	
	private static void addCategory(){
		
	}
	private static void addTaobaoItem() throws ApiException, ParseException{
		TaobaoClient client=new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
		ItemAddRequest req=new ItemAddRequest();
		req.setNum(30L);
		req.setPrice("200.07");
		req.setType("fixed");
		req.setStuffStatus("new");
		req.setTitle("Nokia N97全新行货");
		req.setDesc("这是一个好商品");
		req.setLocationState("北京");
		req.setLocationCity("北京");
		req.setApproveStatus("instock");
		req.setCid(50006298L);
//		req.setProps("20000:33564;21514:38489");
		req.setFreightPayer("buyer");
		req.setValidThru(7L);
//		req.setHasInvoice(true);
//		req.setHasWarranty(true);
//		req.setHasShowcase(true);
//		req.setSellerCids("1101,1102,1103");
//		req.setHasDiscount(true);
		req.setPostFee("5.07");
		req.setExpressFee("15.07");
		req.setEmsFee("25.07");
//		Date dateTime = SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
//		req.setListTime(dateTime);
//		req.setIncrement("2.50");
		FileItem fItem = new FileItem(new File("C:\\Users\\W.k\\Desktop\\新建文件夹\\0008860153429_PE156595_S4.jpg"));
		req.setImage(fItem);
//		req.setPostageId(775752L);
//		req.setAuctionPoint(5L);
//		req.setPropertyAlias("pid:vid:别名;pid1:vid1:别名1");
//		req.setInputPids("pid1,pid2,pid3");
//		req.setSkuProperties("pid:vid;pid:vid");
//		req.setSkuQuantities("2,3,4");
//		req.setSkuPrices("200.07");
//		req.setSkuOuterIds("1234,1342");
//		req.setLang("zh_CN");
//		req.setOuterId("12345");
//		req.setProductId(123456789L);
//		req.setPicPath("i7/T1rfxpXcVhXXXH9QcZ_033150.jpg");
//		req.setAutoFill("time_card");
//		req.setInputStr("耐克;耐克系列;科比系列;科比系列;2K5,Nike乔丹鞋;乔丹系列;乔丹鞋系列;乔丹鞋系列;");
//		req.setIsTaobao(true);
//		req.setIsEx(true);
//		req.setIs3D(true);
//		req.setSellPromise(true);
//		req.setAfterSaleId(47758L);
//		req.setCodPostageId(53899L);
//		req.setIsLightningConsignment(true);
//		req.setWeight(100L);
//		req.setIsXinpin(false);
//		req.setSubStock(1L);

		ItemAddResponse response = client.execute(req , "6102317aace075904bf8deeeb1fb93f226b32eceb7837e142635718");
	System.out.println(response.getBody());
	}

	private static ArrayList<SellerCid> getSellerCid() {
		ArrayList<SellerCid> tbcategories = new ArrayList<SellerCid>();
		TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
		SellercatsListGetRequest req = new SellercatsListGetRequest();
		req.setNick(Constant.nick);
		SellercatsListGetResponse response;
		String tmp = null;
		try {
			response = client.execute(req);
			tmp = response.getBody();
		} catch (ApiException e1) {
			e1.printStackTrace();
		}
		try {
			JSONObject root = new JSONObject(tmp);
			JSONObject seller_cats = (JSONObject) root.get("sellercats_list_get_response");
			JSONObject seller_cat = (JSONObject) seller_cats.get("seller_cats");
			JSONArray seller_cat_array = seller_cat.getJSONArray("seller_cat");
			int i = 0;
			JSONObject product;
			while (!seller_cat_array.isNull(i)) {
				product = (JSONObject) seller_cat_array.get(i++);
				if ("0".equals(product.getString("parent_cid"))) {
					continue;
				} else {
					SellerCid tbp = new SellerCid();
					tbp.cid = product.getString("cid");
					tbp.name = product.getString("name");
					if (tbcategories.contains(tbp)) {
						SellerCid older = tbcategories.get(tbcategories.indexOf(tbp));
						older.cid = older.cid + "," + tbp.cid;
					} else {
						tbcategories.add(tbp);
					}
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tbcategories;
	}


}



class SellerCid {
	public String cid;
	public String name;

	@Override
	public boolean equals(Object obj) {
		return ((SellerCid) obj).name.equals(name);
	}

	@Override
	public String toString() {
		return name + Constant.split + cid;
	}
}
class OfficialCid {
	public String cid;
	public String name;

	@Override
	public boolean equals(Object obj) {
		return ((SellerCid) obj).name.equals(name);
	}

	@Override
	public String toString() {
		return name + Constant.split + cid;
	}
}
