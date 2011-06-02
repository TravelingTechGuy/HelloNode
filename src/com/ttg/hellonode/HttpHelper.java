package com.ttg.hellonode;

/**
 *
 * @author guy
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpHelper {
    private HttpClient client;
    
    public HttpHelper() {
        client = new DefaultHttpClient();
    }
    
    private String parseResponse(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line).append("\r\n");
            }
            in.close();
            result = str.toString();
        }
        catch(Exception e) {
            result = "Error: " + e.getMessage();
        }
        return result;
    }
    
    public String get(String url, ArrayList<BasicNameValuePair> params) {
        try {
        	if(params != null) {
        		if(!url.endsWith("?"))
        	        url += "?";
        		String paramString = URLEncodedUtils.format(params, "utf-8");
        	    url += paramString;
            }
        	HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            return this.parseResponse(response);
        }
        catch (ClientProtocolException e) {
            return "Error: " + e.getMessage(); 
        } 
        catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
    
    public JSONObject getJSON(String url, ArrayList<BasicNameValuePair> params) {
    	JSONObject reply = null;
    	try {
        	String result = this.get(url, params);
            reply = new JSONObject(result);
        }
        catch (JSONException e) {
        	e.printStackTrace();
		}
        return reply;
    }
    
    public String post(String url, ArrayList<BasicNameValuePair> params) throws UnsupportedEncodingException {
        HttpPost request = new HttpPost(url);
        try {
        	if(params != null) {
                request.setEntity(new UrlEncodedFormEntity(params));
        	}
            HttpResponse response = client.execute(request);
            return this.parseResponse(response);  
        }
        catch (ClientProtocolException e) {
            return "Error: " + e.getMessage(); 
        } 
        catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    } 
} 