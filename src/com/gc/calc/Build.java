package com.gc.calc;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import com.gc.util.APICredentials;

public class Build {
	public static JSONArray detroitAPIBuilder(String page) throws IOException, ClientProtocolException {
		HttpClient http = HttpClientBuilder.create().build();
		HttpHost host = new HttpHost("data.detroitmi.gov", 443, "https");
		HttpGet getPage = new HttpGet(page + APICredentials.DATADETROIT_KEY);
		HttpResponse resp = http.execute(host, getPage);
		String jsonString = EntityUtils.toString(resp.getEntity());
		JSONArray arr = new JSONArray(jsonString);
		return arr;
	}
}
