package com.fec.ikea.taobao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.response.ItemcatsGetResponse;

public class CatsGet {
	protected static String url = "http://gw.api.taobao.com/router/rest";
	protected static String appkey = "21402583";
	protected static String appSecret = "b1e64744762fc12051465563b0a59724";

	public static void testCatsGet(Long cid) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
		ItemcatsGetRequest req = new ItemcatsGetRequest();
		req.setFields("cid,parent_cid,name,is_parent");
		req.setParentCid(cid);
		// req.setCids("18957,19562,");
		ItemcatsGetResponse response = client.execute(req);
		String tmp = response.getBody();
		System.out.println(tmp);
		tmp = tmp.replace("{\"itemcats_get_response\":{\"item_cats\":{\"item_cat\":[{", "").replace("}]}}}", "").replace("},{", "!");
		String[] results = tmp.split("!");
		for (int i = 0; i < results.length; i++) {
			results[i] = results[i].replace("\"cid\":", "").replace("\"is_parent\":", "").replace("\"parent_cid\":", "").replace("\"name\":", "");
			if (results[i].contains("false")) {
				System.out.println(results[i]);
			} else {
				String[] str = results[i].split(",");

				testCatsGet(new Long(str[0]));
			}
		}

	}

	public static void main(String[] args) {
//		String url1 = "http://gw.api.taobao.com/router/rest";
//		TaobaoClient client=new DefaultTaobaoClient(url1, appkey, appSecret);
//		TopatsItemcatsGetRequest req=new TopatsItemcatsGetRequest();
//		req.setCids("18957,19562");
//		req.setOutputFormat("csv");
//		req.setType(1L);
//		try {
//			TopatsItemcatsGetResponse response = client.execute(req);
//		} catch (ApiException e1) {
//			e1.printStackTrace();
//		}
		try {
			CatsGet.testCatsGet(50006281L);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

}
