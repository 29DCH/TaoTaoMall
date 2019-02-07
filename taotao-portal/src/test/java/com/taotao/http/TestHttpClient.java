package com.taotao.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {

	@Test
	public void doGet() throws Exception {
		//创建一个httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个httpGet对象,指定要访问的url
		HttpGet httpGet = new HttpGet("http://www.jd.com");
		//执行请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		//取出相应的内容
		HttpEntity entity = response.getEntity();
		//默认的编码是ISO8859-1
		String responseStr = EntityUtils.toString(entity, "gb2312");
		System.out.println(responseStr);
		//关闭httpclient
		response.close();
		httpClient.close();
	}
	
	@Test
	public void doGetWithParam() throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个URI
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web")
				.addParameter("query", "谢霆锋被骑脖子");
		System.out.println(uriBuilder.build());
		//创建一个GET对象
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		//执行请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		//取出相应的内容
		HttpEntity entity = response.getEntity();
		//默认的编码是ISO8859-1
		String responseStr = EntityUtils.toString(entity, "gb2312");
		System.out.println(responseStr);
		//关闭httpclient
		response.close();
		httpClient.close();
	}
	
	@Test
	public void doPost() throws Exception {
		//创建一个Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一post对象
		HttpPost httpPost = new HttpPost("http://www.baidu.com");
		//创建一个模拟表单
		List<NameValuePair> param = new ArrayList<>();
		param.add(new BasicNameValuePair("name", "admin"));
		param.add(new BasicNameValuePair("password", "123"));
		StringEntity entity = new UrlEncodedFormEntity(param);
		//把表单内容放入httpPost对象
		httpPost.setEntity(entity);
		//执行请求
		CloseableHttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity());
		System.out.println(result);
		response.close();
		httpClient.close();
	}
}
