package com.fec.ikeaII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CaptureHtml {
	static String buf = new String();


	public static String captureHtml(String id) {
		String strURL = "http://www.ikea.com/cn/zh/catalog/products/" + id + "/";
		URL url;
		try {
			url = new URL(strURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
			BufferedReader bufReader = new BufferedReader(input);
			String line = "";
			StringBuilder contentBuf = new StringBuilder();
			while ((line = bufReader.readLine()) != null) {
				contentBuf.append(line);
			}

			buf = contentBuf.toString();

		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + id + "³¬Ê±´íÎó");
			captureHtml(id);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// captureHtml(id);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + id + "IO´íÎó");
			// captureHtml(id);
		}
		return buf;
	}
}
