/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.robotclient;



import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;

import utd.aifc.credauth.CAConfiguration;

/**
 * @author DomainA
 *
 */
public class UserClient {

	public static String applicationDatabaseUrl = null;
	/**
	 * 
	 */
	public UserClient() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String configfilePath = "config.properties";
		String contributorlist = "Target:DomainB:12";
		
		// Loading configuration file
		loadConfigurationFile(configfilePath);
	
	
		// authenticating with OpenLDAP
		// receiving credential 
/*		String cred= CADBValidator.loginCA("Person1", "person1", "R_D_Director", "DomainB");
		//String cred = CAValidator.loginCA("Person1", "person1", "DomainB");
		System.out.println("Credential from Jovi:: " + cred );
		String[] creds = cred.split(":");*/
		
		Service s = new Service();
		
		

		
		//long stime1 = System.currentTimeMillis();			
		//s.create_robot("Corner321", "DomainB", "RobotRepository","Person1","DomainB", "R_D_Director",contributorlist,"10-11-2014:300:R_D_Director:DomainB");
		//long etime1 = System.currentTimeMillis();
		//System.out.println("Start Time = " + stime1 +" End Time = " + etime1 +" Total Time= " + (etime1 - stime1) );
		
		
		long stime19 = System.currentTimeMillis();
		s.read_robot("Corner321", "DomainB", "Person1", "R_D_Director","DomainB", "10-11-2014:300:R_D_Director:DomainB");
		long etime19 = System.currentTimeMillis();
		System.out.println("Start Time = " + stime19 +" End Time = " + etime19 +" Total Time= " + (etime19 - stime19) +"\n");
		
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
