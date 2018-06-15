package com.qait.demo.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.RestAPITester.getResponse;
import static com.qait.automation.utils.RestAPITester.getResponseusingToken;
import static com.qait.automation.utils.RestAPITester.getQueryParameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.google.gson.JsonObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.RestAPITester;

public class HrisCustomerAction extends GetPage  {

	WebDriver driver;
	Response res,response;
	String key[],value[],readtokenpath,token_value;
	Map<String, String> queryParamResponse;
	private String defaultQueryParamFile = System.getProperty("user.dir") + "/src/test/resources/ResponseFiles/QueryParam.properties";
	public static String ConfigResponseFile = System.getProperty("user.dir") + "/src/test/resources/ResponseFiles/Config.properties";
	String[] loginconfigKeys = { "email", "password", "udid", "device_os", "device_name", "device_ip_addr" };
	String[] timesheetconfigKeys = {"start_date", "end_date"};
	
	public HrisCustomerAction(WebDriver driver) {
		super(driver, "hrisaction");
		this.driver = driver;
		
	}

	
	public Response login(String loginUrl,int port,String endpoint) throws FileNotFoundException, JSONException  {
		queryParamResponse = getQueryParameter(defaultQueryParamFile,loginconfigKeys);
    	response = getResponse(loginUrl, port, endpoint, queryParamResponse);
    	writeTokenToFile(response);
		return response;
		
	}
	
	public Response timeSheet(String loginUrl,int port,String endpoint) throws FileNotFoundException, JSONException  {
		queryParamResponse = getQueryParameter(defaultQueryParamFile,timesheetconfigKeys);
    	String tokenvalue = getProperty(ConfigResponseFile, "Token");
    	response = getResponseusingToken(loginUrl, port, endpoint, queryParamResponse,tokenvalue);
    	return response;
		
	}
	
	public String convertResponseToJson(Response response,String value) throws JSONException {
		JSONObject jsonObject= new JSONObject(response.asString());
		DocumentContext parsejsonobject = com.jayway.jsonpath.JsonPath.parse(jsonObject.toString());
		String readjsonpath = parsejsonobject.read(value).toString();
		return readjsonpath;
		
	}
	
	private void writeTokenToFile(Response response) throws FileNotFoundException, JSONException {
		PrintStream printstream=new PrintStream( new FileOutputStream(new File(ConfigResponseFile)));
		readtokenpath = convertResponseToJson(response,"$..token");
		token_value = readtokenpath.substring(2, readtokenpath.length()-2);
		printstream.print("Token=" +  token_value + "\n");
		printstream.print("EmpId=" +  convertResponseToJson(response,"$..emp_number").substring(1, 5));
		//printstream.print("Status=" +  parsejsonobject.read("$..status").toString());
	}

	
}
