/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.robotclient;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import utd.aifc.credauth.CAConfiguration;

/**
 * @author DomainA
 *
 */
public class UserProg_DomainD {

	/**
	 * 
	 */
	public static String applicationDatabaseUrl = null;
	public static String authenticationURL = null;
	
	public UserProg_DomainD() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			String configfilePath = "config.properties";
			String contributorlist = null;
			loadConfigurationFile(configfilePath);
		
			
//////////////////////////////////////////////////////
			String uname = "Person1";
			String pass = "person1";
			String role="R_D_Director";
			String domainName="DomainB";
			
			String requestString  = null;
			String credential = null;
			requestString = "username=" + uname
			+ "&&password=" + pass 
			+ "&&rolename=" + role
			+ "&&domainname=" + domainName;
			
			URL url = new URL(authenticationURL+ requestString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			//System.out.println("url Stirng " + url.toString());
			
			if (conn.getResponseCode() != 200) {
			throw new RuntimeException(
			"Failed : HTTP error code : "
			+ conn.getResponseCode());
			}
			
			BufferedReader br2 = new BufferedReader(
			new InputStreamReader((conn.getInputStream())));
			
			System.out.println("Output from Server ....");
			
			if ((credential = br2.readLine()) != null) {
			System.out.println("These is the result from the CA");
			
			}
			
			conn.disconnect();



/////////////////////////////////////////////////////////////


		
		
			//String cred = CAValidator.loginCA("Person1", "person1", "DomainD");
			System.out.println("Credential from Jovi:: " + credential );
			
			String[] creds = credential.split(":");
		
		

			Service s = new Service();
			
			long stime1 = System.currentTimeMillis();
			s.create_robot("rbt_New", "DomainD", "RobotTable","Person1","DomainD", creds[3],contributorlist,credential);
			long etime1 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime1 +" End Time = " + etime1 +" Total Time= " + (etime1 - stime1) );
			
			long stime2 = System.currentTimeMillis();
			s.create_robot("rbt_New", "DomainD", "RobotTable","Person1","DomainD", creds[3],contributorlist,credential);
			long etime2 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime2 +" End Time = " + etime2 +" Total Time= " + (etime2 - stime2) );
			
			long stime3 = System.currentTimeMillis();
			s.create_robot("rbt_new", "DomainD", "RobotTable","Person1","DomainD", creds[3],contributorlist,credential);
			long etime3 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime3 +" End Time = " + etime3 +" Total Time= " + (etime3 - stime3) );
			
			
			
			
			
			
			
			//s.read_robot("MyFirstDroid", "DomainD", "Person1", creds[2],creds[3], cred);
			
			
			
			//resourcename: resource Domain: ACRN: user name: userdomain: user role: (contributorname: contributordomain: version):
			//s.create_robot("Test100", "DomainB", "RobotRepository", "Person1", creds[3], creds[2], contributors, cred);
			
			//s.create_robot("Test100", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			
			
			//contributorlist = "Test100:DomainB:1";
			//s.create_robot("Test101", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	public static boolean loadConfigurationFile(String configFile) {

		try {

			File file = new File(configFile);
			FileInputStream input = new FileInputStream(file);
			Properties prop = null;
			prop = new Properties();
			prop.load(input);

			if (prop.isEmpty()) {
				System.err.println("Error: ops~~ Config file loaded failed ...");
				return false;
			}
			
			CAConfiguration.databaseUser = prop.getProperty("database-user");
			CAConfiguration.databasePassword = prop.getProperty("database-password");
			CAConfiguration.databaseURL = prop.getProperty("aifc-database-URL");
			CAConfiguration.domain = prop.getProperty("current-domain");
			applicationDatabaseUrl = prop.getProperty("application-database-url");
			System.out.print("Config File loaded");

		} catch (Exception ex) {
			System.err.println("Error loading config file." + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

		System.out.println("****************************************\n"
				+ "Config file loaded successfully...\n"
				+ "******************************");
		return true;

	}

}
