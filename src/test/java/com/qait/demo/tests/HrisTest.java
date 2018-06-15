package com.qait.demo.tests;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;
import com.qait.demo.keywords.HrisCustomerAction;

import junit.framework.Assert;

public class HrisTest {
	
	private TestSessionInitiator test;
	String  authendPoint, loginUrl,timesheetendPoint,messagevalue;
	int loginPort;
	Response statusresponse;
	
	@BeforeClass
	public void initializeVariable(){
		test = new TestSessionInitiator(this.getClass().getName());
		_initVars();
	}
	
	//@BeforeClass
	private void _initVars() {
			loginUrl = YamlReader.getYamlValue("baseUrl");
			loginPort =Integer.parseInt(YamlReader.getYamlValue("baseport"));
			authendPoint = YamlReader.getYamlValue("authendpoint");
			timesheetendPoint = YamlReader.getYamlValue("timesheetendpoint");
	
	}
	
	@Test
	public void Test01LoginResponse() throws FileNotFoundException, JSONException{
			statusresponse = test.hrisaction.login(loginUrl,loginPort,authendPoint);
			Assert.assertEquals("1314", getProperty(test.hrisaction.ConfigResponseFile, "EmpId"));
			//Assert.assertEquals("200", statusresponse.getStatusCode());
	}
	
	@Test
	public void Test02TimesheetResponse() throws FileNotFoundException, JSONException{
			statusresponse = test.hrisaction.timeSheet(loginUrl,loginPort,timesheetendPoint);
			messagevalue = test.hrisaction.convertResponseToJson(statusresponse,"$..message");
			Assert.assertEquals("Timesheet generated", messagevalue.substring(2, messagevalue.length()-2));
	}

	
	
}
