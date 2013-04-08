package com.fec.shop.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author wanghg
 * @time 上午11:56:35
 * @DEC 自动(或者叫半自动)获取淘宝API sessionkey -- 正式环境
 *
 */
public class GetSessionKey {
	
	// 获取授权码的地址
	private static final String CONTAINER_URL = "http://container.api.taobao.com/container?appkey=${appkey}";
	// 新URL中sessionkey的参数名
	private static final String SESSION_PARAM_KEY = "top_session";
	// 淘宝登录地址
	private static final String LOGIN_URL = "http://login.taobao.com/member/login.jhtml";
	
	@SuppressWarnings("deprecation")
	/**
	 * 根据APPKEY,用户名,密码获取sessionkey
	 * @param appkey
	 * @param nick
	 * @param psw
	 * @return
	 */
	public static String getSessionKey(String appkey, String nick, String psw) {
        HttpClient httpClient = new HttpClient();
        String redirectURL = "";
        PostMethod postMethod = new PostMethod(LOGIN_URL);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        nameValues.add(new NameValuePair("TPL_username", nick));
        nameValues.add(new NameValuePair("TPL_password", psw));
        nameValues.add(new NameValuePair("action", "Authenticator"));
        nameValues.add(new NameValuePair("TPL_redirect_url", ""));
        nameValues.add(new NameValuePair("from", "tb"));
        nameValues.add(new NameValuePair("event_submit_do_login", "anything"));
        nameValues.add(new NameValuePair("loginType", "3"));
        postMethod.setRequestBody(nameValues.toArray(new NameValuePair[nameValues.size()]));
        
        try {
            httpClient.executeMethod(postMethod);
            redirectURL = postMethod.getResponseHeader("Location").getValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            postMethod.releaseConnection();
        }
        
        String newUrl = CONTAINER_URL.replace("${appkey}", appkey);
        String agreementsign="";
        
        //登录到授权页面获取 agreementsign值
        try {
            URI uri = new URI(newUrl);
            postMethod.setURI(uri);
            httpClient.executeMethod(postMethod);
            String body = new String(postMethod.getResponseBodyAsString().getBytes("gb2312"));
            agreementsign = body.substring(body.indexOf("name='agreementsign' value=")+28, body.indexOf("' id='agreementsign'"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            postMethod.releaseConnection();
        }
        
        //提交agreementsign值,获取包含sessionkey的新URL
        try {
        	postMethod.setURI(new URI("http://container.api.taobao.com/container?appkey="+appkey+"&agreement=true&agreementsign="+agreementsign));
        	httpClient.executeMethod(postMethod);
        	redirectURL = postMethod.getResponseHeader("Location").getValue();
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        } finally {
        	postMethod.releaseConnection();
        }
        
        GetMethod get = new GetMethod(redirectURL);
        try {
            httpClient.executeMethod(get);
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        } finally {
            get.releaseConnection();
        }
        return extractSessionKey(get.getQueryString(), SESSION_PARAM_KEY);
    }
    
    
    
	/**
	 * 截取新的URL地址,返回sessionkey
	 * @param rsp
	 * @param key
	 * @return
	 */
    private static String extractSessionKey(String rsp, String key) {
        if (rsp == null)
            return null;
        Map<String, String> nameValuePair = new HashMap<String, String>();
        String[] array = rsp.split("&");
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (s.indexOf("=") > 0) {
                String[] nameValues = s.split("=");
                if (nameValues.length == 2) {
                    nameValuePair.put(s.split("=")[0], s.split("=")[1]);
                }
            }
        }
        return nameValuePair.get(key);
    }
    

    /**
     * sesssionkey 测试
     * @param args
     */
	public static void main(String[] args) {
		try{
			String sessionKey = getSessionKey("appkey", "账户","密码");
			System.out.println("sessionKey:"+sessionKey);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
