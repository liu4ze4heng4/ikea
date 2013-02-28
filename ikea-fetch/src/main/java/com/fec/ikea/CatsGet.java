package com.fec.ikea;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.response.ItemcatsGetResponse;

public class CatsGet {
	protected static String url = "http://gw.api.taobao.com/router/rest";
	// 正式环境需要设置为:http://gw.api.taobao.com/router/rest
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
		tmp = tmp
				.replace(
						"{\"itemcats_get_response\":{\"item_cats\":{\"item_cat\":[{",
						"").replace("}]}}}", "").replace("},{", "!");
		String[] results = tmp.split("!");
		for (int i = 0; i < results.length; i++) {
			results[i] = results[i].replace("\"cid\":", "")
					.replace("\"is_parent\":", "")
					.replace("\"parent_cid\":", "").replace("\"name\":", "");
			System.out.println(results[i]);
			 if(results[i].contains("true"))
			 {String[] str=results[i].split(",");
			 
			 testCatsGet(new Long(str[0]));
			 }
			 else break;
		}

		// System.out.println("result:"+response.getSubMsg());
	}

	public static void main(String[] args) {
		try {
			CatsGet.testCatsGet(50008164L);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}