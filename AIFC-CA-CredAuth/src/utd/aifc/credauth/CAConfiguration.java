package utd.aifc.credauth;

/*********************** AIFC-Validator **************************************
(C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas

All rights reserved. Use of this software is permitted for non-commercial
research purposes, and it may be copied only for that use.  All copies must
include this copyright message.  This software is made available AS IS, and
neither the authors nor UTD make any warranty about the software or its
performance.
*************************************************************************/

import java.io.InputStream;
import java.util.Properties;


public class CAConfiguration {
	
	public static String domain = null;
	public static String databaseURL=null;
	public static String databasePassword=null;
	public static String databaseUser=null;
	
	
	public static Properties prop = null;
	public static String loginCAURL=null;
	public static String getRoleDomainURL=null;
	
	public static boolean load() {
		try {
			
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			if (classLoader == null) {
			    classLoader = Class.class.getClassLoader();
			}

			InputStream input = classLoader.getResourceAsStream("/afic-config.properties");

			
			
			prop = new Properties();
			prop.load(input);
			
			if (prop.isEmpty()) {
				System.err.println("Error: ops~~ Config file loaded failed ...");
				return false;
			}
			
			domain = prop.getProperty("current-domain");
			System.out.println("Current Domain is:" + prop.getProperty("current-domain"));
			String  tempDbUrl =  prop.getProperty("current-domain") + "-database-URL"; 
			databaseURL= prop.getProperty(tempDbUrl);
			System.out.println("Database URL = " + databaseURL);
			
			String  tempDbpass =  prop.getProperty("current-domain") + "-database-password";			
			databasePassword = prop.getProperty(tempDbpass);
			System.out.println("Database password = " + databasePassword);
			
			String  tempDbUser =  prop.getProperty("current-domain") + "-database-user";
			databaseUser =prop.getProperty(tempDbUser);
			System.out.println("Database user = " + databaseUser);
			
			String  tempDblogin =  prop.getProperty("current-domain") + "-authentication";
			loginCAURL=prop.getProperty(tempDblogin);
			System.out.println("LoginCA URL = " + loginCAURL);
			
			String  tempDbRoleMapping =  prop.getProperty("current-domain") + "-RoleMapping";
			getRoleDomainURL= prop.getProperty(tempDbRoleMapping);
			System.out.println("Get Domain URL = " + getRoleDomainURL);
			
			
			
			

		
			
		} catch (Exception ex) {
			System.err.println("Error: ops~ loading config file."+ex.getMessage());
			ex.printStackTrace();
			return false;
		}

		return true;
	}


}
