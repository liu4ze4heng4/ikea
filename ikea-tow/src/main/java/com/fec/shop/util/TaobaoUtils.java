package com.fec.shop.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fec.shop.constant.Constant;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.SellercatsListAddRequest;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.response.SellercatsListAddResponse;
import com.taobao.api.response.SellercatsListGetResponse;

public class TaobaoUtils {
	public static void main(String[] args) {
		TaobaoUtils.saveTBcategory2File();
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
			ArrayList<TBCategory> tbclist = getTBcategories();
			FileWriter fw = new FileWriter(new File(Constant.tb_category_file));
			for (TBCategory tbCategory : tbclist) {
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

	private static ArrayList<TBCategory> getTBcategories() {
		ArrayList<TBCategory> tbcategories = new ArrayList<TBCategory>();
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
					TBCategory tbp = new TBCategory();
					tbp.cid = product.getString("cid");
					tbp.name = product.getString("name");
					if (tbcategories.contains(tbp)) {
						TBCategory older = tbcategories.get(tbcategories.indexOf(tbp));
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

class TBCategory {
	public String cid;
	public String name;

	@Override
	public boolean equals(Object obj) {
		return ((TBCategory) obj).name.equals(name);
	}

	@Override
	public String toString() {
		return name + Constant.split + cid;
	}
}
