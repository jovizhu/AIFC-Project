/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.FilePolicyModule;


public class Configuration {

	public static PDP pdp = null;
	public static Properties prop = null;
	public static int debugLevel = 0;
	public static ArrayList <String> domainList = new ArrayList<String> ();  

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
			
			
			String level = Configuration.prop.getProperty("debug-level");
			if(level != null)
				debugLevel= Integer.parseInt(level);
			
			
			String policyPath = Configuration.prop.getProperty("policy-path");
			String policyFileList = Configuration.prop.getProperty("policy-files");
			
			
			//File path = new File(policyPath);
			
			//System.out.println("Path is "+path.getPath());
			String[] policyFiles = policyFileList.split(";");

			FilePolicyModule filePolicyModule = new FilePolicyModule();
			for (int i = 0; i < policyFiles.length; i++)
				{
					URL policyInput = classLoader.getResource(policyPath + "/" + policyFiles[i]);

					Logger.printLog("Policy FIles is : "+policyPath + "/" + policyFiles[i], 2);
					Logger.printLog(policyInput.getFile(), 2);

					filePolicyModule.addPolicy(policyInput.getFile());
				}

			// Step 2: Setup the PolicyFinder that this PDP will use
			PolicyFinder policyFinder = new PolicyFinder();
			Set<FilePolicyModule> policyModules = new HashSet<FilePolicyModule>();
			policyModules.add(filePolicyModule);
			policyFinder.setModules(policyModules);

			// create the PDP
			pdp = new PDP(new PDPConfig(null, policyFinder, null));
				
			
			Logger.printLog(" PDP created Finished", 1);
			
			
			String domainString = Configuration.prop.getProperty("domain-list");
			
			String[] domains = domainString.split(";");

		
			for (int i = 0; i < domains.length; i++)
				{
				    domainList.add(domains[i]);
					Logger.printLog(" Domain Loaded "+domains[i], 1);
				}
			
		} catch (Exception ex) {
			System.err.println("Error: ops~ loading config file."+ex.getMessage());
			ex.printStackTrace();
			return false;
		}

		Logger.printLog("****************************************\n"
				+ "Config file loaded successfully...\n"
				+ "****************************************", 1);
		return true;
	}

	
	public static boolean loadfortesting(String configFile) {

		try {

			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();

			if (classLoader == null) {
				classLoader = Class.class.getClassLoader();
			}

			// InputStream input =
			// classLoader.getResourceAsStream("afic-config.properties");

			File file = new File(configFile);
			FileInputStream input = new FileInputStream(file);

			prop = new Properties();
			prop.load(input);

			if (prop.isEmpty()) {
				System.err.println("Error: ops~~ Config file loaded failed ...");
				return false;
			}

			
			String level = Configuration.prop.getProperty("debug-level");
			if(level != null)
				debugLevel= Integer.parseInt(level);
			
			String policyPath = Configuration.prop.getProperty("policy-path");

			File path = new File(policyPath);

			//System.out.println("Path is " + path.getPath());
			String[] policyFiles = path.list();

			FilePolicyModule filePolicyModule = new FilePolicyModule();
			for (int i = 0; i < policyFiles.length; i++)
				filePolicyModule.addPolicy(policyPath + "//" + policyFiles[i]);

			// Step 2: Setup the PolicyFinder that this PDP will use
			PolicyFinder policyFinder = new PolicyFinder();
			Set<FilePolicyModule> policyModules = new HashSet<FilePolicyModule>();
			policyModules.add(filePolicyModule);
			policyFinder.setModules(policyModules);

			// create the PDP
			pdp = new PDP(new PDPConfig(null, policyFinder, null));
			
			String domainString = Configuration.prop.getProperty("domain-list");
			
			String[] domains = domainString.split(";");

		
			for (int i = 0; i < domains.length; i++)
				{
				    domainList.add(domains[i]);
					Logger.printLog(" Domain Loaded "+domains[i], 1);
				}

		} catch (Exception ex) {
			System.err.println("Error loading config file." + ex.getMessage());
			ex.printStackTrace();
			return false;
		}

		Logger.printLog("****************************************\n"
				+ "Config file loaded successfully...\n"
				+ "******************************", 1);
		return true;

	}
	
	public static void main(String [] args) throws FileNotFoundException, MalformedURLException
	{
		//Configuration.load(new URL("./config.properties"));
	}
}
