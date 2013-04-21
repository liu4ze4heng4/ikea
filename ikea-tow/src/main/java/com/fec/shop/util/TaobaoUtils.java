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
import com.fec.shop.model.ProductDetail;
import com.fec.shop.model.ProductSimple;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.request.ItemUpdateRequest;
import com.taobao.api.request.ProductsGetRequest;
import com.taobao.api.request.SellercatsListAddRequest;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.response.ItemAddResponse;
import com.taobao.api.response.ItemUpdateResponse;
import com.taobao.api.response.ProductsGetResponse;
import com.taobao.api.response.SellercatsListAddResponse;
import com.taobao.api.response.SellercatsListGetResponse;

public class TaobaoUtils {
	public static void main(String[] args) {
		String pid;
		try {
			InputStreamReader isr=new InputStreamReader(new FileInputStream("E:\\IKEA临时项目\\灯.txt"));
			BufferedReader bufReader = new BufferedReader(isr);
			try {
				while ((pid = bufReader.readLine()) != null) {

					TaobaoUtils.addTaobaoItem(pid);
				}
			
				bufReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			Product p = new Product("80133987", "680631506");
//			p.toPic(4, "E:\\IKEA临时项目\\","jpg");
//			TaobaoUtils.updateItem(p,"19466427313");
//			TaobaoUtils.getProductFromTaobao();
;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		// TaobaoUtils.getCCMapFromFile();
		// SellercatsListAddRequest req=new SellercatsListAddRequest();
		// req.setParentCid(0l);
		// req.setName("testsets======");
		// req.setPictUrl("http://www.ikea.com/cn/zh/images/products/fu-li-suo-yang-san__0103516_PE249675_S4.JPG");
		// TaobaoClient client = new DefaultTaobaoClient(Constant.url,
		// Constant.appkey, Constant.appSecret);
		// try {
		// SellercatsListAddResponse response=client.execute(req);
		// response.getParams();
		// response.getBody();
		// response.getSellerCat();
		// response.getMsg();
		// } catch (ApiException e) {
		// e.printStackTrace();
		// }
		System.out.println("ok!");
	}

	public static Map<String, String> getCCMapFromFile() {
		Map<String, String> result = new HashMap<String, String>();
		String temp;
		String[] tempA;
		try {
			InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File(Constant.tb_category_file)), "utf-8");
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



	private static void addTaobaoItem(String id)  {
		TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
		SQLHelper sh=new SQLHelper();
		ProductSimple p=sh.getProduct(id);

		ItemAddRequest req = new ItemAddRequest();

		req.setNum(30L);
		req.setPrice(Double.toString(p.getPrice().get(0)));
		req.setType("fixed");
		req.setStuffStatus("new");
		req.setTitle(p.getTitle());
		req.setDesc("12345667890");
		req.setLocationState("北京");
		req.setLocationCity("北京");
		req.setApproveStatus("instock");
		req.setCid(50006298L);
		// req.setProps("20000:33564;21514:38489");
		req.setFreightPayer("buyer");
		// req.setValidThru(7L);
		req.setHasInvoice(true);
		req.setHasWarranty(true);
		// req.setHasShowcase(true);
		req.setSellerCids(p.getOuter_cid());
		req.setItemWeight("1");
		// req.setHasDiscount(true);
		// req.setPostFee("5.07");
		// req.setExpressFee("15.07");
		// req.setEmsFee("25.07");
		// Date dateTime =
		// SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
		// req.setListTime(dateTime);
		// req.setIncrement("2.50");
		
//		File picpath=new File("E:\\IKEA临时项目\\处理后\\"+p.getProduct_id() + "_" + p.getaMainPic(0) + "_S4.jpg");
//		if(picpath.exists())
//				{FileItem fItem = new FileItem(picpath);	
//				req.setImage(fItem);
//				System.out.println("从已处理更新了主图");}
//		else if(new File("E:\\IKEA临时项目\\products\\" + p.getProduct_id() + "_" + p.getaMainPic(0) + "_S4.jpg").exists() )
//		{
//			picpath=new File("E:\\IKEA临时项目\\products\\" + p.getProduct_id() + "_" + p.getaMainPic(0) + "_S4.jpg");
			FileItem fItem = new FileItem(p.getPicpath());
			req.setImage(fItem);
//			System.out.println("从网络更新了主图");}
//		else{
//			System.out.println("未更新主图");
//		}
		req.setPostageId(755800881L);
		// req.setAuctionPoint(5L);
		// req.setPropertyAlias("pid:vid:别名;pid1:vid1:别名1");
		// req.setInputPids("pid1,pid2,pid3");
		// req.setSkuProperties("pid:vid;pid:vid");
		// req.setSkuQuantities("2,3,4");
		// req.setSkuPrices("200.07");
		// req.setSkuOuterIds("1234,1342");
		// req.setLang("zh_CN");
		 req.setOuterId(p.getPid());
		// req.setProductId(123456789L);
		// req.setPicPath("i7/T1rfxpXcVhXXXH9QcZ_033150.jpg");
		// req.setAutoFill("time_card");
		// req.setInputStr("耐克;耐克系列;科比系列;科比系列;2K5,Nike乔丹鞋;乔丹系列;乔丹鞋系列;乔丹鞋系列;");
		// req.setIsTaobao(true);
		// req.setIsEx(true);
		// req.setIs3D(true);
		// req.setSellPromise(true);
		// req.setAfterSaleId(47758L);
		// req.setCodPostageId(53899L);
		// req.setIsLightningConsignment(true);
		// req.setWeight(100L);
		// req.setIsXinpin(false);
		// req.setSubStock(1L);
		ItemAddResponse response;
		try {
			response = client.execute(req, "6101508db5e3251c1508e4bd8a89da7c49d44f121b33dc642635718");
			System.out.println(response.getBody()+"!");
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/**
 * 从IKEA更新淘宝产品描述信息
 */
	private static void updateItemFromWeb(String id,String tid) {
		TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
ProductSimple p=null;
IkeaUtils.initProductSimple(p);
ProductDetail pd = null;
IkeaUtils.initProductdetail(pd);

		ItemUpdateRequest req = new ItemUpdateRequest();
		req.setNumIid(Long.parseLong(tid));
//		req.setCid(1512L);
//		req.setProps("20000:33564;21514:38489");
//		req.setNum(50L);
//		req.setPrice("200.07");
//		req.setTitle("Nokia N97全新行货");
		req.setDesc(pd.getDescribtion());
//		req.setLocationState("浙江");
//		req.setLocationCity("杭州");
//		req.setPostFee("5.07");
//		req.setExpressFee("15.07");
//		req.setEmsFee("25.07");
//		Date dateTime;
//		try {
//			dateTime = SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
//			req.setListTime(dateTime);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		req.setIncrement("10.50");
		File picpath=new File("E:\\QuHoo\\all\\"+p.getPid() + "_" + p.getMainPics().get(0) + "_S4.jpg");
if(picpath.exists())
		{FileItem fItem = new FileItem(picpath);	
		req.setImage(fItem);
		System.out.println("从已处理更新了主图");}
else if(new File("E:\\IKEA临时项目\\products\\" + p.getPid() + "_" + p.getMainPics().get(0) + "_S4.jpg").exists() )
{
	picpath=new File("E:\\IKEA临时项目\\products\\" + p.getPid() + "_" + p.getMainPics().get(0) + "_S4.jpg");
	FileItem fItem = new FileItem(picpath);
	req.setImage(fItem);
	System.out.println("从网络更新了主图");}
else{
	System.out.println("未更新主图");
}
//		req.setStuffStatus("new");
//		req.setAuctionPoint(5L);
//		req.setPropertyAlias("pid:vid:别名;pid1:vid1:别名1");
//		req.setInputPids("pid1,pid2,pid3");
//		req.setSkuQuantities("2,3,4");
//		req.setSkuPrices("10.00,5.00");
//		req.setSkuProperties("pid:vid;pid:vid");
//		req.setSellerCids("1105");
		req.setPostageId(755800881L);
		req.setOuterId(p.getProductIds().get(0));
//		req.setProductId(123456789L);
//		req.setPicPath("i7/T1rfxpXcVhXXXH9QcZ_033150.jpg");
//		req.setAutoFill("time_card");
//		req.setSkuOuterIds("1234,1342");
//		req.setIsTaobao(true);
//		req.setIsEx(true);
//		req.setIs3D(true);
//		req.setIsReplaceSku(true);
//		req.setInputStr("耐克;耐克系列;科比系列;科比系列;2K5,Nike乔丹鞋;乔丹系列;乔丹鞋系列;乔丹鞋系列;json5");
//		req.setLang("zh_CN");
//		req.setHasDiscount(true);
//		req.setHasShowcase(true);
		req.setApproveStatus("onsale");
//		req.setFreightPayer("buyer");
//		req.setValidThru(7L);
//		req.setHasInvoice(true);
//		req.setHasWarranty(true);
//		req.setAfterSaleId(47745L);
//		req.setSellPromise(true);
//		req.setCodPostageId(53899L);
//		req.setIsLightningConsignment(true);
//		req.setWeight(1L);
//		req.setIsXinpin(true);
//		req.setSubStock(1L);
//		req.setFoodSecurityPrdLicenseNo("QS410006010388");
//		req.setFoodSecurityDesignCode("Q/DHL.001-2008");
//		req.setFoodSecurityFactory("远东恒天然乳品有限公司");
//		req.setFoodSecurityFactorySite("台北市仁爱路4段85号");
//		req.setFoodSecurityContact("00800-021216");
//		req.setFoodSecurityMix("有机乳糖、有机植物油");
//		req.setFoodSecurityPlanStorage("常温");
//		req.setFoodSecurityPeriod("2年");
//		req.setFoodSecurityFoodAdditive("磷脂 、膨松剂");
//		req.setFoodSecuritySupplier("深圳岸通商贸有限公司");
//		req.setFoodSecurityProductDateStart("2012-06-01");
//		req.setFoodSecurityProductDateEnd("2012-06-10");
//		req.setFoodSecurityStockDateStart("2012-06-20");
//		req.setFoodSecurityStockDateEnd("2012-06-30");
//		req.setGlobalStockType("1");
//		req.setItemSize("bulk:8");
		req.setItemWeight("1");
//		req.setEmptyFields("food_security.plan_storage,food_security.period");
//		req.setLocalityLifeExpirydate("2012-08-06,2012-08-16");
//		req.setLocalityLifeNetworkId("5645746");
//		req.setLocalityLifeMerchant("56879:码商X");
//		req.setLocalityLifeVerification("1");
//		req.setLocalityLifeRefundRatio(50L);
//		req.setLocalityLifeChooseLogis("0");
//		req.setLocalityLifeOnsaleAutoRefundRatio(80L);
//		req.setScenicTicketPayWay(1L);
//		req.setScenicTicketBookCost("5.99");
//		req.setPaimaiInfoMode(1L);
//		req.setPaimaiInfoDeposit(20L);
//		req.setPaimaiInfoInterval(5L);
//		req.setPaimaiInfoReserve("11");
//		req.setPaimaiInfoValidHour(2L);
//		req.setPaimaiInfoValidMinute(22L);
		try {
			
			ItemUpdateResponse response = client.execute(req, "6102317aace075904bf8deeeb1fb93f226b32eceb7837e142635718");
			System.out.println(response.getBody());

		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static void getProductFromTaobao(){
		TaobaoClient client=new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
		ProductsGetRequest req=new ProductsGetRequest();
		req.setFields("product_id,tsc,cat_name,name");
		req.setNick("charick");
//		req.setPageNo(1L);
//		req.setPageSize(40L);
		try {
			ProductsGetResponse response = client.execute(req);
			System.out.println(response.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
