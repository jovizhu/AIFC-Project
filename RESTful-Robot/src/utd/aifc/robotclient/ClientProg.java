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
import java.sql.SQLException;
import java.util.Properties;

public class ClientProg {

	
	public static String applicationDatabaseUrl = null;
	public static String authenticationURL = null;
	public static Properties prop = null;
	public static String currentDomain = null;
	
	public ClientProg() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		try{
			
		
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
		HttpURLConnection conn = (HttpURLConnection) url
				.openConnection();
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
				
		//String cred = CADBValidator.loginCA("Person1", "person1","R_D_Director", "DomainB");
		System.out.println("Credential:: " + credential );
		
		
		
		String[] creds = credential.split(":");
	
		if(creds[0].equalsIgnoreCase("true"))		
		{
			Service s = new Service();
			
			long stime1 = System.currentTimeMillis();			
			s.create_robot("new001", "DomainB", "RobotRepository","Person1","DomainB", creds[3],contributorlist,credential);
			long etime1 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime1 +" End Time = " + etime1 +" Total Time= " + (etime1 - stime1) );
			
			
			long stime19 = System.currentTimeMillis();
			s.read_robot("new001", "DomainB", "Person1", creds[3],creds[4], credential);
			long etime19 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime19 +" End Time = " + etime19 +" Total Time= " + (etime19 - stime19) +"\n");
		
		}
		}
		
		catch(Exception ex){
			System.out.println("System Message: " + ex.getMessage());
		}
	}

	public static boolean loadConfigurationFile(String configFile) {

		try {

			File file = new File(configFile);
			FileInputStream input = new FileInputStream(file);
		
			prop = new Properties();
			prop.load(input);

			if (prop.isEmpty()) {
				System.err.println("Error: ops~~ Config file loaded failed ...");
				return false;
			}
			
			//CAConfiguration.databaseUser = prop.getProperty("database-user");
			//CAConfiguration.databasePassword = prop.getProperty("database-password");
			//CAConfiguration.databaseURL = prop.getProperty("aifc-database-URL");
			//CAConfiguration.domain = prop.getProperty("current-domain");
			authenticationURL = prop.getProperty("DomainB-authentication");
			applicationDatabaseUrl = prop.getProperty("application-database-url");
			currentDomain = prop.getProperty("current-domain");
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
