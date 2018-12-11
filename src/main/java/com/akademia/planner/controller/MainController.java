package com.akademia.planner.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akademia.planner.model.address.Address;
import com.akademia.planner.service.MainService;

@Controller
@RequestMapping(value = { "/" })
public class MainController {
	
	@RequestMapping(value = { "/", "/main" }, method = RequestMethod.GET)
	public @ResponseBody String getAddressById() {
		return "Welcome!";
	}
	
	//TEST!
	@RequestMapping(value = { "/testRequest" }, method = RequestMethod.GET)
	public @ResponseBody String testRequest() throws IOException {
		//it works - sync
		
		String USER_AGENT = "Mozilla/5.0";
		String url = "http://localhost:8080/schedule/getAllSchedule";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		
		return response.toString();
	}
}