package com.fec.ikeaII;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.response.SellercatsListGetResponse;
import com.taobao.api.ApiException;


public class OurCats {
	protected static String url = "http://gw.api.taobao.com/router/rest";
	protected static String appkey = "21402583";
	protected static String appSecret = "b1e64744762fc12051465563b0a59724";
	
	public static void testCatsGet() throws ApiException{
	TaobaoClient client=new DefaultTaobaoClient(url, appkey, appSecret);
	SellercatsListGetRequest req=new SellercatsListGetRequest();
	req.setNick("charick");
	SellercatsListGetResponse response = client.execute(req);


	String tmp = response.getBody();
	System.out.println(tmp);

	}
	public static void main(String[] args){
		try {
			OurCats.testCatsGet();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
