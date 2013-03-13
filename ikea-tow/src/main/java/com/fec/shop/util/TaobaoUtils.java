package com.fec.shop.util;

import static com.fec.shop.taobao.TBConstant.appSecret;
import static com.fec.shop.taobao.TBConstant.appkey;
import static com.fec.shop.taobao.TBConstant.nick;
import static com.fec.shop.taobao.TBConstant.url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fec.shop.model.Category;
import com.fec.shop.model.TBCategroy;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.response.SellercatsListGetResponse;

public class TaobaoUtils {
	public static ArrayList<TBCategroy> getTBcategories() {
		ArrayList<TBCategroy> tbcategories=new ArrayList<TBCategroy>();
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
		SellercatsListGetRequest req = new SellercatsListGetRequest();
		req.setNick(nick);
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
			//全部店铺分类
			JSONArray seller_cat_array = seller_cat.getJSONArray("seller_cat");
			int i = 0;
			JSONObject product;
			while (!seller_cat_array.isNull(i)) {
				product = (JSONObject) seller_cat_array.get(i++);
				TBCategroy tbp = new TBCategroy();
				tbp.setTb_cid(product.getString("cid"));
				tbp.setTb_parent_cid(product.getString("parent_cid"));
				tbp.setTb_name(product.getString("name"));
				if ("0".equals(tbp.getTb_parent_cid()))
					continue;
				tbcategories.add(tbp);
				
			
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tbcategories;
}
}
