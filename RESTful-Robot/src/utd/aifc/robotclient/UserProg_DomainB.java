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
import utd.aifc.credauth.CADBValidator;

/**
 * @author DomainA
 *
 */
public class UserProg_DomainB {

	/**
	 * 
	 */
	public static String applicationDatabaseUrl = null;
	
	public UserProg_DomainB() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		try {
		
			String configfilePath = "config.properties";
			String contributorlist = null;
			
			loadConfigurationFile(configfilePath);
		
			String cred = CADBValidator.loginCA("Person1", "person1", "R_D_Director","DomainB");
			//String cred = CAValidator.loginCA("Person1", "person1", "DomainB");
			System.out.println("Credential from Jovi:: " + cred );
			
			String[] creds = cred.split(":");
		
		

			Service s = new Service();
			
			///////////////////
			// creating robots rb1, rb2, rb3
			///////////////////
			
			long stime1 = System.currentTimeMillis();			
			s.create_robot("rB11", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime1 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime1 +" End Time = " + etime1 +" Total Time= " + (etime1 - stime1) );
			
			long stime2 = System.currentTimeMillis();
			s.create_robot("rB21", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime2 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime2 +" End Time = " + etime2 +" Total Time= " + (etime2 - stime2) );
			
			long stime3 = System.currentTimeMillis();
			s.create_robot("rB31", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime3 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime3 +" End Time = " + etime3 +" Total Time= " + (etime3 - stime3) );
			
			
			
			long stime01 = System.currentTimeMillis();
			s.read_robot("rB11", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime01 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime01 +" End Time = " + etime01 +" Total Time= " + (etime01 - stime01) );
			
			long stime02 = System.currentTimeMillis();
			s.read_robot("rB21", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime02 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime02 +" End Time = " + etime02 +" Total Time= " + (etime02 - stime02) );
			
			long stime03 = System.currentTimeMillis();
			s.read_robot("rB31", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime03 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime03 +" End Time = " + etime03 +" Total Time= " + (etime03 - stime03) );
			
			
			long stime4 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1";
			s.create_robot("rB1001", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime4 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime4 +" End Time = " + etime4 +" Total Time= " + (etime4 - stime4) );
			
						
			long stime5 = System.currentTimeMillis();
			contributorlist = "rC11:DomainC:1";
			s.create_robot("rB0101", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime5 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime5 +" End Time = " + etime5 +" Total Time= " + (etime5 - stime5) );
			
			
			long stime6 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rB21:DomainB:1";
			s.create_robot("rB2001", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime6 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime6 +" End Time = " + etime6 +" Total Time= " + (etime6 - stime6) );
			
			
			long stime7 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rC11:DomainC:1";
			s.create_robot("rB1101", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime7 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime7 +" End Time = " + etime7 +" Total Time= " + (etime7 - stime7) );
			
			
			long stime8 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rC11:DomainC:1:rD11:DomainD:1";
			s.create_robot("rB1111", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime8 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime8 +" End Time = " + etime8 +" Total Time= " + (etime8 - stime8) );
			
			
			long stime9 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rB21:DomainB:1:rC11:DomainC:1";
			s.create_robot("rB2101", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime9 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime9 +" End Time = " + etime9 +" Total Time= " + (etime9 - stime9) );
			
			
			long stime10 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rC11:DomainC:1:rC21:DomainC:1";
			s.create_robot("rB1201", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime10 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime10 +" End Time = " + etime10 +" Total Time= " + (etime10 - stime10) );
			
			
			long stime11 = System.currentTimeMillis();
			contributorlist = "rC11:DomainC:1:rC21:DomainC:1:rC31:DomainC:1";
			s.create_robot("rB0301", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime11 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime11 +" End Time = " + etime11 +" Total Time= " + (etime11 - stime11) );
			
			
			long stime12 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rB21:DomainB:1:rB31:DomainB:1";
			s.create_robot("rB3001", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime12 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime12 +" End Time = " + etime12 +" Total Time= " + (etime12 - stime12) );
			
			long stime13 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rB21:DomainB:1:rC11:DomainC:1:rC21:DomainC:1";
			s.create_robot("rB2201", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime13 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime13 +" End Time = " + etime13 +" Total Time= " + (etime13 - stime13) );
			
			long stime14 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rC11:DomainC:1:rC21:DomainC:1:rD11:DomainD:1";
			s.create_robot("rB1211", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime14 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime14 +" End Time = " + etime14 +" Total Time= " + (etime14 - stime14) );
			
			
			long stime15 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rB21:DomainB:1:rC11:DomainC:1:rC21:DomainC:1:rD11:DomainD:1";
			s.create_robot("rB2211", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime15 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime15 +" End Time = " + etime15 +" Total Time= " + (etime15 - stime15) );
			
			
			long stime16 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rC11:DomainC:1:rC21:DomainC:1:rC31:DomainC:1:rD11:DomainD:1";
			s.create_robot("rB1311", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime16 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime16 +" End Time = " + etime16 +" Total Time= " + (etime16 - stime16) );
			
			long stime17 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rB21:DomainB:1:rC11:DomainC:1:rC21:DomainC:1:rD11:DomainD:1:rD21:DomainD:1";
			s.create_robot("rB2221", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime17 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime17 +" End Time = " + etime17 +" Total Time= " + (etime17 - stime17) );
			
			long stime18 = System.currentTimeMillis();
			contributorlist = "rB11:DomainB:1:rB21:DomainB:1:rB31:DomainB:1:rC11:DomainC:1:rC21:DomainC:1:rC31:DomainC:1";
			s.create_robot("rB3301", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			long etime18 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime18 +" End Time = " + etime18 +" Total Time= " + (etime18 - stime18) );
			
			
			///////////////////
			// 	reading robots robots rb1, rb2, rb3
			///////////////////
			
			int i;
			int NumIterations = 10;
			System.out.println("1.===================================================================");
			for (i=0; i<NumIterations; i++) {
			long stime19 = System.currentTimeMillis();
			s.read_robot("rB1001", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime19 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime19 +" End Time = " + etime19 +" Total Time= " + (etime19 - stime19) +"\n");
			}

			//Thread.sleep(1000);
			System.out.println("2.===================================================================");
			for (i=0; i<NumIterations; i++) {
			long stime20 = System.currentTimeMillis();
			s.read_robot("rB0101", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime20 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime20 +" End Time = " + etime20 +" Total Time= " + (etime20 - stime20) +"\n");
			}			
			
			//Thread.sleep(1000);
			System.out.println("3.===================================================================");
			for (i=0; i<NumIterations; i++) {
			long stime20a = System.currentTimeMillis();
			s.read_robot("rB2001", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime20a = System.currentTimeMillis();
			System.out.println("Start Time = " + stime20a +" End Time = " + etime20a +" Total Time= " + (etime20a - stime20a) + "\n" );
			}
			System.out.println("4.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime20b = System.currentTimeMillis();
			s.read_robot("rB1101", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime20b = System.currentTimeMillis();
			System.out.println("Start Time = " + stime20b +" End Time = " + etime20b +" Total Time= " + (etime20b - stime20b) +"\n");
			}
			System.out.println("5.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {	
			long stime21 = System.currentTimeMillis();
			s.read_robot("rB1111", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime21 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime21 +" End Time = " + etime21 +" Total Time= " + (etime21 - stime21) +"\n");
			}
			System.out.println("6.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime22 = System.currentTimeMillis();
			s.read_robot("rB2101", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime22 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime22 +" End Time = " + etime22 +" Total Time= " + (etime22 - stime22) +"\n");
			}
			System.out.println("7.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime23 = System.currentTimeMillis();
			s.read_robot("rB1201", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime23 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime23 +" End Time = " + etime23 +" Total Time= " + (etime23 - stime23) +"\n");
			}
			System.out.println("8.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime24 = System.currentTimeMillis();
			s.read_robot("rB0301", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime24 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime24 +" End Time = " + etime24 +" Total Time= " + (etime24 - stime24) +"\n");
			}
			System.out.println("9.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime25 = System.currentTimeMillis();
			s.read_robot("rB3001", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime25 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime25 +" End Time = " + etime25 +" Total Time= " + (etime25 - stime25) +"\n");
			}
			System.out.println("10.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime26 = System.currentTimeMillis();
			s.read_robot("rB2201", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime26 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime26 +" End Time = " + etime26 +" Total Time= " + (etime26 - stime26) +"\n");
			}
			System.out.println("11.===================================================================");
		//	Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime27 = System.currentTimeMillis();
			s.read_robot("rB1211", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime27 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime27 +" End Time = " + etime27 +" Total Time= " + (etime27 - stime27) +"\n");
			}
			System.out.println("12.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime28 = System.currentTimeMillis();
			s.read_robot("rB2211", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime28 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime28 +" End Time = " + etime28 +" Total Time= " + (etime28 - stime28) +"\n");
			}
			System.out.println("13.===================================================================");
			//Thread.sleep(1000);
			for (i=0; i<NumIterations; i++) {
			long stime29 = System.currentTimeMillis();
			s.read_robot("rB1311", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime29 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime29 +" End Time = " + etime29 +" Total Time= " + (etime29 - stime29) +"\n");
			}
			System.out.println("14.===================================================================");
			//Thread.sleep(4000);
			for (i=0; i<NumIterations; i++) {
			long stime30 = System.currentTimeMillis();
			s.read_robot("rB2221", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime30 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime30 +" End Time = " + etime30 +" Total Time= " + (etime30 - stime30) +"\n");
			}
			System.out.println("15.===================================================================");
			//Thread.sleep(4000);
			for (i=0; i<NumIterations; i++) {
			long stime31 = System.currentTimeMillis();
			s.read_robot("rB3301", "DomainB", "Person1", creds[2],creds[3], cred);
			long etime31 = System.currentTimeMillis();
			System.out.println("Start Time = " + stime31 +" End Time = " + etime31 +" Total Time= " + (etime31 - stime31) +"\n");
			}
			System.out.println("===================================================================");
			
			
			
			
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			//s.read_robot("Target", "DomainB", "Person1", creds[2],creds[3], cred);
			
			
			//resourcename: resource Domain: ACRN: user name: userdomain: user role: (contributorname: contributordomain: version):
			
			
			//create without contributor
			//String contributorlist = null;
			//s.create_robot("Test100", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			
			//create with contributor in same domain only 1 contributor
			//contributorlist = "Test100:DomainB:1";
			//s.create_robot("Test101", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			
			//create with contributor in same domain only 1 contributor but that 1 contributor has 9 contributors 3 in domainC and 6 in domain D 
			//contributorlist = "Target:DomainB:1";
			//s.create_robot("Test102", "DomainB", "RobotRepository","Person1","DomainB", creds[2],contributorlist,cred);
			
		
		
		} catch (SQLException e) {
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
