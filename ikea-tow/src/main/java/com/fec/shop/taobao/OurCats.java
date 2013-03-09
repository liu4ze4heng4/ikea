package com.fec.shop.taobao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fec.shop.model.TaobaoProduct;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.response.SellercatsListGetResponse;

public class OurCats {
	protected static String url = "http://gw.api.taobao.com/router/rest";
	protected static String appkey = "21402583";
	protected static String appSecret = "b1e64744762fc12051465563b0a59724";

	public static Map<String, List<TaobaoProduct>> getTaobaoCats() {
		Map<String, List<TaobaoProduct>> tbProducts = new HashMap<String, List<TaobaoProduct>>();

		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
		SellercatsListGetRequest req = new SellercatsListGetRequest();
		req.setNick("charick");
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
			List<TaobaoProduct> tbpList;
			while (!seller_cat_array.isNull(i)) {
				product = (JSONObject) seller_cat_array.get(i++);
				TaobaoProduct tbp = new TaobaoProduct();
				tbp.setCid(product.getString("cid"));
				tbp.setName(product.getString("name"));
				tbp.setParent_cid(product.getString("parent_cid"));
				tbp.setSort_order(product.getString("sort_order"));
				tbp.setType(product.getString("type"));
				if("0".equals(tbp.getParent_cid()))continue;
				tbpList = tbProducts.get(tbp.getName());
				if (tbpList == null) {
					tbpList = new ArrayList<TaobaoProduct>(1);
					tbpList.add(tbp);
					tbProducts.put(tbp.getName(), tbpList);
				} else {
					tbpList.add(tbp);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println(tbProducts);
		return tbProducts;
	}

	public static void main(String[] args) {
		OurCats.getTaobaoCats();
	}
}
