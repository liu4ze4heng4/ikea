package com.fec.shop.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HtmlUtil {

	public static String getHtmlContent(String url0) throws IOException {
		String content = null;
		String strURL = url0;
		URL url;
		try {
			url = new URL(strURL);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			InputStreamReader input = new InputStreamReader(
					httpConn.getInputStream(), "utf-8");
			BufferedReader bufReader = new BufferedReader(input);
			String line = "";
			StringBuilder contentBuf = new StringBuilder();
			while ((line = bufReader.readLine()) != null) {
				contentBuf.append(line);
			}

			content = contentBuf.toString();

		} catch (ConnectException e) {
			System.err.println("抓取失败ConnectException："+url0);
			return null;
		} catch (MalformedURLException e) {
			System.err.println("抓取失败MalformedURLException："+url0);
			return null;
		} 
		return content;
	}
}
