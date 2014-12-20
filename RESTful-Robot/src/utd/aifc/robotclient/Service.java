/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.robotclient;

import java.util.ArrayList;
import java.util.List;

import utd.aifc.credauth.CAValidator;
import com.jovi.validator.AccessControl;
import com.jovi.validator.Configuration;
import com.jovi.validator.DBConnector;

/**
 * @author DomainA
 *
 */
public class Service {

	/**
	 * 
	 */
	public Service() {
		// TODO Auto-generated constructor stub
		com.jovi.validator.Configuration.loadfortesting("afic-config.properties");
		DBConnector.getConnectionToDB(Configuration.prop.getProperty("current-domain"));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		/*
		//create_robot (rid, cred, …); //read_robot (rid, cred, …);	//write_robot (rid, cred, …);

    	//	read_robot (rid, cred, …) //{ ok1, role = validate_credential (cred);
		  if (!ok1) return(error); 		  
		  ok2, null = validate_access(serviceid, role,exec,…);
		  if (!ok2) return (error);		  
		  service logic 
		  ok3, contributor = validate_access(rid, role, read, …); 
		  if (!ok3) return (error);
		  service logic
		  return(robot, contributor); 
		}*/		
	}
	
	
	// read robot uses resource name, domain name, user name, role name,... 
	
	public void read_robot(String resourceName, String domainName, String userName,String roleName,String userDomain, String cred)
	{
		try{
			
			com.jovi.validator.RequestRecord req = new com.jovi.validator.RequestRecord();
			req.setAccessResource(resourceName);
			req.setSourceDomain(domainName);
			req.setSourceUserName(userName);
			req.setSourceRole(roleName);
			req.setAccessDomain(userDomain);
			req.setDoAction("READ");

			// external access control
			// validating credential
			String validation = CAValidator.validate_credential(cred,domainName);
					
			
			String[] validationparams = validation.split(":");
			
			if(validationparams[0].equals("true"))
			{
				//System.out.println("Validation Successful");
				
				//Configuration.loadfortesting("afic-config.properties");
				boolean result = AccessControl.validateRequest(req);
				
				if(result)
				{
					//System.out.println("READ Access can be given. Result is True");
				}
					

			}
			else{
				return;
			}
			
					}catch(Exception ex)
		{
			System.out.println("Error Message:: " + ex.getMessage());
		}
	}
	
	
	// Create robot using Resource name, Domain Name, Role Name, ....
	
	
	public void create_robot(String resourceName, String domainName,String ACRN, String userName,String userDomain,String roleName,String contributors, String cred)
	{
		try{
			
			List<String> allContributor = new ArrayList<String>();
			List<String> allContributorDomain = new ArrayList<String>();
			List<Integer> allContributorVer = new ArrayList<Integer>();
			
			if(!(contributors==null)){
			
			//System.out.print("Inside nu");
			String[] conts = contributors.split(":");
			int i = 0;
			while(i< conts.length){
				allContributor.add(conts[i]);
				allContributorDomain.add(conts[i+1]);
				allContributorVer.add(Integer.parseInt(conts[i+2]));
				i = i+3;
			}
			
			
			}
			com.jovi.validator.RequestRecord req = new com.jovi.validator.RequestRecord();
			req.setAccessResource(resourceName);
			req.setSourceDomain(domainName);
			req.setResourceACRN(ACRN);
			req.setSourceUserName(userName);
			req.setSourceRole(roleName);
			req.setAccessDomain(userDomain);
			
			req.setContributor(allContributor);
			req.setContributorDomain(allContributorDomain);
			req.setContributorVer(allContributorVer);
			
			req.setDoAction("CREATE");
			
			
			// validating credential
			String validation = CAValidator.validate_credential(cred, domainName);
			
			
			//System.out.println("Result of create Validate Credential:: " + validation);
			
			String[] validationparams = validation.split(":");
			
			if(validationparams[0].equals("true"))
			{
				//System.out.println("Validation Successful");
				
				Configuration.loadfortesting("afic-config.properties");
				boolean result = AccessControl.validateRequest(req);
				
				if(result)
				{
			//		System.out.println("create Access can be given. Result is True");
				}
				
			}
			else{
				return;
			}
			
					}catch(Exception ex)
		{
			System.out.println("Error Message:: " + ex.getMessage());
		}
	}
	
	
	
	}