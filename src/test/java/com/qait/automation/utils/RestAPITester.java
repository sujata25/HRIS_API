package com.qait.automation.utils;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

/**
 * This is the utility class for data read write
 *
 * @author QAIT
 *
 */
public class RestAPITester {
	URL url = null;
	HttpURLConnection connection;
	JsonParser jsonParser;
	JsonObject object;
	static Response res;
	Response response;
	
	private void _connectToServiceURL(String urlToConnect) throws IOException {

		url = new URL(urlToConnect);
		connection = (HttpURLConnection) url.openConnection();

	}

	//added by sujata on 14/06/2018
	public static Response getResponse(String loginUrl,int port,String endpoint, Map<String, String> mapObject) {
		RestAssured.baseURI =loginUrl;
    	RestAssured.port= port;
    	res = RestAssured.given().log().all().header("Content-Type","Application/JSON").queryParameters(mapObject).when().post(endpoint);
    	return res;
	}
	
	//added by sujata on 14/06/2018
	public static Response getResponseusingToken(String loginUrl,int port,String endpoint, Map<String, String> mapObject2,String token) {
		RestAssured.baseURI =loginUrl;
    	RestAssured.port= port;
    	res = RestAssured.given().log().all().header("Content-Type","Application/JSON").header("Authorization", "Bearer "+token).queryParameters(mapObject2).when().get(endpoint);
    	return res;
	}
	
	public static Map<String, String> getQueryParameter(String filepath, String[] configKeys) {
		Map<String, String> mapObject = new HashMap<String, String>();
		for (String string : configKeys) {
			mapObject.put(string, getProperty(filepath, string));
		}
		return mapObject;
	}
	
	
	public int getStatusCodeOfTheService(String urlToConnect) {
		int code;
		try {
			_connectToServiceURL(urlToConnect);
			code = connection.getResponseCode();
		} catch (IOException ex) {
			code = 0;
			ex.printStackTrace();
		}

		return code;
	}

	public Map<String, List<String>> getAllHeaderFields(String urlToConnect) {
		Map<String, List<String>> headerFiledValue = new HashMap<String, List<String>>();
		try {
			_connectToServiceURL(urlToConnect);
			headerFiledValue = connection.getHeaderFields();
		} catch (IOException ex) {
			headerFiledValue = null;
			ex.printStackTrace();
		}
		return headerFiledValue;
	}

	public String getHeaderFieldValueFromTheService(String urlToConnect,
			String header) {
		String headerFiledValue = null;
		try {
			_connectToServiceURL(urlToConnect);
			headerFiledValue = connection.getHeaderField(header);
		} catch (IOException ex) {
			headerFiledValue = null;
			ex.printStackTrace();
		}
		return headerFiledValue;
	}

	public String getReponseMessageFromTheService(String urlToConnect) {
		String message = null;
		try {
			_connectToServiceURL(urlToConnect);
			message = connection.getResponseMessage();
		} catch (IOException ex) {
			message = null;
			ex.printStackTrace();
		}
		return message;
	}

	public void puttingInAQuery(String... a) {

	}

	private static String resOutput;

	public static String sendRequest(String urlStr, String param, String method)
			throws IOException {

		URL url = new URL(urlStr);

		HttpURLConnection conn = null;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod(method);

		if (method.equals("POST")) {
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(param.getBytes());
			os.flush();
		}

		conn.connect();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			resOutput = output;
		}

		conn.disconnect();
		return resOutput;
	}

}
